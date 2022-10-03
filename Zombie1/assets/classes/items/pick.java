/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import zombie.*;
import java.awt.*;

public class pick extends Item {

    public int pickPower = 5;
    
    public pick() {
        super();
        this.type = "tool";
    }
        
    public void LeftClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    { 
    }
 
    public void HeldClick(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        if (z.MousePressed)
        try{
            Feld breakingBlock = new WorldAccessor().GetBlock(z, (int) ((targetX + z.Z) / 60), (int) ((targetY + z.Q) / 60), usingEntity.chunkLayer, usingEntity.onMap);    
            if (breakingBlock.blocktop != null)
            {breakingBlock.blocktop.addBreakProgress(z, g, (int) ((targetX + z.Z) / 60), (int) ((targetY + z.Q) / 60), usingEntity, pickPower); return;}
            if (breakingBlock.blockground != null)
            {breakingBlock.blockground.addBreakProgress(z, g, (int) ((targetX + z.Z) / 60), (int) ((targetY + z.Q) / 60), usingEntity, pickPower); return;}
            if (breakingBlock.blocknormal != null)
            {breakingBlock.blocknormal.addBreakProgress(z, g, (int) ((targetX + z.Z) / 60), (int) ((targetY + z.Q) / 60), usingEntity, pickPower); return;}
        }catch (Exception e){}
    }
    
    public void IfSelectedInSlot(Zombie z, int targetX, int targetY, Graphics g, Entity holdingEntity){
        for (GUIElement gui : z.guiElements){
            if (gui.blockInteractions(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y)) return;
        }
        g.setColor(Color.black);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - 2 - z.Z, (int)((targetY + z.Q) / 60) * 60 - 2 - z.Q, 64, 64);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - 3 - z.Z, (int)((targetY + z.Q) / 60) * 60 - 3 - z.Q, 66, 66);
        g.setColor(Color.yellow);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - z.Z, (int)((targetY + z.Q) / 60) * 60 - z.Q, 60, 60);
        g.drawRect((int)((targetX + z.Z) / 60) * 60 - 1 - z.Z, (int)((targetY + z.Q) / 60) * 60 - 1 - z.Q, 62, 62);
    }
    
    @Override
    public pick CreateNew(){
        
        pick i = new pick();
        i.name = this.name;
        i.imagePath = this.imagePath;
        i.pickPower = this.pickPower;
        i.maxStackSize = this.maxStackSize;
        i.description = this.description;
        i.NameColor = new Color(this.NameColor.getRGB());
        return i;
    }
}
