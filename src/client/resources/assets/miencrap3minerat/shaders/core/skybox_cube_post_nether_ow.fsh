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

float smin(float a, float b, float k) {
    float h = clamp(0.5 + 0.5*(a-b)/k, 0.0, 1.0);
    return mix(a, b, h) - k*h*(1.0-h);
}

// ray marching
float sphere(in vec3 p, in vec3 c, float r) {
    return length(p - c) - r;
}
float flatThing(in vec3 p, in vec3 c, float r) {
    vec3 pc = p - c;
    return max(max(length(pc) - r, p.y - c.y - 0.3), -p.y + c.y + 0.3);
}

float map_the_world(in vec3 p) {

    return min(sphere(p, vec3(0.0, 200.0, 0.0), 20.0), flatThing(p, vec3(0.0, 200.0, 0.0), 38.0));

}
float map_the_flat_thing(in vec3 p) {

    return flatThing(p, vec3(0.0, 200.0, 0.0), 38.0);

}
float map_the_sphere(in vec3 p) {

    return sphere(p, vec3(0.0, 200.0, 0.0), 21.0);

}
vec3 get_normal(vec3 position) {
	float distance = map_the_world(position);
	vec2 epsilon = vec2(.01,0);
	vec3 n = distance - vec3(
		map_the_world(position-epsilon.xyy), //X Component
		map_the_world(position-epsilon.yxy), //Y Component
		map_the_world(position-epsilon.yyx)  //Z Component
	);

	return normalize(n);
} 

vec3 raymarching(in vec3 ro, in vec3 rd) {
    float travelled = 0.0;
    const int STEPS = 100;
    const float MIN_HIT_DISTANCE = 0.001;
    const float MAX_TRACE_DISTANCE = 200.0;
    const float FOG_DISTANCE = 190.0;
    const vec3 SKY = vec3(0.0, 0.0, 0.0);

    float minDist = 1000.0;

    vec3 pos = ro;
    for (int i = 0; i < STEPS; ++i) {

        float distance_to_closest = map_the_world(pos);
        pos += rd * distance_to_closest;
        //vec3 gravity = vec3(0.0, 200.0, 0.0);
        //ro += (gravity - pos) / distance_to_closest / 15.0;



        vec3 gdir = normalize(vec3(0.0, 200.0, 0.0) - pos);
        float dist = 0.03 / distance(pos, vec3(0.0, 200.0, 0.0));
        ro = mix(rd, gdir, dist);
        
        if (distance_to_closest < MIN_HIT_DISTANCE) {

            return mix(vec3(1.0, 1.0, 1.0) / map_the_flat_thing(pos) + map_the_sphere(pos) * 1000.0, vec3(1.0, 1.0, 1.0) / minDist, min(1.0, travelled / FOG_DISTANCE));
        }
        if (travelled > MAX_TRACE_DISTANCE) break;
        travelled += distance_to_closest;
        minDist = min(minDist, distance_to_closest * 0.8);
    }

    return vec3(1.0, 1.0, 1.0) / minDist;

}


void main() {


    vec2 screenPos = gl_FragCoord.xy / vec2(W, H);
    vec2 uv = screenPos * 2.0 - 1.0;


    vec3 cam = vec3(CX, CY, CZ);
    
    float x = radians(CRX);
    float y = radians(CRY);
    vec3 forward = vec3(cos(x) * sin(y), -sin(x), cos(x) * cos(y));
    vec3 right   = vec3(cos(y), 0.0, -sin(y));
    vec3 up      = cross(forward, right);

    float ar = W / H;
    float fov = tan(radians(FOV) * 0.5);

    vec3 rd = forward + (right * uv.x * ar * fov) + (up * uv.y * fov);
    rd.x = -rd.x;

    vec3 color = raymarching(cam, rd);

    fragColor = vec4(color, 0.5);
    //fragColor = vec4(vec3(sin(pointX), 0.0, sin(pointZ)), 0.5); cool looking sky


}