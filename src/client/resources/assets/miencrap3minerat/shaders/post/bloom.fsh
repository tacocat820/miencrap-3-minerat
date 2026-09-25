#version 330

uniform sampler2D InSampler;

in vec2 texCoord;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

layout(std140) uniform InvertConfig {
    float InverseAmount;
};

out vec4 fragColor;

void main(){
    vec2 sizeRatio = OutSize / InSize;

    vec4 diffuseColor = texture(InSampler, texCoord);

    for (float i = -0.05; i <= 0.05; i += 0.01) {
        vec4 v = texture(InSampler, texCoord - vec2(i, i));
        float avg = (v.x + v.y + v.z) / 3.0;
        diffuseColor += avg * 0.1;
    }

    vec4 avg = diffuseColor; 

    fragColor = avg;
}
/*


void main(){
    vec2 sizeRatio = OutSize / InSize;

    vec4 diffuseColor = texture(InSampler, texCoord);

    for (float i = -0.05; i <= 0.05; i += 0.01) {
        vec4 v = texture(InSampler, texCoord - vec2(i, i));
        //float avg = (v.x + v.y + v.z) / 3.0;
        float m = max(v.x, max(v.y, v.z));
        diffuseColor += (max(m, 0.3) - 0.3) * 0.1;
    }

    vec4 avg = diffuseColor; 

    fragColor = avg;
}

*/
