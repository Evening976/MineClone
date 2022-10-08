package MineClone.world;

import MineClone.Game;
import MineClone.graphics.Camera;
import MineClone.world.blocks.BlockType;
import org.joml.Vector3f;

import java.util.*;
import java.util.stream.Collectors;

import static MineClone.Game.CHUNK_SIZE;
import static MineClone.Game.RENDER_DISTANCE;

public class ChunkManager {
    private static final Map<Vector3f, Chunk> m_chunksMap = new HashMap<>();
    private List<Chunk> m_chunksToUpdate;


    public ChunkManager(List<Chunk> chunks){
        m_chunksToUpdate = new ArrayList<>();
    }
    public ChunkManager(int startx, int starty, int lenx, int lenz){
        //m_chunks = new ArrayList<>();
        for (int x = startx; x < lenx; x++) {
            for (int z = starty; z < lenz; z++) {
                addChunk(new Vector3f(x * CHUNK_SIZE, -CHUNK_SIZE, z * CHUNK_SIZE));
            }
        }
    }

    public ChunkManager(){
        m_chunksToUpdate = new ArrayList<>();
    }

    public void init(){
        for (Chunk chunk : m_chunksMap.values()) {
            chunk.init();
        }
    }

    public void updateChunks(Camera camera) {

        for (Iterator<Chunk> it = m_chunksMap.values().iterator(); it.hasNext();) {
            Chunk chunk = it.next();
            if (chunk.getPosition().distance(camera.getPosition()) > RENDER_DISTANCE) {
                chunk.destroy();
                it.remove();
                m_chunksToUpdate.remove(chunk);
            }
            if(m_chunksToUpdate.contains(chunk)) {
                chunk.update();
            }
        }
        m_chunksToUpdate.clear();
    }

    public void addChunkToUpdate(Chunk chunk) {
        if(!m_chunksToUpdate.contains(chunk) && chunk != null) {
            addToUpdate(chunk);
        }
    }

    public static boolean isChunkLoaded(Vector3f position) {
        return m_chunksMap.containsKey(position);
    }

    public Chunk getChunk(int x, int y, int z) {
        return getChunk(new Vector3f(x, y, z));
    }

    public Chunk getChunk(Vector3f position) {

        if(m_chunksMap.containsKey(position)) {
            return m_chunksMap.get(position);
        }


        for (Chunk chunk : m_chunksMap.values()) {
            if(chunk.getPosition().distance(position) < CHUNK_SIZE) {
                return chunk;
            }
            else{
                addChunk(position);
                addChunkToUpdate(m_chunksMap.get(position));
                return m_chunksMap.get(position);
            }
        }

        return null;
    }

    private Chunk getClosest(Vector3f position) {
        Chunk closest = null;
        float closestDistance = Float.MAX_VALUE;

        for (Chunk chunk : m_chunksMap.values()) {
            float distance = chunk.getPosition().distance(position);
            if (distance < closestDistance) {
                closest = chunk;
                closestDistance = distance;
            }
        }
        return closest;
    }

    private Vector3f getClosestPos(Vector3f Position){
        Vector3f closest = null;
        float closestDistance = Float.MAX_VALUE;

        for (Vector3f chunk : m_chunksMap.keySet()) {
            float distance = chunk.distance(Position);
            if (distance < closestDistance) {
                closest = chunk;
                closestDistance = distance;
            }
        }
        return closest;
    }

    private Vector3f getFarthestChunk(Vector3f position){
        Vector3f farthest = null;
        float farthestDistance = Float.MIN_VALUE;

        for (Vector3f chunk : m_chunksMap.keySet()) {
            float distance = chunk.distance(position);
            if (distance > farthestDistance) {
                farthest = chunk;
                farthestDistance = distance;
            }
        }
        return farthest;
    }

    private float getFarthestChunkF(Vector3f position){
        float farthestDistance = Float.MIN_VALUE;

        for (Vector3f chunk : m_chunksMap.keySet()) {
            float distance = chunk.distance(position);
            if (distance > farthestDistance) {
                farthestDistance = distance;
            }
        }
        return farthestDistance;
    }

    private float getClosestF(Vector3f position){
        float closestDistance = Float.MAX_VALUE;
        for (Vector3f chunk : m_chunksMap.keySet()) {
            float distance = chunk.distance(position);
            if (distance < closestDistance) {
                closestDistance = distance;
            }
        }
        return closestDistance;
    }

    public void removeBlock(Vector3f position) {
        Chunk chunk = getChunk(position);
        if (chunk != null) {
            chunk.setBlock(position, BlockType.AIR);
        }
        addToUpdate(chunk);
    }

    public void addBlock(Vector3f position, BlockType blockType) {
        Chunk chunk = getChunk(position);
        if (chunk != null && chunk.getBlock(position) == BlockType.AIR) {
            chunk.setBlock(position, blockType);
        }
        addToUpdate(chunk);
    }

    public void addChunk(Vector3f pos){
        if(!m_chunksMap.containsKey(pos) && getClosestF(pos) >= CHUNK_SIZE) {
            Chunk chunk = new Chunk(pos);
            m_chunksMap.put(pos, chunk);
            addToUpdate(chunk);
        }
    }

    public void Gen(Vector3f playerPos) {

        Map<Vector3f, Chunk> toAdd = new HashMap<>();
        for(Chunk c : m_chunksMap.values()){

            if(c.northNeighbour.distance(playerPos) <= RENDER_DISTANCE && !m_chunksMap.containsKey(c.northNeighbour)){
                Chunk chunk = new Chunk(c.northNeighbour);
                toAdd.put(c.northNeighbour, chunk);
                addToUpdate(chunk);
            }
            if(c.eastNeighbour.distance(playerPos) <= RENDER_DISTANCE && !m_chunksMap.containsKey(c.eastNeighbour)){
                Chunk chunk = new Chunk(c.eastNeighbour);
                toAdd.put(c.eastNeighbour, chunk);
                addToUpdate(chunk);
            }
            if(c.westNeighbour.distance(playerPos) <= RENDER_DISTANCE && !m_chunksMap.containsKey(c.westNeighbour)){
                Chunk chunk = new Chunk(c.westNeighbour);
                toAdd.put(c.westNeighbour, chunk);
                addToUpdate(chunk);
            }
            if(c.southNeighbour.distance(playerPos) <= RENDER_DISTANCE && !m_chunksMap.containsKey(c.southNeighbour)){
                Chunk chunk = new Chunk(c.southNeighbour);
                toAdd.put(c.southNeighbour, chunk);
                addToUpdate(chunk);
            }
        }

        m_chunksMap.putAll(toAdd);
        toAdd.clear();
    }

    public BlockType getBlock(int x, int y, int z) {
        return getChunk(x, y, z).getBlock(x, y, z);
    }


    public void addToUpdate(Chunk chunk) {
        if(!m_chunksToUpdate.contains(chunk))
            m_chunksToUpdate.add(chunk);
    }

    public List<Chunk> getRenderList(Camera camera){
        List<Chunk> t = new ArrayList<>();
        for (Chunk chunk : m_chunksMap.values()) {
            if(chunk.getPosition().distance(camera.getPosition()) < RENDER_DISTANCE && chunk.canChnkRender()) {
                t.add(chunk);
            }
            else{
                chunk.destroy();
            }
        }

        return t;
    }



    public static List<Chunk> getChunks() {
        return new ArrayList<>(m_chunksMap.values());
    }

    public void destroy() {
        m_chunksMap.clear();
        m_chunksToUpdate.clear();
    }
}
