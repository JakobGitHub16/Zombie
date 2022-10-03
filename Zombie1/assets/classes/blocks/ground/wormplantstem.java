

import zombie.*;
import java.awt.*;

public class wormplantstem extends BlockGround{
            
            @Override
            public wormplantstem CreateNew() {
                
            	wormplantstem b = new wormplantstem();
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
            public void breakBlock(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                
                super.breakBlock(z, g, blockX, blockY, byEntity);
                WorldAccessor w = new WorldAccessor();
                
                if (w.GetBlock(z, blockX + 1, blockY, byEntity.chunkLayer, byEntity.onMap).blockground.name.equals("wormplant")) {
                	w.GetBlock(z, blockX + 1, blockY, byEntity.chunkLayer, byEntity.onMap).blockground.breakBlock(z, g, blockX + 1, blockY, byEntity);
                }
            }
}
