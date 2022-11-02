package MineClone.input;

import MineClone.Game;
import MineClone.utils.GameState;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput {

    final int[] keys = new int[65536];
    GLFWKeyCallback keyCallback;

    public KeyboardInput() {
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action;
                if(keys[GLFW_KEY_ESCAPE] == GLFW_PRESS || keys[GLFW_KEY_ESCAPE] == GLFW_REPEAT) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        };
    }

    public boolean isKeyDown(int key){
        return keys[key] == GLFW_PRESS || keys[key] == GLFW_REPEAT;
    }

    public void register(long window) {
        glfwSetKeyCallback(window, keyCallback);
    }

    public void destroy(){
        keyCallback = null;
        glfwSetKeyCallback(Game.getWindow().windowHandle(), null);
    }

    public void update(){
        glfwPollEvents();
    }
}
