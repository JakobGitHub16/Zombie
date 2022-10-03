

import zombie.*;
import java.awt.*;
import java.util.Random;


public class corruptblock extends BlockNormal{
    
            int corruptionRadius = 1;
            
            
            
            @Override
            public corruptblock CreateNew() {
                
                corruptblock b = new corruptblock();
                if (this.name == "corruptgrass") this.value = 0;
                if (this.name == "corruptlog") this.value = 1;
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
            public void OnStepOn(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                
                WorldAccessor wa = new WorldAccessor();
                boolean plantGenerated = false;
                for (int a = -this.corruptionRadius; a <= this.corruptionRadius; a++){
                    for (int b = -this.corruptionRadius; b <= this.corruptionRadius; b++){
                        try{
                            if ("grass".equals(wa.GetBlockName(z, "BlockNormal", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap))){
                                wa.SetBlock(z, "BlockNormal", "corruptgrass", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap);
                                if ("".equals(wa.GetBlockName(z, "BlockGround", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap))){
                                    double i = Math.random();
                                    if (i <= 0.1)wa.SetBlock(z, "BlockGround", "corruptplant", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap);
                                    if (i >= 0.9)wa.SetBlock(z, "BlockGround", "corruptplant2", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap);
                                    plantGenerated = true;
                                }
                            }
                            if ("log".equals(wa.GetBlockName(z, "BlockNormal", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap))){
                                wa.SetBlock(z, "BlockNormal", "corruptlog", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap);
                            }
                            if ("water1".equals(wa.GetBlockName(z, "BlockNormal", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap))){
                                wa.SetBlock(z, "BlockNormal", "corruptwater", blockX + a, blockY + b, byEntity.chunkLayer, byEntity.onMap);
                            }
                        }catch(Exception e){}
                    }
                }
                
            }
}
