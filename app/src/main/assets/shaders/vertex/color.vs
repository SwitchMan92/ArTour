uniform mat4 m_Model;
uniform mat4 m_View;
uniform mat4 m_Projection;

attribute vec4 a_Position;

void main() {
    gl_Position = a_Position;
}