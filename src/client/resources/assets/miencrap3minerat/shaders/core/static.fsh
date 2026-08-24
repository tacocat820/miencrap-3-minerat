#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 texProj0;

out vec4 fragColor;

void main() {

    float GameTime = vertexColor.r;
    float seed = GameTime + sphericalVertexDistance;
    fragColor = vec4( vec3(fract(543.2543 * sin(dot(vec2(seed, seed), vec2(3525.46, -54.3415 ))))), 1.0 )
         * ColorModulator;
    
}
