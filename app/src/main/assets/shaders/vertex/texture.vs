uniform mat4 m_Model;
uniform mat4 m_ModelViewProj;
uniform vec4 v_Color;

attribute vec3 v_Position;
attribute vec2 a_TexCoordinate;

varying vec2 v_TexCoordinate;

void main() {
    gl_Position = m_ModelViewProj * vec4(v_Position.xyz, 1.0);
    v_TexCoordinate = a_TexCoordinate;
}