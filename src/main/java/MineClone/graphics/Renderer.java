package MineClone.graphics;

import MineClone.Game;
import MineClone.Window;
import MineClone.world.Chunk;
import MineClone.world.ChunkManager;
import MineClone.utils.Transformation;
import MineClone.utils.Utils;
import MineClone.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    public static final int VIEW_DISTANCE = Game.CHUNK_SIZE * 1000;
    private final Window window;
    private ShaderManager shader;

    public Renderer() {
        window = Game.getWindow();
    }

    public void create() throws Exception {
        shader = new ShaderManager();
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));

        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
    }

    public void Clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(List<Entity> entity, Camera camera) {
        Clear();
        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        //glFrontFace(GL_CCW);

        for (Entity ent : entity) {
            if (ent.getPosition().distance(camera.getPosition()) < VIEW_DISTANCE * 20) {
                bind(ent.getModel());
                prepare(ent, camera);
                glDrawElements(GL11.GL_TRIANGLES, ent.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }

        unbind();
        shader.unbind();
    }

    public void renderChnk(List<Chunk> chunks, Camera camera) {
        Clear();
        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());


        for (Chunk chunk : chunks) {
            if(chunk.isMeshed() && !chunk.canChnkRender()){
                chunk.setTexture();
            }
            if (chunk.getPosition().distance(camera.getPosition()) < VIEW_DISTANCE && chunk.canChnkRender()) {
                bind(chunk.getModel());
                prepare(chunk, camera);
                glDrawElements(GL11.GL_TRIANGLES, chunk.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }

        unbind();
        shader.unbind();
    }

    public void renderChnkManager(ChunkManager chunkManager, Camera camera) {
        Clear();
        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());

       List<Chunk> chunks = chunkManager.getChunks();

        for (Chunk chunk : chunks) {
            if (chunk.getPosition().distance(camera.getPosition()) < VIEW_DISTANCE) {
                bind(chunk.getModel());
                prepare(chunk, camera);
                glDrawElements(GL11.GL_TRIANGLES, chunk.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
        }

        unbind();
        shader.unbind();
    }

    public void renderWorld(World world, Camera camera) {
        Clear();
        shader.bind();
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());

        for(Chunk ch : world.getRenderList(camera)){
            bind(ch.getModel());
            prepare(ch, camera);
            glDrawElements(GL11.GL_TRIANGLES, ch.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        }

        unbind();
        shader.unbind();
    }

    public void bind(Model model){
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getID());
    }

    public void unbind(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void prepare(Entity entity, Camera camera){
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }

    public void prepare(Chunk chunk, Camera camera){
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createChunkTransformationMatrix(chunk));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }



    public void destroy(){
        shader.destroy();
    }
}
