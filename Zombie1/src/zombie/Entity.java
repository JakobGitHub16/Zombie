/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class Entity {
    
    public int X = 0;
    public int Y = 0;
    public Map<String, double[]> motions = new HashMap<String, double[]>();
    public Map<String, double[]> attributes = new HashMap<String, double[]>();
    public List<Hitbox> hitboxes = new ArrayList<Hitbox>();
    public List<EntitySpawnCondition> worldGenSpawnConditions = new ArrayList<EntitySpawnCondition>();
    public List<EntitySpawnCondition> runtimeSpawnConditions = new ArrayList<EntitySpawnCondition>();
    public List<EntityDespawnCondition> despawnConditions = new ArrayList<EntityDespawnCondition>();
    public String name;
    public int Id = 0;
    public double Speed = 2;
    public int immunityFrames = 0;
    public int chunkLayer = 0;
    public String onMap = "overworld";
    public boolean inNewChunk = false;
    
    public Image image = null;
    public String imagePath = "";
    public String idleAnimPath = "";
          
    public int facing = 0;
    
    public double health = 100;
    public double defense = 0;
    public boolean invincible = false;
    public boolean canReceiveKnockBack = true;
    public boolean canClimb = true;
    public int drawoffsetX = 0, drawoffsetY = 0;
    
    public boolean up = false;
    public boolean down = false;
    public boolean right = false;
    public boolean left = false;
    
    public int timeToFall = 0;
    public int endTimeToFall = 10;
    
    public GUIElement helathBar;
    boolean inizializedHelathBar = false;
    
    public Entity(){
        double[] m = new double[2];
        m[0] = 0;
        m[1] = 0;
        hitboxes.add(new Hitbox(59,59,0,0));
        motions.put("movement", m);
        motions.put("knockback", m.clone());
    }
    
    public int getxAxis() {
        return X;
    }

    public void setxAxis(int xAxis) {
        this.X = xAxis;
    }

    public int getyAxis() {
        return Y;
    }

    public void setyAxis(int yAxis) {
        this.Y = yAxis;
    }
    
    public void draw(Zombie z, Graphics g, int Z, int Q, int shakeX, int shakeY, int depthOffset){
        try{this.helathBar.draw(z, g);}catch(Exception e){}
    }
    
    public void OnGameTick(Zombie z, Graphics g){
        
        List<Entity> collidingEntites;
        if ((collidingEntites = hitboxes.get(0).detectCollisionWithEntity(z, this)) != null){
            for (Entity e : collidingEntites)
                this.OnCollideWithEntity(z, g, e);
        }
        
        List<Projectile> collidingProjectiles;
        if ((collidingProjectiles = hitboxes.get(0).detectCollisionWithProjectile(z, this)) != null){
            for (Projectile p : collidingProjectiles){
                this.OnCollideWithProjectile(z, g, p);
                if (p.passThroughEntities == 0) p.remove(z);
            }
        }
        List<HitDetectionBox> collidingHitDetectionBoxes;
        if ((collidingHitDetectionBoxes = hitboxes.get(0).detectCollisionWithHitDetectionBox(z, this)) != null){
            for (HitDetectionBox h : collidingHitDetectionBoxes){
                this.OnCollideWithHitDetectionBox(z, g, h);
            }
        }
        
        if (this.attributes.containsKey("health")){ if (this.attributes.get("health")[0] <= 0) Die(z); }
        
        if (this.immunityFrames > 0) this.immunityFrames--;
        
        if (z.timeSinceStart >= 5){
            WorldAccessor w = new WorldAccessor();
            if (z.playMaps.get(this.onMap).ChunkCollumns[this.X / 60 / 16][this.Y / 60 / 16] != null){
                Feld block1 = w.GetBlock(z, (int)((this.X + this.hitboxes.get(0).offsetX) / 60), (int)((this.Y + this.hitboxes.get(0).offsetY) / 60), this.chunkLayer, this.onMap);
                Feld block2 = w.GetBlock(z, (int)((this.X + this.hitboxes.get(0).offsetX + this.hitboxes.get(0).sizeX) / 60), (int)((this.Y + this.hitboxes.get(0).offsetY) / 60), this.chunkLayer, this.onMap);
                Feld block3 = w.GetBlock(z, (int)((this.X + this.hitboxes.get(0).offsetX) / 60), (int)((this.Y + this.hitboxes.get(0).offsetY + this.hitboxes.get(0).sizeY) / 60), this.chunkLayer, this.onMap);
                Feld block4 = w.GetBlock(z, (int)((this.X + this.hitboxes.get(0).offsetX + this.hitboxes.get(0).sizeX) / 60), (int)((this.Y + this.hitboxes.get(0).offsetY + this.hitboxes.get(0).sizeY) / 60), this.chunkLayer, this.onMap);
                if ((block1 == null || (block1 != null && block1.blocknormal == null)
                        || (block1 != null && block1.blocknormal != null && block1.blocknormal.fallThrough == true))
                        &&
                        (block2 == null || (block2 != null && block2.blocknormal == null)
                        || (block2 != null && block2.blocknormal != null && block2.blocknormal.fallThrough == true))
                        &&
                        (block3 == null || (block3 != null && block3.blocknormal == null)
                        || (block3 != null && block3.blocknormal != null && block3.blocknormal.fallThrough == true))
                        &&
                        (block4 == null || (block4 != null && block4.blocknormal == null)
                        || (block4 != null && block4.blocknormal != null && block4.blocknormal.fallThrough == true))){
                    this.timeToFall++;
                    if (this.chunkLayer >= z.viewedChunkLayer){
                        g.setColor(Color.red);
                        g.drawRect(this.X + this.hitboxes.get(0).offsetX + (this.hitboxes.get(0).sizeX / 2) - 50 - z.Z, this.Y + this.hitboxes.get(0).offsetY - 50 - z.Q, 100, 40);
                        g.fillRect(this.X + this.hitboxes.get(0).offsetX + (this.hitboxes.get(0).sizeX / 2) - 50 - z.Z, this.Y + this.hitboxes.get(0).offsetY - 50 - z.Q, 100 / this.endTimeToFall * this.timeToFall, 40);
                    }
                    if (this.timeToFall >= this.endTimeToFall){
                        this.chunkLayer++;
                        this.timeToFall = 0;
                        try{if (this.equals(z.controllablePlayers.get(0).entity)) z.viewedChunkLayer++;}catch(Exception e){}
                    }
                }
                else this.timeToFall = 0;
            }
        }
    }
    
    public void UpdatePosition(Zombie z, PlayMap Spielkarte, int xscreensize, int yscreensize, boolean moveCamera) {
        
    	this.inNewChunk = false;
        
        int x = ((int)this.X / 60 / 16);
        int y = ((int)this.Y / 60 / 16);
            	
        if (this.motions.get("movement")[0] > 0)this.facing = 1;
        if (this.motions.get("movement")[0] < 0)this.facing = 0;
        
        Object[] motionNames = this.motions.keySet().toArray();
        double[] m = new double[2];
        m[0] = 0;
        m[1] = 0;
        
        for (Object o : motionNames){
            m[0] += this.motions.get(o.toString())[0];
            m[1] += this.motions.get(o.toString())[1];
        }
        
        
        if (testRight(z, Spielkarte, xscreensize) && m[0] > 0) {
                boolean onFreeBlockOrMotionNotCompleted = true;
                double i = 0;
                while (onFreeBlockOrMotionNotCompleted == true){
                    try{
                        Feld feld = new WorldAccessor().GetBlock(z, (X + 60) / 60, Y / 60, this.chunkLayer, this.onMap);
                        Feld feld2 = new WorldAccessor().GetBlock(z, (X + 60) / 60, (Y + 59) / 60, this.chunkLayer, this.onMap);
                        if (feld.Wert != 0 || feld2.Wert != 0) {this.motions.get("knockback")[0] = 0;this.motions.get("knockback")[1] = 0; break;}
                        if (feld.Wert == 0 && feld2.Wert == 0) {this.X++;if (moveCamera)z.Z++;}
                        i++;
                        if (i > m[0])break;
                    }catch(Exception e){if (this.X + this.hitboxes.get(0).offsetX + this.hitboxes.get(0).sizeX > z.Spielkarte.SizeX * 60) this.X = z.Spielkarte.SizeX * 60 - this.hitboxes.get(0).offsetX - this.hitboxes.get(0).sizeX; break;}           
                }
        }
        if (testLeft(z, Spielkarte, xscreensize) && m[0] < 0) {
                boolean onFreeBlockOrMotionNotCompleted = true;
                 double i = 0;
                 while (onFreeBlockOrMotionNotCompleted == true){
                     try{
                         Feld feld = new WorldAccessor().GetBlock(z, (X - 1) / 60, (Y + 59) / 60, this.chunkLayer, this.onMap);
                         Feld feld2 = new WorldAccessor().GetBlock(z, (X - 1) / 60, (Y + 59) / 60, this.chunkLayer, this.onMap);
                         if (feld.Wert != 0 || feld2.Wert != 0) {this.motions.get("knockback")[0] = 0;this.motions.get("knockback")[1] = 0; break;}
                         if (feld.Wert == 0 && feld2.Wert == 0) {this.X--;if (moveCamera)z.Z--;}
                         i--;
                         if (i < m[0])break;
                     }catch(Exception e){if (this.X < 0) this.X = 0; break;}           
                 }
        }
        if (testUp(z, Spielkarte, xscreensize) && m[1] < 0) {
                 boolean onFreeBlockOrMotionNotCompleted = true;
                 double i = 0;
                 while (onFreeBlockOrMotionNotCompleted == true){
                     try{
                         Feld feld = new WorldAccessor().GetBlock(z, (X) / 60, (Y - 1) / 60, this.chunkLayer, this.onMap);
                         Feld feld2 = new WorldAccessor().GetBlock(z, (X + 59) / 60, (Y - 1) / 60, this.chunkLayer, this.onMap);
                         if (feld.Wert != 0 || feld2.Wert != 0) {this.motions.get("knockback")[0] = 0;this.motions.get("knockback")[1] = 0; break;}
                         if (feld.Wert == 0 && feld2.Wert == 0) {this.Y--;if (moveCamera)z.Q--;}
                         i--;
                         if (i < m[1])break;
                     }catch(Exception e){if (this.Y < 0) this.Y = 0; break;}           
                 }
        }
        if (testDown(z, Spielkarte, xscreensize) && m[1] > 0) {
                boolean onFreeBlockOrMotionNotCompleted = true;
                 double i = 0;
                 while (onFreeBlockOrMotionNotCompleted == true){
                     try{
                         Feld feld = new WorldAccessor().GetBlock(z, (X) / 60, (Y + 60) / 60, this.chunkLayer, this.onMap);
                         Feld feld2 = new WorldAccessor().GetBlock(z, (X + 59) / 60, (Y + 60) / 60, this.chunkLayer, this.onMap);
                         if (feld.Wert != 0 || feld2.Wert != 0) {this.motions.get("knockback")[0] = 0;this.motions.get("knockback")[1] = 0; break;}
                         if (feld.Wert == 0 && feld2.Wert == 0) {this.Y++;if (moveCamera)z.Q++;}
                         i++;
                         if (i > m[1])break;
                     }catch(Exception e){if (this.Y + this.hitboxes.get(0).offsetY + this.hitboxes.get(0).sizeY > z.Spielkarte.SizeY * 60) this.Y = z.Spielkarte.SizeY * 60 - this.hitboxes.get(0).offsetY - this.hitboxes.get(0).sizeY; break;}
                 }
        }
        
        {
            if (this.motions.get("knockback")[0] > 0) { this.motions.get("knockback")[0] -= 1; if (this.motions.get("knockback")[0] < 0) this.motions.get("knockback")[0] = 0; }
            if (this.motions.get("knockback")[0] < 0) { this.motions.get("knockback")[0] += 1; if (this.motions.get("knockback")[0] > 0) this.motions.get("knockback")[0] = 0; }
            if (this.motions.get("knockback")[1] > 0) { this.motions.get("knockback")[1] -= 1; if (this.motions.get("knockback")[1] < 0) this.motions.get("knockback")[1] = 0; }
            if (this.motions.get("knockback")[1] < 0) { this.motions.get("knockback")[1] += 1; if (this.motions.get("knockback")[1] > 0) this.motions.get("knockback")[1] = 0; }   
        }
        
        
        if (moveCamera == true){
            try {
                if (this.X + this.hitboxes.get(0).sizeX / 2 < xscreensize / 2) {
                    z.Z = 0;
                }
                if (this.X + this.hitboxes.get(0).sizeX / 2 > (Spielkarte.SizeX * 60 - xscreensize / 2)) {
                    z.Z = (Spielkarte.SizeX * 60 - xscreensize);
                }
                if (this.Y + this.hitboxes.get(0).sizeY / 2 < yscreensize / 2) {
                    z.Q = 0;
                }
                if (this.Y + this.hitboxes.get(0).sizeY / 2 > (Spielkarte.SizeY * 60 - yscreensize / 2)) {
                    z.Q = (Spielkarte.SizeY * 60 - yscreensize);
                }
            } catch (Exception e) {
                System.out.println("[ERROR]: Screen-wach-area out of borders in UpdatePosition()");
            }
        }
        
        if (x < (this.X / 60 / 16)) this.inNewChunk = true;
        if (x > (this.X / 60 / 16)) this.inNewChunk = true;
        if (y < (this.Y / 60 / 16)) this.inNewChunk = true;
        if (y > (this.Y / 60 / 16)) this.inNewChunk = true;
        
    }
    
    public Boolean testRight(Zombie z, PlayMap playmap, int xscreensize) {
        try {
            Feld feld = new WorldAccessor().GetBlock(z, (X + 60) / 60, (Y) / 60, this.chunkLayer, this.onMap);
            Feld feld2 = new WorldAccessor().GetBlock(z, (X + 60) / 60, (Y + 59) / 60, this.chunkLayer, this.onMap);
            
            if (this.X + this.hitboxes.get(0).offsetX + this.hitboxes.get(0).sizeX >= playmap.SizeX * 60) {
                return false;
            }
            if (feld.Wert != 0 || feld2.Wert != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            //System.out.println("[ERROR]: Hero out of array in funk testRight()");
            return false;
        }
    }

    public Boolean testLeft(Zombie z, PlayMap playmap, int xscreensize) {
        try {
            
            Feld feld = new WorldAccessor().GetBlock(z, (X - 1) / 60, (Y) / 60, this.chunkLayer, this.onMap);
            Feld feld2 = new WorldAccessor().GetBlock(z, (X - 1) / 60, (Y + 59) / 60, this.chunkLayer, this.onMap);
            
            if (this.X <= 0) {
                return false;
            }
            if (feld.Wert != 0 || feld2.Wert != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            //System.out.println("[ERROR]: Hero out of array in funk testLeft()");
            return false;
        }
    }

    public Boolean testUp(Zombie z, PlayMap playmap, int yscreensize) {
        try {
            
            Feld feld = new WorldAccessor().GetBlock(z, (X) / 60, (Y - 1) / 60, this.chunkLayer, this.onMap);
            Feld feld2 = new WorldAccessor().GetBlock(z, (X + 59) / 60, (Y - 1) / 60, this.chunkLayer, this.onMap);
            
            if (this.Y <= 0) {
                return false;
            }
            if (feld.Wert != 0 || feld2.Wert != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            //System.out.println("[ERROR]: Hero out of array in funk testUp()");
            return false;
        }
    }

    public Boolean testDown(Zombie z, PlayMap playmap, int yscreensize) {
        try {
            
            Feld feld = new WorldAccessor().GetBlock(z, (X) / 60, (Y + 60) / 60, this.chunkLayer, this.onMap);
            Feld feld2 = new WorldAccessor().GetBlock(z, (X + 59) / 60, (Y + 60) / 60, this.chunkLayer, this.onMap);
            
            if (this.Y + this.hitboxes.get(0).offsetY + this.hitboxes.get(0).sizeY >= playmap.SizeY * 60) {
                return false;
            }
            if (feld.Wert != 0 || feld2.Wert != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            //System.out.println("[ERROR]: Hero out of array in funk testDown()");
            return false;
        }
    }
    
    
    //----------------------------------------------------------------------------------------------------------EVENTS
    public void OnCollideWithEntity(Zombie z, Graphics g, Entity collidingEntity){
        if (z.EntityDebuginfo){
            g.setColor(Color.red);
            for (Hitbox h : this.hitboxes){
                g.drawRect(this.X + h.offsetX - z.Z, this.Y + h.offsetY - z.Q, h.sizeX, h.sizeY);
            }
            for (Hitbox h : collidingEntity.hitboxes){
                g.drawRect(collidingEntity.X + h.offsetX - z.Z, collidingEntity.Y + h.offsetY - z.Q, h.sizeX, h.sizeY);
            }
        }
    }
    
    public void OnCollideWithProjectile(Zombie z, Graphics g, Projectile collidingProjectile){
        if (immunityFrames == 0){
            
            //if (((Mob)this).targetingEntities.size() == 0)
            //((Mob)this).targetingEntities.add(collidingProjectile.sourceEntity);
            double[] d = collidingProjectile.getKnockbackSourcePoint();
            this.receiveKnockback((int)d[0], (int)d[1], (int)this.X + 30, (int)this.Y + 30, 2);
            this.OnProjDamage(z, g, collidingProjectile.sourceEntity);
            immunityFrames = 3;
            this.receiveDamage(collidingProjectile.Damage, collidingProjectile.sourceEntity);
        }
    }
    
    public void OnCollideWithHitDetectionBox(Zombie z, Graphics g, HitDetectionBox hDB){
        
        if (immunityFrames == 0){
            
            //if (((Mob)this).targetingEntities.size() == 0)
            //((Mob)this).targetingEntities.add(collidingProjectile.sourceEntity);
            this.receiveKnockback((int)hDB.owningEntity.X + 30, (int)hDB.owningEntity.Y + 30, (int)this.X + 30, (int)this.Y + 30, hDB.knockback);
            this.OnMeleeDamage(z, g, hDB.owningEntity);
            immunityFrames = 3;
            this.receiveDamage(hDB.usedItem.damage, hDB.owningEntity);
        }
    }
    
    public void OnProjDamage(Zombie z, Graphics g, Entity byEntity){
        
    }
    
    public void OnMeleeDamage(Zombie z, Graphics g, Entity byEntity){
        
    }
    
    //----------------------------------------------------------------------------------------------------------EVENTS END
    
    public void receiveKnockback(int fromX, int fromY, int middlePointX, int middlePointY, double amount){
        if (this.canReceiveKnockBack){
            double antiKB = 0;
            if (this.attributes.containsKey("kBResistance")) antiKB = this.attributes.get("kBResistance")[0];
            double dltX = middlePointX - fromX;
            double dltY = middlePointY - fromY;
            double dltXAlt = dltX;

            if (antiKB == 0) antiKB = 1;  
            
            dltX = (amount / (Math.sqrt(dltXAlt*dltXAlt + dltY*dltY))) * dltXAlt / antiKB;
            dltY = (amount / (Math.sqrt(dltXAlt*dltXAlt + dltY*dltY))) * dltY / antiKB;
            //System.out.println("dltX = " + dltX);
            this.motions.get("knockback")[0] = dltX;
            this.motions.get("knockback")[1] = dltY;
            this.motions.get("movement")[0] = 0;
            this.motions.get("movement")[1] = 0;
        }
    }
    
    public void receiveDamage(double amount, Entity sourceEntity){
        if (!this.invincible){
            
            if (this.attributes.containsKey("health")){
                double damage = 0;
                if (this.attributes.containsKey("defense")) {damage = amount - this.attributes.get("defense")[0];}
                else {damage = amount;}
                if (damage <= 0) if (Math.random() > 0.5) damage = 2; else damage = 1;
                this.attributes.get("health")[0] -= damage;
            }
        }
    }
    
    /**
     *
     * @param amount
     * @param sourceBlock = the block (!!! not BlockNormal, BlockTop, BlockGround, but the Feld!!!)
     */
    public void receiveDamage(double amount, Feld sourceBlock){
        if (!this.invincible){
            
            if (this.attributes.containsKey("health")){
                double damage = 0;
                if (this.attributes.containsKey("defense")) {damage = amount - this.attributes.get("defense")[0];}
                else {damage = amount;}
                if (damage <= 0) if (Math.random() > 0.5) damage = 2; else damage = 1;
                this.attributes.get("health")[0] -= damage;
            }
        }
    }
    
    public void Die(Zombie z){
        if (z.entities.contains(this)){
            z.entities.remove(this);
        }
    }
    
    public void drawDebugInfo(Zombie z, Graphics g){
        g.setColor(Color.YELLOW);
        for (Hitbox h : this.hitboxes){
            g.drawRect(this.X + h.offsetX - z.Z, this.Y + h.offsetY - z.Q, h.sizeX, h.sizeY);
        }
    }   
    
    public List<Feld> getHitboxBlockedBlocks(Zombie z){

        List<Feld> blocks = new ArrayList<Feld>();
        WorldAccessor w = new WorldAccessor();
        
        for (Hitbox b : this.hitboxes){
            for (int a = 0; a <= b.sizeX; a += 60){
                for (int c = 0; c <= b.sizeY; c += 60){
                    Feld d = null;
                    if (!blocks.contains(d = w.GetBlock(z, this.X + b.offsetX + a, this.Y + b.offsetY + c, this.chunkLayer, this.onMap)))
                    {
                        blocks.add(d);
                    }
                }   
            }
        }
        
        return blocks;
    }
    
    public Entity CreateNew(){
        
        Entity e = new Entity();
        e.X = this.X;
        e.Y = this.Y;
        e.drawoffsetX = this.drawoffsetX;
        e.drawoffsetY = this.drawoffsetY;
        e.Speed = this.Speed;
        this.motions.keySet().forEach((s) -> {
            e.motions.put(s, this.motions.get(s).clone());
        });
        this.attributes.keySet().forEach((s) -> {
            e.attributes.put(s, this.attributes.get(s).clone());
        });
        e.name = this.name;
        e.Id = this.Id;
        this.hitboxes.forEach((b) -> {
            e.hitboxes.add(new Hitbox(b.sizeX, b.sizeY, b.offsetX, b.offsetY));
        });
        this.despawnConditions.forEach((d) -> {
            e.despawnConditions.add(d.CreateNew());
        });
        e.image = this.image;
        e.imagePath = this.imagePath;
        e.idleAnimPath = this.idleAnimPath;
        e.chunkLayer = this.chunkLayer;
        e.canClimb = this.canClimb;
        e.invincible = this.invincible;
        e.canReceiveKnockBack = this.canReceiveKnockBack;
        this.OnCreateNew(e);
        
        return e;
    }
    
    public void OnCreateNew(Entity newCreatedEntity){
        
    }
    
    public void inizialize(Zombie z){
        helathBar = z.worldAccessor.getGUIElement(z, "entityHealthBar").CreateNew();
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("entity", this);
        m.put("startValue", this.attributes.get("health")[0]);
        helathBar.inizialize(z, m);
        inizializedHelathBar = true;
    }
}
