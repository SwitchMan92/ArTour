uniform mat4 m_Model;
uniform mat4 m_View;
uniform mat4 m_Projection;

attribute vec3 v_Position;

void main() {
    gl_Position = m_Projection * m_View * m_Model * vec4(v_Position, 1.0);
}