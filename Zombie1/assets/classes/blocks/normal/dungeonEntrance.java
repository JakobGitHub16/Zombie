
import zombie.*;
import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.function.IntBinaryOperator;


public class dungeonEntrance extends BlockNormal{
    
            int param = 1;
    	    int classId = 5;
            int i2 = 0;
            String leadsToMap_Name = "";
            int exitPosX = 0;
            int exitPosY = 0;

            @Override
            public dungeonEntrance CreateNew() {
                
            	dungeonEntrance b = new dungeonEntrance();
                b.Animationtimer = this.Animationtimer;
                b.cAnimationtimer = 0;
                b.name = this.name;
                b.value = this.value;
                b.textureId = this.textureId;
                b.changetextures = this.changetextures;
                b.texturechangechance = this.texturechangechance;
                b.param = this.param;
                b.classId = this.classId;
		        for (Item o : this.drops){
	        	    b.drops.add(o.CreateNew());
		        }
                return b;
            }
            
            @Override
            public void OnStepOn(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
            	WorldAccessor w = new WorldAccessor();
                
                if (byEntity instanceof EntityPlayer) {
	                if (this.leadsToMap_Name.equals("")) {
	                	int a = 0;
	                	while (z.playMaps.get("BaseGameDungeon1_" + a) != null || w.doesMapExist(z, "BaseGameDungeon1_" + a)) {
	                		a++;
	                	}
                                this.leadsToMap_Name = "BaseGameDungeon1_" + a;
                                w.createPlayMap(z, 3, 3, "BaseGameDungeon1_" + a, "assets/worldgen/maptypes/BaseGameDungeon1.txt",false);
                                Random r = new Random();
                                this.exitPosX = r.nextInt(47);
                                this.exitPosY = r.nextInt(45) + 2;
                                Object[] args = new Object[5];args[0] = z; args[1] = exitPosX; args[2] = exitPosY;args[3] = "BaseGameDungeon1_" + a;args[4] = ((EntityPlayer) byEntity).chunkLayer;
                                z.playMaps.get("BaseGameDungeon1_" + a).GenerateChunks(z, z.playMaps.get("BaseGameDungeon1_" + a).generator, this, "placeOutrance", args);
	                }
	                else if (z.playMaps.get(this.leadsToMap_Name) == null){ Map<String, String> m = w.getMapProperties(z, this.leadsToMap_Name);
                        w.createPlayMap(z, Integer.parseInt(m.get("sizeX")) / 16, Integer.parseInt(m.get("sizeY")) / 16, this.leadsToMap_Name, "assets/worldgen/maptypes/BaseGameDungeon1.txt",false);
                        Object[] args = new Object[5];args[0] = z; args[1] = blockX; args[2] = blockY;args[3] = this.leadsToMap_Name;args[4] = ((EntityPlayer) byEntity).chunkLayer;
                                z.playMaps.get(this.leadsToMap_Name).GenerateChunks(z, z.playMaps.get(this.leadsToMap_Name).generator, this, "placeOutrance", args);}
	                String previousMap = byEntity.onMap;
	                byEntity.onMap = this.leadsToMap_Name;
	                byEntity.inNewChunk = true;
                        
                        byEntity.X = this.exitPosX * 60;
                        byEntity.Y = this.exitPosY * 60;
                        z.Z = byEntity.X - (z.xscreensize / 2);
                        z.Q = byEntity.Y - (z.yscreensize / 2);
                        if (z.Z < 0) {
                            z.Z = 0;
                        }
                        if (z.Z > (180 * 60 - z.xscreensize)) {
                            z.Z = (180 * 60 - z.xscreensize);
                        }
                        if (z.Q < 0) {
                            z.Q = 0;
                        }
                        if (z.Q > (180 * 60 - z.yscreensize)) {
                            z.Q = (180 * 60 - z.yscreensize);
                        }
            
	                z.playMaps.get(previousMap).UnloadChunk(z);
	                w.GetBlock(z, blockX, blockY - 2, byEntity.chunkLayer, byEntity.onMap).blocknormal.onLoad(previousMap);
                }
            }
            
            public void placeOutrance(Object[] args){
                
                try{
                    Zombie z = (Zombie)args[0];
                    System.out.println("z = " + z + " " + (Integer)args[1] + " " + (Integer)args[2] + " " + (Integer)args[4] + " " + (String)args[3]);
                    z.worldAccessor.SetBlock(z, "BlockNormal", "dungeonOutrance", (Integer)args[1], (Integer)args[2] - 2, (Integer)args[4], (String)args[3]);
                    
                    Object[] params = new Object[2]; params[0] = (Integer)args[1]; params[1] = (Integer)args[2] - 2;
                    z.worldAccessor.GetBlockNormal(z, (Integer)args[1], (Integer)args[2] - 2, (Integer)args[4], (String)args[3])
                            .onPlaced(z, (Integer)args[1], (Integer)args[2] - 2, (Integer)args[4], (String)args[3], params);
                }catch(Exception e){System.out.println("exception in placeOutrance: " + e);}
            }

            @Override
            public String getSaveData() {
            	return this.leadsToMap_Name + "." + this.exitPosX + "." + this.exitPosY;
            }
            
            @Override
            public void onLoad(String s) {
            	try{this.leadsToMap_Name = s.split(".")[0];}catch(Exception e){}
                try{this.exitPosX = Integer.parseInt(s.split(".")[1]);}catch(Exception e){}
                try{this.exitPosY = Integer.parseInt(s.split(".")[2]);}catch(Exception e){}
            }
}
