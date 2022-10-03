

import zombie.*;
import java.awt.*;

public class tree extends BlockGround{
            
            @Override
            public tree CreateNew() {
                
            	tree b = new tree();
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
            /*
            @Override
            public randomGenStructure onPlaceForWorldGen(Zombie z, int x, int y, randomGenStructure structure){
            	
            	WorldAccessor w = new WorldAccessor();
            	
            	structure.blocks[x - 1][y].blockground = w.GetBlockGround(z, "treestem").CreateNew();
            	
            	return structure;
            }*/
}
