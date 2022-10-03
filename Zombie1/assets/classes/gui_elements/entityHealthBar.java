import java.awt.Color;
import zombie.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;

public class entityHealthBar extends HUDElement {
 
    public Image image = null;
    public Entity owningEntity = null;
    public double startValue = 0;
    public Color color = new Color(255,0,0);
    
    public entityHealthBar(){
        super();
        this.type = "entityHealthBar";
    }
    
    @Override
    public void draw(Zombie z, Graphics g){
        //try{g.drawImage(image, this.owningEntity.X - z.Z, this.owningEntity.Y - z.Q + 60, null);
        try{
            if (this.owningEntity.attributes.containsKey("health")){
                g.fillRect(this.owningEntity.hitboxes.get(0).sizeX / 2 - 50 + this.owningEntity.X - z.Z, this.owningEntity.Y - z.Q + 60, (int)(100 / this.startValue * (int)this.owningEntity.attributes.get("health")[0]), 30);
                g.drawImage(image, this.owningEntity.hitboxes.get(0).sizeX / 2 - 50 + this.owningEntity.X - z.Z, this.owningEntity.Y - z.Q + 60, null);
            }
        }catch(Exception e){}
    }
    
    @Override
    public void inizialize(Zombie z, Map<String, Object> m){
        
        try{
            this.image = ImageIO.read(new File("assets/textures/hud/processBar.png")).getScaledInstance(100, 30, BufferedImage.SCALE_FAST);
            this.owningEntity = (Entity)m.get("entity");
            this.startValue = (Double)m.get("startValue");
        }   
        catch(Exception e){System.out.println("Oh shooooit, fug, something ist going very wrong: " + e);}
        
    }
    
    public entityHealthBar CreateNew(){
        return new entityHealthBar();
    }
    
}
