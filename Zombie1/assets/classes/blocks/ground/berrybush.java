
import zombie.*;
import java.awt.*;

public class berrybush extends BlockGround{
            
            @Override
            public berrybush CreateNew() {
                
                berrybush b = new berrybush();
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
            public void OnInteract(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                
                double dist = 0;
                dist = Math.sqrt((blockX * 60 + 30 - byEntity.X + 30)*(blockX * 60 + 30 - byEntity.X + 30) + (blockY * 60 + 30 - byEntity.Y + 30)*(blockY * 60 + 30 - byEntity.Y + 30));
                WorldAccessor w = new WorldAccessor();
                if (dist <= 4 * 60){
                    if (w.GetBlockGround(z, blockX, blockY, byEntity.chunkLayer, byEntity.onMap).name.equals("berrybush_normal")){
                    	
                        w.SpawnItem(z, w.GetItem(z, "strawberry"), blockX, blockY - 2, byEntity.chunkLayer, byEntity.onMap, true);
                        w.SetBlock(z, "BlockGround", "berrybush_harvested", blockX, blockY, byEntity.chunkLayer, byEntity.onMap);
                    }
                }
                
                
            }
}
