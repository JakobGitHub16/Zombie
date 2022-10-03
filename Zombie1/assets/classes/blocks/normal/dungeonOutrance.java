
import zombie.*;
import java.awt.*;


public class dungeonOutrance extends BlockNormal{
    
            int param = 1;
    	    int classId = 5;
            int i2 = 0;
            String leadsToMap_Name = "overworld";
            int exitPosX = 0;
            int exitPosY = 0;
            
            @Override
            public dungeonOutrance CreateNew() {
                
            	dungeonOutrance b = new dungeonOutrance();
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
	                if (z.playMaps.get(this.leadsToMap_Name) == null) {
                            z.worldAccessor.createPlayMap(z, 60, 60, this.leadsToMap_Name, "assets/worldgen/maptypes/overworld.txt", true);
	                    z.playMaps.get(this.leadsToMap_Name).fill(z.loadedNormalBlocks);
	                    z.playMaps.get(this.leadsToMap_Name).GenerateChunks(z, z.playMaps.get(this.leadsToMap_Name).generator);
	                }
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
                        if (z.Z > (z.playMaps.get(previousMap).SizeX * 60 - z.xscreensize)) {
                            z.Z = (z.playMaps.get(previousMap).SizeX * 60 - z.xscreensize);
                        }
                        if (z.Q < 0) {
                            z.Q = 0;
                        }
                        if (z.Q > (z.playMaps.get(previousMap).SizeY * 60 - z.yscreensize)) {
                            z.Q = (z.playMaps.get(previousMap).SizeY * 60 - z.yscreensize);
                        }   
                        
	                z.playMaps.get(previousMap).UnloadChunk(z);
                }
                
            }

            @Override
            public String getSaveData() {
            	return this.leadsToMap_Name + "." + this.exitPosX + "." + this.exitPosY;
            }
            
            @Override
            public void onPlaced(Zombie z, int x, int y, int chunklayer, String mapName, Object[] customParams) {
                try{
                    this.exitPosX = (Integer)customParams[0];
                    this.exitPosY = (Integer)customParams[1];
                    System.out.println("noice ;:)");
                }
                catch(Exception e){System.out.println("fog düd");}
            }
            
            @Override
            public void onLoad(String s) {
            	try{this.leadsToMap_Name = s.split(".")[0];}catch(Exception e){}
                try{this.exitPosX = Integer.parseInt(s.split(".")[1]);}catch(Exception e){}
                try{this.exitPosY = Integer.parseInt(s.split(".")[2]);}catch(Exception e){}
            }
}
