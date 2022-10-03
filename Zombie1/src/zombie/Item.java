/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.swing.ImageIcon;
import static zombie.Zombie.Spielkarte;

/**
 *
 * @author 123
 */
public class Item {
    
    public Image image;
    //public ImageIcon bild1;
    public String imagePath = "";
    public String name = "";
    public int damage = 0;
    public String type = "";
    public boolean shouldReceiveGameTicks = false;
    public int knockback = 2;
    public int maxStackSize = 10;
    public String description = "";
    public Color NameColor = new Color(155,155,0);
    
    public Item()
    {
    }
    
    public void draw(Graphics g, int scale, int x, int y, Zombie z)
    {
       //bild1 = new ImageIcon("sand.png");
       //Image b = bild1.getImage();
       //bild = b.getScaledInstance(30, 30, java.awt.Image.SCALE_FAST);
       //try{
       
       //ImageIO.read(new File("items/weapons/prism.png"))
               
       g.drawImage(new ImageIcon(this.imagePath).getImage(), x + 20, y, z);
       //}catch(IOException ex){}
    }
    
    public void LeftClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        
    }
    
    public void RightClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        
    }
    
    public void HeldClick(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        
    }
    
    public void OnSelectedInSlot(Zombie z, int targetX, int targetY, Graphics g, Entity holdingEntity){
        
    }
    
    public void IfSelectedInSlot(Zombie z, int targetX, int targetY, Graphics g, Entity holdingEntity){
        
    }
    
    public void Inizialize(Map<String, String> attributes){
        
    }
    
    public Feld getBlock(PlayMap Spielkarte, double x, double y)
    {   
        return Spielkarte.ChunkCollumns
                        [(int)(x / 60 / 16)]
                        [(int)(y / 60 / 16)].
                        chunklayers.get(0).
                        Spielfeld   [(int)((int)(x / 60) - (int)(x / 60 / 16) * 16)]
                                    [(int)((int)(y / 60) - (int)(y / 60 / 16) * 16)];
    }
    
    public Item CreateNew(){
        
        Item i = new Item();
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
    
    public void OnGameTick(Zombie z, Graphics g){
        this.shouldReceiveGameTicks = false;
    }
}
