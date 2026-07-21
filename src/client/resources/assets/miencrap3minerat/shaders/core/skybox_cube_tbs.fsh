#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
layout(std140) uniform Whatever {
    float W, H;
    float CX, CY;
    float GT;
};

// shamlessly stolen from https://github.com/HackerPoet/FractalSoundExplorer/blob/main/frag.glsl
struct ColorStop {
	float position;
	vec4 color;
}; 
ColorStop gradient[5] = ColorStop[5](
	ColorStop(0.0, vec4(0.0 / 255.0, 7.0 / 255.0, 100.0 / 255.0, 1.0)),
	ColorStop(0.16, vec4(32.0 / 255.0, 107.0 / 255.0, 203.0 / 255.0, 1.0)),
	ColorStop(0.42, vec4(237.0 / 255.0, 255.0 / 255.0, 255.0 / 255.0, 1.0)),
	ColorStop(0.6425, vec4(255.0 / 255.0, 170.0 / 255.0, 0.0 / 255.0, 1.0)),
	ColorStop(0.8575, vec4(0.0 / 255.0, 0.0 / 255.0, 0.0 / 255.0, 1.0))
);

vec4 sampleGradient(float position) {
	int maxIndex = gradient.length() - 1;
	for (int i = 0; i < maxIndex; i++) {
		if (position >= gradient[i].position && position <= gradient[i + 1].position) {
			float t = (position - gradient[i].position) / (gradient[i + 1].position - gradient[i].position);
			return mix(gradient[i].color, gradient[i + 1].color, t);
		}
	}
	return gradient[maxIndex].color;
}

vec2 complexPow(vec2 num, vec2 exponent) {
	float r = length(num);

	if (r == 0.0) return vec2(0.0, 0.0);

	float theta = atan(num.y, num.x);
	float logR = log(r);

	float newR = pow(r, exponent.x) * exp(-exponent.y * theta);
	float newTheta = exponent.x * theta + exponent.y * logR;

	return vec2(newR * cos(newTheta), newR * sin(newTheta));
}

in vec2 texCoord0;
out vec4 fragColor;

void main() {
    vec2 screenPos = gl_FragCoord.xy / vec2(W, H);

    vec2 z = vec2(0.0, 0.0); // vec2 z = vec2(0.0, screenPos.x);
    vec2 e = vec2(2.0 + (GT - 1.0) * 0.5, 0.0); // 
    vec2 c = (gl_FragCoord.xy * vec2(2.0, 2.0) + vec2((CY - 300.0) * 30.0, -1500.0 - CX * 30.0)) / vec2(W, H);

    int it = 0;
    while (dot(z, z) < 4.0 && it < 100) {
        float xtemp = z.x * z.x - z.y * z.y + c.x;
        z.y = abs(2.0 * z.x * z.y) + c.y;
        z.x = xtemp;
        ++it;
    }

    if (it == 100) {
        fragColor = vec4(0.0, 0.0, 0.0, 0.0);
    } else {
        float t = float(it) / 100.0;
		vec4 grad = sampleGradient(t);
        fragColor = vec4(grad.x, grad.z, grad.y, grad.w);
    }

    //vec4 color = vec4(screenUV.x, 0.0, 0.0, 0.5);
    //fragColor = color * ColorModulator;
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}