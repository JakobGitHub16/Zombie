/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 123
 */
public class Projectile {
    public double X, Y;
    public int Damage = 10;
    public double[] Motion = new double[2];
    public int LifetimeMax, Lifetime;
    public int Id = 0;
    public boolean BreakOnWalls = false;
    public int passThroughEntities = 0;
    public Entity sourceEntity = null;
    public int chunklayer = 0;
    public String imagePath = "";
    public String name = "";
    public String onMap = "";
    
    public Projectile()
    {
        
    }
    
    public void draw(Graphics g, int Z, int Q, int depthOffset)
    {
       
    }
    
    public Projectile[] update(Projectile[] projetile, PlayMap feld)
    {
        return projetile;
    }
    
    public boolean isCollidingWithEntity(Zombie z, Entity e){
        
        for (Hitbox h : e.hitboxes){
            if (this.X >= e.X + h.offsetX && this.X < e.X + h.offsetX + h.sizeX)
                if (this.Y >= e.Y + h.offsetY && this.Y < e.Y + h.offsetY + h.sizeY)
                    return true;
        }
        return false;
    }
    
    public void remove(Zombie z){
        z.projectile[this.Id] = null;
    }
    
    public double[] getKnockbackSourcePoint(){
        double[] d = new double[2];
        d[0] = this.X;
        d[1] = this.Y;
        return d;
    }
    
    public void summon(Zombie z, Map<String,Object> m){
        if (m.containsKey("x")) this.X = (int)m.get("x");
        if (m.containsKey("y")) this.Y = (int)m.get("y");
        if (m.containsKey("id")) this.Id = (int)m.get("id");
        if (m.containsKey("chunklayer")) this.chunklayer = (int)m.get("chunklayer");
        if (m.containsKey("map")) this.onMap = (String)m.get("map");
        if (m.containsKey("entity")) this.sourceEntity = (Entity)m.get("entity");
        if (m.containsKey("motion")) this.Motion = (double[])m.get("motion");
    }
    
    public void inizialize(Zombie z, Map<String,String> m){
        
        if (m.containsKey("damage")) this.Damage = Integer.parseInt(m.get("damage")) * this.Damage;
        if (m.containsKey("lifetime")) this.LifetimeMax = Integer.parseInt(m.get("lifetime"));
        if (m.containsKey("breakOnWalls")) this.BreakOnWalls = Boolean.parseBoolean(m.get("breakOnWalls"));
    }
    
    public Projectile CreateNew(){
        Projectile p = new Projectile();
        
        p.Damage = this.Damage;
        p.LifetimeMax = this.LifetimeMax;
        p.BreakOnWalls = this.BreakOnWalls;
        p.imagePath = this.imagePath;
        p.name = this.name;
        
        return p;
    }
}
