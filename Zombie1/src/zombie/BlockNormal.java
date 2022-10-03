/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author 123
 */
public class BlockNormal {
    
    public int Animationtimer;
    public int cAnimationtimer = 0;
    public String name = "";
    public int value = 0;
    public int textureId = 0;
    public int verticalTextureId = 0;
    public String texturePath = "";
    public Map<String, String> changetextures = null;
    public Map<String, String> texturechangechance = null;
    public boolean transparent = false;
    public List<Item> drops = new ArrayList<Item>();
    public int breakProgress = 0;
    public int resistance = 1;
    public String material = "dirt";
    public boolean fallThrough = false;
    public boolean shouldReceiveGameTicks = false;
    public int lightLevel = 0;
    public int lightAbsorbtion = 1;
    public boolean breaking = false;
    public String seasonColorMapPath = "";
    
    public void ManageAnimation(Zombie z)
    {
        if (this.changetextures != null)
        {
            cAnimationtimer++;
            if (cAnimationtimer > Animationtimer) cAnimationtimer = 0;
            if (this.changetextures.get(String.valueOf(cAnimationtimer)) != null)
            {
                Random r = new Random();
                double chance = (double)(r.nextInt(1000)) / 1000;
                if (Double.parseDouble(this.texturechangechance.get(String.valueOf(cAnimationtimer))) >= chance)
                    this.textureId = Integer.parseInt(this.changetextures.get(String.valueOf(cAnimationtimer)));
            }
        }
    }
    
    public BlockNormal CreateNew()
    {
        BlockNormal b = new BlockNormal();
        b.Animationtimer = this.Animationtimer;
        b.cAnimationtimer = 0;
        b.name = this.name;
        b.value = this.value;
        b.textureId = this.textureId;
        b.verticalTextureId = this.verticalTextureId;
        b.changetextures = this.changetextures;
        b.texturechangechance = this.texturechangechance;
        b.transparent = this.transparent;
        for (Item o : this.drops){
            b.drops.add(o.CreateNew());
        }
        b.resistance = this.resistance;
        b.material = this.material;
        b.fallThrough = this.fallThrough;
        b.lightLevel = this.lightLevel;
        b.lightAbsorbtion = this.lightAbsorbtion;
        b.seasonColorMapPath = this.seasonColorMapPath;
        b.texturePath = this.texturePath;
        return b;
    }
    
    public String getSaveData(){
        return "";
    }
    
    public void onLoad(String s){
        
    }
    
    public void onPlaced(Zombie z, int x, int y, int chunklayer, String mapName) {
    	
    }
    
    public void onPlaced(Zombie z, int x, int y, int chunklayer, String mapName, Object[] customParams) {
    	
    }
    
    public void OnStepOn(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
        
    }
    
    public void OnInteract(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
        
    }
    
    public void breakBlock(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
        
        WorldAccessor w = new WorldAccessor();
        List<Item> dr = this.getDrops();
        w.GetBlock(z, blockX, blockY, byEntity.chunkLayer, byEntity.onMap).blocknormal = null;
        w.SetBlock(z, "BlockGround", "ladder", blockX, blockY, byEntity.chunkLayer + 1, byEntity.onMap);
        w.GetBlock(z, blockX, blockY + 1, byEntity.chunkLayer + 1, byEntity.onMap).blockground = null;
        w.GetBlock(z, blockX, blockY, byEntity.chunkLayer, byEntity.onMap).SetValue();
        w.GetBlock(z, blockX, blockY, byEntity.chunkLayer + 1, byEntity.onMap).SetValue();
        w.GetBlock(z, blockX, blockY + 1, byEntity.chunkLayer + 1, byEntity.onMap).SetValue();
        
        for (Item i : dr){
            int a = 0;
            boolean found = false;
            while (a < 100){
                
                int x = new Random().nextInt(120) - 60;
                int y = new Random().nextInt(120) - 60;
                if (w.GetBlock(z, blockX + (int)(x/60), blockY + (int)(y/60), byEntity.chunkLayer, byEntity.onMap).Wert == 0)
                {
                    w.SpawnItem(z, i, blockX * 60 + x, blockY * 60 + y, byEntity.chunkLayer, byEntity.onMap);
                    found = true;
                    break;
                }
                a++;
            }
            if (!found){
                w.SpawnItem(z, i, blockX * 60, blockY * 60, byEntity.chunkLayer, byEntity.onMap);
            }
        }
        
        
    }
    
    public randomGenStructure onPlaceForWorldGen(Zombie z, int x, int y, randomGenStructure structure) {
    	
    	
    	return structure;
    }
    
    public void addBreakProgress(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity, int amount){
        
        z.worldAccessor.refreshLight(z, blockX, blockY, byEntity.chunkLayer, byEntity.onMap);
        this.breakProgress += amount;
        if (this.breakProgress > this.resistance) breakBlock(z, g, blockX, blockY, byEntity);
        this.shouldReceiveGameTicks = true;
        this.breaking = true;
    }
    
    public void drawBreakProgress(Zombie z, Graphics g, int a, int b, int depthOffset){
        if (this.breakProgress > 0 && this.breakProgress <= (this.resistance / 5))g.drawImage(z.break1, a * 60 - z.Z + z.screenshakeX, b * 60 - z.Q + z.screenshakeY + depthOffset, z);
	if (this.breakProgress > (this.resistance / 5) && this.breakProgress <= (this.resistance / 5) * 2)g.drawImage(z.break2, a * 60 - z.Z + z.screenshakeX, b * 60 - z.Q + z.screenshakeY + depthOffset, z);
	if (this.breakProgress > (this.resistance / 5) * 2 && this.breakProgress <= (this.resistance / 5) * 3)g.drawImage(z.break3, a * 60 - z.Z + z.screenshakeX, b * 60 - z.Q + z.screenshakeY + depthOffset, z);
	if (this.breakProgress > (this.resistance / 5) * 3 && this.breakProgress <= (this.resistance / 5) * 4)g.drawImage(z.break4, a * 60 - z.Z + z.screenshakeX, b * 60 - z.Q + z.screenshakeY + depthOffset, z);
	if (this.breakProgress > (this.resistance / 5) * 4)g.drawImage(z.break5, a * 60 - z.Z + z.screenshakeX, b * 60 - z.Q + z.screenshakeY + depthOffset, z);
    }
    
    public List<Item> getDrops(){
        
        return this.drops;
    }
    
    public void OnGameTick(Zombie z, Graphics g){
        if (this.breaking == false){
            if (this.breakProgress > 0) this.breakProgress -= (int)(this.resistance / 10) + 1;
            else this.shouldReceiveGameTicks = false;
        }
        else this.breaking = false;
    }
    
}
