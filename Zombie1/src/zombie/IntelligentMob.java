/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import enumClasses.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IntelligentMob extends Mob {
 
    public List<IntelligencePropertie> intelligenceProperties = new ArrayList<IntelligencePropertie>();
    
    public IntelligentMob() {
        super();
        Map agression = new HashMap<>();
        //agression.put(enumIntelligencePropertieChangeConditions.OnCollideWithProjectile, 1);
        //intelligenceProperties.add(new IntelligencePropertie("agression", 0, agression));        
    }
    
    @Override
    public void OnCollideWithEntity(Zombie z, Graphics g, Entity collidiongEntity){
        
        super.OnCollideWithEntity(z, g, collidiongEntity);
        
        for (IntelligencePropertie p : this.intelligenceProperties){
            if (p.changeConditions.containsKey(enumIntelligencePropertieChangeConditions.OnCollideWithEntity)){
                p.value += p.changeConditions.get(enumIntelligencePropertieChangeConditions.OnCollideWithEntity);
            }
        }
        
    }
    
    @Override
    public void OnCollideWithProjectile(Zombie z, Graphics g, Projectile collidingProjectile){
        super.OnCollideWithProjectile(z, g, collidingProjectile);
        
        for (IntelligencePropertie p : this.intelligenceProperties){
            if (p.changeConditions.containsKey(enumIntelligencePropertieChangeConditions.OnCollideWithProjectile)){
                p.value += p.changeConditions.get(enumIntelligencePropertieChangeConditions.OnCollideWithProjectile);
            }
        }
        
    }
    
    @Override
    public void OnProjDamage(Zombie z, Graphics g, Entity byEntity){
        super.OnProjDamage(z, g, byEntity);
        for (IntelligencePropertie p : this.intelligenceProperties){
            if (p.changeConditions.containsKey(enumIntelligencePropertieChangeConditions.OnProjDamage)){
                p.value += p.changeConditions.get(enumIntelligencePropertieChangeConditions.OnProjDamage);
            }
        }
    }
    
    @Override
    public void OnMeleeDamage(Zombie z, Graphics g, Entity byEntity){
        super.OnMeleeDamage(z, g, byEntity);
        for (IntelligencePropertie p : this.intelligenceProperties){
            if (p.changeConditions.containsKey(enumIntelligencePropertieChangeConditions.OnMeleeDamage)){
                p.value += p.changeConditions.get(enumIntelligencePropertieChangeConditions.OnMeleeDamage);
            }
        }
    }
    
    @Override
    public IntelligentMob CreateNew(){
        
        IntelligentMob e = new IntelligentMob();
        e.X = this.X;
        e.Y = this.Y;
        e.drawoffsetX = this.drawoffsetX;
        e.drawoffsetY = this.drawoffsetY;
        e.Speed = this.Speed;
        for (String s : this.motions.keySet()){
            e.motions.put(s, this.motions.get(s).clone());
        }
        for (String s : this.attributes.keySet()){
            e.attributes.put(s, this.attributes.get(s).clone());
        }
        e.name = this.name;
        e.Id = this.Id;
        for (Hitbox b : this.hitboxes){
            e.hitboxes.add(new Hitbox(b.sizeX, b.sizeY, b.offsetX, b.offsetY));
        }
        this.despawnConditions.forEach((d) -> {
            e.despawnConditions.add(d.CreateNew());
        });
        e.image = this.image;
        e.imagePath = this.imagePath;
        e.idleAnimPath = this.idleAnimPath;
        e.canClimb = this.canClimb;
        e.invincible = this.invincible;
        e.canReceiveKnockBack = this.canReceiveKnockBack;
        e.chunkLayer = this.chunkLayer;
        if (this.animation != null) e.animation = this.animation.CreateNew(); else e.animation = null;
        if (this.idleAnimation != null) e.idleAnimation = this.idleAnimation.CreateNew(); else e.idleAnimation = null;
        
        for (AITaskBase a : this.AITasks){
            e.AITasks.add(a.CreateNew());
        }
        
        for (IntelligencePropertie i : this.intelligenceProperties){
            e.intelligenceProperties.add(i.CreateNew());
        }
        
        this.OnCreateNew(e);
        
        return e;
    }
    
    public void OnCreateNew(IntelligentMob newCreatedEntity){
        
    }
}
