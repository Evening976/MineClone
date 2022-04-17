package MineClone.world;

import MineClone.Game;
import MineClone.world.blocks.Block;
import MineClone.world.blocks.BlockFace;
import MineClone.world.blocks.BlockType;
import MineClone.graphics.Model;
import MineClone.graphics.Texture;
import MineClone.utils.*;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    private static final int CHUNKSIZE = 16;
    private static final int CHUNKSIZE_Y = 64;
    private static final int CHUNKVOLUME = CHUNKSIZE * CHUNKSIZE_Y * CHUNKSIZE;

    private final Vector3f Position;
    float[]  Vertices;
    int[]   Indices;
    float[] texCoords;
    private int[][][] Blocks;

    Model chunkModel;
    final Loader loader;
    private boolean meshed = false;
    private boolean canRender = false;

    public Chunk(Vector3f position) throws InterruptedException {
        Position = position;
        Blocks = new int[CHUNKSIZE][CHUNKSIZE_Y][CHUNKSIZE];
        genChunk();
        loader = new Loader();
        Thread t = new Thread(this::makeMesh);
        t.start();
        t.join();
        setTexture();
    }

    public Chunk(Vector3f pos, Loader loader) throws InterruptedException {
        Position = pos;
        this.loader = loader;
        Blocks = new int[CHUNKSIZE][CHUNKSIZE_Y][CHUNKSIZE];
        genChunk();

        Thread t = new Thread(this::makeMesh);
        t.start();
        t.join();
        setTexture();
    }

    public void setTexture(){
        chunkModel = loader.loadModel(Vertices, texCoords, Indices);
        chunkModel.setTexture(new Texture(loader.loadTexture(Block.getBlockPath(BlockType.GRASS))));
        canRender = true;
    }

    private void genChunk(){
        for (int x = 0; x < CHUNKSIZE; x++) {
            for (int y = 0; y < CHUNKSIZE_Y; y++) {
                for (int z = 0; z < CHUNKSIZE; z++) {

                    int block = BlockType.AIR.getValue();
                    float ab = OpenSimplex2.noise3_ImproveXZ(Game.SEED, (Position.x + x)/35f , (Position.y + y)/80f, (Position.z + z)/70f) + 1;
                    int w = (int) (ab / 2 * (CHUNKSIZE_Y/3));

                    if(y < Math.abs(w / 2)){
                        block = BlockType.STONE.getValue();
                    }
                    else if(y < Math.abs(w)){
                        block = BlockType.DIRT.getValue();
                    }
                    else if(y == Math.abs(w)){
                        block = BlockType.GRASS.getValue();
                    }
                    Blocks[x][y][z] = block;
                }
            }
        }
    }


    public void makeMesh(){
        int vIndex = 0;
        int tIndex = 0;
        Vertices = new float[CHUNKVOLUME * 12 * 6];
        texCoords = new float[CHUNKVOLUME * 8 * 6 * 4];

        int faces = 0;

        for (int x = 0; x < CHUNKSIZE; x++) {
            for(int y = 0; y < CHUNKSIZE_Y; y++) {
                for(int z = 0; z < CHUNKSIZE; z++) {

                    for (BlockFace face : getFaces(x, y, z)) {
                        if(face != null){
                            for (int i = 0; i < Block.getVertices(face).length; i+=3) {
                                assert Block.getVertices(face) != null;
                                Vertices[vIndex + i] = Block.getVertices(face)[i] + x;
                                Vertices[vIndex + i + 1] = Block.getVertices(face)[i + 1] + y;
                                Vertices[vIndex + i + 2] = Block.getVertices(face)[i + 2] + z;
                            }

                            BlockType bT = BlockType.values()[Blocks[x][y][z]] ;

                            System.arraycopy(Block.getTextCoords(bT, face), 0, texCoords, tIndex, Block.getTextCoords(bT, face).length);
                            tIndex += Block.getTextCoords(bT, face).length;
                            vIndex += Block.getVertices(face).length;
                            faces++;
                        }
                    }
                }
            }
        }

        Indices = Block.getIndices(faces);
        meshed = true;
    }

    private List<BlockFace> getFaces(int x, int y, int z){

        List<BlockFace> faces = new ArrayList<>(6);
        if(Blocks[x][y][z] != BlockType.AIR.getValue()){
            if(x == 0 || Blocks[x - 1][y][z] == BlockType.AIR.getValue()){
                faces.add(BlockFace.WEST);
            }

            if(x == CHUNKSIZE - 1 || Blocks[x + 1][y][z] == BlockType.AIR.getValue()){
                faces.add(BlockFace.EAST);
            }

            if(y == 0 || Blocks[x][y - 1][z] == BlockType.AIR.getValue()){
                faces.add(BlockFace.BOTTOM);
            }

            if(y == CHUNKSIZE_Y - 1 || Blocks[x][y + 1][z] == BlockType.AIR.getValue()){
                faces.add(BlockFace.TOP);
            }

            if(z == 0 || Blocks[x][y][z - 1] == BlockType.AIR.getValue()){
                faces.add(BlockFace.NORTH);
            }

            if(z == CHUNKSIZE - 1 || Blocks[x][y][z + 1] == BlockType.AIR.getValue()){
                faces.add(BlockFace.SOUTH);
            }

        }

        return faces;
    }

    public boolean isMeshed() {
        return meshed;
    }

    public boolean canChnkRender() {
        return canRender;
    }

    public Model getModel() {
        return chunkModel;
    }
    public float[] getTexCoords() {
        return texCoords;
    }

    public float[] getVertices() {
        return Vertices;
    }

    public int[] getIndices() {
        return Indices;
    }

    public void setBlock(int x, int y, int z, BlockType id) {
        Blocks[x][y][z] = id.getValue();
    }

    public int getBlock(int x, int y, int z) {
        return Blocks[x][y][z];
    }

    public Vector3f getPosition() {
        return Position;
    }
}
