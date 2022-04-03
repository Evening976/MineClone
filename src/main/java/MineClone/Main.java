package MineClone;

public class Main {

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 900;
    private static final Boolean VSYNC = true;
    private static final String TITLE = "MineClone";


    public static void main(String[] args) throws Exception {
        Game game = new Game(WIDTH, HEIGHT, TITLE, VSYNC);
        game.init();
        game.run();
    }
}
