/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import static zombie.Zombie.Spielkarte;


public class BeamProjectile extends Projectile {

    public double StartX, StartY, StartXAlt, StartYAlt;
    public Color firstColor = new Color(0,0,0);
    public Color secondColor = new Color(255,255,255);
    public int thickness;
    
    public BeamProjectile() {
        super();
    }
    
    @Override
    public void draw(Graphics g, int Z, int Q, int depthOffset)
    {
        g.setColor(this.firstColor);
        //g.fillOval(Id, Id, Id, Id);
              
        g.drawLine((int)(StartX) - Z, (int)(StartY) - Q + depthOffset, (int)(X) - Z, (int)(Y) - Q + depthOffset);
        
        
        
        g.drawLine((int)(StartX + 1) - Z, (int)(StartY) - Q + depthOffset, (int)(X + 1) - Z, (int)(Y) - Q + depthOffset);
        g.drawLine((int)(StartX - 1) - Z, (int)(StartY) - Q + depthOffset, (int)(X - 1) - Z, (int)(Y) - Q + depthOffset);
        
        g.drawLine((int)(StartX) - Z, (int)(StartY + 1) - Q + depthOffset, (int)(X) - Z, (int)(Y + 1) - Q + depthOffset);
        g.drawLine((int)(StartX) - Z, (int)(StartY - 1) - Q + depthOffset, (int)(X) - Z, (int)(Y - 1) - Q + depthOffset);

        g.setColor(this.secondColor);
        
        for (int a = 2; a <= this.thickness; a++){
            
            g.drawLine((int)(StartX + a) - Z, (int)(StartY) - Q + depthOffset, (int)(X + a) - Z, (int)(Y) - Q + depthOffset);
            g.drawLine((int)(StartX - a) - Z, (int)(StartY) - Q + depthOffset, (int)(X - a) - Z, (int)(Y) - Q + depthOffset);
        
            g.drawLine((int)(StartX) - Z, (int)(StartY + a) - Q + depthOffset, (int)(X) - Z, (int)(Y + a) - Q + depthOffset);
            g.drawLine((int)(StartX) - Z, (int)(StartY - a) - Q + depthOffset, (int)(X) - Z, (int)(Y - a) - Q + depthOffset);
        }
        
    
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
                Feld block2 = Spielkarte.ChunkCollumns
                        [(int)(this.StartX / 60 / 16)]
                        [(int)(this.StartY / 60 / 16)].
                        chunklayers.get(this.chunklayer).
                        Spielfeld   [(int)((int)(this.StartX / 60) - (int)(this.StartX / 60 / 16) * 16)]
                                    [(int)((int)(this.StartY / 60) - (int)(this.StartY / 60 / 16) * 16)];
                
                //System.out.println(this.X + " " + this.Y);
                
                if (this.X > 0 && this.X < feld.SizeX * 60 && this.Y > 0 && (int)(this.Y) < feld.SizeY * 60 && block.Wert != 1)
                {
                    this.X += this.Motion[0];
                    this.Y += this.Motion[1];
                }
                if (this.StartX > 0 && this.StartX < feld.SizeX * 60 && this.StartY > 0 && (int)(this.StartY) < feld.SizeY * 60 && block2.Wert != 1)
                {
                    this.StartX += this.Motion[0];
                    this.StartY += this.Motion[1];
                }
                else
                {
                projetile[this.Id] = null;
                return projetile;
                }
            
                this.Lifetime++;
                if (this.Lifetime >= this.LifetimeMax || (block2.Wert == 1) || (StartXAlt == StartX && StartYAlt == StartY))
                {
                    projetile[this.Id] = null;
                }
                
                /*if (this.X > 0 && this.X < feld.SizeX * 60 && this.Y > 0 && (int)(this.Y) < feld.SizeY * 60 && feld.Spielfeld[(int)(this.X / 60)][(int)(this.Y / 60)].Wert != 1)
                {
                    this.X += this.Motion[0];
                    this.Y += this.Motion[1];
                }
                if (this.StartX > 0 && this.StartX < feld.SizeX * 60 && this.StartY > 0 && (int)(this.StartY) < feld.SizeY * 60 && feld.Spielfeld[(int)(this.StartX / 60)][(int)(this.StartY / 60)].Wert != 1)
                {
                    this.StartX += this.Motion[0];
                    this.StartY += this.Motion[1];
                }
                else
                {
                projetile[this.Id] = null;
                return projetile;
                }
            
                this.Lifetime++;
                if (this.Lifetime >= this.LifetimeMax ||(feld.Spielfeld[(int)(this.StartX / 60)][(int)(this.StartY / 60)].Wert == 1) || (StartXAlt == StartX && StartYAlt == StartY))
                {
                    projetile[this.Id] = null;
                }*/
            }
            catch (Exception e){}
            StartXAlt = StartX;
            StartYAlt = StartY;
        }
        else
        {
            this.X += this.Motion[0];
            this.Y += this.Motion[1];
            this.StartX += this.Motion[0];
            this.StartY += this.Motion[1];
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
        
        double dltX = this.X - this.StartX;
        double dltY = this.Y - this.StartY;
        double length = Math.sqrt((dltX * dltX) + (dltY * dltY));
        
        double testX = 0;
        double xPlus = dltX / length;
        
        for (int b = 0; b <= (int)length; b++){
                testX += xPlus;
                try{
                    for (Hitbox h : e.hitboxes){
                        if (this.StartX + testX >= e.X + h.offsetX && this.StartX + testX < e.X + h.offsetX + h.sizeX)
                            if (this.StartY + (int)(dltY / dltX * testX) >= e.Y + h.offsetY && this.StartY + (int)(dltY / dltX * testX) < e.Y + h.offsetY + h.sizeY)
                            {
                               return true;
                            }
                    }
                   
                }catch(Exception ex){}
            }
        
        return false;
    }
    
    @Override
    public double[] getKnockbackSourcePoint(){
        double[] d = new double[2];
        d[0] = this.StartX;
        d[1] = this.StartY;
        return d;
    }
    
    @Override
    public void inizialize(Zombie z, Map<String,String> m){
        super.inizialize(z, m);
        
        if (m.containsKey("color1")) this.firstColor = new Color(Integer.parseInt(m.get("color1").split("->")[0]),Integer.parseInt(m.get("color1").split("->")[1]),Integer.parseInt(m.get("color1").split("->")[2]));
        if (m.containsKey("color2")) this.secondColor = new Color(Integer.parseInt(m.get("color2").split("->")[0]),Integer.parseInt(m.get("color2").split("->")[1]),Integer.parseInt(m.get("color2").split("->")[2]));
        if (m.containsKey("thickness")) this.thickness = Integer.parseInt(m.get("thickness"));
    }
    
    public void summon(Zombie z, Map<String,Object> m){
        if (m.containsKey("x")) this.X = (int)m.get("x");
        if (m.containsKey("y")) this.Y = (int)m.get("y");
        if (m.containsKey("id")) this.Id = (int)m.get("id");
        if (m.containsKey("startx")) this.StartX = (int)m.get("startx");
        if (m.containsKey("starty")) this.StartY = (int)m.get("starty");
        if (m.containsKey("chunklayer")) this.chunklayer = (int)m.get("chunklayer");
        if (m.containsKey("entity")) this.sourceEntity = (Entity)m.get("entity");
        if (m.containsKey("motion")) this.Motion = (double[])m.get("motion");
    }
    
    public BeamProjectile CreateNew(){
        BeamProjectile p = new BeamProjectile();
        
        p.Damage = this.Damage;
        p.LifetimeMax = this.LifetimeMax;
        p.BreakOnWalls = this.BreakOnWalls;
        p.firstColor = new Color(this.firstColor.getRGB());
        p.secondColor = new Color(this.secondColor.getRGB());
        p.thickness = this.thickness;
        
        return p;
    }
}
