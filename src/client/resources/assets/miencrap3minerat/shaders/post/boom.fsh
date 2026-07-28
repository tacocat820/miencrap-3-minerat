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

    float avg = (diffuseColor.x + diffuseColor.y + diffuseColor.z) / 3.0; 

    vec4 outColor;
    if (avg > 0.4) {
        outColor = vec4(1.0, 1.0, 1.0, 0.0);
    } else {
        outColor = vec4(0.0, 0.0, 0.0, 0.0);
    }
    fragColor = vec4(outColor.rgb, 1.0);
}
