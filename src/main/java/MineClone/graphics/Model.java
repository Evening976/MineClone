package MineClone.graphics;

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class Model {
    private final int vaoID;

    private final int vboID;
    private final int vertexCount;
    private Texture texture;

    public Model(int vaoid, int vboid, int vertexCount) {
        this.vaoID = vaoid;
        this.vboID = vboid;
        this.vertexCount = vertexCount;
    }

    public Model(int vaoid, int vboid, int vertexCount, Texture texture) {
        this.vaoID = vaoid;
        this.vboID = vboid;
        this.vertexCount = vertexCount;
        this.texture = texture;
    }

    public Model(Model model, Texture texture) {
        this.vaoID = model.vaoID;
        this.vboID = model.vboID;
        this.vertexCount = model.vertexCount;
        this.texture = texture;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void destroy(){
        glDeleteVertexArrays(vaoID);
        glDeleteBuffers(vboID);
        glDeleteTextures(texture.getID());
    }
}
