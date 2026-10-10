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
    vec2 screenPos = gl_FragCoord.xy / vec2(W, H);
    screenPos.y = 1.0 - screenPos.y;

	fragColor = texture(Sampler0, screenPos);
    //vec4 color = vec4(screenUV.x, 0.0, 0.0, 0.5);
    //fragColor = color * ColorModulator;
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}