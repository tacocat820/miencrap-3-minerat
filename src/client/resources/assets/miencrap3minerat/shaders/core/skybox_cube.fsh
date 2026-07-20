#version 150

in vec4 vertexColor;
out vec4 fragColor;

void main() {
    fragColor = vec4(1.0, 1.0, 0.0, 0.5);
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}