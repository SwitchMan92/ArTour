uniform mat4 m_Model;
uniform mat4 m_View;
uniform mat4 m_Projection;

attribute vec3 v_Position;
attribute vec2 a_TexCoordinate;

varying vec2 v_TexCoordinate;

void main() {
    gl_Position = vec4(v_Position, 1.0);
    v_TexCoordinate = a_TexCoordinate;
}