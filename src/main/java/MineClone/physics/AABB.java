package MineClone.physics;

import org.joml.Vector3f;

public class AABB {
    private final Vector3f size;
    private Vector3f position;

    public AABB(Vector3f size, Vector3f position) {
        this.size = size;
        this.position = position;
    }

    public Vector3f getBound(){
        return new Vector3f(position.x + size.x, position.y + size.y, position.z + size.z);
    }

    public Vector3f getSize() {
        return size;
    }

    public Vector3f getPosition() {
        return position;
    }

}
