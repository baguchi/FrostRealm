#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;
uniform mat4 ModelViewMat;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;
in vec3 position;

out vec4 fragColor;

void main() {

        // 1 second 1200 tick
        float speed = 400;
        float time = cos(GameTime * speed) * 0.75;

   vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
        //float distFromCenter = distance(position, normal);
        vec4 color = texture(Sampler0, texCoord0);
        color.a *= time;
        color.a += 0.5 * (1.0 - time);
        if (color.a < 0.1) {
            discard;
        }
        color *= vertexColor * ColorModulator;
        color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);


        color *= lightMapColor;
        fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
