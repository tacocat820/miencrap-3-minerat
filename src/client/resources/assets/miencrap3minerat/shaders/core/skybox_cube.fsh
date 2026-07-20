#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
uniform float GameTime;

in vec2 texCoord0;
out vec4 fragColor;

void main() {

    vec2 bleh = vec2(texCoord0.x + sin((texCoord0.y + GameTime) / 0.3) * 3, texCoord0.y);

    vec4 color = texture(Sampler0, texCoord0);
    fragColor = color * ColorModulator;
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}