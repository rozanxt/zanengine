#version 330

in vec3 modelViewPosition;
in vec3 modelViewNormal;
in vec2 fragmentCoord;

out vec4 result;

uniform sampler2D textureUnit;

void main() {
	vec3 lightPosition = vec3(0.0, 0.0, 0.0);
	vec3 lightDifference = lightPosition - modelViewPosition;
	vec3 lightVector = normalize(lightDifference);
	float lightDistance = length(lightDifference);
	float diffuse = max(dot(modelViewNormal, lightVector), 0.0);
	diffuse = diffuse * (1.0 / (1.0 + (0.05 * lightDistance * lightDistance)));
	diffuse = diffuse + 0.3;
	result = diffuse * texture2D(textureUnit, fragmentCoord);
}
