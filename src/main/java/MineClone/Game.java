package MineClone;

import MineClone.Input.KeyboardInput;
import MineClone.Input.MouseInput;
import MineClone.entity.Block;
import MineClone.graphics.*;
import MineClone.utils.Loader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    public static final float FOV = (float) Math.toRadians(60.0f);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000.f;
    public static final float MOUSE_SENSITIVITY = 0.05f;
    public static final float MOVEMENT_SENSITIVITY = 0.1f;

    private static Window window = null;
    private final Renderer renderer;
    private final KeyboardInput input;
    private final MouseInput mouseInput;

    private final Loader loader;
    private Entity entity;
    private final Camera camera;


    Vector3f cameraInc;

    public Game(int width, int height, String title, boolean vSync){
        window = new Window(width, height, title, vSync);
        renderer = new Renderer();
        input = new KeyboardInput();
        loader = new Loader();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        mouseInput = new MouseInput();
    }

    public void init() throws Exception {
        window.create();
        renderer.create();
        mouseInput.init();
        input.register(window.getWindow());
        Model model = loader.loadModel(Block.getVertices(), Block.getTextCoords(), Block.getIndices());
        model.setTexture(new Texture(loader.loadTexture(Block.getBlockPath())));
        entity = new Entity(model, new Vector3f(0, 0, -5), new Vector3f(0,0,0), 1);
    }

    public void run(){
        while(!glfwWindowShouldClose(window.getWindow())){
            update();
            HandleInput();
            renderer.render(entity, camera);
            glfwSwapBuffers(window.getWindow());
        }

        destroyGame();
    }

    private void update(){
        camera.moveRotation(mouseInput.getDisplVec().mul(MOUSE_SENSITIVITY));
        entity.incRot(0.0f, 0.0f,0.0f);
    }

    public static Window getWindow(){
        return window;
    }

    private void HandleInput(){
        cameraInc = new Vector3f(0,0,0);

        if(input.isKeyDown(GLFW_KEY_W)){
            cameraInc.z -= MOVEMENT_SENSITIVITY;
        }
        if(input.isKeyDown(GLFW_KEY_S)){
            cameraInc.z += MOVEMENT_SENSITIVITY;
        }
        if(input.isKeyDown(GLFW_KEY_A)){
            cameraInc.x -= MOVEMENT_SENSITIVITY;
        }
        if(input.isKeyDown(GLFW_KEY_D)){
            cameraInc.x += MOVEMENT_SENSITIVITY;
        }
        if(input.isKeyDown(GLFW_KEY_SPACE)){
            cameraInc.y += MOVEMENT_SENSITIVITY;
        }
        if(input.isKeyDown(GLFW_KEY_LEFT_SHIFT)){
            cameraInc.y += -1f * MOVEMENT_SENSITIVITY;
        }

        if(input.isKeyDown(GLFW_KEY_W) && input.isKeyDown(GLFW_KEY_D)){
            cameraInc.z = -MOVEMENT_SENSITIVITY / 2;
            cameraInc.x = MOVEMENT_SENSITIVITY / 2;
        }
        if(input.isKeyDown(GLFW_KEY_W) && input.isKeyDown(GLFW_KEY_A)){
            cameraInc.z = -MOVEMENT_SENSITIVITY / 2;
            cameraInc.x = -MOVEMENT_SENSITIVITY / 2;
        }

        if(input.isKeyDown(GLFW_KEY_S) && input.isKeyDown(GLFW_KEY_D)){
            cameraInc.z = MOVEMENT_SENSITIVITY / 2;
            cameraInc.x = MOVEMENT_SENSITIVITY / 2;
        }
        if(input.isKeyDown(GLFW_KEY_S) && input.isKeyDown(GLFW_KEY_A)){
            cameraInc.z = MOVEMENT_SENSITIVITY / 2;
            cameraInc.x = -MOVEMENT_SENSITIVITY / 2;
        }

        camera.movePos(cameraInc);
        input.Update();
        mouseInput.input();
    }

    private void destroyGame(){
        input.destroy();
        window.destroy();
        renderer.destroy();
        loader.destroy();
    }
}

