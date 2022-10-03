

import zombie.*;
import java.awt.*;

public class treestem extends BlockGround{
            
            @Override
            public treestem CreateNew() {
                
            	treestem b = new treestem();
                b.name = this.name;
                        b.value = this.value;
                        b.valueInString = this.valueInString;
                        b.textureId = this.textureId;
                        b.hidePlayer = this.hidePlayer;
                        b.drawoffsetX = drawoffsetX;
                        b.drawoffsetY = drawoffsetY;
                        for (Item o : this.drops){
                            b.drops.add(o.CreateNew());
                        }
                        b.resistance = this.resistance;
                        b.material = this.material;
                        b.drawNormalBlock = this.drawNormalBlock;
                        b.vertTextureId = this.vertTextureId;
                        b.solid = this.solid;
                        b.lightLevel = this.lightLevel;
                        b.lightAbsorbtion = this.lightAbsorbtion;
                        b.texturePath = this.texturePath;
                        b.seasonColorMapPath = this.seasonColorMapPath;
	            return b;
            }
            
            @Override
            public void breakBlock(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                
                super.breakBlock(z, g, blockX, blockY, byEntity);
                WorldAccessor w = new WorldAccessor();
                
                if (w.GetBlock(z, blockX + 1, blockY, byEntity.chunkLayer, byEntity.onMap).blockground.name.equals("tree")) {
                	w.GetBlock(z, blockX + 1, blockY, byEntity.chunkLayer, byEntity.onMap).blockground.breakBlock(z, g, blockX + 1, blockY, byEntity);
                }
            }
}
