package MineClone.graphics;

import MineClone.utils.Transformation;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Camera {
    private Vector3f position;
    private Vector3d rotation;
    private Vector3f forward;

    public Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3d(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3d rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void movePos(float x, float y, float z) {
        if(z != 0) {
            position.x += (float) (sin(Math.toRadians(rotation.y)) * -1.0f * z);
            position.z += (float) (cos(Math.toRadians(rotation.y)) * z);
        }
        if(x != 0) {
            position.x += (float) (sin(Math.toRadians(rotation.y - 90)) * -1.0f * x);
            position.z += (float) (cos(Math.toRadians(rotation.y - 90)) * x);
        }

        position.y += y;

    }

    public void movePos(Vector3f pos) {

        if(pos.z != 0) {
            position.x += (float) (sin(Math.toRadians(rotation.y)) * -1.0f * pos.z);
            position.z += (float) (cos(Math.toRadians(rotation.y)) * pos.z);
        }
        if(pos.x != 0) {
            position.x += (float) (sin(Math.toRadians(rotation.y - 90)) * -1.0f * pos.x);
            position.z += (float) (cos(Math.toRadians(rotation.y - 90)) * pos.x);
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

    public void moveRotation(Vector3d rotation) {

        if(rotation.x + this.rotation.x < 90 && this.rotation.x + rotation.x > -90) {
            this.rotation.x += rotation.x;
        }
        else if(this.rotation.x + rotation.x > 90) {
            this.rotation.x = 90;
        }
        else if(this.rotation.x + rotation.x < -90) {
            this.rotation.x = -90;
        }

        if(this.rotation.y + rotation.y < 180 && this.rotation.y + rotation.y > -180) {
            this.rotation.y += rotation.y;
        }
        else if(this.rotation.y + rotation.y > 180){
            this.rotation.y = -180 + rotation.y;
        }
        else if(this.rotation.y + rotation.y < -180){
            this.rotation.y = 180 + rotation.y;
        }

        this.rotation.z += rotation.z;
    }



    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public Vector3f getForward() {
        Vector3f forward = new Vector3f();
        Matrix4f mat;

        mat = Transformation.getViewMatrix(this);
        mat.getColumn(2, forward);
        forward.normalize();

        forward = new Vector3f(forward.x, forward.y, -forward.z);
        forward.add(position);


        return forward;
    }

    public void setRotation(Vector3d rotation) {
        this.rotation = rotation;
    }
}
