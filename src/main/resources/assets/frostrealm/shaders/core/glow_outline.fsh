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
    float gray = length(color.rgb);
    if(step(0.5, fwidth(gray)) > 0.5){
        fragColor = vec4(color.rgb * 1.5, color.a);
        discard;
    }

    fragColor = color;
}
