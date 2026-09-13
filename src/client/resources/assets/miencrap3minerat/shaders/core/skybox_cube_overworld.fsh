#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
layout(std140) uniform Whatever {
    float GameTime;
};

in vec2 texCoord0;
out vec4 fragColor;

void main() {

    vec4 color0 = texture(Sampler0, vec2(texCoord0.x, 1.0 - texCoord0.y));
    vec4 color1 = texture(Sampler1, vec2(texCoord0.x, 1.0 - texCoord0.y));
    fragColor = mix(color0, color1, GameTime);
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}