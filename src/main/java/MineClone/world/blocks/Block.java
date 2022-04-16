package MineClone.world.blocks;

public class Block {

    private static final float[] NORTH_VERTS = {
            -0.5f, -0.5f, -1.0f, //0
            0.5f, -0.5f, -1.0f,
            0.5f, 0.5f, -1.0f,
            -0.5f, 0.5f, -1.0f, //3
    };

    private static final float[] SOUTH_EAST_VERTS = {

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
            -0.5f, -0.5f, 0.0f, //i
            -0.5f, -0.5f, -1.0f, //j
            0.5f, -0.5f, -1.0f, //k
            0.5f, -0.5f, 0.0f,
    };

    private static final float[] TOP_VERTS = {
            -0.5f, 0.5f, 0.0f, //i
            -0.5f, 0.5f, -1.0f, //j
            0.5f, 0.5f, -1.0f, //k
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
        switch (face) {
            case NORTH:
                return NORTH_VERTS;
            case SOUTH:
                return SOUTH_VERTS;
            case EAST:
                return EAST_VERTS;
            case WEST:
                return WEST_VERTS;
            case TOP:
                return TOP_VERTS;
            case BOTTOM:
                return BOTTOM_VERTS;
        }

        return null;
    }

    public static float[] getTextCoords(BlockType type, BlockFace face) {
        if(type == BlockType.GRASS) {

            switch (face) {
                case NORTH:
                case WEST:
                case EAST:
                case SOUTH:
                    return SIDE_TEX;
                case TOP:
                    return TOP_TEX;
                case BOTTOM:
                    return BOT_TEX;

            }
        }

        else if(type == BlockType.STONE) {
            return STONE_TEX;
        }

        else if(type == BlockType.DIRT) {
            return BOT_TEX;
        }

        return null;
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
        switch (type) {
            case GRASS:
                return "res/textures/grass.png";
            case DIRT:
                return "res/textures/dirt.png";
            case STONE:
                return "res/textures/stone.png";
        }
        String blockPath = "res/textures/grass.png";
        return blockPath;
    }
}
