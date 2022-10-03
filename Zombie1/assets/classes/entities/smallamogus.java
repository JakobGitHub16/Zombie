import zombie.*;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class smallamogus extends IntelligentMob {
     
    boolean foundVictim = false;
    
    public smallamogus(){
        super();
    }
    
    
    @Override
    public void OnGameTick(Zombie z, Graphics g){
        
        super.OnGameTick(z, g);
        
        if (this.foundVictim == false)
            for (AITaskBase a : this.AITasks)
                if (a.name == "AITaskFollowEntity")
                    if ((this.targetingEntities.size() == 0))
                        this.targetingEntities.add(z.entities.get(0));
    }
    
    @Override
    public smallamogus CreateNew(){
        
        smallamogus e = new smallamogus();
        e.X = this.X;
        e.Y = this.Y;
        e.drawoffsetX = this.drawoffsetX;
        e.drawoffsetY = this.drawoffsetY;
        e.Speed = this.Speed;
        for (String s : this.motions.keySet()){
            e.motions.put(s, this.motions.get(s).clone());
        }
        for (String s : this.attributes.keySet()){
            e.attributes.put(s, this.attributes.get(s).clone());
        }
        e.name = this.name;
        e.Id = this.Id;
        for (Hitbox b : this.hitboxes){
            e.hitboxes.add(new Hitbox(b.sizeX, b.sizeY, b.offsetX, b.offsetY));
        }
        e.image = this.image;
        e.imagePath = this.imagePath;
        e.idleAnimPath = this.idleAnimPath;
        e.chunkLayer = this.chunkLayer;
        if (this.animation != null) e.animation = this.animation.CreateNew(); else e.animation = null;
        if (this.idleAnimation != null) e.idleAnimation = this.idleAnimation.CreateNew(); else e.idleAnimation = null;
        this.despawnConditions.forEach((d) -> {
            e.despawnConditions.add(d.CreateNew());
        });
        for (AITaskBase a : this.AITasks){
            e.AITasks.add(a.CreateNew());
        }
        
        for (IntelligencePropertie i : this.intelligenceProperties){
            e.intelligenceProperties.add(i.CreateNew());
        }
             
        
        this.OnCreateNew(e);
        
        return e;
    }
}
