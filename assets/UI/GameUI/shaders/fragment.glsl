

varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec2 circleCenter; // центр круга в нормализованных координатах (0..1)
uniform float radius;      // радиус круга (0..1)
uniform float darknessAlpha; // прозрачность затемнения (например 0.7)

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    float dist = distance(v_texCoords, circleCenter);

    if (dist <= radius) {
        // Внутри круга показываем оригинальную текстуру
        gl_FragColor = texColor;
    } else {
        // Вне круга — затемнение (черный с прозрачностью)
        gl_FragColor = vec4(0.0, 0.0, 0.0, darknessAlpha);
    }
}
