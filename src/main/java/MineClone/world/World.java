package MineClone.world;

import MineClone.Game;
import MineClone.graphics.Camera;
import org.joml.Vector3f;

import java.util.List;

import static MineClone.Game.CHUNK_SIZE;

public class World {

    private static final int RENDER_DISTANCE = Game.RENDER_DISTANCE;
    private final ChunkManager _chnkManager;

    public World() {
        _chnkManager = new ChunkManager();
    }

    public void init(){
        for(int x = -2; x < 2; x++){
            for (int z = -2; z < 2; z++) {
                _chnkManager.addChunk(new Vector3f(x*CHUNK_SIZE, -CHUNK_SIZE, z*CHUNK_SIZE));
            }
        }
        _chnkManager.init();
    }

    public void Update(Camera camera) {

        _chnkManager.addChunk(new Vector3f((float) Math.floor((double)camera.getPosition().x + 16), -CHUNK_SIZE, (float)Math.floor((double)camera.getPosition().z + 16)));
        _chnkManager.addChunk(new Vector3f((float) Math.floor((double)camera.getPosition().x + 16), -CHUNK_SIZE, (float)Math.floor(camera.getPosition().z)));
        _chnkManager.addChunk(new Vector3f((float) Math.floor((double)camera.getPosition().x), -CHUNK_SIZE, (float)Math.floor((double)camera.getPosition().z + 16)));
        _chnkManager.addChunk(new Vector3f((float) Math.floor((double)camera.getPosition().x - 16), -CHUNK_SIZE, (float)Math.floor((double)camera.getPosition().z + 16)));
        _chnkManager.addChunk(new Vector3f((float) Math.floor((double)camera.getPosition().x - 16), -CHUNK_SIZE, (float)Math.floor(camera.getPosition().z)));
        _chnkManager.addChunk(new Vector3f((float) Math.floor((double)camera.getPosition().x - 16), -CHUNK_SIZE, (float)Math.floor((double)camera.getPosition().z - 16)));

        _chnkManager.updateChunks(camera);
    }

    public List<Chunk> getRenderList(Camera camera) {
        return _chnkManager.getRenderList(camera);
    }


    public void destroy() {
        _chnkManager.destroy();
    }
}
