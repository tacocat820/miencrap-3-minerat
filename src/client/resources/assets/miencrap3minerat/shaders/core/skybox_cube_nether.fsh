#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
layout(std140) uniform Whatever {
    float W, H;
    float CRX, CRY, CRZ;
    float CX, CY, CZ;
};

in vec2 texCoord0;
out vec4 fragColor;

void main() {


    vec2 screenPos = gl_FragCoord.xy / vec2(W, W);
    vec2 uv = screenPos * 2.0 - 1.0;
    
    vec3 forward = normalize(vec3(CRX, CRY + 0.35, CRZ));
    vec3 right = normalize(cross(forward, vec3(0.0, 1.0, 0.0)));
    vec3 up = cross(right, forward);

    vec3 rd = normalize(uv.x * right + uv.y * up + forward);

    float h = 50.0;
    float pointX = (h / rd.y) * rd.x + CX;
    float pointZ = (h / rd.y) * rd.z + CZ; 

    float top = abs(rd.y) / rd.y;

	fragColor = top * texture(Sampler0, vec2(mod(pointX / 50.0, 1.0), mod(pointZ / 50.0, 1.0)));

    //fragColor = vec4(vec3(sin(pointX), 0.0, sin(pointZ)), 0.5); cool looking sky


}