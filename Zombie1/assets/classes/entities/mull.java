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

public class mull extends EntityItem {
 
    public List<int[]> markers = new ArrayList<int[]>();
    double previousX = 0;
    double previousY = 0;
    int onSamePlace = 0;
    int searchingRadius = 10;
    int seekingRange = 20;
    int speed = 0;
    
    public mull(){
        super();
    }
    
    @Override
    public void setItem(Item item){
        this.itemslot.item = item;
        this.itemslot.setItem(item.name, item.imagePath, 60);
    }
    
    @Override
    public void draw(Zombie z, Graphics g, int Z, int Q, int shakeX, int shakeY){
        
        try{
        if (this.itemslot.item == null) newItem(z);
        else if (this.itemslot.io != null) g.drawImage(this.itemslot.io, (int)this.X - z.Z, (int)this.Y - z.Q, z);
        }catch(Exception e){}
    }
    
    public void newItem(Zombie z){
        int i = new Random().nextInt(3);
        if (i == 0){this.setItem(new WorldAccessor().GetItem(z, "mull"));}
        if (i == 1){this.setItem(new WorldAccessor().GetItem(z, "mull2"));}
        if (i == 2){this.setItem(new WorldAccessor().GetItem(z, "mull3"));}
        
    }
    
    @Override
    public mull CreateNew(){
        
        mull e = new mull();
        e.X = this.X;
        e.Y = this.Y;
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
        
        this.OnCreateNew(e);
        
        return e;
    }
}
