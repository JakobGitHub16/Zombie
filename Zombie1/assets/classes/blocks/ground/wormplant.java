

import zombie.*;
import java.awt.*;

public class wormplant extends BlockGround{
            
            @Override
            public wormplant CreateNew() {
                
            	wormplant b = new wormplant();
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
	            return b;
            }
            
            @Override
            public randomGenStructure onPlaceForWorldGen(Zombie z, int x, int y, randomGenStructure structure){
            	
            	WorldAccessor w = new WorldAccessor();
            	
            	structure.blocks[x - 1][y].blockground = w.GetBlockGround(z, "wormplantstem").CreateNew();
            	
            	return structure;
            }
}
