package MineClone.world.blocks;

public enum BlockType {
    AIR(0),
    GRASS(1),
    STONE(2),
    DIRT(3);

    private final int id;

    BlockType(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
