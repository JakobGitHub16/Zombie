import java.awt.Color;
import zombie.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class Workbench extends HUDElement {
 
    public Image image = null;
    public Entity owningEntity = null;
    public double startValue = 0;
    public Color color = new Color(255,0,0);
    public ItemSlot[] craftingslots = new ItemSlot[9];
    public int[] craftingslotLosses = new int[9];
    public ItemSlot outputSlot = new ItemSlot(660 + 560 + 10, 240 + 220 + 10);
    public Entity interactingEntity = null;
    
    
    public Workbench(){
        super();
        this.type = "workbench";
        for (int a = 0; a <= craftingslots.length - 1; a++){
            craftingslots[a] = new ItemSlot(660 + 87 + (140 * (a % 3)), 240 + 87 + (140 * (a / 3)));
        }
    }
    
    @Override
    public void draw(Zombie z, Graphics g){
        //try{g.drawImage(image, this.owningEntity.X - z.Z, this.owningEntity.Y - z.Q + 60, null);
        try{
                g.drawImage(image, 660, 240, null);
                for (ItemSlot s : craftingslots){
                    s.draw(g, 140, z);
                }
                this.outputSlot.draw(g, 140, z);
        }catch(Exception e){}
        
        
    }
    
    @Override
    public boolean onKeyPressed(Zombie z, Graphics g, KeyEvent k){
        if (k.getKeyCode() == KeyEvent.VK_ESCAPE){
            for (ItemSlot i : craftingslots){
                            if (i.item != null)
                                z.worldAccessor.SpawnItem(z, i.item, interactingEntity.X, interactingEntity.Y, interactingEntity.chunkLayer, interactingEntity.onMap);
                        }
            z.guiElements.remove(this);
        }
        return true;
    }
    
    @Override
    public void onClicked(Zombie z, Graphics g){
        //try{g.drawImage(image, this.owningEntity.X - z.Z, this.owningEntity.Y - z.Q + 60, null);
        try{
            
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;
            
                if (x >= 660 + 690 && x <= 660 + 750)
                    if (y >= 240 && y <= 240 + 60){
                        
                        for (ItemSlot i : craftingslots){
                            if (i.item != null)
                                z.worldAccessor.SpawnItem(z, i.item, interactingEntity.X, interactingEntity.Y, interactingEntity.chunkLayer, interactingEntity.onMap);
                        }
                        z.guiElements.remove(this);
                    }
                
                for (ItemSlot s : craftingslots){
                        if (s.testClicke(x, y, 140)){
                            if (s.item == null)((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.excangeItemsInSlots(s, 
                                    ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot, 140, z);
                            else if (((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.item == null)((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.excangeItemsInSlots(s, 
                                    ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot, z.gUIScale, z);
                            this.testCrafting(z);
                        }
                }
                if (outputSlot.testClicke(x, y, 140)){
                    if (this.outputSlot.item != null && ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.item == null){
                        ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.excangeItemsInSlots(this.outputSlot, 
                                    ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot, z.gUIScale, z);
                        for (int a = 0; a <= 8; a++){
                            craftingslots[a].stackSize -= craftingslotLosses[a];
                            if (craftingslots[a].stackSize <= 0) craftingslots[a].item = null;
                        }
                        this.testCrafting(z);
                    }
                    else if (this.outputSlot.item != null && ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.item != null){
                        if (((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.item.name.equals(this.outputSlot.item.name)){
                            if (((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.stackSize < ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.item.maxStackSize){
                                for (int a = 0; a <= 8; a++){
                                    craftingslots[a].stackSize -= craftingslotLosses[a];
                                    if (craftingslots[a].stackSize <= 0) craftingslots[a].item = null;
                                }
                                ((EntityPlayer)z.controllablePlayers.get(0).entity).inventory.mouseslot.stackSize++;
                                this.outputSlot.item = null;
                                this.testCrafting(z);
                                
                            }
                        }
                    }
                }
                
                
                
        }catch(Exception e){}
        
        
    }
    
    @Override
    public void inizialize(Zombie z, Map<String, Object> m){
        
        try{
            this.image = ImageIO.read(new File("assets/textures/hud/workbench.png"));
            this.interactingEntity = (Entity)(m.get("entity"));
        }   
        catch(Exception e){System.out.println("Oh shooooit, fug, something ist going very wrong in workbench: " + e);}
        
    }
    
    @Override
    public boolean blockInteractions(int mX, int mY){
        if (mX >= 660 && mX <= 660 + 750)
            if (mY >= 240 && mY <= 240 + 600){
                return true;
            }
        return false;
        
    }
    
    public Workbench CreateNew(){
        return new Workbench();
    }
    
    public void testCrafting(Zombie z){
        
        List<CraftingRecepie> recepies = z.worldAccessor.getRecepiesByCraftingStation(z, "workbench");
        for (CraftingRecepie c : recepies){
            try{
                boolean craft = true;
                String[] s = c.data.get("grid").split(",", -1);
                for (int a = 0; a <= 8; a++){
                    craftingslotLosses[a] = 0;
                    if (this.craftingslots[a].item == null){
                        if(!s[a].equals("")){craft = false;}
                        
                    }
                    if (this.craftingslots[a].item != null){
                        if(s[a].split("#").length > 1) {
                            craftingslotLosses[a] = Integer.parseInt(s[a].split("#")[1]);
                            if(!(this.craftingslots[a].item.name.equals(s[a].split("#")[0]))
                              || this.craftingslots[a].stackSize < Integer.parseInt(s[a].split("#")[1])){craft = false;}
                            
                        }
                        else {
                            craftingslotLosses[a] = 1;
                            if(!(this.craftingslots[a].item.name.equals(s[a]))){craft = false;}
                            
                        }
                    }
                }
                
                if (craft){
                    this.outputSlot.stackSize = c.outputQuantity;
                    this.outputSlot.item = z.worldAccessor.GetItem(z, c.output).CreateNew();
                    this.outputSlot.setItem(c.output, this.outputSlot.item.imagePath, 140);
                    break;
                }
                else{
                    outputSlot.item = null;
                }
                craft = true;
            }
            catch(Exception e){System.out.println("--------------------------------------------------------ERROR : " + e);}
            
        }
                
    }
    
}
