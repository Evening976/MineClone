package MineClone.world;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static MineClone.Game.CHUNK_SIZE;

public class ChunkHelper {
    public static List<Vector3f> getNeighbors(Chunk curChunk, ChunkManager chunks){
        return getNeighbors(curChunk.getPosition(), ChunkManager.getChunks());
    }

    public static List<Vector3f> getNeighbors(Vector3f curPos, List<Chunk> chunks){
        List<Vector3f> neighbors = new ArrayList<>(5);

        for (int i = 0; i < 5; i++) {
            Chunk c = getClosest(curPos, chunks);
            Vector3f neighborPos;

            curPos.sub(c.getPosition(), neighborPos = new Vector3f());

            if(c.getPosition().distance(curPos.x, c.getPosition().y, curPos.z) <= 2*CHUNK_SIZE && !neighbors.contains(neighborPos)) {
                neighbors.add(new Vector3f(curPos.x - c.getPosition().x, curPos.y - c.getPosition().y, curPos.z - c.getPosition().z));
            }
        }


        return neighbors;
    }

    public static List<Vector3f> getNeighbors(Chunk curChunk, List<Chunk> chunks){
        return getNeighbors(curChunk.getPosition(), chunks);
    }


    public static Chunk getClosest(Vector3f curPos, List<Chunk> chunks){
        Chunk closest = null;
        float closestDist = Float.MAX_VALUE;

        for (Chunk chunk : chunks) {
            float distance = chunk.getPosition().distance(curPos);
            if (distance < closestDist && distance > 0) {
                closest = chunk;
                closestDist = distance;
            }
        }
        return closest;
    }
    public static Chunk getClosest(Chunk curChunk){
        return getClosest(curChunk.getPosition(), ChunkManager.getChunks());
    }
}
