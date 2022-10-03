/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import zombie.*;
import java.awt.*;

public class wood extends Item {

    public wood() {
        super();
        this.type = "melee";
    }
        
    public void LeftClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    { 
        
        new WorldAccessor().SetBlock(z, "BlockGround", "log", (int)((targetX + z.Z) / 60), (int)((targetY + z.Q) / 60), usingEntity.chunkLayer, usingEntity.onMap);
        ((EntityPlayer)usingEntity).inventory.takeOutItem(((EntityPlayer)usingEntity).inventory.getSelectedItemSlot(damage), 1);
        
    }
         
    public void IfSelectedInSlot(Zombie z, int targetX, int targetY, Graphics g, Entity holdingEntity){
        
        g.setColor(Color.black);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - 2 - z.Z, (int)((targetY + z.Q) / 60) * 60 - 2 - z.Q, 64, 64);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - 3 - z.Z, (int)((targetY + z.Q) / 60) * 60 - 3 - z.Q, 66, 66);
        g.setColor(Color.yellow);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - z.Z, (int)((targetY + z.Q) / 60) * 60 - z.Q, 60, 60);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - 1 - z.Z, (int)((targetY + z.Q) / 60) * 60 - 1 - z.Q, 62, 62);
        
    }
    
    @Override
    public wood CreateNew(){
        
        wood i = new wood();
        i.image = this.image;
        i.imagePath = this.imagePath;
        i.name = this.name;
        i.damage = this.damage;
        i.type = this.type;
        i.maxStackSize = this.maxStackSize;
        i.description = this.description;
        i.NameColor = new Color(this.NameColor.getRGB());
        return i;
    }
}
