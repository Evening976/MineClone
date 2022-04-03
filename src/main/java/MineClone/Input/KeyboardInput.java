package MineClone.Input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput {

    int[] keys = new int[65536];
    GLFWKeyCallback keyCallback;

    public KeyboardInput() {
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action;
                if(keys[GLFW_KEY_ESCAPE] == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        };
    }

    public boolean isKeyDown(int key){
        return keys[key] == GLFW_PRESS;
    }

    public void register(long window) {
        glfwSetKeyCallback(window, keyCallback);
    }

    public void destroy(){
        keyCallback = null;
    }

    public void Update(){
        glfwPollEvents();
    }
}
