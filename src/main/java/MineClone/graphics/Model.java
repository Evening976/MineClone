package MineClone.graphics;

public class Model {
    private final int id;
    private final int vertexCount;
    private Texture texture;

    public Model(int id, int vertexCount) {
        this.id = id;
        this.vertexCount = vertexCount;
    }

    public Model(int id, int vertexCount, Texture texture) {
        this.id = id;
        this.vertexCount = vertexCount;
        this.texture = texture;
    }

    public Model(Model model, Texture texture) {
        this.id = model.id;
        this.vertexCount = model.vertexCount;
        this.texture = texture;
    }

    public int getVaoID() {
        return id;
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
}
