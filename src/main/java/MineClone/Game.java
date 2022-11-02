package MineClone;

import MineClone.utils.GameState;
import MineClone.utils.Utils;
import MineClone.graphics.*;
import MineClone.player.Player;
import MineClone.utils.FPS;
import MineClone.world.World;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glFlush;

public class Game {

    public static final float FOV = (float) Math.toRadians(90.0f);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000.f;
    public static final float MOUSE_SENSITIVITY = 0.05f;
    public static final float MOVEMENT_SENSITIVITY = 10.0f;
    public static final int CHUNK_SIZE = 16;
    public static final int RENDER_DISTANCE = 4 * CHUNK_SIZE;

    public static GameState GAME_STATE = GameState.PAUSE;

    public static long SEED;

    private static Window window = null;
    private final Renderer renderer;
    private final Camera camera;
    private final Player player;
    private final FPS fpsCounter;
    private final World world;

    public Game(int width, int height, String title, boolean vSync){
        SEED = Utils.rnd.nextLong();
        window = new Window(width, height, title, vSync);
        renderer = new Renderer();
        camera = new Camera();
        player = new Player();
        fpsCounter = new FPS();
        world = new World();
    }

    public void init() throws Exception {
        window.create();
        renderer.create();
        player.init();
        world.init();
    }

    public void run(){
        while(!glfwWindowShouldClose(window.windowHandle())){
            update();
            render();
        }
        destroyGame();

    }

    private void update() {
        fpsCounter.update();
        player.Update(camera, fpsCounter.deltaTime());
        world.Update(camera);
    }

    private void render(){
        renderer.renderWorld(world, camera);
        glFlush();
        glfwSwapBuffers(window.windowHandle());
    }

    public static Window getWindow(){
        return window;
    }


    public void destroyGame(){
        player.destroy();
        window.destroy();
        renderer.destroy();
        world.destroy();
        System.gc();
    }
}

