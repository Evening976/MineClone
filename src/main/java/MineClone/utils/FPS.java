package MineClone.utils;

public class FPS {
    private static final long SECOND_IN_NANOS = 1000000000;
    private long lastTime, currentTime, delta;
    private int fps, frames;

    public FPS() {
        lastTime = System.nanoTime();
        frames = 0;
    }

    public void update() {
        delta = currentTime;
        currentTime = System.nanoTime();
        frames++;
        if (currentTime - lastTime >= SECOND_IN_NANOS) {
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }
    }

    public float deltaTime() {
        return (float) (System.nanoTime() - delta) / SECOND_IN_NANOS ;
    }

    public int getFPS() {
        return fps;
    }
}
