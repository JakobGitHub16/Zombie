package zombie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class UnloadChunkThread extends Thread
{

    Zombie z;
    PlayMap pm;
    public boolean r = false;
    boolean b = true;
    
    public UnloadChunkThread(Zombie z, PlayMap pm) {
        this.z = z;
        this.pm = pm;
    }
         
    
    public void run() {
       
       while (this.pm.fileLocked)try{ synchronized (this) { this.wait();}}catch(Exception e){}
       this.pm.fileLocked = true;
       UnloadChunk();
       this.pm.fileLocked = false;
       if (this.pm.gct.size() > 0){GenerateChunkThread g = this.pm.gct.get(0); synchronized (g) { g.notify();}}
       this.pm.uct.remove(this);
       if (this.pm.waitinguct.size() > 0) {this.pm.uct.add(this.pm.waitinguct.remove(0)); this.pm.uct.get(0).start();}
    }
    
    void UnloadChunk() {
        
    	boolean allChunksUnloaded = true;
        
        for (int a = 0; a <= pm.ChunkCollumns.length - 1; a++)
        {
            //for (Player p : z.controllablePlayers){
            //        System.out.println((z.entities.get(p.EntityId).X / 60 / 16) + " " + a);
            //}
            for (int b = 0; b <= pm.ChunkCollumns[a].length - 1; b++)
            {
            	boolean shouldUnload = true;
                for (Player p : z.controllablePlayers){
                	
                	if (p.entity.onMap.equals(this.pm.name) && a >= (p.entity.X / 60 / 16) - this.z.loadChunkRadius && a <= (p.entity.X / 60 / 16) + this.z.loadChunkRadius + 1
                			&& b >= (p.entity.Y / 60 / 16) - this.z.loadChunkRadius && b <= (p.entity.Y / 60 / 16) + this.z.loadChunkRadius + 1) 
                		{ shouldUnload = false; allChunksUnloaded = false; }
                		
                }
                
                if (shouldUnload) {
                	if (pm.ChunkCollumns[a][b] != null) 
                        if (pm.ChunkCollumns[a][b].save(a, b, this.pm.name)) { pm.ChunkCollumns[a][b].deleteEntities(z, a, b, this.pm.name); pm.ChunkCollumns[a][b] = null; System.out.println("sucsessfully saved chunk " + a + " " + b);}
                }
                
            }
        }
        if (allChunksUnloaded) z.playMaps.remove(pm.name);
    }
}

