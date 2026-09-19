#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 texProj0;
in vec2 texCoord;
in vec2 texCoord2;
in vec3 Pos;

out vec4 fragColor;

void main() {

    // float GameTime = vertexColor.r;
    // float seed = GameTime + sphericalVertexDistance;

    float pos2 = mod((Pos.x+Pos.z+Pos.y)*0.5, 7);

    vec4 color;

    if ( pos2 > 4 ) {
        color = vec4(vec3(1.0), 0.5);
    }
    else {
        color = vec4(0,0,0,0);
        discard;
    }

    // color = vec4(mod(Pos.x, 1.0),mod(Pos.y, 1.0),mod(Pos.z, 1.0), 0.5);

    fragColor = color;
    
}
