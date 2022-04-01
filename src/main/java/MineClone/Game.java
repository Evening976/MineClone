package MineClone;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    private final Window window;
    private final Input input;

    /*
    **SPOILER ALERT** :
    private final Window window;
    private final Input input;
    private final Renderer renderer;
    private final Camera camera;
    private final World world;
    private final Player player;
    private final ChunkManager chunkManager;
    private final int chunkSize = 16;

    public Game() {
        window = new Window();
        input = new Input();
        renderer = new Renderer();
        camera = new Camera();
        world = new World();
        player = new Player(world);
        chunkManager = new ChunkManager(world, chunkSize);
    }

    public void run() {
        window.create();
        input.create();
        renderer.create();
        camera.create();
        world.create();
        player.create();
        chunkManager.create();

        while (!window.shouldClose()) {
            input.update();
            camera.update();
            player.update();
            chunkManager.update();
            world.update();
            renderer.update();
            window.update();
        }

        window.destroy();
        input.destroy();
        renderer.destroy();
        camera.destroy();
        world.destroy();
        player.destroy();
        chunkManager.destroy();
    }
    **SPOILERS ENDED**
}*/

    public Game(int width, int height, String title, boolean vSync){
        window = new Window(width, height, "MineClone", vSync);
        input = new Input();
    }

    public void init(){
        window.create();
        input.register(window.getWindow());
    }

    public void run(){
        while(!glfwWindowShouldClose(window.getWindow())){
            input.Update();
            window.render();
        }

        input.destroy();
        window.destroy();
    }
}

