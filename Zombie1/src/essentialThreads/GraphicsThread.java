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
import static zombie.Zombie.loadedBlockTextures;

/**
 *
 * @author admin
 */
public class GraphicsThread extends Thread {
    
    Zombie z = null;
    
    public GraphicsThread(Zombie Z){
        z = Z;
    }
    
    public void run() {
    
        if (z != null){
            
            Graphics g = z.frame.getGraphics();
            
            while (true){
             
                if (g == null) z.frame.getGraphics();
                else{
                    List<int[]> renderBlocks = new ArrayList<int[]>();

                    for (int a = (int) this.z.Z / 60; a <= (int) ((this.z.Z + this.z.xscreensize) / 60); a++) {
                        for (int b = (int) this.z.Q / 60; b <= (int) ((this.z.Q + this.z.yscreensize) / 60); b++) {


                               if (this.z.Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)] != null)
                                {
                                    for (int c = this.z.Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)].chunklayers.size() - 1; c >= 0; c--){

                                        Feld feld = this.z.Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)].chunklayers.get(c).Spielfeld[(int)(a - (a / 16) * 16)][(int)(b - (b / 16) * 16)];
                                        if (feld != null){
                                            if (c == 0)
                                            {
                                                if (feld.blocknormal != null) {g.drawImage(this.z.loadedBlockTextures.get(feld.blocknormal.textureId), a * 60 - this.z.Z + this.z.screenshakeX, b * 60 - this.z.Q + this.z.screenshakeY, this.z);
                                                    if (feld.blocknormal.changetextures != null) feld.blocknormal.ManageAnimation(this.z);
                                                    System.out.println("name = " + feld.blocknormal.name);
                                                }
                                                if (feld.blockground != null){
                                                    if (feld.blockground.hidePlayer == false && feld.blockground.drawoffsetX == 0 && feld.blockground.drawoffsetY == 0)
                                                    g.drawImage(this.z.loadedBlockTextures.get(feld.blockground.textureId), (a + feld.blockground.drawoffsetX) * 60 - this.z.Z + this.z.screenshakeX, (b + feld.blockground.drawoffsetY) * 60 - this.z.Q + this.z.screenshakeY, this.z);
                                                }
                                                if (feld.renderBlocks.size() != 0){
                                                    for (int[] tni : feld.renderBlocks) if (!renderBlocks.contains(tni)) renderBlocks.add(tni);
                                                }
                                            }

                                            if (c != 0){
                                                if (this.z.Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)].chunklayers.get(c - 1).Spielfeld[(int)(a - (a / 16) * 16)][(int)(b - (b / 16) * 16)].blocknormal.transparent == true
                                                    || this.z.Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)].chunklayers.get(c - 1).Spielfeld[(int)(a - (a / 16) * 16)][(int)(b - (b / 16) * 16)].blocknormal == null){
                                                        if (feld.blocknormal != null) g.drawImage(this.z.loadedBlockTextures.get(feld.blocknormal.textureId), a * 60 - this.z.Z + this.z.screenshakeX, b * 60 - this.z.Q + this.z.screenshakeY, this.z);
                                                        if (feld.blocknormal.changetextures != null) feld.blocknormal.ManageAnimation(this.z);
                                                        if (feld.blockground != null){
                                                            if (feld.blockground.hidePlayer == false && feld.blockground.drawoffsetX == 0 && feld.blockground.drawoffsetY == 0)
                                                            g.drawImage(this.z.loadedBlockTextures.get(feld.blockground.textureId), (a + feld.blockground.drawoffsetX) * 60 - this.z.Z + this.z.screenshakeX, (b + feld.blockground.drawoffsetY) * 60 - this.z.Q + this.z.screenshakeY, this.z);
                                                        }
                                                        if (feld.renderBlocks.size() != 0){
                                                            for (int[] tni : feld.renderBlocks) if (!renderBlocks.contains(tni)) renderBlocks.add(tni);
                                                        }
                                                }
                                            }
                                        }
                                    }

                                }
                        }
                    }

                    for (int[] tni : renderBlocks){
                    Feld feld = new WorldAccessor().GetBlock(this.z, tni[0], tni[1], 0, z.Spielkarte.name);
                    if (feld.blockground != null){
                        g.drawImage(loadedBlockTextures.get(feld.blockground.textureId), (tni[0] + feld.blockground.drawoffsetX) * 60 - this.z.Z + this.z.screenshakeX, (tni[1] + feld.blockground.drawoffsetY) * 60 - this.z.Q + this.z.screenshakeY, this.z);

                        }
                    }
                }
                
                
                                
            }
        }
        
        else
        { 
            System.out.println("[ERROR] in Graphics Thread: please inizialize GraphicsThread.z in the constructor!");            
        }
    }
}
