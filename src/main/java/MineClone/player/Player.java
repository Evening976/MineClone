package MineClone.player;

import MineClone.Game;
import MineClone.Input.KeyboardInput;
import MineClone.Input.MouseInput;
import MineClone.graphics.Camera;
import org.joml.Vector3f;

import static MineClone.Game.MOUSE_SENSITIVITY;
import static MineClone.Game.MOVEMENT_SENSITIVITY;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class Player {
    private KeyboardInput kb;
    private MouseInput mouse;

    private static final boolean CREATIVE_MODE = false;

    private boolean isOnGround;

    Vector3f cameraInc;

    public Player() {
        this.kb = new KeyboardInput();
        this.mouse = new MouseInput();
        cameraInc = new Vector3f(0,0,-5);
    }

    public void init(){
        mouse.init();
        kb.register(Game.getWindow().windowHandle());
    }

    public void Update(Camera camera, float dt) {
        kb.update();
        mouse.input();

        cameraInc = new Vector3f(0,0,0);

        if(kb.isKeyDown(GLFW_KEY_W)){
            cameraInc.z += -1;
        }
        if(kb.isKeyDown(GLFW_KEY_S)){
            cameraInc.z += 1;
        }
        if(kb.isKeyDown(GLFW_KEY_A)){
            cameraInc.x += -1;
        }
        if(kb.isKeyDown(GLFW_KEY_D)){
            cameraInc.x += 1;
        }
        if(kb.isKeyDown(GLFW_KEY_SPACE) && CREATIVE_MODE){
            cameraInc.y += 1;
        }
        else if(kb.isKeyDown(GLFW_KEY_SPACE) && !CREATIVE_MODE){
            jump();
        }
        if(kb.isKeyDown(GLFW_KEY_LEFT_SHIFT)){
            cameraInc.y += -1;
        }

        if(!isOnGround){
            cameraInc.y += -MOVEMENT_SENSITIVITY * 4 * dt;
        }


        if(cameraInc.x != 0 || cameraInc.y != 0 || cameraInc.z != 0){
            float t = cameraInc.y;
            cameraInc.normalize();
            cameraInc.y = t;
            cameraInc.mul(dt * MOVEMENT_SENSITIVITY);
        }


        if(mouse.getDisplVec().y > 90.0f){
            mouse.getDisplVec().y = 90.0f;
        }

        camera.movePos(cameraInc);
        camera.moveRotation(mouse.getDisplVec().mul(MOUSE_SENSITIVITY));
    }

    private void collide(){

    }


    public void destroy(){
        mouse.destroy();
        kb.destroy();
    }

    private void jump(){
        if(isOnGround){
            cameraInc.y += MOVEMENT_SENSITIVITY * 5;
            isOnGround = false;
        }
    }
}
