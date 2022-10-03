/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Hitbox {
    
    public int sizeX = 0;
    public int sizeY = 0;
    public int offsetX = 0;
    public int offsetY = 0;
    
    public Hitbox(int sizeX, int sizeY, int offsetX, int offsetY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    public List<Entity> detectCollisionWithEntity(Zombie z, Entity owningEntity)
    {
        List<Entity> collidingEntities = new ArrayList<Entity>();
        
        for (Entity e : z.entities)
        {
            if (!e.equals(owningEntity))
            {
                boolean found = false;
                if (e.chunkLayer == owningEntity.chunkLayer && e.onMap.equals(owningEntity.onMap))
                for (Hitbox h : e.hitboxes){
                    for (Hitbox owningH : owningEntity.hitboxes){
                        if (
                                ((e.X + h.offsetX           >= owningEntity.X + owningH.offsetX   && e.X + h.offsetX            <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (e.Y + h.offsetY           >= owningEntity.Y + owningH.offsetY   && e.Y + h.offsetY            <= owningEntity.Y + owningH.sizeY + owningH.offsetY)) ||
                                
                                ((e.X + h.offsetX + h.sizeX >= owningEntity.X + owningH.offsetX   && e.X + h.sizeX + h.offsetX  <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (e.Y + h.offsetY + h.sizeY >= owningEntity.Y + owningH.offsetY   && e.Y + h.sizeY + h.offsetY  <= owningEntity.Y + owningH.sizeY + owningH.offsetY)) ||
                                
                                ((e.X + h.offsetX           >= owningEntity.X + owningH.offsetX   && e.X + h.offsetX            <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (e.Y + h.offsetY + h.sizeY >= owningEntity.Y + owningH.offsetY   && e.Y + h.sizeY + h.offsetY  <= owningEntity.Y + owningH.sizeY + owningH.offsetY)) ||
                                
                                ((e.X + h.offsetX + h.sizeX >= owningEntity.X + owningH.offsetX   && e.X + h.sizeX + h.offsetX  <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (e.Y + h.offsetY           >= owningEntity.Y + owningH.offsetY   && e.Y + h.offsetY            <= owningEntity.Y + owningH.sizeY + owningH.offsetY))
                        )
                        {
                            collidingEntities.add(e);
                            found = true;
                            break;
                        }
                    }
                    if (found == true) break;
                }
            }
        }
        
        if (collidingEntities.size() > 0) return collidingEntities;
        
        return null;
    }
    
    public List<Projectile> detectCollisionWithProjectile(Zombie z, Entity owningEntity)
    {
        List<Projectile> collidingProjectiles = new ArrayList<Projectile>();
        
        for (Projectile p : z.projectile)
        {
            if (p != null)
            if (p.chunklayer == owningEntity.chunkLayer && p.sourceEntity.onMap.equals(owningEntity.onMap))
            if (p.sourceEntity != null)
            if (!(p.sourceEntity.equals(owningEntity)))
            if (p.isCollidingWithEntity(z, owningEntity)) collidingProjectiles.add(p);            
        }
        
        if (collidingProjectiles.size() > 0) return collidingProjectiles;
        
        return null;
    }
    
    public List<HitDetectionBox> detectCollisionWithHitDetectionBox(Zombie z, Entity owningEntity)
    {
        List<HitDetectionBox> collidingBox = new ArrayList<HitDetectionBox>();
        
        for (HitDetectionBox p : z.hitDetectionBoxes)
        {
            if (p != null)
            if (p.owningEntity != null)
            if (!(p.owningEntity.equals(owningEntity))){
                
                boolean found = false;
                if (p.chunkLayer == owningEntity.chunkLayer && p.owningEntity.onMap.equals(owningEntity.onMap))
                    for (Hitbox owningH : owningEntity.hitboxes){
                        if (
                                (((p.X           >= owningEntity.X + owningH.offsetX   && p.X            <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (p.Y            >= owningEntity.Y + owningH.offsetY   && p.Y            <= owningEntity.Y + owningH.sizeY + owningH.offsetY)) ||
                                
                                ((p.X + p.sizeX >= owningEntity.X + owningH.offsetX   && p.X + p.sizeX  <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (p.Y + p.sizeY  >= owningEntity.Y + owningH.offsetY   && p.Y + p.sizeY  <= owningEntity.Y + owningH.sizeY + owningH.offsetY)) ||
                                
                                ((p.X           >= owningEntity.X + owningH.offsetX   && p.X            <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (p.Y+ p.sizeY   >= owningEntity.Y + owningH.offsetY   && p.Y + p.sizeY  <= owningEntity.Y + owningH.sizeY + owningH.offsetY)) ||
                                
                                ((p.X + p.sizeX >= owningEntity.X + owningH.offsetX   && p.X + p.sizeX  <= owningEntity.X + owningH.sizeX + owningH.offsetX) &&
                                (p.Y            >= owningEntity.Y + owningH.offsetY   && p.Y            <= owningEntity.Y + owningH.sizeY + owningH.offsetY)))
                                ||
                                (((owningEntity.X + owningH.offsetX                 >= p.X  && owningEntity.X + owningH.offsetX                     <= p.X + p.sizeX) &&
                                (owningEntity.Y + owningH.offsetY                   >= p.Y   && owningEntity.Y + owningH.offsetY                    <= p.Y + p.sizeY)) ||
                                
                                ((owningEntity.X + owningH.offsetX + owningH.sizeX  >= p.X   && owningEntity.X + owningH.offsetX + owningH.sizeX    <= p.X + p.sizeX) &&
                                (owningEntity.Y + owningH.offsetY                   >= p.Y   && owningEntity.Y + owningH.offsetY                    <= p.Y + p.sizeY)) ||
                                
                                ((owningEntity.X + owningH.offsetX                  >= p.X   && owningEntity.X + owningH.offsetX                    <= p.X + p.sizeX) &&
                                (owningEntity.Y + owningH.offsetY + owningH.sizeY   >= p.Y   && owningEntity.Y + owningH.offsetY + owningH.sizeY    <= p.Y + p.sizeY)) ||
                                    
                                ((owningEntity.X + owningH.offsetX + owningH.sizeX  >= p.X   && owningEntity.X + owningH.offsetX + owningH.sizeX    <= p.X + p.sizeX) &&
                                (owningEntity.Y + owningH.offsetY + owningH.sizeY   >= p.Y   && owningEntity.Y + owningH.offsetY + owningH.sizeY    <= p.Y + p.sizeY)))
                        )
                        {
                            if (!collidingBox.contains(p))collidingBox.add(p);
                            found = true;
                            break;
                        }
                    }
                    if (found == true) break;
                
            }
        }
        
        if (collidingBox.size() > 0) return collidingBox;
        
        return null;
    }
    
}
