package MineClone;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    GLFWKeyCallback keyCallback;

    public Input() {
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                switch (key) {
                    case GLFW_KEY_ESCAPE:
                        if (action == GLFW_RELEASE){
                            glfwSetWindowShouldClose(window, true);
                        }
                        break;
                    case GLFW_KEY_W:
                        if (action == GLFW_PRESS){
                            System.out.println("Z");
                        }
                }
            }
        };
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
