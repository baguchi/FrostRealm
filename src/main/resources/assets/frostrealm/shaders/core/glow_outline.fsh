#version 150

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 sampleStep;

out vec4 fragColor;


float circleMask(vec2 uv, vec2 pos, float r, float edge) {
  return 1. - smoothstep(r - edge * .5, r + edge * .5, length(uv - pos));
}

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < vertexColor.a) {
        discard;
    }
    float gray = length(color.rgb);
    if(step(1, fwidth(gray)) > 0){
        fragColor = vec4(1, 0.45, 0.05, color.a);
    }else{
      fragColor = color;
    }

}
