#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 sampleStep;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < vertexColor.a) {
        discard;
    }
    float gray = step(0.01, fwidth(length(color.rgb)));
    vec3 color2 = vec3(1.0, 1.0, 1.0);
    fragColor = vec4((color.rgb * (1.0 - gray)) + (color2 * gray), color.a);
}
