
#include "camera.sh"

in vec3 vertexPos;
in vec2 texCoords;
in vec3 normal;
in vec3 tangent;

uniform mat4 T_model;
uniform mat4 T_projection;
uniform Camera camera;