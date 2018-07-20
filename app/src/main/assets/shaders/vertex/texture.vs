uniform mat4 m_Model;
uniform mat4 m_View;
uniform mat4 m_Projection;

attribute vec4 a_Position;
attribute vec2 a_TexCoordinate;

varying vec2 v_TexCoordinate;

void main() {
    gl_Position = a_Position;
    v_TexCoordinate = a_TexCoordinate;
}