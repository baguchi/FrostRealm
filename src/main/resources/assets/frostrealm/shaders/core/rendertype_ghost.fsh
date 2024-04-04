#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float GameTime;

in vec2 texCoord0;

out vec4 fragColor;

void main() {

   // 1 second 1200 tick
   float speed = 400;
   float time = cos(GameTime * speed) * 0.5;


   //float distFromCenter = distance(texCoord0, vec2(0.5, 0.5));
   vec4 color = texture(Sampler0, texCoord0) * ColorModulator;
   color.a *= time;
   color.a += 0.25 * (1.0 - time);
    if (color.a <= 0.1) {
        discard;
    }
    fragColor = color;
}