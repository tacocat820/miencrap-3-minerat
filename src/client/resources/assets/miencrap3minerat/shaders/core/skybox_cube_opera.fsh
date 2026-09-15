#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
layout(std140) uniform Whatever {
    float W, H;
    float CRX, CRY, CRZ;
    float CX, CY, CZ;
    float GT;
};

in vec2 texCoord0;
out vec4 fragColor;

// ray marching
float sphere(in vec3 p, in vec3 c, float r) {
    return length(p - c) - r;
}
float spheres(in vec3 p, in vec3 c, float r) {

    vec3 pp = p;

    pp.z = mod(pp.z, 3.0) - 1.5;
    pp.x = mod(pp.x, 3.0) - 1.5;

    return length(pp - c) - r;
}

float box(vec3 p, vec3 b) {
  vec3 q = abs(p) - b;
  return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}

float sun(in vec3 p, in vec3 c, float r) {
    float offset = sin(5.0 * p.x + GT) * sin(5.0 * p.y + GT) * sin(5.0 * p.z + GT) * 0.25;
    return length(p - c) - r + offset;
}

float pyramid( vec3 p, float h ) {
  float m2 = h*h + 0.25;
    
  p.xz = abs(p.xz);
  p.xz = (p.z>p.x) ? p.zx : p.xz;
  p.xz -= 0.5;

  vec3 q = vec3( p.z, h*p.y - 0.5*p.x, h*p.x + 0.5*p.y);
  float s = max(-q.x,0.0);
  float t = clamp( (q.y-0.5*p.z)/(m2+0.25), 0.0, 1.0 );
  float a = m2*(q.x+s)*(q.x+s) + q.y*q.y;
  float b = m2*(q.x+0.5*t)*(q.x+0.5*t) + (q.y-m2*t)*(q.y-m2*t);
    
  float d2 = min(q.y,-q.x*m2-q.y*0.5) > 0.0 ? 0.0 : min(a,b);
  return sqrt( (d2+q.z*q.z)/m2 ) * sign(max(q.z,-p.y));
}

float menger(vec3 p) {
    float d = box(p, vec3(1.0));

    float s = 1.0;
    for (int m = 0; m < 4; m++) {
        vec3 a = mod(p * s, 2.0) - 1.0;
        s *= 3.0;
        vec3 r = abs(1.0 - 3.0 * abs(a));

        float da = max(r.x, r.y);
        float db = max(r.y, r.z);
        float dc = max(r.z, r.x);
        float c = (min(da, min(db, dc)) - 1.0) / s;

        d = max(d, c);
    }

    return d;
}

float plane(vec3 p, float height) {
    return p.y - height;
}
float antiplane(vec3 p, float height) {
    return height - p.y;
}
float wavy_plane(vec3 p) {
    float offset = (sin(p.x * 0.5)
        + sin(p.z * 0.3)
        + sin(p.x * 0.14) * 1.54
        + sin(p.z * 0.63)
        + sin(p.z * 0.3))
        * 0.1;
    return p.y - 0.5 + offset;
}

float map_the_world(in vec3 p) {

    if (p.y < 5.0) {

        if (abs(p.x) < 6.0 && abs(p.z) < 6.0) {

            float box = box(p - vec3(-3.0, 0.0, 0.0), vec3(1.0,1.0,1.0));
            float ball = sphere(p, vec3(-3.0, 0.0, 0.0), 2.0);
            float pyr = pyramid(p - vec3(0.0, 0.5, 0.0), 2.0);
            float pyr2 = pyramid(p - vec3(-5.5, 0.5, 0.0), 2.0);

            float plane = max(wavy_plane(p),-ball);

            return min(min(min(min(plane, box), antiplane(p, 6.0)), pyr), pyr2);

        } else {

            return min(min(wavy_plane(p), antiplane(p, 6.0)), box(p, vec3(5.9, 5.9, 5.9)));

        }
    } else {
        //float tetrahedrom = mandelbulb(p - vec3(-3.0, 0.0, 0.0));
        float balls = spheres(p, vec3(0.0, 10.0, 0.0), 0.5);
        
        float sun = sun(p, vec3(0.0, 10.0, 0.0), 3.0);
        
        return min(min(balls, sun), plane(p, 4.0));

    }
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
    const float MAX_TRACE_DISTANCE = 30.0;
    const float FOG_DISTANCE = 25.0;
    const vec3 SKY = vec3(0.2, 0.2, 0.7);

    for (int i = 0; i < STEPS; ++i) {
        vec3 pos = ro + travelled * rd;
        float distance_to_closest = map_the_world(pos);

        if (distance_to_closest < MIN_HIT_DISTANCE) {
            vec3 normal = get_normal(pos);
            vec3 light_position = vec3(0.0, 3.0, -7.0);
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


    vec2 screenPos = gl_FragCoord.xy / vec2(max(W,H), max(W,H));
    vec2 uv = screenPos * 2.0 - 1.0;

    vec3 cam = vec3(CX, CY + 45.5, CZ);

    float fov = 1.0;
    
    vec3 forward = normalize(vec3(CRX, CRY + 0.35, CRZ));
    vec3 right = normalize(cross(forward, vec3(0.0, 1.0, 0.0)));
    vec3 up = cross(right, forward);

    vec3 rd = normalize(uv.x * right + uv.y * up + forward);

    vec3 color = raymarching(cam, rd);

    fragColor = vec4(color, 0.5);
    //fragColor = vec4(sin(fract(GameTime * 100) * 12) * (0.5 + cos(1 * 20 + sin(fract(GameTime * 200) * 12) * 4)), sin(fract(GameTime * 200) * 3), 1.0 - sin(fract(GameTime * 200) * 3), 5.0);
}