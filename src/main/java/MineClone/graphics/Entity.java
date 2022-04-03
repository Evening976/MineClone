package MineClone.graphics;

import org.joml.Vector3f;

public class Entity {
    private Model model;
    private Vector3f pos;
    private Vector3f rotation;
    private float scale;

    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.setModel(model);
        this.setPos(pos);
        this.setRotation(rotation);
        this.setScale(scale);
    }

    public void incPos(float x, float y, float z) {
        getPosition().x += x;
        getPosition().y += y;
        getPosition().z += z;
    }

    public void setPos(float x, float y, float z) {
        getPosition().x = x;
        getPosition().y = y;
        getPosition().z = z;
    }

    public void incRot(float x, float y, float z) {
        getRotation().x += x;
        getRotation().y += y;
        getRotation().z += z;
    }

    public void setRot(float x, float y, float z) {
        getRotation().x = x;
        getRotation().y = y;
        getRotation().z = z;
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
