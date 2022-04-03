package MineClone.graphics;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position, rotation;

    public Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void movePos(float x, float y, float z) {
        if(z != 0) {
            position.x += (float) (Math.sin(Math.toRadians(rotation.y)) * -1.0f * z);
            position.z += (float) (Math.cos(Math.toRadians(rotation.y)) * z);
        }
        if(x != 0) {
            position.x += (float) (Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x);
            position.z += (float) (Math.cos(Math.toRadians(rotation.y - 90)) * x);
        }

        position.y += y;

    }

    public void movePos(Vector3f pos) {
        if(pos.z != 0) {
            position.x += (float) (Math.sin(Math.toRadians(rotation.y)) * -1.0f * pos.z);
            position.z += (float) (Math.cos(Math.toRadians(rotation.y)) * pos.z);
        }
        if(pos.x != 0) {
            position.x += (float) (Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * pos.x);
            position.z += (float) (Math.cos(Math.toRadians(rotation.y - 90)) * pos.x);
        }

        position.y += pos.y;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }

    public void moveRotation(Vector2f rot, float z){
        rotation.x += rot.x;
        rotation.y += rot.y;
        rotation.z += z;
    }



    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
