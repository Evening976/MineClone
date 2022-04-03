package MineClone.Input;

import MineClone.Game;
import MineClone.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

public class MouseInput {
    private final Vector2d previousPos, currentPos;
    private final Vector2f displVec;
    private boolean inWindow = false, leftButtonPressed = false, rightButtonPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0,0);
        displVec = new Vector2f();
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
        displVec.x = 0;
        displVec.y = 0;
        if(previousPos.x >= 0 && previousPos.y >= 0 && inWindow){
            double x =  (currentPos.x - previousPos.x);
            double y =  (currentPos.y - previousPos.y);
            boolean rotX = x != 0;
            boolean rotY = y != 0;
            if(rotX)
                displVec.y = (float)x;
            if(rotY)
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

    public Vector2f getDisplVec() {
        return displVec;
    }
}
