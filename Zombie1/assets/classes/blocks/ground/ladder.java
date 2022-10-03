
import zombie.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ladder extends BlockGround{
            
            public List<Entity> climbingEntities = new ArrayList<Entity>();
            public List<Integer> climbingTimes = new ArrayList<Integer>();
            public List<Boolean> climbingStatus = new ArrayList<Boolean>();
            public List<Integer> climbingLayers = new ArrayList<Integer>();
            public int climbTime = 100;
            
            public ladder(){
                super();
                this.shouldReceiveGameTicks = true;
            }
    
            @Override
            public ladder CreateNew() {
                
                ladder b = new ladder();
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
                b.climbTime = this.climbTime;
                return b;
            }
            
            @Override
            public void OnStepOn(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                
                if (!climbingEntities.contains(byEntity)){climbingEntities.add(byEntity);climbingTimes.add(0);climbingStatus.add(true);climbingLayers.add(byEntity.chunkLayer);}
                
                for (int a = 0; a <= climbingEntities.size() - 1; a++){
                    Integer i = climbingTimes.get(a) + 1;
                    climbingTimes.set(a, i);
                    climbingStatus.set(a, true);
                    if (climbingEntities.get(a).chunkLayer >= z.viewedChunkLayer){
                        g.setColor(Color.red);
                        g.drawRect(climbingEntities.get(a).X + climbingEntities.get(a).hitboxes.get(0).offsetX + (climbingEntities.get(a).hitboxes.get(0).sizeX / 2) - 50 - z.Z, climbingEntities.get(a).Y + climbingEntities.get(a).hitboxes.get(0).offsetY - 50 - z.Q, 100, 40);
                        g.fillRect(climbingEntities.get(a).X + climbingEntities.get(a).hitboxes.get(0).offsetX + (climbingEntities.get(a).hitboxes.get(0).sizeX / 2) - 50 - z.Z, climbingEntities.get(a).Y + climbingEntities.get(a).hitboxes.get(0).offsetY - 50 - z.Q, 100 / this.climbTime * climbingTimes.get(a), 40);
                    }
                    if (climbingTimes.get(a) >= this.climbTime){
                        climbingEntities.get(a).chunkLayer = climbingLayers.get(a) - 1;
                        try{if (climbingEntities.get(a).equals(z.controllablePlayers.get(0).entity)) z.viewedChunkLayer = climbingLayers.get(a) - 1;}catch(Exception e){}
                        climbingEntities.remove(a);
                        climbingTimes.remove(a);
                        climbingStatus.remove(a);
                        climbingLayers.remove(a);
                    }
                }
            }
            
            @Override
            public void OnGameTick(Zombie z, Graphics g){
                
                for (int a = 0; a <= climbingStatus.size() - 1; a++){
                    if (climbingStatus.get(a) == false){
                        climbingEntities.remove(a);
                        climbingTimes.remove(a);
                        climbingStatus.remove(a);
                        climbingLayers.remove(a);
                    }
                    climbingStatus.set(a, false);
                }
                
            }   
            
            
                    
}
