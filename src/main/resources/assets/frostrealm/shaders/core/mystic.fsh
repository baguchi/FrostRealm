#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 sampleStep;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a == 0) {
        discard;
    }
    vec4 blurred = vec4(0.0);
    float radius = 2.0;
    for (float a = -radius + 0.5; a <= radius; a += 2.0) {
     blurred += texture(Sampler0, texCoord0 + sampleStep * a);
    }
    blurred += texture(Sampler0, texCoord0 + sampleStep * radius) / 2.0;
    if (blurred.a <= 0.5) {
            discard;
    }
    fragColor = vec4((blurred / (radius + 0.5)).rgb * ColorModulator.rgb * vertexColor.rgb, blurred.a);
}
