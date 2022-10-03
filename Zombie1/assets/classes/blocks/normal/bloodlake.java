

import zombie.*;
import java.awt.*;
import java.util.Random;


public class bloodlake extends BlockNormal{
    
            int corruptionRadius = 1;
            
            
            
            @Override
            public bloodlake CreateNew() {
                
                bloodlake b = new bloodlake();
                b.Animationtimer = this.Animationtimer;
                b.cAnimationtimer = 0;
                b.name = this.name;
                b.value = this.value;
                b.textureId = this.textureId;
                b.changetextures = this.changetextures;
                b.texturechangechance = this.texturechangechance;
                b.corruptionRadius = this.corruptionRadius;
        	for (Item o : this.drops){
	            b.drops.add(o.CreateNew());
	        }
                return b;
            }
            
            @Override
            public randomGenStructure onPlaceForWorldGen(Zombie z, int x, int y, randomGenStructure structure) {
                    WorldAccessor w = new WorldAccessor();
                    try {

                        Random radius = new Random();
                        Random leng = new Random();
                        int r = radius.nextInt(2);
                        r = r + 1;
                        
                        for (int c = r * 2 - r; c <= r * 2 + r; c++) 
                        {    try{
                                int lp = leng.nextInt(r + 2) + r;
                                int lm = -leng.nextInt(r + 2) - r;
                                for (int b = r * 2 + 2 + lm; b <= r * 2 - 2 + lp; b++) 
                                {
                                    structure.blocks[c][b] = new Feld(w.GetBlockNormal(z, "bloodlake").CreateNew(), null, null);
                                }
                            }catch (Exception e) {}
                        }
                        for (int c = r * 2 - r; c <= r * 2 + r; c++) 
                        {    try{
                                int lp = leng.nextInt(r + 2) + r;
                                int lm = -leng.nextInt(r + 2) - r;
                                for (int b = r * 2 + 2 + lm; b <= r * 2 - 2 + lp; b++) 
                                {
                                    structure.blocks[b][c] = new Feld(w.GetBlockNormal(z, "bloodlake").CreateNew(), null, null);
                                }
                            }catch (Exception e) {}
                        }
                        return structure;

                    } catch (Exception e) {

                    }
                return structure;
            }
}
