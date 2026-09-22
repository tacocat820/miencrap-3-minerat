#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec3 Normal;

out float sphericalVertexDistance;
out float cylindricalVertexDistance;
out vec4 vertexColor;
out vec3 Pos;
out vec3 Norm;
out vec4 lightColor;

flat out int m3mType;

uniform float GameTime;
uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;


void main() {

    sphericalVertexDistance = fog_spherical_distance(Position);
    cylindricalVertexDistance = fog_cylindrical_distance(Position);
    vertexColor = Color;

    m3mType = int(Color.g * 256);

    vec3 CustomPosOffset = vec3(0,0,0);
    if (m3mType == 2 || m3mType == 4) { // wave block
        // CustomPosOffset = vec3( sin(Position.x), sin(Position.y), sin(Position.z) );
        CustomPosOffset = vec3(0.0, sin(Position.x + Position.z + vertexColor.r*3.145*2) * 0.5 - 0.5, 0.0);
        // 

    }
    else if (m3mType == 3) { // wtf block
        CustomPosOffset = vec3(cos(Position.z) * 0.3, sin(Position.y) * 0.3 , cos(Position.x) * 0.3);
    }

    gl_Position = ProjMat * ModelViewMat * vec4(Position + CustomPosOffset, 1.0);

    Pos = Position;
    Norm = Normal;
}
