#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 texProj0;

out vec4 fragColor;


float smin(float a, float b, float k) {
    float h = clamp(0.5 + 0.5*(a-b)/k, 0.0, 1.0);
    return mix(a, b, h) - k*h*(1.0-h);
}
float sphere(in vec3 p, in vec3 c, float r) {
    return length(p - c) - r;
}
float sun(in vec3 p, in vec3 c, float r, float GT) {
    float offset = sin(5.0 * p.x + GT) * sin(5.0 * p.y + GT) * sin(5.0 * p.z + GT) * 0.25;
    return length(p - c) - r + offset;
}

vec2 rot(vec2 what, float angle) {
    float s = sin(angle); 
    float c = cos(angle); 
    return vec2(s, c) + what;
}

float boxx(in vec3 p, in vec3 b, float GT) {
    vec2 r = rot(p.xz, p.y + GT);
    vec3 q = abs(vec3(r.x, p.y, p.z)) - b;
    return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}

float box(in vec3 p, in vec3 b) {
    vec3 q = abs(p) - b;
    return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}

float floating_balls(in vec3 p, float GT) {
    float ball_1 = sphere(p, vec3(sin(3.0 * GT) * 5.0, 0.0, cos(3.0 * GT) * 5.0), 2.0);
    float ball_2 = sphere(p, vec3(cos(2.0 * GT) * 4.0, 0.0, sin(2.0 * GT) * 4.0), 2.0);
    return smin(ball_1, ball_2, 6.3);
}

float map_the_world(in vec3 p, float GT) {

    //float sun = floating_balls(p - vec3(0.0, 0.0, 10.0), GT);

    float b1 = boxx(p - vec3(0.0 + sin(GT / 2.0) * 10.0, 0.0, 15.0 - cos(GT / 2.0)), vec3(2.0, 20.0, 2.0), GT);
    float b2 = boxx(p - vec3(0.0 - sin(GT / 2.0) * 10.0, 0.0, 15.0 + cos(GT / 2.0)), vec3(2.0, 20.0, 2.0), GT);

    return smin(b1, b2, 5.0);
}

vec3 get_normal(vec3 position, float GT) {
	float distance = map_the_world(position, GT);
	vec2 epsilon = vec2(.01,0);
	vec3 n = distance - vec3(
		map_the_world(position-epsilon.xyy, GT), //X Component
		map_the_world(position-epsilon.yxy, GT), //Y Component
		map_the_world(position-epsilon.yyx, GT)  //Z Component
	);

	return normalize(n);
} 

vec3 raymarching(in vec3 ro, in vec3 rd, float GT) {
    float travelled = 0.0;
    const int STEPS = 32;
    const float MIN_HIT_DISTANCE = 0.001;
    const float MAX_TRACE_DISTANCE = 30.0;
    const float FOG_DISTANCE = 25.0;
    const vec3 SKY = vec3(0.2, 0.2, 0.7);

    for (int i = 0; i < STEPS; ++i) {
        vec3 pos = ro + travelled * rd;
        float distance_to_closest = map_the_world(pos, GT);

        if (distance_to_closest < MIN_HIT_DISTANCE) {
            vec3 normal = get_normal(pos, GT);
            vec3 light_position = vec3(0.0, 0.0, 0.0);
            vec3 direction_to_light = normalize(light_position - pos);

            float diffuse_intensity = max(0.2, dot(normal, direction_to_light));

            return mix(vec3(1.0, 1.0, 1.0) * diffuse_intensity * (1.0 - float(i) / float(STEPS)), SKY, min(1.0, travelled / FOG_DISTANCE));
        }
        if (travelled > MAX_TRACE_DISTANCE) break;
        travelled += distance_to_closest;
    }

    return SKY;
}

void main() {

    float x = vertexColor.b;
    float y = vertexColor.g;
    vec2 uv = vec2(x, y) * 2.0 - 1.0;

    vec3 cam = vec3(0.0, 0.0, 0.0);

    vec3 rd = vec3(uv.x, uv.y, 1.0);
    
    float GameTime = vertexColor.r * 3.14 * 10.0;

    vec3 color = raymarching(cam, rd, GameTime);

    fragColor = vec4(color, 1.0)
         * ColorModulator;
    
}
