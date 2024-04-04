#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float GameTime;

in vec2 texCoord0;

out vec4 fragColor;


vec4 crystal(vec4 fragCoord)
{
    vec3 color;
    //色設定(単色)
    color = vec3(fragCoord.x, fragCoord.y, fragCoord.z);



    float alpha = cos(length(fragCoord.x * fragCoord.y * fragCoord.z) * 0.25 * GameTime);
    //色設定(円形)
    color *= vec3(cos(length(fragCoord) * 0.25) * 0.25);
    return vec4(color, alpha);
}

void main() {
   vec4 color = crystal(texture(Sampler0, texCoord0)) * ColorModulator;
    if (color.a <= 0.1) {
        discard;
    }
    fragColor = color;
}