#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
layout(std140) uniform Whatever {
    float W, H;
    float CRX, CRY, FOV;
    float CX, CY, CZ;
};

in vec2 texCoord0;
out vec4 fragColor;

void main() {


    vec2 screenPos = gl_FragCoord.xy / vec2(W, H);
    vec2 uv = screenPos * 2.0 - 1.0;
    
    float x = radians(CRX);
    float y = radians(CRY);
    vec3 forward = vec3(cos(x) * sin(y), -sin(x), cos(x) * cos(y));
    vec3 right   = vec3(cos(y), 0.0, -sin(y));
    vec3 up      = cross(forward, right);

    float ar = W / H;
    float fov = tan(radians(FOV) * 0.5);

    vec3 rd = forward + (right * uv.x * ar * fov) + (up * uv.y * fov);
    rd.x = -rd.x;
    rd.y = -rd.y;

    float h = 0.0 + CY + 44.0;
    float pointX = (h / rd.y) * rd.x + CX;
    float pointZ = (h / rd.y) * rd.z + CZ; 

    float top = abs(rd.y) / (rd.y);

    if (top < 0.0) fragColor = vec4(1.0, 1.0, 1.0, 1.0);
	else fragColor = texture(Sampler0, vec2(mod(pointX / 50.0, 1.0), mod(pointZ / 50.0, 1.0)));

    //fragColor = vec4(vec3(sin(pointX), 0.0, sin(pointZ)), 0.5); cool looking sky


}