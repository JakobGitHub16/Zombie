/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import zombie.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class sword extends Item {

    int speed = 10;
    public int length = 150;
    public int X = 0, Y = 0;
    int angle = -115, targetangle = 35;
    int size = 360;
    Entity e = null;
    Image image = new ImageIcon("assets/textures/items/weapons/darksword.png").getImage().getScaledInstance(size, size, java.awt.Image.SCALE_FAST);
    
    public sword() {
        super();
        this.type = "melee";
    }
        
    public void LeftClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        
        if (this.shouldReceiveGameTicks == false){
            int dltX = targetX - (int)(usingEntity.X - z.Z + usingEntity.hitboxes.get(0).sizeX / 2);
            int dltY = targetY - (int)(usingEntity.Y - z.Q + usingEntity.hitboxes.get(0).sizeY / 2);

            int dltX2 = dltX;
            int dltY2 = dltY;
            if (dltX2 < 0) dltX2 *= -1;
            if (dltY2 < 0) dltY2 *= -1;

            if (dltY2 > dltX2){
                if (dltY < 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X + usingEntity.hitboxes.get(0).sizeX / 2) - size, (int)(usingEntity.Y) - size, size * 2, size, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = 0; this.Y = -(int)(usingEntity.hitboxes.get(0).sizeY / 2);
                this.angle = -135; this.targetangle = 35;
                e = usingEntity;
                }
                if (dltY > 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X + usingEntity.hitboxes.get(0).sizeX / 2) - size, (int)(usingEntity.Y + usingEntity.hitboxes.get(0).sizeY), size * 2, size, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = 0; this.Y = (int)(usingEntity.hitboxes.get(0).sizeY / 2);
                this.angle = 45; this.targetangle = 215;
                e = usingEntity;
                }


            }

            if (dltX2 > dltY2){
                if (dltX < 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X) - size, (int)(usingEntity.Y + usingEntity.hitboxes.get(0).sizeY / 2) - size, size, size * 2, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = -(int)(usingEntity.hitboxes.get(0).sizeX / 2); this.Y = 0;
                this.angle = -225; this.targetangle = -55; 
                e = usingEntity;
                }
                if (dltX > 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X + usingEntity.hitboxes.get(0).sizeX), (int)(usingEntity.Y + usingEntity.hitboxes.get(0).sizeY / 2) - size, size, size * 2, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = (int)(usingEntity.hitboxes.get(0).sizeX / 2); this.Y = 0;
                this.angle = -45; this.targetangle = 125;
                e = usingEntity;
                }

            }
            this.shouldReceiveGameTicks = true;
        }
    }
 
    public void HeldClick(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        if (this.shouldReceiveGameTicks == false){
            int dltX = targetX - (int)(usingEntity.X - z.Z + usingEntity.hitboxes.get(0).sizeX / 2);
            int dltY = targetY - (int)(usingEntity.Y - z.Q + usingEntity.hitboxes.get(0).sizeY / 2);

            int dltX2 = dltX;
            int dltY2 = dltY;
            if (dltX2 < 0) dltX2 *= -1;
            if (dltY2 < 0) dltY2 *= -1;

            if (dltY2 > dltX2){
                if (dltY < 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X + usingEntity.hitboxes.get(0).sizeX / 2) - size, (int)(usingEntity.Y) - size, size * 2, size, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = 0; this.Y = -(int)(usingEntity.hitboxes.get(0).sizeY / 2);
                this.angle = -135; this.targetangle = 35;
                e = usingEntity;
                }
                if (dltY > 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X + usingEntity.hitboxes.get(0).sizeX / 2) - size, (int)(usingEntity.Y + usingEntity.hitboxes.get(0).sizeY), size * 2, size, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = 0; this.Y = (int)(usingEntity.hitboxes.get(0).sizeY / 2);
                this.angle = 45; this.targetangle = 215;
                e = usingEntity;
                }


            }

            if (dltX2 > dltY2){
                if (dltX < 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X) - size, (int)(usingEntity.Y + usingEntity.hitboxes.get(0).sizeY / 2) - size, size, size * 2, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = -(int)(usingEntity.hitboxes.get(0).sizeX / 2); this.Y = 0;
                this.angle = -225; this.targetangle = -55; 
                e = usingEntity;
                }
                if (dltX > 0)
                {z.hitDetectionBoxes.add(new HitDetectionBox((int)(usingEntity.X + usingEntity.hitboxes.get(0).sizeX), (int)(usingEntity.Y + usingEntity.hitboxes.get(0).sizeY / 2) - size, size, size * 2, 2, this.knockback, usingEntity,((EntityPlayer)usingEntity).inventory.Hotbar[((EntityPlayer)usingEntity).inventory.HotbarSlotSelected].item, usingEntity.chunkLayer));
                this.X = (int)(usingEntity.hitboxes.get(0).sizeX / 2); this.Y = 0;
                this.angle = -45; this.targetangle = 125;
                e = usingEntity;
                }

            }
            this.shouldReceiveGameTicks = true;
        }
        
    }
    
    @Override
    public sword CreateNew(){
        
        sword i = new sword();
        i.name = this.name;
        i.imagePath = this.imagePath;
        i.speed = this.speed;
        i.knockback = this.knockback;
        i.damage = this.damage;
        i.size = this.size;
        i.image = this.image;
        i.maxStackSize = this.maxStackSize;
        i.description = this.description;
        i.NameColor = new Color(this.NameColor.getRGB());
        return i;
    }
    
    @Override
    public void OnGameTick(Zombie z, Graphics g){
        
       
        if (e != null){
            
            BufferedImage newImageFromBuffer = new BufferedImage(size * 6, size * 6, BufferedImage.TYPE_4BYTE_ABGR);

            if (image != null){
            Graphics2D graphics2D = newImageFromBuffer.createGraphics();

            graphics2D.rotate(Math.toRadians(angle), (int)(size * 1.5), (int)(size * 2.4));
            int x = (int) (e.X + e.hitboxes.get(0).sizeX / 2) + this.X - z.Z - (int)(size * 1.5);
            int y = (int) (e.Y + e.hitboxes.get(0).sizeY / 2) + this.Y - z.Q - (int)(size * 2.4);
            graphics2D.drawImage(image, (int)(size * 1.5), (int)(size * 1.5), null);

            g.drawImage(newImageFromBuffer, x, y, z);

            }
            angle += this.speed;
            
        }
        if (angle >= targetangle){//this.X == this.targetX && this.Y == this.targetY
            this.shouldReceiveGameTicks = false;
        }
        
    }
    
    @Override
    public void Inizialize(Map<String, String> attributes){
        if (attributes != null){
            if (attributes.containsKey("damage"))this.damage = Integer.parseInt(attributes.get("damage"));
            if (attributes.containsKey("size"))this.size = Integer.parseInt(attributes.get("size"));
            if (attributes.containsKey("image"))this.image = new ImageIcon(attributes.get("image")).getImage().getScaledInstance(size, size, java.awt.Image.SCALE_FAST);
            if (attributes.containsKey("type"))this.type = attributes.get("type");
            if (attributes.containsKey("knockback"))this.knockback = Integer.parseInt(attributes.get("knockback"));
            if (attributes.containsKey("speed"))this.speed = Integer.parseInt(attributes.get("speed"));
        }
    }
}
