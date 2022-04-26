package MineClone.world.blocks;

public class Block {

    private static final float[] NORTH_VERTS = {
            -0.5f, -0.5f, -1.0f, //0
            0.5f, -0.5f, -1.0f,
            0.5f, 0.5f, -1.0f,
            -0.5f, 0.5f, -1.0f, //3
    };

    private static final float[] SOUTH_VERTS = {
            -0.5f, -0.5f, 0.0f, //0
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f, //3
    };

    private static final float[] EAST_VERTS = {
            0.5f, -0.5f, 0.0f, //i
            0.5f, -0.5f, -1.0f, //j
            0.5f, 0.5f, -1.0f, //k
            0.5f, 0.5f, 0.0f,
    };

    private static final float[] WEST_VERTS = {
            -0.5f, -0.5f, 0.0f, //i
            -0.5f, -0.5f, -1.0f, //j
            -0.5f, 0.5f, -1.0f, //k
            -0.5f, 0.5f, 0.0f,
    };

    private static final float[] BOTTOM_VERTS = {
            -0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, -1.0f,
            0.5f, -0.5f, -1.0f,
            0.5f, -0.5f, 0.0f,
    };

    private static final float[] TOP_VERTS = {
            -0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, -1.0f,
            0.5f, 0.5f, -1.0f,
            0.5f, 0.5f, 0.0f,
    };

    private static final int[] INDICES={
            0, 1, 2,
            2, 3, 0
    };


    private static final float[] TOP_TEX = {
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 1.0f,
            0.0f, 1.0f,
    };

    private static final float[] BOT_TEX = {
            0.5f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            1.0f, 0.0f
    };

    private static final float[] SIDE_TEX = {
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            0.0f, 0.0f
    };

    private static final float[] STONE_TEX = {
            0.5f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.5f,
            0.5f, 0.5f
    };


    public static float[] getVertices(BlockFace face) {
        return switch (face) {
            case NORTH -> NORTH_VERTS;
            case SOUTH -> SOUTH_VERTS;
            case EAST -> EAST_VERTS;
            case WEST -> WEST_VERTS;
            case TOP -> TOP_VERTS;
            case BOTTOM -> BOTTOM_VERTS;
        };
    }

    public static float[] getTextCoords(BlockType type, BlockFace face) {

        if(type == BlockType.GRASS){
            return switch (face) {
                case NORTH, WEST, EAST, SOUTH -> SIDE_TEX;
                case TOP -> TOP_TEX;
                case BOTTOM -> BOT_TEX;
            };
        }

        return switch(type) {
            case STONE -> STONE_TEX;
            case DIRT -> BOT_TEX;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static int[] getIndices(int sides) {
        int[] blk_ind = new int[INDICES.length * sides];

        for(int i = 0; i < sides; i++){
            for(int j = 0; j < INDICES.length; j++){
                blk_ind[(INDICES.length * i) + j] = INDICES[j] + (4*i);
            }
        }

        return blk_ind;
    }

    public static String getBlockPath(BlockType type) {
        return switch (type) {
            case GRASS -> "res/textures/grass.png";
            case DIRT -> "res/textures/dirt.png";
            case STONE -> "res/textures/stone.png";
            default -> "res/textures/grass.png";
        };
    }
}
