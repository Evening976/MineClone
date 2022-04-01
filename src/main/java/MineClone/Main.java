package MineClone;

public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Boolean VSYNC = true;
    private static final String TITLE = "MineClone";


    public static void main(String[] args) {
        Game game = new Game(WIDTH, HEIGHT, TITLE, VSYNC);
        game.init();
        game.run();
    }
}
