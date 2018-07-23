uniform mat4 m_Model;
uniform mat4 m_ModelViewProj;
uniform vec4 v_Color;
attribute vec3 v_Position;

varying vec4 a_Color;

void main() {
    gl_Position = m_ModelViewProj * vec4(v_Position, 1.0);
    a_Color = v_Color;
}