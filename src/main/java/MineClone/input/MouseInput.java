package MineClone.input;

import MineClone.Game;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {
    private final Vector2d previousPos, currentPos;
    private final Vector3d displVec;
    private boolean inWindow = false, leftButtonPressed = false, rightButtonPressed = false, leftButtonReleased= false;
    private boolean previousLeftBtnPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0,0);
        displVec = new Vector3d();
    }

    public boolean isPreviousLeftBtnPressed() {
        return previousLeftBtnPressed;
    }

    public void init(){
        GLFW.glfwSetCursorPosCallback(Game.getWindow().windowHandle(), (window, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });

        glfwSetCursorEnterCallback(Game.getWindow().windowHandle(), (window, entered) -> inWindow = entered);

        glfwSetMouseButtonCallback(Game.getWindow().windowHandle(), (window, button, action, mode) -> {
            previousLeftBtnPressed = leftButtonPressed;
            leftButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            leftButtonReleased = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_RELEASE;
            rightButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        });
    }

    public void input(){
        if(inWindow){
            double x =  (currentPos.x - previousPos.x);
            double y =  (currentPos.y - previousPos.y);
            displVec.y = x;
            displVec.x = y;
        }

        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;

        previousLeftBtnPressed = leftButtonPressed;
    }

    public void destroy(){
        glfwSetCursorPosCallback(Game.getWindow().windowHandle(), null);
        glfwSetCursorEnterCallback(Game.getWindow().windowHandle(), null);
        glfwSetMouseButtonCallback(Game.getWindow().windowHandle(), null);
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isLeftButtonReleased() {
        return leftButtonReleased;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public Vector3d getDisplVec() {
        return displVec;
    }
}
