#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
layout(std140) uniform Whatever {
    float W, H;
    float GT;
};

in vec2 texCoord0;
out vec4 fragColor;

void main() {
    vec2 screenPos = gl_FragCoord.xy / H;

    float power = (0.5 + sin(screenPos.x * 100.0 + screenPos.y * 100.0 + GT * 31.4) * 0.5);

    vec3 piss = vec3(0.95, 0.87, 0.34);
    vec3 piss_p = min(power * 9.0, 1.0) * piss;


	fragColor = vec4(piss_p, 1.0);
    //vec4 color = vec4(screenUV.x, 0.0, 0.0, 0.5);
    //fragColor = color * ColorModulator;
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}