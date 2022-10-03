package zombie;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class WorldAccessor {
    
    public Item GetItem(Zombie z, String name){
        
        boolean returnable = false;
        int id = 0;
        for (id = 0; id <= z.loadedItems.size() - 1; id++)
        {
            if (z.loadedItems.get(id).name.equals(name)){ returnable = true; break; }
        }
        
        if (returnable == true)return z.loadedItems.get(id).CreateNew();
        return null;
    }
    
    public BlockNormal GetBlockNormal(Zombie z, String name){
       
        boolean returnable = false;
        int id = 0;
        for (id = 0; id <= z.loadedNormalBlocks.size() - 1; id++)
        {
            if (z.loadedNormalBlocks.get(id).name.equals(name)){ returnable = true; break; }
        }
        
        if (returnable == true)return z.loadedNormalBlocks.get(id).CreateNew();
        return null;
    }
    
    public BlockGround GetBlockGround(Zombie z, String name){
        boolean returnable = false;
        int id = 0;
        for (id = 0; id <= z.loadedGroundBlocks.size() - 1; id++)
        {
            if (z.loadedGroundBlocks.get(id).name.equals(name)){ returnable = true; break; }
        }
        
        if (returnable == true)return z.loadedGroundBlocks.get(id).CreateNew();
        return null;
    }
    
    public BlockTop GetBlockTop(Zombie z, String name){
        boolean returnable = false;
        int id = 0;
        for (id = 0; id <= z.loadedTopBlocks.size() - 1; id++)
        {
            if (z.loadedTopBlocks.get(id).name.equals(name)){ returnable = true; break; }
        }
        
        if (returnable == true)return z.loadedTopBlocks.get(id).CreateNew();
        return null;
    }
    
    /**
     *
     * @param z
     * @param blockType this is the type of the block, it can be BlockNomal, BlockTop or BlockGround
     * @return
     */
    public boolean SetBlock(Zombie z, String blockType, String BlockName, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
        
    	while (ChunkLayer > z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.size() - 1)
    		z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.add(new Chunk(z));
    	
        if (blockType == "BlockNormal"){
            BlockNormal b = GetBlockNormal(z, BlockName);
            try{
                z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blocknormal = b;
                z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].SetValue();
            }catch(Exception e){return false;}
            return true;
        }
        if (blockType == "BlockGround"){
            BlockGround b = GetBlockGround(z, BlockName);
            try{
                z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blockground = b;
                z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].SetValue();
            }catch(Exception e){return false;}
            return true;
        }
        if (blockType == "BlockTop"){
            BlockTop b = GetBlockTop(z, BlockName);
            try{
                z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blocktop = b;
                z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].SetValue();
            }catch(Exception e){return false;}
            return true;
        }
        return false;
    }
    
    public String GetBlockName(Zombie z, String blockType, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
        
        String name = "";
        
        if (blockType == "BlockNormal"){
            try{
                name = z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blocknormal.name;
            }catch(Exception e){return "";}
            return name;
        }
        if (blockType == "BlockGround"){
            try{
                name = z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blockground.name;
            }catch(Exception e){return "";}
            return name;
        }
        if (blockType == "BlockTop"){
            try{
                name = z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blocktop.name;
            }catch(Exception e){return "";}
            return name;
        }
        return "";
    }
    
    public int GetBlockValue(Zombie z, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
        
        int value = 0;
        
        try{
            value = z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                    [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].Wert;
            return value;
        }
        catch(Exception e)
        {return -1;}
        
    }
    
    public BlockNormal GetBlockNormal(Zombie z, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
       
        try{
            return z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blocknormal;            
        }
        catch(Exception e){ return null; }
    }
    
    public Feld GetBlock(Zombie z, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
       
        try{
            return z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)];    
        }
        catch(Exception e){ return null; }
    }
    
    public BlockGround GetBlockGround(Zombie z, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
       
        try{
            return z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blockground;            
        }
        catch(Exception e){ return null; }
    }
    
    public BlockTop GetBlockTop(Zombie z, int BlockPosX, int BlockPosY, int ChunkLayer, String mapName){
       
        try{
            return z.playMaps.get(mapName).ChunkCollumns[(int)(BlockPosX / 16)][(int)(BlockPosY / 16)].chunklayers.get(ChunkLayer).Spielfeld
                        [(int)(BlockPosX - (BlockPosX / 16) * 16)][(int)(BlockPosY - (BlockPosY / 16) * 16)].blocktop;            
        }
        catch(Exception e){ return null; }
    }
    
    public boolean SpawnItem(Zombie z, Item item, int BlockPosX, int BlockPosY, int layer, String mapName, boolean PosAsBlockPos){
        try{
            z.entities.add(new EntityItem());
            ((EntityItem)z.entities.get(z.entities.size() - 1)).setItem(item);
            z.entities.get(z.entities.size() - 1).X = (int)(BlockPosX * 60);
            z.entities.get(z.entities.size() - 1).Y = (int)(BlockPosY * 60);
            z.entities.get(z.entities.size() - 1).chunkLayer = layer;
            z.entities.get(z.entities.size() - 1).onMap = mapName;
            return true;
        }catch(Exception exception){return false;}
    }
    
    public boolean SpawnItem(Zombie z, Item item, int EntityPosX, int EntityPosY, int layer, String mapName){
        
        try{
            z.entities.add(new EntityItem());
            ((EntityItem)z.entities.get(z.entities.size() - 1)).setItem(item);
            z.entities.get(z.entities.size() - 1).X = EntityPosX;
            z.entities.get(z.entities.size() - 1).Y = EntityPosY;
            z.entities.get(z.entities.size() - 1).chunkLayer = layer;
            z.entities.get(z.entities.size() - 1).onMap = mapName;
            return true;
        }catch(Exception exception){return false;}
    }
    
    public Entity getEntity(Zombie z, String name){
        try{
                boolean returnable = false;
            int id = 0;
                for (id = 0; id <= z.loadedEntities.size() - 1; id++)
                {
                    if (z.loadedEntities.get(id).name.equals(name)){ returnable = true; break; }
                }


            if (returnable == true)return z.loadedEntities.get(id).CreateNew();
        }catch(Exception exception){}
        return null;
    }
    
    public AITaskBase getAITask(Zombie z, String name){
        
        boolean returnable = false;
        if (z.loadedAITasks.containsKey(name))
        if (z.loadedAITasks.get(name).name.equals(name)){ return z.loadedAITasks.get(name).CreateNew(); }
        
        return null;
    }
    
    public GUIElement getGUIElement(Zombie z, String name){
        boolean returnable = false;
        int id = 0;
        for (id = 0; id <= z.loadedGUIElements.size() - 1; id++)
        {
            if (z.loadedGUIElements.get(id).type.equals(name)){ returnable = true; break; }
        }
        
        if (returnable == true)return z.loadedGUIElements.get(id);
        return null;
    }
    
    public boolean SpawnEntity(Zombie z, Entity ent, int BlockPosX, int BlockPosY, int chunklayer, String mapName, boolean PosAsBlockPos){
        
        try{
            if (ent != null){
                z.entities.add(ent);
                if (z.entities.get(z.entities.size() - 1) instanceof Mob
                        || z.entities.get(z.entities.size() - 1) instanceof IntelligentMob)
                {
                    for (AITaskBase aTb : ((Mob)z.entities.get(z.entities.size() - 1)).AITasks)
                    aTb.owningEntity = z.entities.get(z.entities.size() - 1);
                }
                if (z.entities.get(z.entities.size() - 1) instanceof IntelligentMob)
                {
                    for (IntelligencePropertie iP : ((IntelligentMob)z.entities.get(z.entities.size() - 1)).intelligenceProperties)
                    iP.value = ThreadLocalRandom.current().nextInt(-iP.variation, iP.variation + 1) + iP.value;
                }
                z.entities.get(z.entities.size() - 1).X = (int)(BlockPosX * 60);
                z.entities.get(z.entities.size() - 1).Y = (int)(BlockPosY * 60);
                z.entities.get(z.entities.size() - 1).chunkLayer = chunklayer;
                z.entities.get(z.entities.size() - 1).onMap = mapName;
                z.entities.get(z.entities.size() - 1).inizialize(z);
            }
            return true;
        }catch(Exception exception){return false;}
        
    }
    
    public boolean SpawnEntity(Zombie z, Entity ent, int EntityPosX, int EntityPosY, String mapName, int chunklayer){
        
        try{
            if (ent != null){
                z.entities.add(ent);
                if (z.entities.get(z.entities.size() - 1) instanceof Mob
                        || z.entities.get(z.entities.size() - 1) instanceof IntelligentMob)
                {
                    for (AITaskBase aTb : ((Mob)z.entities.get(z.entities.size() - 1)).AITasks)
                    aTb.owningEntity = z.entities.get(z.entities.size() - 1);
                }
                if (z.entities.get(z.entities.size() - 1) instanceof IntelligentMob)
                {
                    for (IntelligencePropertie iP : ((IntelligentMob)z.entities.get(z.entities.size() - 1)).intelligenceProperties)
                    {iP.value = ThreadLocalRandom.current().nextInt(-iP.variation, iP.variation + 1) + iP.value;}
                }
                z.entities.get(z.entities.size() - 1).X = EntityPosX;
                z.entities.get(z.entities.size() - 1).Y = EntityPosY;
                z.entities.get(z.entities.size() - 1).chunkLayer = chunklayer;
                z.entities.get(z.entities.size() - 1).onMap = mapName;
                z.entities.get(z.entities.size() - 1).inizialize(z);
            }
            return true;
        }catch(Exception exception){return false;}
    }
    
    public Projectile getProjectile(Zombie z, String name){
        try{
                boolean returnable = false;
            int id = 0;
                for (id = 0; id <= z.loadedProjectiles.size() - 1; id++)
                {
                    if (z.loadedProjectiles.get(id).name.equals(name)){ returnable = true; break; }
                }


            if (returnable == true)return z.loadedProjectiles.get(id).CreateNew();
        }catch(Exception exception){}
        return null;
    }

    public boolean doesMapExist(Zombie z, String mapName) {
    
    	File file = new File("saves/" + z.worldName + "/maps/" + mapName + ".json");
        if (file.exists())  return true;    	
    	return false;
    }
    
    public PlayMap createPlayMap(Zombie z, int sizeXInChunks, int sizeYInChunks, String mapName, String mapTypePath, boolean generate){
        
        try{
            Map<String,String> m = new newJSONreader().ReadNewJSON(mapTypePath);
            Map<String,String> timeMap = new HashMap<String, String>();
            PlayMap p = new PlayMap(sizeXInChunks * 16, sizeYInChunks * 16, z.loadedNormalBlocks, mapName);
            
            String[] splitTimeParams = m.get("time").replace("{", "").replace("}", "").split(";");
            for (String s : splitTimeParams){timeMap.put(s.split("=")[0], s.split("=")[1]);}
            p.mapTime = new Time(timeMap);
            p.generator = new Generator(m.get("generator").replace("}", "").split("\\{"));
            z.playMaps.put(mapName, p);
            if (generate)p.GenerateChunks(z, p.generator);
        
        
            try{
                File f = new File("saves/" + z.worldName + "/mapproperties/" + mapName + ".txt");
                f.createNewFile();

                FileWriter w = new FileWriter(f);
                w.write("sizeX:" + sizeXInChunks * 16 + "\nsizeY:" + sizeYInChunks * 16);
                w.close();
            }
            catch (Exception e){}
            return p;
        }
        catch(Exception e)
        {System.out.println("[ERROR] something went wrong when trying creating a map: " + e);}
        
        return null;
    }
    
    public Map<String, String> getMapProperties(Zombie z, String mapName){
        
        return new newJSONreader().ReadNewJSON("saves/" + z.worldName + "/mapproperties/" + mapName + ".txt");
        
    }
    
    public void refreshLight(Zombie z, int blockPosX, int blockPosY, int chunklayer, String mapName)
    {
        PlayMap m = z.playMaps.get(mapName);
        
        if (m != null){
            
            ChunkCollumn cc = m.ChunkCollumns[blockPosX / 16][blockPosY / 16];
            
            if (cc != null){
            
                if (cc.chunklayers.size() > chunklayer){
                
                    Feld f = this.GetBlock(z, blockPosX, blockPosY, chunklayer, mapName);
                    if (f != null){
                        BlockNormal bn = f.blocknormal;
                        BlockGround bg = f.blockground;
                        BlockTop bt = f.blocktop;

                        int brightness = f.lightemission;

                        f.brightness = brightness;
                        
                        if (f.brightness > 15) f.brightness = 15;
                        
                        for (int a = blockPosX; a >= blockPosX - brightness; a--){
                            for (int b = blockPosY; b >= blockPosY - brightness; b--){
                                
                                setLight(z, a, b, chunklayer, mapName);
                            }
                        }
                        for (int a = blockPosX + 1; a <= blockPosX + brightness; a++){
                            for (int b = blockPosY; b >= blockPosY - brightness; b--){
                                
                                setLight(z, a, b, chunklayer, mapName);
                            }
                        }
                        for (int a = blockPosX + 1; a <= blockPosX + brightness; a++){
                            for (int b = blockPosY + 1; b <= blockPosY + brightness; b++){
                                
                                setLight(z, a, b, chunklayer, mapName);
                            }
                        }
                        for (int a = blockPosX; a >= blockPosX - brightness; a--){
                            for (int b = blockPosY + 1; b <= blockPosY + brightness; b++){
                                
                               setLight(z, a, b, chunklayer, mapName);
                            }
                        }
                    }
                }
            }
            
        }
        
    }
    
    public void setLight(Zombie z, int a, int b, int chunklayer, String mapName){          
        
            Feld feld = this.GetBlock(z, a, b, chunklayer, mapName);
            int brightness = 0;
            if (feld != null){
                brightness = feld.lightemission;
                Feld feldUp = this.GetBlock(z, a, b - 1, chunklayer, mapName);
                Feld feldDown = this.GetBlock(z, a, b + 1, chunklayer, mapName);
                Feld feldRight = this.GetBlock(z, a + 1, b, chunklayer, mapName);
                Feld feldLeft = this.GetBlock(z, a - 1, b, chunklayer, mapName);



                int brightness2 = 0;
                if (feldUp != null) brightness2 = this.GetBlock(z, a, b - 1, chunklayer, mapName).brightness;
                if (feldDown != null && feldDown.brightness > brightness2) brightness2 = feldDown.brightness;
                if (feldRight != null && feldRight.brightness > brightness2) brightness2 = feldRight.brightness;
                if (feldLeft != null && feldLeft.brightness > brightness2) brightness2 = feldLeft.brightness;

                if (brightness > 0)
                    {feld.brightness = brightness; if (feld.brightness < brightness2)feld.brightness = brightness2 - feld.lightAbsorbtion;if (feld.brightness > 15) feld.brightness = 15; if (feld.brightness < 0) feld.brightness = 0;}
                else 
                    {feld.brightness = brightness2 - feld.lightAbsorbtion;if (feld.brightness > 15) feld.brightness = 15; if (feld.brightness < 0) feld.brightness = 0;}
            }
    }
    
    public List<CraftingRecepie> getRecepiesByCraftingStation(Zombie z, String craftingStationName){
        
        List<CraftingRecepie> recepies = new ArrayList<CraftingRecepie>();
        
        for (CraftingRecepie s : z.loadedCraftingRecepies){
            try{
                if (s.craftingStation.equals(craftingStationName)) recepies.add(s);
            }catch(Exception e){}
        }
        
        return recepies;
    }
    
    public List<CraftingRecepie> getRecepiesByOutputItem(Zombie z, String outputItemName){
        
        List<CraftingRecepie> recepies = new ArrayList<CraftingRecepie>();
        
        for (CraftingRecepie s : z.loadedCraftingRecepies){
            try{
                if (s.output.equals(outputItemName)) recepies.add(s);
            }catch(Exception e){}
        }
        
        return recepies;
    }
}
