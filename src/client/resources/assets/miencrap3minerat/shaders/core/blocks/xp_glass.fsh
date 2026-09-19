#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 texProj0;
in vec2 texCoord;
in vec2 texCoord2;

flat in int m3mType;
in vec3 Pos;

out vec4 fragColor;

void main() {

    // float GameTime = vertexColor.r;
    // float seed = GameTime + sphericalVertexDistance;


    vec4 color = vec4(0);

    if (m3mType == 0) {
        float pos2 = mod((Pos.x+Pos.z+Pos.y)*0.5, 7);
        if ( pos2 > 4 ) {
            color = vec4(vec3(1.0), (1-abs(pos2-5))*1.5);
        }
        else {
            color = vec4(0,0,0,0);
            discard;
        }
    }
    else if (m3mType == 1) {
        color = vec4(mod(Pos.x, 1.0),mod(Pos.y, 1.0),mod(Pos.z, 1.0), 0.5);
    }
    else if (m3mType == 2) {
        color = vec4(
            vec3(0.2,0.2,0.9) + vec3( abs( sin(Pos.x + Pos.y + vertexColor.r * 3.145*2 - 0.2) ) - 0.5 ),
            0.5
        );
    }
    else if (m3mType == 3) {
        color = vec4(
            vec3(1.0-mod(Pos.y, 1.0),1.0-mod(Pos.x, 1.0),1.0-mod(Pos.z, 1.0)),
            1.0
        );
    }
    else {
        discard;
    }

    fragColor = color;
    
}
