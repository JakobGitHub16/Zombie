/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import zombie.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.Map;
import javax.swing.ImageIcon;
import static zombie.Zombie.Spielkarte;


public class arrow extends Projectile {

    public double StartX, StartY, StartXAlt, StartYAlt;
    public Color firstColor = new Color(0,0,0);
    public Color secondColor = new Color(255,255,255);
    public int thickness;
    public int size = 60;
    public int rotation = 45;
    public int Startrotation = 45;
    Image image = null;
    
    public arrow() {
        super();
    }
    
    @Override
    public void draw(Graphics g, int Z, int Q, int depthOffset)
    {
        g.drawImage(image, (int)this.X - Z - (int)(this.size / 2), (int)this.Y - Q - (int)(this.size / 2), null);
    }
    
     public Projectile[] update(Projectile[] projetile, PlayMap feld)
    {
        if (this.BreakOnWalls == true)
        {
            try
            {
                Feld block =  Spielkarte.ChunkCollumns
                        [(int)(this.X / 60 / 16)]
                        [(int)(this.Y / 60 / 16)].
                        chunklayers.get(this.chunklayer).
                        Spielfeld   [(int)((int)(this.X / 60) - (int)(this.X / 60 / 16) * 16)]
                                    [(int)((int)(this.Y / 60) - (int)(this.Y / 60 / 16) * 16)];
                
                if (this.X > 0 && this.X < feld.SizeX * 60 && this.Y > 0 && (int)(this.Y) < feld.SizeY * 60 && block.Wert != 1)
                {
                    this.X += this.Motion[0];
                    this.Y += this.Motion[1];
                }
                else
                {
                    projetile[this.Id] = null;
                    return projetile;
                }
            
                this.Lifetime++;
                if (this.Lifetime >= this.LifetimeMax)
                {
                    projetile[this.Id] = null;
                }
                
            }
            catch (Exception e){}
        }
        else
        {
            this.X += this.Motion[0];
            this.Y += this.Motion[1];
            this.Lifetime++;
            if (this.Lifetime >= this.LifetimeMax)
            {
                projetile[this.Id] = null;
            }
        }
        return projetile;
    }
    
    
    @Override
    public boolean isCollidingWithEntity(Zombie z, Entity e){
        
        
        for (Hitbox h : e.hitboxes){
            if ((this.X - (this.size / 2) >= e.X + h.offsetX && this.X - (this.size / 2) < e.X + h.offsetX + h.sizeX)
                    || (this.X + (this.size / 2) >= e.X + h.offsetX && this.X + (this.size / 2) < e.X + h.offsetX + h.sizeX))
                if ((this.Y - (this.size / 2) >= e.Y + h.offsetY && this.Y - (this.size / 2) < e.Y + h.offsetY + h.sizeY)
                    || (this.Y + (this.size / 2) >= e.Y + h.offsetY && this.Y + (this.size / 2) < e.Y + h.offsetY + h.sizeY))
                {
                    return true;
                }
            
            if ((e.X + h.offsetX >= this.X - (this.size / 2) && e.X + h.offsetX < this.X + (this.size / 2))
                    || (e.X + h.offsetX  + h.sizeX >= this.X - (this.size / 2) && e.X + h.offsetX + h.sizeX < this.X + (this.size / 2)))
                if ((e.Y + h.offsetY >= this.Y - (this.size / 2) && e.Y + h.offsetY < this.Y + (this.size / 2))
                    || (e.Y + h.offsetY  + h.sizeY >= this.Y - (this.size / 2) && e.Y + h.offsetY + h.sizeY < this.Y + (this.size / 2)))
                {
                    return true;
                }
        }
        
        return false;
    }
        
    @Override
    public double[] getKnockbackSourcePoint(){
        double[] d = new double[2];
        d[0] = this.X;
        d[1] = this.Y;
        return d;
    }
    
    @Override
    public void inizialize(Zombie z, Map<String,String> m){ 
        super.inizialize(z, m);
        if (m.containsKey("startrotation")) this.Startrotation = Integer.parseInt(m.get("startrotation"));
    }
    
    @Override
    public void summon(Zombie z, Map<String,Object> m){
        
        
        if (m.containsKey("x")) this.X = (int)m.get("x");
        if (m.containsKey("y")) this.Y = (int)m.get("y");
        if (m.containsKey("id")) this.Id = (int)m.get("id");
        if (m.containsKey("startx")) this.StartX = (int)m.get("startx");
        if (m.containsKey("starty")) this.StartY = (int)m.get("starty");
        if (m.containsKey("chunklayer")) this.chunklayer = (int)m.get("chunklayer");
        if (m.containsKey("map")) this.onMap = (String)m.get("map");
        if (m.containsKey("entity")) this.sourceEntity = (Entity)m.get("entity");
        if (m.containsKey("motion")) this.Motion = (double[])m.get("motion");
        
        this.rotation = (int)Math.toDegrees(Math.atan((this.StartY - this.Y) / (this.StartX - this.X))) + this.Startrotation;
        if ((this.StartX - this.X) < 0) this.rotation += 180;
            this.image = new ImageIcon(this.imagePath).getImage();
            this.size = (int)(Math.max(this.image.getHeight(z), this.image.getWidth(z)));
            
            BufferedImage newImageFromBuffer = new BufferedImage(this.image.getWidth(z), this.image.getHeight(z), BufferedImage.TYPE_4BYTE_ABGR);

            if (image != null){
                Graphics2D graphics2D = newImageFromBuffer.createGraphics();

                graphics2D.rotate(Math.toRadians(this.rotation), (int)(this.image.getWidth(z) / 2), (int)(this.image.getHeight(z) / 2));
                graphics2D.drawImage(image, 0, 0, null);

                this.image = newImageFromBuffer;

            }
        
    }
    
    public arrow CreateNew(){
        arrow p = new arrow();
        
        p.Damage = this.Damage;
        p.LifetimeMax = this.LifetimeMax;
        p.BreakOnWalls = this.BreakOnWalls;
        p.firstColor = new Color(this.firstColor.getRGB());
        p.secondColor = new Color(this.secondColor.getRGB());
        p.thickness = this.thickness;
        p.name = this.name;
        p.imagePath = this.imagePath;
        p.size = this.size;
        p.Startrotation = this.Startrotation;
        return p;
    }
}
