/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static zombie.Zombie.loadedNormalBlocks;

/**
 *
 * @author 123
 */
public class Chunk {
    
    public Feld[][] Spielfeld = new Feld[16][16];
    public int SizeX = 16, SizeY = 16;
    
    public Chunk(Zombie z)
    {
        for (int a = 0; a <= SizeX - 1; a++) {
            for (int b = 0; b <= SizeY - 1; b++) {
                Spielfeld[a][b] = new Feld(null, null, null);
            }
        }
        
        
        
    }
    
    List<randomGenStructure> GenerateBlockPatches(Zombie z, List<BlockNormal> blocks, List<BlockTop> topblocks, List<BlockGround> groundblocks, int chunklayer, String mapName)
    {
        List<Map<String,String>> patches = new newJSONreadmanager(z).ReadBlockPatches();
        List<randomGenStructure> structures = new ArrayList<randomGenStructure>();
        
                
        for (int a = 0; a <= this.SizeX - 1; a++)
        {
            for (int b = 0; b <= this.SizeY - 1; b++)
            {                
                for (int c = 0; c <= patches.size() - 1; c++)
                {
                    Random r = new Random();
                    if ((double)(r.nextInt(1000000)) / (double)1000000 <= Double.parseDouble(patches.get(c).get("chance")))
                    {
                        int distX = 3;
                        if (patches.get(c).get("distX") != null) distX = Integer.parseInt(patches.get(c).get("distX"));
                        int distY = 3;
                        if (patches.get(c).get("distY") != null) distY = Integer.parseInt(patches.get(c).get("distY"));
                        int quantity = 10;
                        if (patches.get(c).get("quantity") != null) quantity = Integer.parseInt(patches.get(c).get("quantity"));
                        String onBlockName = null;
                        if (patches.get(c).get("on_block") != null) onBlockName = patches.get(c).get("on_block");
                        String[] blocktypes = patches.get(c).get("blocktypes").split(";");
                        Random posRandom = new Random();
                        Random nameRandom = new Random();
                        
                        structures.add(new randomGenStructure());
                        structures.get(structures.size() - 1).x = a;
                        structures.get(structures.size() - 1).y = b;
                        structures.get(structures.size() - 1).width = distX * 2 + 3;
                        structures.get(structures.size() - 1).heigth = distY * 2 + 3;
                        structures.get(structures.size() - 1).blocks = new Feld[structures.get(structures.size() - 1).width][structures.get(structures.size() - 1).heigth];
                        for (int aaa = 0; aaa <= structures.get(structures.size() - 1).width - 1; aaa++)
                            for (int bbb = 0; bbb <= structures.get(structures.size() - 1).heigth - 1; bbb++)
                                structures.get(structures.size() - 1).blocks[aaa][bbb] = new Feld(null, null, null);
                        
                        for (int d = 0; d < quantity; d++)
                        {
                            try{
                                int diX = posRandom.nextInt(distX * 2);
                                int diY = posRandom.nextInt(distY * 2);
                                if (diX > distX) diX = diX * (-1) + distX;
                                if (diY > distY) diY = diY * (-1) + distY;
                                String name = blocktypes[0];
                                if (blocktypes.length > 1) name = blocktypes[nameRandom.nextInt(blocktypes.length - 1)];
                                int nb = getBlockIdN(blocks, name);
                                int tb = getBlockIdT(topblocks, name);
                                int gb = getBlockIdG(groundblocks, name);
                                //System.out.println("lol: " + structures.get(structures.size() - 1).width + " " + structures.get(structures.size() - 1).heigth + " " + (distX + diX) + " " + (distY + diY) + " / " + distX + " " + diX + " " + distY + " " + diY);
                                if (onBlockName == null){
                                if (nb >= 0) {structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocknormal = blocks.get(nb).CreateNew();
                                structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocknormal.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));}
                                else if (tb >= 0) {structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocktop = topblocks.get(tb).CreateNew();
                                structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocktop.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));}
                                else if (gb >= 0) {structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground = groundblocks.get(gb).CreateNew();structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));
                                structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));}
                                }
                                else {
                                if (nb >= 0 && this.Spielfeld[a + distX + diX][b + distY + diY].blocknormal.name.equals(onBlockName)) {structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocknormal = blocks.get(nb).CreateNew();
                                structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocknormal.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));}
                                else if (tb >= 0 && this.Spielfeld[a + distX + diX][b + distY + diY].blocknormal.name.equals(onBlockName)) {structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocktop = topblocks.get(tb).CreateNew();
                                structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blocktop.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));}
                                else if (gb >= 0 && this.Spielfeld[a + distX + diX][b + distY + diY].blocknormal.name.equals(onBlockName)) {structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground = groundblocks.get(gb).CreateNew();
                                structures.set(structures.size() - 1, structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground.onPlaceForWorldGen(z, diX + distX, diY + distY, structures.get(structures.size() - 1)));}
                                }
                                
                                /*if (structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground != null)
                                {
                                    if(structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground.valueInString != "")
                                    {
                                        String[] values = structures.get(structures.size() - 1).blocks[distX + diX][distY + diY].blockground.valueInString.split(";");
                                        for (int e = 0; e <= values.length - 1; e++)
                                        {
                                            String pos = values[e].split("->")[1];
                                            try{
                                                if (structures.get(structures.size() - 1).blocks[distX + diX + Integer.parseInt(pos.split("&")[0])][distY + diY + Integer.parseInt(pos.split("&")[1])].blockground == null)
                                                    structures.get(structures.size() - 1).blocks[distX + diX + Integer.parseInt(pos.split("&")[0])][distY + diY + Integer.parseInt(pos.split("&")[1])].blockground = new BlockGround();
                                                structures.get(structures.size() - 1).blocks[distX + diX + Integer.parseInt(pos.split("&")[0])][distY + diY + Integer.parseInt(pos.split("&")[1])].blockground.value = Integer.parseInt(values[e].split("->")[0]);
                                                //    = new Block = Integer.parseInt(values[e].split("->")[0]);
                                            }
                                            catch(Exception ee)
                                            {
                                            }
                                        }
                                    }
                                }*/
                            }
                            catch(Exception e){}
                        }

                       
                        
                    }
                   
                }   
                
            }
        }
        return structures;
    }
    
    randomGenStructure BäumeGenerieren(int count, List<BlockNormal> blocks, List<BlockTop> topblocks, List<BlockGround> groundblocks) {
        for (int a = 0; a <= count; a++) {
            try {
                
                randomGenStructure structure = new randomGenStructure();
                
                Random rx = new Random();
                Random ry = new Random();
                int x = rx.nextInt(SizeX - 2);
                int y = ry.nextInt(SizeY - 2);
                Random radius = new Random();
                Random leng = new Random();
                int r = radius.nextInt(1);
                r = r + 1;
                if (radius.nextInt(50) == 9) r = 4;

                structure.x = x;
                structure.y = y;
                structure.width = r * 4 + 1;
                structure.heigth = r * 4 + 1;
                structure.blocks = new Feld[structure.width][structure.heigth];
                
                
                
                int logId = 0;
                for (logId = 0; logId <= groundblocks.size() - 1; logId++){if (groundblocks.get(logId).name.equals("log")){ break;}}
                int grassId = 0;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++){if (blocks.get(grassId).name.equals("grass")){ break;}}
                int leavesId = 0;
                for (leavesId = 0; leavesId <= blocks.size() - 1; leavesId++){if (topblocks.get(leavesId).name.equals("leaves")){ break;}}
                
                for (int c = 0; c <= structure.width - 1; c++)
                    for (int b = 0; b <= structure.heigth - 1; b++)
                        structure.blocks[c][b] = new Feld(null, null, null);
                        
                for (int c = r * 2 - r; c <= r * 2 + r; c++) 
                { 
                    int lp = leng.nextInt(r + 1) + r;
                    int lm = -leng.nextInt(r + 1) - r;
                    for (int b = r * 2 + lm; b <= r * 2 + lp; b++) 
                    {
                        structure.blocks[c][b].blocktop = topblocks.get(leavesId).CreateNew();
                    }
                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                for (int c = r * 2 - r; c <= r * 2 + r; c++) 
                { 
                    int lp = leng.nextInt(r + 1) + r;
                    int lm = -leng.nextInt(r + 1) - r;
                    for (int b = r * 2 + lm; b <= r * 2 + lp; b++) 
                    {
                        structure.blocks[b][c].blocktop = topblocks.get(leavesId).CreateNew();
                    }
                }
                if (r < 4) {
                    structure.blocks[r * 2][r * 2] = new Feld(null, topblocks.get(leavesId).CreateNew(), groundblocks.get(logId).CreateNew());
                }
                if (r >= 4) {
                    structure.blocks[r * 2][r * 2] = new Feld(null, topblocks.get(leavesId).CreateNew(), groundblocks.get(logId).CreateNew());
                    structure.blocks[r * 2 + 1][r * 2] = new Feld(null, topblocks.get(leavesId).CreateNew(), groundblocks.get(logId).CreateNew());
                    structure.blocks[r * 2][r * 2 + 1] = new Feld(null, topblocks.get(leavesId).CreateNew(), groundblocks.get(logId).CreateNew());
                    structure.blocks[r * 2 + 1][r * 2 + 1] = new Feld(null, topblocks.get(leavesId).CreateNew(), groundblocks.get(logId).CreateNew());
                }
                return structure;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    randomGenStructure HäuserGenerieren(int count, List<BlockNormal> blocks, List<BlockTop> topblocks, List<BlockGround> groundblocks) {
        for (int a = 0; a <= count; a++) {
            try {
                
                randomGenStructure structure = new randomGenStructure();
                
                Random rx = new Random();
                Random ry = new Random();
                int x = rx.nextInt(SizeX - 2);
                int y = ry.nextInt(SizeY - 2);
                Random radius = new Random();
                int r = radius.nextInt(2);
                r = r + 3;
                
                 
                structure.x = x;
                structure.y = y;
                structure.width = r * 2 + 1;
                structure.heigth = r * 2 + 1;
                structure.blocks = new Feld[structure.width][structure.heigth];
                
                int planksId = 0;
                for (planksId = 0; planksId <= blocks.size() - 1; planksId++){if (blocks.get(planksId).name.equals("planks")){ break;}}
                int cobblestoneId = 0;
                for (cobblestoneId = 0; cobblestoneId <= groundblocks.size() - 1; cobblestoneId++){if (groundblocks.get(cobblestoneId).name.equals("cobblestone")){ break;}}
                int roofId = 0;
                for (roofId = 0; roofId <= blocks.size() - 1; roofId++){if (topblocks.get(roofId).name.equals("roof_roof")){ break;}}
                int rooftopId = 0;
                for (rooftopId = 0; rooftopId <= blocks.size() - 1; rooftopId++){if (topblocks.get(rooftopId).name.equals("roof_roof_top")){ break;}}
                int glasId = 0;
                for (glasId = 0; glasId <= groundblocks.size() - 1; glasId++){if (groundblocks.get(glasId).name.equals("glas")){ break;}}
                
                Boolean generate = true;
                for (int c = x - r - 3; c <= x + r + 4; c++) {
                    for (int b = y - r - 3; b <= y + r + 4; b++) {
                        //if (!"grass".equals(this.Spielfeld[c][b].blocknormal.name)) {
                        //    generate = false;
                       // }
                    }
                }
                generate = true;
                if (generate == true) {
                    for (int c = r - r; c <= r + r; c++) {
                        for (int b = r - r; b <= r + r; b++) {
                            structure.blocks[c][b] = new Feld(blocks.get(planksId).CreateNew(), topblocks.get(roofId).CreateNew(), null);
                        }
                    }
                    //wände
                    for (int c = r - r; c <= r + r; c++) {
                        structure.blocks[c][r - r] = new Feld(null, topblocks.get(roofId).CreateNew(), groundblocks.get(cobblestoneId).CreateNew());
                        structure.blocks[c][r + r] = new Feld(null, topblocks.get(roofId).CreateNew(), groundblocks.get(cobblestoneId).CreateNew());
                    }
                    for (int c = r - r; c <= r + r; c++) {
                        structure.blocks[r - r][c] = new Feld(null, topblocks.get(roofId).CreateNew(), groundblocks.get(cobblestoneId).CreateNew());
                        structure.blocks[r + r][c] = new Feld(null, topblocks.get(roofId).CreateNew(), groundblocks.get(cobblestoneId).CreateNew());
                    }
                    //dach
                    for (int c = r - r; c <= r + r; c++) {
                        structure.blocks[r][c] = new Feld(blocks.get(planksId).CreateNew(), topblocks.get(rooftopId).CreateNew(), null);
                    }
                    //glas
                    for (int c = r - 1; c <= r + 1; c++) {
                        structure.blocks[c][r - r] = new Feld(blocks.get(planksId).CreateNew(), structure.blocks[c][r - r].blocktop, groundblocks.get(glasId).CreateNew());
                        structure.blocks[c][r + r] = new Feld(blocks.get(planksId).CreateNew(), structure.blocks[c][r + r].blocktop, groundblocks.get(glasId).CreateNew());
                    }
                    for (int c = r - 1; c <= r + 1; c++) {
                        structure.blocks[r + r][c] = new Feld(blocks.get(planksId).CreateNew(), structure.blocks[r + r][c].blocktop, groundblocks.get(glasId).CreateNew());
                    }
                    structure.blocks[r - r][r] = new Feld(blocks.get(planksId).CreateNew(), structure.blocks[r - r][r].blocktop, null);
                    return structure;
                }
                else{
                    //return null;
                }
            } catch (Exception e) {
                
            }
        }
        return null;
    }
    
    randomGenStructure SeenGenerieren(int count, List<BlockNormal> blocks) {
        for (int a = 0; a <= count; a++) {
            try {
                
                randomGenStructure structure = new randomGenStructure();
                
                Random rx = new Random();
                Random ry = new Random();
                int x = rx.nextInt(SizeX - 2);
                int y = ry.nextInt(SizeY - 2);
                Random radius = new Random();
                Random leng = new Random();
                int r = radius.nextInt(2);
                r = r + 1;
                
                structure.x = x;
                structure.y = y;
                structure.width = r * 4 + 2;
                structure.heigth = r * 4 + 2;
                structure.blocks = new Feld[structure.width][structure.heigth];
                
                for (int c = 0; c <= structure.width - 1; c++)
                    for (int b = 0; b <= structure.heigth - 1; b++)
                        structure.blocks[c][b] = new Feld(null,null, null);
                
                
                for (int c = r * 2 - r; c <= r * 2 + r; c++) 
                { 
                    int lp = leng.nextInt(r + 2) + r;
                    int lm = -leng.nextInt(r + 2) - r;
                    for (int b = r * 2 + 2 + lm; b <= r * 2 - 2 + lp; b++) 
                    {
                        structure.blocks[c][b] = new Feld(blocks.get(getBlockIdN(blocks, "water1")).CreateNew(), null, null);
                    }
                }
                for (int c = r * 2 - r; c <= r * 2 + r; c++) 
                { 
                    int lp = leng.nextInt(r + 2) + r;
                    int lm = -leng.nextInt(r + 2) - r;
                    for (int b = r * 2 + 2 + lm; b <= r * 2 - 2 + lp; b++) 
                    {
                        structure.blocks[b][c] = new Feld(blocks.get(getBlockIdN(blocks, "water1")).CreateNew(), null, null);
                    }
                }
                return structure;
                
            } catch (Exception e) {
                
            }
        }
        return null;
    }
    
    public void fill(Zombie z, Generator generator, int layer){
        try{
        
        for (int a = 0; a <= SizeX - 1; a++) {
            for (int b = 0; b <= SizeY - 1; b++) {
                Spielfeld[a][b] = new Feld(null, null, null);
                generator.getBlocksForLayer(z, layer);
                try {if(Spielfeld[a][b].blocknormal == null) Spielfeld[a][b].blocknormal = generator.currentNormalBlock.CreateNew();}catch (Exception e) {}
                try {if(Spielfeld[a][b].blockground == null) Spielfeld[a][b].blockground = generator.currentGroundBlock.CreateNew();}catch (Exception e) {}
                try {if(Spielfeld[a][b].blocktop == null) Spielfeld[a][b].blocktop = generator.currentTopBlock.CreateNew();}catch (Exception e) {}
                try {Spielfeld[a][b].SetValue();}catch (Exception e) {}
            }
        }
        }
        catch (Exception e){System.out.println("--------e = " + e);}
    }
    
    public int getBlockIdN(List<BlockNormal> blocks, String name){
        int grassId = 0;
        boolean returnable = false;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals(name)){ returnable = true; break; }
                }
        
        if (returnable == true)return grassId;
        return -1;
    }
    
    public int getBlockIdT(List<BlockTop> blocks, String name){
        int grassId = 0;
        boolean returnable = false;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals(name)){ returnable = true; break; }
                }
        
        if (returnable == true)return grassId;
        return -1;
    }
    
    public int getBlockIdG(List<BlockGround> blocks, String name){
        
        int grassId = 0;
        boolean returnable = false;
                for (grassId = 0; grassId <= blocks.size() - 1; grassId++)
                {
                    if (blocks.get(grassId).name.equals(name)){ returnable = true; break; }
                }
        
        if (returnable == true)return grassId;
        return -1;
    }
    
    public String getSaveData(){
        
        String s = "";
        
        for (int a = 0; a <= 15; a++)
            for (int b = 0; b <= 15; b++){
                //try{
                    if (Spielfeld[a][b].blocknormal != null) s += Spielfeld[a][b].blocknormal.name + "<" + Spielfeld[a][b].blocknormal.getSaveData() + ">" + "#"; else {s += "<>#"; }
                    if (Spielfeld[a][b].blockground != null) s += Spielfeld[a][b].blockground.name  + "<" + Spielfeld[a][b].blockground.getSaveData() + ">" + "#"; else {s += "<>#"; }
                    if (Spielfeld[a][b].blocktop != null) s += Spielfeld[a][b].blocktop.name + "<" + Spielfeld[a][b].blocktop.getSaveData() + ">" + ";"; else {s += "<>;"; }
                //}catch(Exception e){System.out.println(e + " iim chunk");}
            }
        //System.out.println(s);
        return s;
    }
    
    public boolean loadChunk(Zombie z, String s){
        
        
        String[] blocks = s.split(";");
        
        int i = 0;
        
            for (int a = 0; a <= SizeX - 1; a++){
                for (int b = 0; b <= SizeY - 1; b++){
                    
                    try{
                    	String[] splitted = blocks[i].split("#")[0].split("<");
                        if(splitted[0] != ""){
                            int id = getBlockIdN(z.loadedNormalBlocks, splitted[0]);
                            if (id >= 0){this.Spielfeld[a][b].blocknormal = z.loadedNormalBlocks.get(getBlockIdN(z.loadedNormalBlocks, blocks[i].split("#")[0].split("<")[0])).CreateNew();
                            this.Spielfeld[a][b].blocknormal.onLoad(splitted[1].replace(">", ""));
                            this.Spielfeld[a][b].SetValue();}
                        }
                    }catch(Exception e){return false;}
                    try{
                    	String[] splitted = blocks[i].split("#")[1].split("<");
                        if(splitted[0] != ""){
                            int id = getBlockIdG(z.loadedGroundBlocks, splitted[0]);
	                        if (id >= 0){
	                            this.Spielfeld[a][b].blockground = z.loadedGroundBlocks.get(getBlockIdG(z.loadedGroundBlocks, blocks[i].split("#")[1].split("<")[0])).CreateNew();
	                            this.Spielfeld[a][b].blockground.onLoad(splitted[1].replace(">", ""));
	                            this.Spielfeld[a][b].SetValue();
	                            
	                            /*if (this.Spielfeld[a][b].blockground.valueInString != "")
	                            {
	                                String[] values = this.Spielfeld[a][b].blockground.valueInString.split(";");
	                                for (int e = 0; e <= values.length - 1; e++)
	                                {
	                                    String pos = values[e].split("->")[1];
	                                    try{
	                                        if (this.Spielfeld[a + Integer.parseInt(pos.split("&")[0])][b + Integer.parseInt(pos.split("&")[1])].blockground == null)
	                                        	this.Spielfeld[a + Integer.parseInt(pos.split("&")[0])][b + Integer.parseInt(pos.split("&")[1])].blockground = new BlockGround();
	                                        this.Spielfeld[a + Integer.parseInt(pos.split("&")[0])][b + Integer.parseInt(pos.split("&")[1])].blockground.value = Integer.parseInt(values[e].split("->")[0]);
	                                        this.Spielfeld[a + Integer.parseInt(pos.split("&")[0])][b + Integer.parseInt(pos.split("&")[1])].SetValue();
	                                    }
	                                    catch(Exception ee)
	                                    {
	                                    }
	                                }
	                            }*/
                            }
                        }
                    }catch (Exception e){return false;}
                    try{
                    	String[] splitted = blocks[i].split("#")[2].split("<");
                        if(splitted[0] != ""){
                              int id = getBlockIdT(z.loadedTopBlocks, splitted[0]);
                              if (id >= 0){this.Spielfeld[a][b].blocktop = z.loadedTopBlocks.get(getBlockIdT(z.loadedTopBlocks, blocks[i].split("#")[2].split("<")[0])).CreateNew();
                              this.Spielfeld[a][b].blocktop.onLoad(splitted[1].replace(">", ""));
                              this.Spielfeld[a][b].SetValue();}
                        }
                    }catch(Exception e){return false;}
                    i++;
                }   
        }
            
        return true;
        
        
    }
    
    public void generateEntities(Zombie z, int chunkPosX, int chunkPosY, String mapName){
        
        int i = 0;
        Random r = new Random();
        
        for (Entity e : z.loadedEntities){
            if (e.worldGenSpawnConditions.size() > 0){
                
                for (EntitySpawnCondition sp : e.worldGenSpawnConditions){
                    for (int a = 0; a <= this.SizeX - 1; a++)
                        for (int b = 0; b <= this.SizeY - 1; b++)
                        {
                            if (Math.random() < sp.chance){
                                boolean generate = false;
                                try{
                                if (this.Spielfeld[a][b].blocknormal != null) if (this.Spielfeld[a][b].blocknormal.name.equals(sp.block)) generate = true;
                                if (this.Spielfeld[a][b].blocktop != null) if (this.Spielfeld[a][b].blocktop.name.equals(sp.block)) generate = true;
                                if (this.Spielfeld[a][b].blockground != null) if (this.Spielfeld[a][b].blockground.name.equals(sp.block)) generate = true;

                                if (sp.needsFreeBlock && this.Spielfeld[a][b].Wert != 0) generate = false;
                                
                                if (sp.maxEntAtOnePlace > 0){
                                    int entetiesInOnePlace = 0;
                                    for (Entity ent : z.entities){
                                        try{
                                            if (ent.name.equals(e.name) && ent.onMap == mapName){
                                                if (ent.X / 60 / 16 >= chunkPosX - z.loadChunkRadius && ent.Y / 60 / 16 <= chunkPosY + z.loadChunkRadius + 1){
                                                    entetiesInOnePlace++;
                                                    if (entetiesInOnePlace >= sp.maxEntAtOnePlace){System.out.println("ents full at " + chunkPosX + ", " + chunkPosY);generate = false; break;}
                                                }
                                            }
                                        }
                                        catch(Exception ex){}
                                    }
                                }

                                if (generate){

                                    int spawnX = 0;
                                    int spawnY = 0;
                                    if (!(sp.needsFreeBlock && this.Spielfeld[a][b].Wert != 0))
                                        z.worldAccessor.SpawnEntity(z, e.CreateNew(), a + (chunkPosX * 16), b + (chunkPosY * 16), 0, mapName, true);

                                    String[] str = sp.companions.split(",");
                                    String[] entitiesToSpawn = new String[str.length + 1];
                                    for (int ii = 0; ii <= str.length - 1; ii++)
                                        entitiesToSpawn[ii + 1] = str[ii];
                                    entitiesToSpawn[0] = e.name;

                                    for (int ii = 0; ii <= sp.groupSize - 1; ii++){
                                        try{
                                            Entity entityToSpawn = z.worldAccessor.getEntity(z, entitiesToSpawn[r.nextInt(entitiesToSpawn.length)]).CreateNew();
                                            if (entityToSpawn != null){
                                                if (sp.groupRadius > 0){
                                                    spawnX = r.nextInt(sp.groupRadius * 2) - sp.groupRadius;
                                                    spawnY = r.nextInt(sp.groupRadius * 2) - sp.groupRadius;
                                                }
                                                if (!(sp.needsFreeBlock && this.Spielfeld[a + spawnX][b + spawnY].Wert != 0))
                                                    z.worldAccessor.SpawnEntity(z, entityToSpawn, a + (chunkPosX * 16) + spawnX, b + (chunkPosY * 16) + spawnY, 0, mapName, true);
                                            }
                                        }
                                        catch(Exception ex){}
                                    }
                                    generate = false;
                                }

                                    /*if (generate){    
                                            new WorldAccessor().SpawnEntity(z, e.CreateNew(), a + (chunkPosX * 16), b + (chunkPosY * 16), 0, mapName, true);
                                            generate = false;
                                            i++;
                                    }*/
                                }
                                catch(Exception exc){}
                            }
                        }
                }
                
            }
        }
    }
}
