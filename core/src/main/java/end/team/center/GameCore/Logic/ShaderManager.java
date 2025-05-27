package end.team.center.GameCore.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderManager {
    public static ShaderProgram maskShader;
    public static ShaderProgram hardMaskShader;
    public static ShaderProgram dimmingShader;
    public static float radiusView1 = 0.2f;
    public static float radiusView2 = 0.17f;
    public static float radiusView3 = 0.15f;

    static {
        ShaderProgram.pedantic = false;

        // Общий вершинный шейдер
        String vertexShaderCode =
            "uniform mat4 u_projTrans;\n" +
                "attribute vec4 a_position;\n" +
                "attribute vec4 a_color;\n" +
                "attribute vec2 a_texCoord0;\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoord0;\n" +
                "void main() {\n" +
                "    v_color = a_color;\n" +
                "    v_texCoord0 = a_texCoord0;\n" +
                "    gl_Position = u_projTrans * a_position;\n" +
                "}";

        // Фрагментный — мягкая маска со свечением
        String fragmentMask =
            "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +

                "uniform sampler2D u_texture;\n" +
                "uniform float u_aspectRatio;\n" +
                "uniform float u_time;\n" +
                "uniform vec2 u_heroPos;\n" +

                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoord0;\n" +

                "void main() {\n" +
                "    vec2 center = u_heroPos;\n" +
                "    vec2 scaledCoords = vec2(v_texCoord0.x, v_texCoord0.y * u_aspectRatio);\n" +
                "    vec2 scaledCenter = vec2(center.x, center.y * u_aspectRatio);\n" +
                "    float dist = distance(scaledCoords, scaledCenter);\n" +

                "    float baseRadius ="+radiusView1+";\n" +
                "    float amplitude = 0.01;\n" +
                "    float period = 2.0;\n" +
                "    float radius = baseRadius + amplitude * sin(6.2831 * u_time / period);\n" +
                "    float edge = 0.05;\n" +

                "    if (dist > radius + edge) discard;\n" +
                "    float mask = smoothstep(radius + edge, radius, dist);\n" +

                "    vec4 texColor = texture2D(u_texture, v_texCoord0);\n" +
                "    vec4 finalColor = v_color * texColor;\n" +

                "    vec3 glowColor = vec3(1.0, 0.941, 0.729);\n" +
                "    float glowStrength = 1.0 - smoothstep(0.0, radius, dist);\n" +
                "    finalColor.rgb += glowColor * glowStrength * 0.4;\n" +
                "    finalColor.rgb = mix(vec3(0.0), finalColor.rgb, mask);\n" +
                "    gl_FragColor = finalColor;\n" +
                "}";

        // Фрагментный — жёсткая маска
        String fragmentHardMask =
            "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +

                "uniform float u_aspectRatio;\n" +
                "uniform vec2 u_heroPos;\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoord0;\n" +

                "void main() {\n" +
                "    vec2 center = u_heroPos;\n" +
                "    vec2 scaledCoords = vec2(v_texCoord0.x, v_texCoord0.y * u_aspectRatio);\n" +
                "    vec2 scaledCenter = vec2(center.x, center.y * u_aspectRatio);\n" +
                "    float dist = distance(scaledCoords, scaledCenter);\n" +

                "    float radius = "+radiusView2+";\n" +
                "    if (dist <= radius) discard;\n" +
                "    else gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);\n" +
                "}";

        // Фрагментный — затемнение по краям
        String fragmentDimming =
            "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +

                "uniform sampler2D u_texture;\n" +
                "uniform float u_aspectRatio;\n" +
                "uniform vec2 u_heroPos;\n" +

                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoord0;\n" +

                "void main() {\n" +
                "    vec2 center = u_heroPos;\n" +
                "    vec2 scaledCoords = vec2(v_texCoord0.x, v_texCoord0.y * u_aspectRatio);\n" +
                "    vec2 scaledCenter = vec2(center.x, center.y * u_aspectRatio);\n" +

                "    float dist = distance(scaledCoords, scaledCenter);\n" +
                "    float innerRadius ="+radiusView3+";\n" +
                "    float outerRadius = 0.5;\n" +
                "    float edgeSoftness = 0.5;\n" +

                "    float alpha = smoothstep(outerRadius, outerRadius - edgeSoftness, dist);\n" +
                "    if (dist < innerRadius) alpha = 0.0;\n" +

                "    vec3 dimColor = vec3(0.0);\n" +
                "    float dimIntensity = 0.8;\n" +

                "    gl_FragColor = vec4(dimColor, alpha * dimIntensity);\n" +
                "}";

        maskShader = new ShaderProgram(vertexShaderCode, fragmentMask);
        hardMaskShader = new ShaderProgram(vertexShaderCode, fragmentHardMask);
        dimmingShader = new ShaderProgram(vertexShaderCode, fragmentDimming);

        if (!maskShader.isCompiled()) Gdx.app.error("MaskShader", maskShader.getLog());
        if (!hardMaskShader.isCompiled()) Gdx.app.error("HardMaskShader", hardMaskShader.getLog());
        if (!dimmingShader.isCompiled()) Gdx.app.error("DimmingShader", dimmingShader.getLog());
    }
}
