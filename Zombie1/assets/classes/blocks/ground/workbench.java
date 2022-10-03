
import zombie.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class workbench extends BlockGround{
            
            public List<Entity> climbingEntities = new ArrayList<Entity>();
            public List<Integer> climbingTimes = new ArrayList<Integer>();
            public List<Boolean> climbingStatus = new ArrayList<Boolean>();
            public List<Integer> climbingLayers = new ArrayList<Integer>();
            public int climbTime = 100;
            
            public workbench(){
                super();
                this.shouldReceiveGameTicks = true;
            }
    
            @Override
            public void OnInteract(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                for (GUIElement u : z.guiElements) if (u.type.equals("workbench")) return;
                Map<String, Object> m = new HashMap<String, Object>();
                GUIElement e = z.worldAccessor.getGUIElement(z, "workbench").CreateNew();
                m.put("entity", byEntity);
                e.inizialize(z, m);
                z.guiElements.add(e);
            }
                
            @Override
            public workbench CreateNew() {
                
                workbench b = new workbench();
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
                    
}
