package MineClone;

import MineClone.world.ChunkManager;
import MineClone.graphics.*;
import MineClone.player.Player;
import MineClone.utils.FPS;
import MineClone.utils.Loader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    public static final float FOV = (float) Math.toRadians(90.0f);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000.f;
    public static final float MOUSE_SENSITIVITY = 0.05f;
    public static final float MOVEMENT_SENSITIVITY = 10.0f;
    public static final int CHUNK_SIZE = 16;

    private static Window window = null;
    private final Renderer renderer;

    private final Loader loader;
    private final Camera camera;
    private final Player player;

    private final FPS fpsCounter;

    private ChunkManager m_chunkManager;


    Vector3f cameraInc;

    public Game(int width, int height, String title, boolean vSync){
        window = new Window(width, height, title, vSync);
        renderer = new Renderer();
        loader = new Loader();
        camera = new Camera();
        player = new Player();
        fpsCounter = new FPS();
    }

    public void init() throws Exception {
        window.create();
        renderer.create();
        player.init();

        m_chunkManager = new ChunkManager();

        for(int x = -7; x < 7; x++){
            for (int z = -5; z < 5; z++) {
                m_chunkManager.addChunk(new Vector3f(x * CHUNK_SIZE, -CHUNK_SIZE, z * CHUNK_SIZE));
            }
        }
    }

    public void run(){
        while(!glfwWindowShouldClose(window.windowHandle())){
            update();
            render();
        }
        destroyGame();
    }

    private void update(){
        fpsCounter.update();
        player.Update(camera, fpsCounter.deltaTime());
        System.out.println("MineClone | FPS: " + fpsCounter.getFPS() + " | DeltaTime : " + fpsCounter.deltaTime());
    }

    private void render(){
        renderer.renderChnkManager(m_chunkManager, camera);
        glfwSwapBuffers(window.windowHandle());
    }

    public static Window getWindow(){
        return window;
    }


    public void destroyGame(){
        player.destroy();
        window.destroy();
        renderer.destroy();
        loader.destroy();
        System.gc();
    }
}

