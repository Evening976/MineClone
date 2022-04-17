package MineClone.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {
    private final int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private final Map<String, Integer> uniforms;

    public ShaderManager() throws Exception{
        programID = glCreateProgram();
        if(programID == 0){
            throw new Exception("Could not create Shader");
        }

        uniforms = new HashMap<>();

    }

    public void createUniform(String uniformName) throws Exception{
        int uniformLocation = GL20.glGetUniformLocation(programID, uniformName);
        if(uniformLocation < 0){
            throw new Exception("Error getting uniform location: " + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value){
        try(MemoryStack stack = MemoryStack.stackPush()){
            glUniformMatrix4fv(uniforms.get(uniformName), false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName, int value){
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value){
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value){
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    public void setUniform(String uniformName, Vector4f value){
        glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }
    public void setUniform(String uniformName, Boolean value){
        float res = value ? 1 : 0;
        glUniform1f(uniforms.get(uniformName), res);
    }

    public void createVertexShader(String shaderCode) throws Exception{
        vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception{
        fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int type) throws Exception{
        int shaderID = GL20.glCreateShader(type);
        if(shaderID == 0){
            throw new Exception("Error creating shader. Type: " + type);
        }

        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        if(glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL_FALSE){
            throw new Exception("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderID, 1024));
        }

        GL20.glAttachShader(programID, shaderID);
        return shaderID;
    }

    public void link() throws Exception{
        glLinkProgram(programID);
        if(glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL_FALSE){
            throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(programID, 1024));
        }

        if(vertexShaderID != 0){
            GL20.glDetachShader(programID, vertexShaderID);
        }
        if(fragmentShaderID != 0){
            GL20.glDetachShader(programID, fragmentShaderID);
        }

        GL20.glValidateProgram(programID);
        if(glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL_FALSE){
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programID, 1024));
        }
    }

    public void bind(){
        GL20.glUseProgram(programID);
    }

    public void unbind(){
        GL20.glUseProgram(0);
    }

    public void destroy(){
        unbind();
        if(programID != 0){
            glDeleteProgram(programID);
        }
    }
}
