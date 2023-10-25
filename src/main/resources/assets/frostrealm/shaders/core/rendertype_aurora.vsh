#version 150

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;

out vec2 texCoord0;

void main() {
    float wave = sin(GameTime * 5) * 30;
    float wave2 = cos(GameTime * 5) * 30;
    float wave3 = 0;
    gl_Position = ProjMat * ModelViewMat * vec4(Position + vec3(wave, wave3, wave2), 1.0);

    texCoord0 = UV0;
}
