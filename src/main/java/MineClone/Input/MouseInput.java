package MineClone.Input;

import MineClone.Game;
import MineClone.Window;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

import java.lang.Math;

import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

public class MouseInput {
    private final Vector2d previousPos, currentPos;
    private final Vector3f displVec;
    private boolean inWindow = false, leftButtonPressed = false, rightButtonPressed = false;
    double yaw, pitch;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0,0);
        displVec = new Vector3f();
    }

    public void init(){
        GLFW.glfwSetCursorPosCallback(Game.getWindow().getWindow(), (window, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        glfwSetCursorEnterCallback(Game.getWindow().getWindow(), (window, entered) -> inWindow = entered);

        glfwSetMouseButtonCallback(Game.getWindow().getWindow(), (window, button, action, mode) -> {
            leftButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            rightButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        });
    }

    public void input(){
        if(previousPos.x != 0 && previousPos.y != 0 && inWindow){
            double x =  (currentPos.x - previousPos.x);
            double y =  (currentPos.y - previousPos.y);
            displVec.y = (float)x;
            displVec.x = (float)y;
        }

        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public Vector3f getDisplVec() {
        return displVec;
    }
    public float getPitch() {
        return (float) pitch;
    }
    public float getYaw() {
        return (float) yaw;
    }

}
