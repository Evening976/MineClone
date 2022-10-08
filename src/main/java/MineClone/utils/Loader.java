package MineClone.utils;

import MineClone.graphics.Model;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Loader {
    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    public Model loadModel(float[] vertices, float[] texCoords, int[] indices){
        int vaoID = createVAO();
        int vboID = storeIndicesBuffer(indices);
        storeDataInAttribList(0, 3, vertices);
        storeDataInAttribList(1,2, texCoords);
        unbind();
        return new Model(vaoID, vboID, indices.length);
    }

    public int loadTexture(String fileName){
        int width, height;
        ByteBuffer buffer;

        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer _width = stack.mallocInt(1);
            IntBuffer _height = stack.mallocInt(1);
            IntBuffer _channels = stack.mallocInt(1);

            buffer = stbi_load(fileName, _width, _height, _channels, 4);

            if(buffer == null){
                throw new RuntimeException("Failed to load image " + fileName + " : " + stbi_failure_reason());
            }

            width = _width.get();
            height = _height.get();
        }

        int id = glGenTextures();
        textures.add(id);
        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);

        return id;
    }

    public static GLFWImage.Buffer loadIcon(String path){
        try(MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buffer = stbi_load(path, width, height, channels, 4);

            if(buffer == null){
                throw new RuntimeException("Failed to load image " + path + " : " + stbi_failure_reason());
            }


            //this is slow and weird but who cares
            GLFWImage a = new GLFWImage(buffer);

            a.set(width.get(), height.get(), buffer);

            GLFWImage.Buffer icons = GLFWImage.malloc(1);
            icons.put(0, a);

            return icons;
        }
    }

    private int createVAO(){
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private int storeIndicesBuffer(int[] indices){
        int vbo = glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);

        IntBuffer buffer = Buffer.createIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        return vbo;
    }

    private void storeDataInAttribList(int attributeNumber, int vertexCount, float[] data){
        int vbo = glGenBuffers();
        vbos.add(vbo);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        FloatBuffer buffer = Buffer.createFloatBuffer(data);

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, vertexCount, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        buffer.clear();
    }

    private void unbind(){
        glBindVertexArray(0);
    }

    public void destroy(){
        for(int vao:vaos){
            glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos){
            glDeleteBuffers(vbo);
        }
        for(int texture: textures){
            glDeleteTextures(texture);
        }
    }

}
