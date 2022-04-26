package MineClone;

import MineClone.utils.Loader;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class Window {
    private int width, height;
    private final String title;
    private final Boolean vSync;
    private long window;

    private final Matrix4f projectionMatrix;


    public Window(int width, int height, String title, Boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;

        projectionMatrix = new Matrix4f();
    }

    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);

        boolean maximised = false;
        if(width == 0 || height == 0) {
            width = 800;
            height = 600;
            GLFW.glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            maximised = true;
        }

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            });

        if(maximised){
            GLFW.glfwMaximizeWindow(window);
        }
        else{
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            assert vidMode != null;
            GLFW.glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        }

        GLFW.glfwMakeContextCurrent(window);
        glfwSwapInterval(vSync ? 1 : 0);

        GL.createCapabilities();
        glfwShowWindow(window);

        glClearColor(0.0f, 0.0f, 0.5f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_MULTISAMPLE);

        glfwSetWindowIcon(window, Loader.loadIcon("res/icon/minecraft.png"));
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        //System.out.println("Window created! OpenGL Version: " + glGetString(GL_VERSION));
    }


    public void destroy() {
        System.out.println("Closing window...");

        glfwSetWindowShouldClose(window, true);
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);


        glfwSetErrorCallback(null).free();
        glfwTerminate();
    }

    public int getWidth(){
        return  width;
    }

    public int getHeight() {
        return height;
    }

    public long windowHandle() {
        return window;
    }

    public Matrix4f updateProjectionMatrix(){
        float aspectRatio = (float) getWidth() / (float) getHeight();
        return projectionMatrix.setPerspective(Game.FOV, aspectRatio, Game.Z_NEAR, Game.Z_FAR);
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
}
