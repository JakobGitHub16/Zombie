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
public class EntitySpawnCondition {
    
    public double chance = 0;
    public double maxLightLevel = 0;
    public double minLightLevel = 0;
    public String block = "";
    public int groupSize = 1; 
    public int groupRadius = 0;
    public int maxPlayerDistance = 0;
    public int minPlayerDistance = 0;
    public int maxEntAtOnePlace = 1;
    public boolean needsFreeBlock = true;
    public String companions = "";
}
