package zombie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlayMap {

    public int SizeX = 100, SizeY = 100, ChunksX = 60, ChunksY = 60;
    public Feld[][] Spielfeld = new Feld[SizeX][SizeY];
    public ChunkCollumn[][] ChunkCollumns = new ChunkCollumn[ChunksX][ChunksY];
    public List<UnloadChunkThread> uct = new ArrayList<UnloadChunkThread>();
    public List<UnloadChunkThread> waitinguct = new ArrayList<UnloadChunkThread>();
    public List<GenerateChunkThread> gct = new ArrayList<GenerateChunkThread>();
    public double wind = 30;
    public double currentWindEffect = 0;
    public double currentWindEffectTarget = 0;
    public double windSpeed = 0.1;
    public String name = "overworld";
    boolean fileLocked = false;
    public Generator generator = null;
    public Time mapTime = new Time(null);
    
    public PlayMap(int sizex, int sizey, List<BlockNormal> blocks, String name) {
        
        this.SizeX = sizex;
        this.SizeY = sizey;
        this.name = name;
        Spielfeld = new Feld[SizeX][SizeY];
        for (int a = 0; a <= SizeX - 1; a++) {
            for (int b = 0; b <= SizeY - 1; b++) {
                Spielfeld[a][b] = new Feld(new BlockNormal(), new  BlockTop(), null);
            }
        }
        for (int a = 0; a <= (SizeX / 16) - 1; a++) {
            for (int b = 0; b <= (SizeY / 16) - 1; b++) {
                ChunkCollumns[a][b] = null;
            }
        }
    }

    public void OnGameTick(Zombie z){
        mapTime.plus1Tick(z);
    }
    
    public void GenerateChunks(Zombie z, Generator g) {
        if (gct.isEmpty()){
            gct.add(new GenerateChunkThread(z, this, this.name, g, false));
            gct.get(gct.size() - 1).start();
        }
        
    }
    
    public void GenerateChunks(Zombie z, Generator g, Object afterGenObject, String afterGenFunkName, Object[] args) {
        if (gct.isEmpty()){
            gct.add(new GenerateChunkThread(z, this, this.name, g, false, afterGenObject, afterGenFunkName, args));
            gct.get(gct.size() - 1).start();
        }
        
    }
    
    public void GenerateLayers(Zombie z, Generator g) {
        if (gct.isEmpty()){
            gct.add(new GenerateChunkThread(z, this, this.name, g, true));
            gct.get(gct.size() - 1).start();
        }
    }
    
    public void UnloadChunk(Zombie z) {
        if (uct.isEmpty()){
            uct.add(new UnloadChunkThread(z, this));
            uct.get(uct.size() - 1).start();
        }
        else if (waitinguct.size() < 4) waitinguct.add(new UnloadChunkThread(z, this));
        
    }
    
    public void fill(List<BlockNormal> blocks){
        int grassId = 0;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals("grass")){ break;}
                }
        
        for (int a = 0; a <= SizeX - 1; a++) {
            for (int b = 0; b <= SizeY - 1; b++) {
                Spielfeld[a][b] = new Feld(blocks.get(grassId).CreateNew(), new BlockTop(), null);
            }
        }
    }
    
    public int getBlockIdN(List<BlockNormal> blocks, String name){
        int grassId = 0;
        boolean returnable = false;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals(name)){ returnable = true; break; }
                }
        
        if (returnable == true)return grassId;
        return -1;
    }
    
    public int getBlockIdT(List<BlockTop> blocks, String name){
        int grassId = 0;
        boolean returnable = false;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals(name)){ returnable = true; break; }
                }
        
        if (returnable == true)return grassId;
        return -1;
    }
    
    public int getBlockIdG(List<BlockGround> blocks, String name){
        
        int grassId = 0;
        boolean returnable = false;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals(name)){ returnable = true; break; }
                }
        
        if (returnable == true)return grassId;
        return -1;
    }
    
}
