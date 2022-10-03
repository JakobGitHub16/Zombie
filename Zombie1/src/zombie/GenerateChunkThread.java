package zombie;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.function.IntBinaryOperator;
//import org.json.simple.JSONObject;

public class GenerateChunkThread extends Thread
{

    Zombie z;
    PlayMap pm;
    public boolean r = false;
    boolean b = true;
    String mapName = "overworld";
    Generator generator;
    boolean onlyLayers = false;
    Object AfterGenObject = null;
    String AfterGenFunkName = null;
    Object[] Args = null;
    
    public GenerateChunkThread(Zombie z, PlayMap pm, String mapName, Generator generator, boolean onlyLayers) {
        this.z = z;
        this.pm = pm;
        this.mapName = mapName;
        this.generator = pm.generator;
        if (pm.generator == null) this.generator = generator;
        this.onlyLayers = onlyLayers;
        
        if (this.pm == null || z.playMaps.get(this.mapName) == null) {
        	z.playMaps.put(this.mapName, new PlayMap(960, 960, z.loadedNormalBlocks, mapName));
        }
    }
    
    /**
     *
     * @param z
     * @param pm
     * @param mapName
     * @param generator
     * @param onlyLayers
     * @param afterGenObject
     * @param afterGenFunkName the name of the aftergenmethod
     * @param args params of the afterGenMethod
     */
    public GenerateChunkThread(Zombie z, PlayMap pm, String mapName, Generator generator, boolean onlyLayers, Object afterGenObject, String afterGenFunkName, Object[] args) {
        this.z = z;
        this.pm = pm;
        this.mapName = mapName;
        this.generator = pm.generator;
        if (pm.generator == null) this.generator = generator;
        this.onlyLayers = onlyLayers;
        
        if (this.pm == null || z.playMaps.get(this.mapName) == null) {
        	z.playMaps.put(this.mapName, new PlayMap(960, 960, z.loadedNormalBlocks, mapName));
        }
        AfterGenObject = afterGenObject;
        AfterGenFunkName = afterGenFunkName;
        Args = args;
    }
         
    
    public void run() {
       while (this.pm.fileLocked)try{ synchronized (this) { this.wait();}}catch(Exception e){}
       this.pm.fileLocked = true;
       if (!onlyLayers)GenerateChunks(this.z, this.z.loadedNormalBlocks, this.z.loadedTopBlocks, this.z.loadedGroundBlocks);
       else generateLayers(z);
       this.pm.fileLocked = false;
       if (this.pm.uct.size() > 0){UnloadChunkThread g = this.pm.uct.get(0); synchronized (g) { g.notify();}}
       this.pm.gct.remove(this.pm.gct.size() - 1);
       
       if (AfterGenFunkName != null && AfterGenObject != null && Args != null){
            try
             {   
                 Class[] arg = new Class[1]; 
                 arg[0] = Args.getClass();  
                 AfterGenObject.getClass().getDeclaredMethod(AfterGenFunkName.split("#", -1)[0], arg).invoke(AfterGenObject, new Object[] {Args});
             }
             catch(Exception e){System.out.println("e in genth= " + e);}
        }
    }
    
    void GenerateChunks(Zombie z, List<BlockNormal> blocks, List<BlockTop> topblocks, List<BlockGround> groundblocks) {
        
        for (Player p : z.controllablePlayers){
            
            for (int a = ((int)p.entity.X / 60 / 16) - z.loadChunkRadius; a <= (p.entity.X / 60 / 16) + z.loadChunkRadius + 1; a++)
            {
                for (int b = ((int)p.entity.Y / 60 / 16) - z.loadChunkRadius; b <= (p.entity.Y / 60 / 16) + z.loadChunkRadius + 1; b++)
                {

                    //try
                    //{
                        if (a >= 0 && b >= 0 && a <= this.pm.ChunksX - 1 && a <= this.pm.ChunksY - 1)
                        if (this.pm.ChunkCollumns[a][b] == null)
                        {   
                            this.pm.ChunkCollumns[a][b] = new ChunkCollumn();
                            if (this.pm.ChunkCollumns[a][b].tryLoadChunkCollumn(z, a, b, this.pm.name) == false)
                            {    
                                //this.pm.ChunkCollumns[a][b].chunklayers.add(new Chunk(z));
                                //this.pm.ChunkCollumns[a][b].chunklayers.get(0).fill(z, generator, 0);
                                while (z.viewedChunkLayer + 1 >= this.pm.ChunkCollumns[a][b].chunklayers.size()){
                                    
                                    this.pm.ChunkCollumns[a][b].chunklayers.add(new Chunk(z));
                                    int layer = this.pm.ChunkCollumns[a][b].chunklayers.size() - 1;
                                    this.pm.ChunkCollumns[a][b].chunklayers.get(layer).fill(z, generator, layer);
                                }
                                fillChunk(z, z.loadedNormalBlocks, z.loadedTopBlocks, z.loadedGroundBlocks, a, b, 0);        //      <------
                                if (this.pm.name.equals("overworld"))try{if (new Random().nextInt(5) == 0)HandleRandomGenStructures(this.pm.ChunkCollumns[a][b].chunklayers.get(0).HäuserGenerieren(1, blocks, topblocks, groundblocks), a, b, 0, blocks, z, topblocks, groundblocks);}catch(Exception e){}
                                System.out.println("generated Chunk " + a + " " + b);
                            }
                            else {

                                while (z.viewedChunkLayer >= this.pm.ChunkCollumns[a][b].chunklayers.size() - 1){
                                    
                                    this.pm.ChunkCollumns[a][b].chunklayers.add(new Chunk(z));
                                    int layer = this.pm.ChunkCollumns[a][b].chunklayers.size() - 1;
                                    for (int i = 0; i <= 15; i++){
                                        for (int i2 = 0; i2 <= 15; i2++){
                                            generator.getBlocksForLayer(z, layer);
                                            try{if (this.pm.ChunkCollumns[a][b].chunklayers.get(layer).Spielfeld[i][i2].blocknormal == null)this.pm.ChunkCollumns[a][b].chunklayers.get(layer).Spielfeld[i][i2].blocknormal = generator.currentNormalBlock.CreateNew();}catch (Exception e) {}
                                            try{if (this.pm.ChunkCollumns[a][b].chunklayers.get(layer).Spielfeld[i][i2].blockground == null)this.pm.ChunkCollumns[a][b].chunklayers.get(layer).Spielfeld[i][i2].blockground = generator.currentGroundBlock.CreateNew();}catch (Exception e) {}
                                            this.pm.ChunkCollumns[a][b].chunklayers.get(layer).Spielfeld[i][i2].SetValue();
                                        }   
                                    }
                                }
                                
                                System.out.println("sucsessfully loaded Chunk " + a + " " + b);
                            }
                            this.pm.ChunkCollumns[a][b].generateEntities(z, a, b, this.mapName);
                        }
                    //}
                    //catch(Exception e){System.out.println(e + " in GenerateChunkThread.GenerateChunks");}

                }
            }
        }
    }
    
    void generateLayers(Zombie z){
        
        for (Player p : z.controllablePlayers){
            
            for (int a = ((int)p.entity.X / 60 / 16) - z.loadChunkRadius; a <= (p.entity.X / 60 / 16) + z.loadChunkRadius + 1; a++)
            {
                for (int b = ((int)p.entity.Y / 60 / 16) - z.loadChunkRadius; b <= (p.entity.Y / 60 / 16) + z.loadChunkRadius + 1; b++)
                {
                    try{

                        while (p.entity.chunkLayer + 1 >= this.pm.ChunkCollumns[a][b].chunklayers.size()){

                            this.pm.ChunkCollumns[a][b].chunklayers.add(new Chunk(z));
                            int layer = this.pm.ChunkCollumns[a][b].chunklayers.size() - 1;
                            this.pm.ChunkCollumns[a][b].chunklayers.get(layer).fill(z, generator, layer);
                        }
                    }catch(Exception e){}
                                
                }
            }
        }
    }
    
    void HandleRandomGenStructures(randomGenStructure structure, int chunkNumberA, int chunkNumberB, int layer, List<BlockNormal> blocks, Zombie z, List<BlockTop> topblocks, List<BlockGround> groundblocks) {
        
        if (structure != null)
        {        
            int wr = (int)(structure.width / 2);
            int hr = (int)(structure.heigth / 2);

            for (int a = -wr; a <= wr; a++)
                for (int b = -hr; b <= hr; b++)
                    {   
                        try{
                            if (structure.x + a >= 0 && structure.x + a < 16 && structure.y + b >= 0 && structure.y + b < 16)
                            {
                                if (structure.blocks[a + wr][b + hr].blockground != null) this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(layer).Spielfeld[structure.x + a][structure.y + b].blockground = structure.blocks[a + wr][b + hr].blockground; 
                                if (structure.blocks[a + wr][b + hr].blocktop != null) this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(layer).Spielfeld[structure.x + a][structure.y + b].blocktop = structure.blocks[a + wr][b + hr].blocktop; 
                                if (structure.blocks[a + wr][b + hr].blocknormal != null) this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(layer).Spielfeld[structure.x + a][structure.y + b].blocknormal = structure.blocks[a + wr][b + hr].blocknormal; 
                                this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(layer).Spielfeld[structure.x + a][structure.y + b].SetValue();
                            }
                            else
                            {
                                int x = (int)((chunkNumberA * 16 + structure.x + a) / 16);
                                int y = (int)((chunkNumberB * 16 + structure.y + b) / 16);
                                if (this.pm.ChunkCollumns[x][y] == null)
                                {
                                    this.pm.ChunkCollumns[x][y] = new ChunkCollumn();
                                    this.pm.ChunkCollumns[x][y].chunklayers.add(new Chunk(z));
                                    this.pm.ChunkCollumns[x][y].chunklayers.get(0).fill(z, generator, 0);
                                    fillChunk(z, z.loadedNormalBlocks, z.loadedTopBlocks, z.loadedGroundBlocks, x, y, 0);        //      <------        //  <-------
                                }
                                int xx = structure.x + a;
                                if (x > chunkNumberA) xx = a - (16 - structure.x);
                                if (x < chunkNumberA) {xx = (16 + a) + structure.x; }
                                int yy = structure.y + b;
                                if (y > chunkNumberB) yy = b - (16 - structure.y);
                                if (y < chunkNumberB) {yy = (16 + b) + structure.y; }
                                if (xx < 0) {xx = 16 - (xx * -1); }
                                if (yy < 0) {yy = 16 - (yy * -1); } 
                                //if (x != chunkNumberA && y != chunkNumberB){
                                    if (structure.blocks[a + wr][b + hr].blocknormal != null) this.pm.ChunkCollumns[x][y].chunklayers.get(layer).Spielfeld[xx][yy].blocknormal = structure.blocks[a + wr][b + hr].blocknormal;
                                    if (structure.blocks[a + wr][b + hr].blocktop != null) this.pm.ChunkCollumns[x][y].chunklayers.get(layer).Spielfeld[xx][yy].blocktop = structure.blocks[a + wr][b + hr].blocktop;
                                    if (structure.blocks[a + wr][b + hr].blockground != null) this.pm.ChunkCollumns[x][y].chunklayers.get(layer).Spielfeld[xx][yy].blockground = structure.blocks[a + wr][b + hr].blockground;
                                    this.pm.ChunkCollumns[x][y].chunklayers.get(layer).Spielfeld[xx][yy].SetValue();
                                //}
                            }
                        }
                        catch(Exception e){}
                }         
        }
    }
    
    void HandleRandomBlockPatch(randomGenStructure structure, int chunkNumberA, int chunkNumberB, List<BlockNormal> blocks, Zombie z, List<BlockTop> topblocks, List<BlockGround> groundblocks) {
        
        if (structure != null)
        {        
            int wr = (int)(structure.width / 2);
            int hr = (int)(structure.heigth / 2);

            for (int a = -wr; a <= wr; a++)
                for (int b = -hr; b <= hr; b++)
                    {   
                        try{
                            if (structure.x + a >= 0 && structure.x + a < 16 && structure.y + b >= 0 && structure.y + b < 16)
                            {
                                if (structure.blocks[a + wr][b + hr].blockground != null){
                                    this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(0).Spielfeld[structure.x + a][structure.y + b].blockground = structure.blocks[a + wr][b + hr].blockground;
                                    if (structure.blocks[a + wr][b + hr].blockground.drawoffsetX != 0 || structure.blocks[a + wr][b + hr].blockground.drawoffsetY != 0){
                                        int[] i = new int[2];
                                        i[0] = chunkNumberA * 16 + structure.x + a; i[1] = chunkNumberB * 16 + structure.y + b;
                                        new WorldAccessor().GetBlock(z, chunkNumberA * 16 + structure.x + a + structure.blocks[a + wr][b + hr].blockground.drawoffsetX, 
                                                chunkNumberB * 16 + structure.y + b + structure.blocks[a + wr][b + hr].blockground.drawoffsetY, 0, this.mapName).renderBlocks.add(i);
                                    }
                                } 
                                if (structure.blocks[a + wr][b + hr].blocktop != null) this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(0).Spielfeld[structure.x + a][structure.y + b].blocktop = structure.blocks[a + wr][b + hr].blocktop; 
                                if (structure.blocks[a + wr][b + hr].blocknormal != null) this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(0).Spielfeld[structure.x + a][structure.y + b].blocknormal = structure.blocks[a + wr][b + hr].blocknormal; 
                                this.pm.ChunkCollumns[chunkNumberA][chunkNumberB].chunklayers.get(0).Spielfeld[structure.x + a][structure.y + b].SetValue();
                            }
                        }
                        catch(Exception e){}
                }         
        }
    }
    
    void fillChunk(Zombie z, List<BlockNormal> blocks, List<BlockTop> topblocks, List<BlockGround> groundblocks, int a, int b, int layer){
        try{               
            List<randomGenStructure> rgs = this.pm.ChunkCollumns[a][b].chunklayers.get(layer).GenerateBlockPatches(z, blocks, topblocks, groundblocks, layer, this.pm.name);
            for (randomGenStructure s : rgs) 
            {
                HandleRandomBlockPatch(s, a, b, blocks, z, topblocks, groundblocks);
            }
            if (this.pm.name.equals("overworld"))HandleRandomGenStructures(this.pm.ChunkCollumns[a][b].chunklayers.get(layer).SeenGenerieren(1, blocks), a, b, layer, blocks, z, topblocks, groundblocks);
            if (this.pm.name.equals("overworld"))HandleRandomGenStructures(this.pm.ChunkCollumns[a][b].chunklayers.get(layer).BäumeGenerieren(1, blocks, topblocks, groundblocks), a, b, layer, blocks, z, topblocks, groundblocks);
            
        }catch(Exception e){}
    }

}

