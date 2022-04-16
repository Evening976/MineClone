package MineClone.utils;

import MineClone.world.Chunk;
import MineClone.graphics.Camera;
import MineClone.graphics.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Transformation {
    public static Matrix4f createTransformationMatrix(Entity entity){
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(entity.getPosition()).
                rotateX((float) Math.toRadians(entity.getRotation().x)).
                rotateY((float) Math.toRadians(entity.getRotation().y)).
                rotateZ((float) Math.toRadians(entity.getRotation().z)).
                scale(entity.getScale());



        return matrix;
    }

    public static Matrix4f createChunkTransformationMatrix(Chunk chunk){
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(chunk.getPosition());

        return matrix;
    }

    public static Matrix4f getViewMatrix(Camera camera){
        Vector3f pos = camera.getPosition();
        Vector3d rot = camera.getRotation();
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1,0,0))
                .rotate((float) Math.toRadians(rot.y), new Vector3f(0,1,0))
                .rotate((float) Math.toRadians(rot.z), new Vector3f(0,0,1));

        viewMatrix.translate(new Vector3f(-pos.x,-pos.y,-pos.z));
        return viewMatrix;
    }
}
