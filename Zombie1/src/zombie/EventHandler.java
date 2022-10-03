package zombie;

import java.awt.Graphics;
import static java.awt.MouseInfo.getPointerInfo;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class EventHandler {
    
    public void HandleEvents(Zombie z, Graphics g){
        
        HandleBlockEventOnStepOn(z, g);
        HandleBlockEventOnIteract(z, g);
        HandleEntitySpawnTick(z, g);
        HandleEntityDespawnTick(z, g);
        HandleChunkGenAndUnload(z, g);
    }
    
    public void HandleBlockEventOnStepOn(Zombie z, Graphics g){
        try{    
            Feld f1, f2, f3, f4;
            
            //right up
            for (Entity e : z.entities){
            	PlayMap Spielkarte = z.playMaps.get(e.onMap);
            	if (Spielkarte != null) {
	                f1 = Spielkarte.ChunkCollumns
	                        [(int)((e.X + 59) / 60 / 16)]
	                        [(int)(e.Y / 60 / 16)].chunklayers.get(e.chunkLayer).
	                        Spielfeld
	                        [(int)((e.X + 59) / 60 - ((e.X + 59) / 60 / 16) * 16)]
	                        [(int)(e.Y / 60 - (e.Y / 60 / 16) * 16)];
	                //right down
	                f2 = Spielkarte.ChunkCollumns
	                        [(int)((e.X + 59) / 60 / 16)]
	                        [(int)((e.Y + 59) / 60 / 16)].chunklayers.get(e.chunkLayer).
	                        Spielfeld[(int)((e.X + 59) / 60 - ((e.X + 59) / 60 / 16) * 16)]
	                        [(int)((e.Y + 59) / 60 - ((e.Y + 59) / 60 / 16) * 16)];
	                //left down
	                f3 = Spielkarte.ChunkCollumns
	                        [(int)((e.X) / 60 / 16)]
	                        [(int)((e.Y + 59) / 60 / 16)].chunklayers.get(e.chunkLayer).
	                        Spielfeld[(int)((e.X) / 60 - ((e.X) / 60 / 16) * 16)]
	                        [(int)((e.Y + 59) / 60 - ((e.Y + 59) / 60 / 16) * 16)];
	                //left up
	                f4 = Spielkarte.ChunkCollumns
	                        [(int)((e.X) / 60 / 16)]
	                        [(int)((e.Y) / 60 / 16)].chunklayers.get(e.chunkLayer).
	                        Spielfeld[(int)((e.X) / 60 - ((e.X) / 60 / 16) * 16)]
	                        [(int)((e.Y) / 60 - ((e.Y) / 60 / 16) * 16)];
	
	
	                {
	                    if (f1.blocknormal != null) f1.blocknormal.OnStepOn(z, g, (int)((e.X + 59) / 60), (int)(e.Y / 60), e); 
	                    if (f1.blockground != null)f1.blockground.OnStepOn(z, g, (int)((e.X + 59) / 60), (int)(e.Y / 60), e); 
	                    if (f1.blocktop != null)f1.blocktop.OnStepOn(z, g, (int)((e.X + 59) / 60), (int)(e.Y / 60), e);
	                }
	                if (!f2.equals(f1))
	                {
	                    if (f2.blocknormal != null)f2.blocknormal.OnStepOn(z, g, (int)((e.X + 59) / 60), (int)((e.Y + 59) / 60), e); 
	                    if (f2.blockground != null)f2.blockground.OnStepOn(z, g, (int)((e.X + 59) / 60), (int)((e.Y + 59) / 60), e); 
	                    if (f2.blocktop != null)f2.blocktop.OnStepOn(z, g, (int)((e.X + 59) / 60), (int)((e.Y + 59) / 60), e);
	                }
	                if (!f3.equals(f2))
	                {
	                    if (f3.blocknormal != null)f3.blocknormal.OnStepOn(z, g, (int)((e.X) / 60), (int)((e.Y + 59) / 60), e); 
	                    if (f3.blockground != null)f3.blockground.OnStepOn(z, g, (int)((e.X) / 60), (int)((e.Y + 59) / 60), e); 
	                    if (f3.blocktop != null)f3.blocktop.OnStepOn(z, g, (int)((e.X) / 60), (int)((e.Y + 59) / 60), e);
	                }
	                if (!f4.equals(f3) && !f4.equals(f1))
	                {
	                    if (f4.blocknormal != null)f4.blocknormal.OnStepOn(z, g, (int)((e.X) / 60), (int)((e.Y) / 60), e); 
	                    if (f4.blockground != null)f4.blockground.OnStepOn(z, g, (int)((e.X) / 60), (int)((e.Y) / 60), e); 
	                    if (f4.blocktop != null)f4.blocktop.OnStepOn(z, g, (int)((e.X) / 60), (int)((e.Y) / 60), e);
	                }
            	}
            }
        }catch(Exception ex){}
    }
    
    public void HandleBlockEventOnIteract(Zombie z, Graphics g){
        if (z.mousepressed == true && z.MousePressed == false)
        {
            try{
                Feld f1;
                
                //right up
                for (Player p : z.controllablePlayers){
                    Entity e = p.entity;
                    PlayMap Spielkarte = z.playMaps.get(e.onMap);
                	if (Spielkarte != null) {
	                    int X = getPointerInfo().getLocation().x + z.Z;
	                    int Y = getPointerInfo().getLocation().y + z.Q;
	                    
                            for (GUIElement gE : z.guiElements){
                                if (gE.blockInteractions(X - z.Z, Y - z.Q)) return;
                            }
                            
	                    f1 = Spielkarte.ChunkCollumns
	                            [(int)(X / 60 / 16)]
	                            [(int)(Y / 60 / 16)].chunklayers.get(e.chunkLayer).
	                            Spielfeld
	                            [(int)(X / 60 - (X / 60 / 16) * 16)]
	                            [(int)(Y / 60 - (Y / 60 / 16) * 16)];
	
	                    {
	                        if (f1.blocknormal != null) f1.blocknormal.OnInteract(z, g, (int)(X / 60), (int)(Y / 60), e); 
	                        if (f1.blockground != null)f1.blockground.OnInteract(z, g, (int)(X / 60), (int)(Y / 60), e); 
	                        if (f1.blocktop != null)f1.blocktop.OnInteract(z, g, (int)(X  / 60), (int)(Y / 60), e);
	                    }
                	}
                }
            }catch(Exception ex){System.out.println("e in HandleBlockEventOnIteract = " + ex);}
        }
    }
    
    public void HandleControllablePalyerRelatedStuff(Zombie z, Graphics g){
        
        for (Player p : z.controllablePlayers){
            HandleItemUsage(z, g, p.entity);
        } 
    }
    
    public void HandleItemUsage(Zombie z, Graphics g, Entity e){
        if (z.mousepressed == true){
            if (((EntityPlayer)e).inventory.BackPackOpen == true){if (!((EntityPlayer)e).inventory.testCursorAboveInventory(z.gUIScale, z)) ((EntityPlayer)e).heldClickUseItem(z, g);}
            else ((EntityPlayer)e).heldClickUseItem(z, g);
            ((EntityPlayer)e).ifSelectedItem(z, g);
        }
    }
    
    public void HandleChunkGenAndUnload(Zombie z, Graphics g){
        
         Set<String> existingMaps = z.playMaps.keySet();
         List<String> mapsWithPlayers = new ArrayList<String>();
         
         for (Player p : z.controllablePlayers){
            PlayMap m = z.playMaps.get(p.entity.onMap);
            mapsWithPlayers.add(p.entity.onMap);
            if (m != null && (p.entity.inNewChunk == true || m.ChunkCollumns[p.entity.X / 60 / 16][p.entity.Y / 60 / 16] == null))
            {
                p.entity.inNewChunk = false; 
                m.UnloadChunk(z); 
                m.GenerateChunks(z, m.generator);
            }
            else if (m == null){
                z.playMaps.put(p.entity.onMap, new PlayMap(100,100,z.loadedNormalBlocks, p.entity.onMap));
                 z.playMaps.get(p.entity.onMap).GenerateChunks(z, z.defaultGenerator);
            }
            
            if (m != null &&  m.ChunkCollumns[p.entity.X / 60 / 16][p.entity.Y / 60 / 16] != null && m.ChunkCollumns[p.entity.X / 60 / 16][p.entity.Y / 60 / 16].chunklayers.size() - 1 <= p.entity.chunkLayer){
                                       
                m.GenerateLayers(z, m.generator);
            }
        }
        
        for (String s : mapsWithPlayers){
            if (!existingMaps.contains(s)) z.playMaps.get(s).UnloadChunk(z);
        }
    }
    
    public void HandleEntitySpawnTick(Zombie z, Graphics g){
        
        Random r = new Random();
            
        boolean spawn = false;
        Set<String> keys = z.playMaps.keySet();
        for (String s : keys){
            PlayMap pm = z.playMaps.get(s);

            for (int chunkX = 0; chunkX <= pm.ChunkCollumns.length - 1; chunkX++)
                for (int chunkY = 0; chunkY <= pm.ChunkCollumns[0].length - 1; chunkY++){

                    if (pm.ChunkCollumns[chunkX][chunkY] != null){
                        for (Chunk c : pm.ChunkCollumns[chunkX][chunkY].chunklayers){
                            for (Entity e : z.loadedEntities){
                                if (!e.runtimeSpawnConditions.isEmpty()){
                                    for (EntitySpawnCondition esc : e.runtimeSpawnConditions){
                                        if (Math.random() < esc.chance){

                                                int a = r.nextInt(16);
                                                int b = r.nextInt(16);

                                                if (c.Spielfeld[a][b].blocknormal != null) if (c.Spielfeld[a][b].blocknormal.name.equals(esc.block)) spawn = true;
                                                if (c.Spielfeld[a][b].blocktop != null) if (c.Spielfeld[a][b].blocktop.name.equals(esc.block)) spawn = true;
                                                if (c.Spielfeld[a][b].blockground != null) if (c.Spielfeld[a][b].blockground.name.equals(esc.block)) spawn = true;

                                                if (esc.needsFreeBlock && c.Spielfeld[a][b].Wert != 0) spawn = false;
                                                
                                                if (c.Spielfeld[a][b].brightness + pm.mapTime.light > esc.maxLightLevel || c.Spielfeld[a][b].brightness + pm.mapTime.light < esc.minLightLevel) spawn = false;

                                                for (Player p : z.controllablePlayers){
                                                    int distance = 
                                                            (int)(
                                                                Math.sqrt(
                                                                    Math.pow(Math.max(p.entity.X / 60, a + (chunkX * 16)) - Math.min(p.entity.X / 60, a + (chunkX * 16)), 2) + 
                                                                    Math.pow(Math.max(p.entity.Y / 60, b + (chunkY * 16)) - Math.min(p.entity.Y / 60, b + (chunkY * 16)), 2)
                                                                )
                                                            );
                                                    if (distance > esc.maxPlayerDistance || distance < esc.minPlayerDistance) {spawn = false; break;}
                                                }
                                                if (esc.maxEntAtOnePlace > 0){
                                                    int entetiesInOnePlace = 0;
                                                    for (Entity ent : z.entities){
                                                        try{
                                                            if (ent.name.equals(e.name) && ent.onMap == pm.name){
                                                                if (ent.X / 60 / 16 >= chunkX - z.loadChunkRadius && ent.Y / 60 / 16 <= chunkY + z.loadChunkRadius + 1){
                                                                    entetiesInOnePlace++;
                                                                    if (entetiesInOnePlace >= esc.maxEntAtOnePlace){spawn = false; break;}
                                                                }
                                                            }
                                                        }
                                                        catch(Exception ex){}
                                                    }
                                                }

                                                if (spawn){
                                                    
                                                    int spawnX;
                                                    int spawnY;
                                                    if (!(esc.needsFreeBlock && c.Spielfeld[a][b].Wert != 0))
                                                        z.worldAccessor.SpawnEntity(z, e.CreateNew(), a + (chunkX * 16), b + (chunkY * 16), 0, pm.name, true);
                                                    
                                                    String[] str = esc.companions.split(",");
                                                    String[] entitiesToSpawn = new String[str.length + 1];
                                                    for (int i = 0; i <= str.length - 1; i++)
                                                        entitiesToSpawn[i + 1] = str[i];
                                                    entitiesToSpawn[0] = e.name;
                                                    
                                                    for (int i = 0; i <= esc.groupSize - 1; i++){
                                                        try{
                                                            Entity entityToSpawn = z.worldAccessor.getEntity(z, entitiesToSpawn[r.nextInt(entitiesToSpawn.length)]).CreateNew();
                                                            if (entityToSpawn != null){
                                                                spawnX = r.nextInt(esc.groupRadius * 2) - esc.groupRadius;
                                                                spawnY = r.nextInt(esc.groupRadius * 2) - esc.groupRadius;
                                                                if (!(esc.needsFreeBlock && c.Spielfeld[a + spawnX][b + spawnY].Wert != 0))
                                                                    z.worldAccessor.SpawnEntity(z, entityToSpawn, a + (chunkX * 16) + spawnX, b + (chunkY * 16) + spawnY, 0, pm.name, true);
                                                            }
                                                        }
                                                        catch(Exception ex){}
                                                    }
                                                    spawn = false;
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            
        }
        
    }
    
    public void HandleEntityDespawnTick(Zombie z, Graphics g){
        
        Random r = new Random();
        Feld f = null;
        
        boolean despawn = true;

        for (int i = z.entities.size() - 1; i >= 0; i--){
            Entity e = z.entities.get(i);
            if (!e.despawnConditions.isEmpty()){
                for (EntityDespawnCondition esc : e.despawnConditions){
                    if (Math.random() < esc.chance){
                        
                            f = z.worldAccessor.GetBlock(z, e.X / 60, e.Y / 60, e.chunkLayer, e.onMap);
                            try{
                                if (esc.maxLightLevel > -1 && f.brightness + z.playMaps.get(e.onMap).mapTime.light > esc.maxLightLevel) despawn = false;
                                if (esc.minLightLevel > -1 && f.brightness + z.playMaps.get(e.onMap).mapTime.light < esc.minLightLevel) despawn = false;
                            }catch(Exception ex){}
                            for (Player p : z.controllablePlayers){
                                int distance = 
                                        (int)(
                                            Math.sqrt(
                                                Math.pow(Math.max(p.entity.X / 60, e.X / 60) - Math.min(p.entity.X / 60, e.X / 60), 2) + 
                                                Math.pow(Math.max(p.entity.Y / 60, e.Y / 60) - Math.min(p.entity.Y / 60, e.Y / 60), 2)
                                            )
                                        );
                                if (esc.maxPlayerDistance > -1 && esc.minPlayerDistance > -1) if (distance > esc.maxPlayerDistance || distance < esc.minPlayerDistance) {despawn = false; break;}
                                if (esc.maxPlayerDistance <= -1 && esc.minPlayerDistance > -1) if (distance < esc.minPlayerDistance) {despawn = false; break;}
                                if (esc.maxPlayerDistance > -1 && esc.minPlayerDistance <= -1) if (distance > esc.maxPlayerDistance) {despawn = false; break;}
                            }

                            if (despawn){z.entities.remove(i);}
                            
                            despawn = true;
                    }
                }
            }
            
        }
        
    }
}


/*public void HandleEntitySpawnTick(Zombie z, Graphics g){
        
        if (z.entitySpawnTick > 100){
            z.entitySpawnTick = 0;
            
            boolean spawn = false;
            Set<String> keys = z.playMaps.keySet();
            for (String s : keys){
                PlayMap pm = z.playMaps.get(s);
                
                for (int chunkX = 0; chunkX <= pm.ChunkCollumns.length - 1; chunkX++)
                    for (int chunkY = 0; chunkY <= pm.ChunkCollumns[0].length - 1; chunkY++){
                        
                        if (pm.ChunkCollumns[chunkX][chunkY] != null){
                            for (Chunk c : pm.ChunkCollumns[chunkX][chunkY].chunklayers){
                                for (Entity e : z.loadedEntities){

                                    if (!e.runtimeSpawnConditions.isEmpty()){

                                        for (EntitySpawnCondition esc : e.runtimeSpawnConditions){
                                            
                                            for (int a = 0; a <= 15; a++)for (int b = 0; b <= 15; b++){
                                                if (Math.random() < esc.chance){
                                                    
                                                    if (c.Spielfeld[a][b].blocknormal != null) if (c.Spielfeld[a][b].blocknormal.name.equals(esc.block)) spawn = true;
                                                    if (c.Spielfeld[a][b].blocktop != null) if (c.Spielfeld[a][b].blocktop.name.equals(esc.block)) spawn = true;
                                                    if (c.Spielfeld[a][b].blockground != null) if (c.Spielfeld[a][b].blockground.name.equals(esc.block)) spawn = true;

                                                    if (c.Spielfeld[a][b].brightness + pm.mapTime.light > esc.maxLightLevel || c.Spielfeld[a][b].brightness + pm.mapTime.light < esc.minLightLevel) spawn = false;
                                                    
                                                    for (Player p : z.controllablePlayers){
                                                        int distance = 
                                                                (int)(
                                                                    Math.sqrt(
                                                                        Math.pow(Math.max(p.entity.X / 60, a + (chunkX * 16)) - Math.min(p.entity.X / 60, a + (chunkX * 16)), 2) + 
                                                                        Math.pow(Math.max(p.entity.Y / 60, b + (chunkY * 16)) - Math.min(p.entity.Y / 60, b + (chunkY * 16)), 2)
                                                                    )
                                                                );
                                                        if (distance > esc.maxPlayerDistance || distance < esc.minPlayerDistance) {spawn = false; break;}
                                                        //System.out.println("distance = " + distance + ", X = " + p.entity.X + ", blockX = " + (a + (chunkX * 16)) * 60
                                                         //+ ", Y = " + p.entity.Y + ", blockY = " + (b + (chunkY * 16)) * 60);
                                                    }
                                                    if (esc.maxEntAtOnePlace > 0){
                                                        int entetiesInOnePlace = 0;
                                                        for (Entity ent : z.entities){
                                                            try{
                                                                if (ent.name.equals(e.name) && ent.onMap == pm.name){
                                                                    if (ent.X / 60 / 16 >= chunkX - z.loadChunkRadius && ent.Y / 60 / 16 <= chunkY + z.loadChunkRadius + 1){
                                                                        entetiesInOnePlace++;
                                                                        if (entetiesInOnePlace >= esc.maxEntAtOnePlace){spawn = false; break;}
                                                                    }
                                                                }
                                                            }
                                                            catch(Exception ex){}
                                                        }
                                                    }

                                                    if (spawn){

                                                        z.worldAccessor.SpawnEntity(z, e.CreateNew(), a + (chunkX * 16), b + (chunkY * 16), 0, pm.name, true);
                                                        spawn = false;

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
            }
            
        }
        
    }
*/
