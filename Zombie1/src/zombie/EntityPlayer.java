package zombie;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Color;
import static java.awt.MouseInfo.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import static zombie.Zombie.Spielkarte;

public class EntityPlayer extends Entity {

    int live = 3;
    private BufferedImage HeroWL, HeroWR, HeroSL, HeroSR, HeroAL, HeroAR, HeroDL, HeroDR;
    private BufferedImage HeroWS, HeroSS, HeroAS, HeroDS;
    
    
    //              -------------------------------------GUI
    public Boolean drawEsc = true;
    private ImageIcon hearth1;
    private Image hearth;
    private ImageIcon Optionsl;
    private Image Options;                                         //------------------------------------------  <<<---------------- Hier Escgröße einstellen xD
    public InventoryPlayer inventory = new InventoryPlayer(60, this);
    public GUIElement inventoryGUI = null;
    
    public EntityPlayer() {
        super();
        this.name = "hero";
        try {
            hearth1 = new ImageIcon("assets/textures/miscellaneous/hearth.png");
            Image h = hearth1.getImage();
            hearth = h.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
            
            
            
            /*HeroWL = ImageIO.read(new File("front_walking_left.png"));
            HeroWR = ImageIO.read(new File("front_walking_right.png"));
            HeroSL = ImageIO.read(new File("back_walking_left.png"));
            HeroSR = ImageIO.read(new File("back_walking_right.png"));
            HeroAL = ImageIO.read(new File("left_walking_left.png"));
            HeroAR = ImageIO.read(new File("left_walking_right.png"));
            HeroDL = ImageIO.read(new File("right_walking_left.png"));
            HeroDR = ImageIO.read(new File("right_walking_right.png"));
            //
            HeroWS = ImageIO.read(new File("front_standing_left.png"));
            HeroSS = ImageIO.read(new File("back_standing_left.png"));
            HeroAS = ImageIO.read(new File("left_standing_left.png"));
            HeroDS = ImageIO.read(new File("right_standing_left.png"));
             */
        } catch (Exception ex) {
            System.out.println("[ERROR]: Some Images not found in the constructor Hero()");
        }
        
        //Hotbar[4].setItem("Coldbeam", 100, "assets/textures/items/weapons/lightsamber.png", this.scaleGui);
    }

    @Override
    public void draw(Zombie z, Graphics g, int Z, int Q, int shakeX, int shakeY, int depthOffset) {
        try {
            g.setColor(Color.blue);
            //g.fillRect(X - Z - 120, Y - Q - 120, 240, 240);
            g.fillRect((int)this.X - Z + shakeX, (int)this.Y - Q + shakeY + depthOffset, 60, 60);
        } catch (Exception e) {
            System.out.println("[ERROR]: Hero could not been drawn in drawHero()");
        }
    }

    public void drawGUI(Graphics g, int xscreensize, int yscreensize, Zombie z) {

        if (z.drawGUIs == true) {
            try {
                for (int a = 0; a <= live - 1; a++) {
                    g.drawImage(hearth, xscreensize - (a * z.gUIScale) - z.gUIScale - 20, 10, null);
                }
            } catch (Exception e) {
                System.out.println("[ERROR]: GUI could not been drawn in drawGUI()");
            }
        }
    }

    
    @Override
    public void OnGameTick(Zombie z, Graphics g){
        super.OnGameTick(z, g);
        
        for (ItemSlot i : this.inventory.Itemslots){
            if (i.item != null){
                if (i.item.shouldReceiveGameTicks == true){
                    i.item.OnGameTick(z, g);
                }
            }
        }
        
    }
    
    //------------------------------------Actions
    
    public void heldClickUseItem(Zombie z, Graphics g){
        try{
        if (inventory.mouseslot.item != null) inventory.mouseslot.item.HeldClick(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, z.frame1.getGraphics(), this);
        else if(inventory.Hotbar[inventory.HotbarSlotSelected].item != null) inventory.Hotbar[inventory.HotbarSlotSelected].item.HeldClick(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, z.frame1.getGraphics(), this);
        }catch(Exception e){}
    }
    
    public void ifSelectedItem(Zombie z, Graphics g){
        try{
        if (inventory.mouseslot.item != null) inventory.mouseslot.item.IfSelectedInSlot(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, z.frame1.getGraphics(), this);
        else if(inventory.Hotbar[inventory.HotbarSlotSelected].item != null) inventory.Hotbar[inventory.HotbarSlotSelected].item.IfSelectedInSlot(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, g, this);
        }catch(Exception e){}
    }
    
    public void leftClickUseItem(Zombie z, Graphics g){
        try{
        if (inventory.mouseslot.item != null) inventory.mouseslot.item.LeftClickUse(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, z.frame1.getGraphics(), this);
        else if(inventory.Hotbar[inventory.HotbarSlotSelected].item != null) inventory.Hotbar[inventory.HotbarSlotSelected].item.LeftClickUse(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, z.frame1.getGraphics(), this);
        }catch(Exception e){}
    }

    //-------------------------------------Events
    
    @Override
    public void OnCollideWithEntity(Zombie z, Graphics g, Entity collidingEntity) {
        
        //System.out.println("lol, collided with " + collidingEntity.name);
        if (collidingEntity instanceof EntityItem){
            this.inventory.tryGiveItem(((EntityItem) collidingEntity).itemslot.item, z.gUIScale);
            z.entities.remove(collidingEntity);
        }
    }
    
    @Override
    public void inizialize(Zombie z){
        z.guiElements.add(z.worldAccessor.getGUIElement(z, "inventoryPlayer"));
        z.guiElements.get(0).inizialize(z, null);
        inventoryGUI = z.guiElements.get(0);
    }
    
}
