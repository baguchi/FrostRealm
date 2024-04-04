#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;

in vec2 texCoord0;

out vec4 fragColor;


void main() {
    float distFromCenter = distance(texCoord0, vec2(0.5, 0.5));
    vec4 color = texture(Sampler0, texCoord0) * ColorModulator;
    color.a -= distFromCenter * 1.5;
    color.a *= 2;
    if (color.a == 0.0) {
        discard;
    }
    fragColor = color;
}