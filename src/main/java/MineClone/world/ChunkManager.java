package MineClone.world;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {
    private List<Chunk> m_chunks;

    public ChunkManager(List<Chunk> chunks) {
        m_chunks = chunks;
    }

    public ChunkManager() {
        m_chunks = new ArrayList<>();
    }

    public Chunk getChunk(int x, int z) {
        for (Chunk chunk : m_chunks) {
            if(chunk.getPosition().x == x && chunk.getPosition().z == z) {
                return chunk;
            }
        }
        return null;
    }
    public void addChunk(Vector3f pos) throws InterruptedException {
        Chunk chunk = new Chunk(pos);
        m_chunks.add(chunk);
    }



    public List<Chunk> getChunks() {
        return m_chunks;
    }
}
