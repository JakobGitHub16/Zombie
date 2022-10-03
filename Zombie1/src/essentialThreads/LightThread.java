/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package essentialThreads;

import zombie.*;
import enumClasses.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class LightThread extends Thread {
    
    Zombie z = null;
    
    public LightThread(Zombie Z){
        z = Z;
    }
    
    public void run() {
    
        WorldAccessor w = new WorldAccessor();
        int layers = 2;
        int a = 0;
        int b = 0;
        while (true){
        
            try{
            try{layers = z.Spielkarte.ChunkCollumns[(int)(z.Z / 60 / 16)][(int)(z.Q / 60 / 16)].chunklayers.size();}catch(Exception e){}
            
            for (int layer = layers - 1; layer >= z.viewedChunkLayer; layer--)
	    {
                for (a = (int) z.Z / 60; a <= (int) ((z.Z + z.xscreensize) / 60); a++) {
                    for (b = (int) z.Q / 60; b <= (int) ((z.Q + z.yscreensize) / 60); b++) {
                        try{
                            /*Feld f = w.GetBlock(z, a, b, layer, z.Spielkarte.name);
                            int lEmm = w.getLightEmissionLevel(z, a, b, layer, z.Spielkarte.name);
                            if (f.brightness < lEmm && w.getLightEmissionLevel(z, a, b, layer, z.Spielkarte.name) > 0){
                                w.refreshLight(z, a, b, layer, z.Spielkarte.name);
                            }
                            else
                                if (f.brightness > 0)*/w.setLight(z, a, b, layer, z.Spielkarte.name);
                        }catch(Exception e){}
                    }
                }
            }
            Thread.sleep(50);
            }catch(Exception e){}
            
        }
    }
}
