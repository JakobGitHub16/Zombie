/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

/**
 *
 * @author admin
 */
public class EntityDespawnCondition {
    
    public int minPlayerDistance = -1;
    public int maxPlayerDistance = -1;
    public int minLightLevel = -1;
    public int maxLightLevel = -1;
    public double chance = 1;
    
    public EntityDespawnCondition CreateNew(){
        
        EntityDespawnCondition e = new EntityDespawnCondition();
        
        e.minPlayerDistance = this.minPlayerDistance;
        e.maxPlayerDistance = this.maxPlayerDistance;
        e.minLightLevel = this.minLightLevel;
        e.maxLightLevel = this.maxLightLevel;
        e.chance = this.chance;
        
        return e;
    }
    
}
