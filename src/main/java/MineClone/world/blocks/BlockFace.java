package MineClone.world.blocks;

public enum BlockFace {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3),
    TOP(4),
    BOTTOM(5);

    private final int id;

    private BlockFace(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}

