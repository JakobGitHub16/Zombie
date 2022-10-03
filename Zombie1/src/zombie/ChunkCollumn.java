/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Standard
 */
public class ChunkCollumn {
    
    public List<Chunk> chunklayers = new ArrayList<Chunk>();
    
    
    public boolean save(int ChunkX, int ChunkY, String mapName){
        
        
        String allChunks = "";
        try{
            for (int a = 0; a <= chunklayers.size() - 1; a++)
                if (chunklayers.get(a) != null)
                    allChunks += "|" + chunklayers.get(a).getSaveData();
        }catch(Exception e){System.out.println(e + "fehler in chunk column.save, erster teil");}

        JSONObject obj = new JSONObject();
        
        obj.put(ChunkX + " " + ChunkY, allChunks);

        try{    
                        
            String s = "";
            //File file = new File("saves/welt1/maps/overworld.json");
            //File file2 = new File("saves/welt1/maps/overworld.json");
            //if (!file.renameTo(file2)) return false;
            
            File file = null;
            File file2 = null;
            file = new File("saves/welt1/maps/" + mapName + ".json");
            if (!(file.exists()))  file.createNewFile();
            file2 = new File("saves/welt1/maps/" + mapName + "Temp.json");
            if (!(file2.exists()))  file2.createNewFile();
            
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean foundMatchingSave = false;
            String st = "";
            
            int currentLine = 0;
            int lastLine = 0;
            
            //while (file.renameTo(file) && file2.renameTo(file2)){}
            while((st = reader.readLine()) != null)
            {
                if (st.chars().filter(ch -> ch == '}').count() == 1) lastLine = currentLine;
                st = st.replace(",", "").replace("{", "").replace("}", "");
                String[] s1 = st.split(":")[0].replace("\"", "").split(" ");
                try{
                    if ((Integer.parseInt(s1[0]) == ChunkX && Integer.parseInt(s1[1]) == ChunkY) && st.chars().filter(ch -> ch == '"').count() == 4){
                        
                        foundMatchingSave = true;
                        String string = obj.toString().replace("{", "").replace("}", "") + ",\n";
                        if (currentLine == 0) string = "{" + string;
                        if (currentLine == lastLine) string = string.replace("\n", "").replace(",", "") + "}";
                        BufferedReader reader2 = new BufferedReader(new FileReader(file));
                        String st2 = "";
                        BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2));
                        while ((st2 = reader2.readLine()) != null)
                        {
                            if (st.equals(st2)) writer2.append(string);
                            else writer2.append(st2.replace(",", "") + ",\n");
                        }
                        reader2.close();
                        writer2.close();
                        reader.close();
                        if (file2.exists() && file.exists()){while(file.delete()== false && file.exists()) { } file2.renameTo(file);}
                        return true;
                    }
                }
                catch(Exception e){System.out.println("moin, fehler in save chunk collumn bei s: " + e);}
                
                currentLine++;
            }
            //s.replace("}", "");
            if (foundMatchingSave == false)
            {
                currentLine = 0; 
                file = new File("saves/welt1/maps/" + mapName + ".json");
                if (!(file.exists()))  file.createNewFile();
                file2 = new File("saves/welt1/maps/" + mapName + "Temp.json");
                if (!(file2.exists()))  file2.createNewFile();
                
                String string = obj.toString().replace("{", "").replace("}", "");
                
                BufferedReader reader2 = new BufferedReader(new FileReader(file));
                String st2 = "";
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2));
                while ((st2 = reader2.readLine()) != null)
                {
                    if (currentLine == lastLine) writer2.append(st2.replace("}", "") + ",\n");
                    else writer2.append(st2 + "\n");
                    currentLine++;
                }
                if (currentLine != 0) writer2.append(string + "}");
                if (currentLine == 0) writer2.append("{" + string + "}");
                reader2.close();
                writer2.close();
                reader.close();
                if (file2.exists() && file.exists()){while(file.delete()== false && file.exists()) { } file2.renameTo(file);}
                return true;
            }
            
        }catch (Exception e){System.out.println("moin, fehler in save chunk collumn: " + e); return false;}
        
        //-----------------------------delete Entities
       
        
        
        return true;
    }
    
    public boolean tryLoadChunkCollumn(Zombie z, int X, int Y, String mapName){
        
        String s = "";
        
        BufferedReader r = null;
        
        
        
        try{
            File f = new File("saves/welt1/maps/" + mapName + ".json");
            if (!(f).exists())  {f.createNewFile();return false;}
            
            r = new BufferedReader(new FileReader("saves/welt1/maps/" + mapName + ".json"));
            
            String strg = "";
           // while (f.renameTo(f)){}
            while ((strg = r.readLine()) != null)
            {
                boolean b = true;
                String[] coords = strg.replace("\"", "").replace("{", "").replace("}", "").replace(",", "").split(":")[0].split(" ");
                if (Integer.parseInt(coords[0]) == X && Integer.parseInt(coords[1]) == Y){
                    r.close();
                    String[] s1 = strg.replace("\"", "").replace("{", "").replace("}", "").replace(",", "").split(":")[1].split("\\|");
                    for (int a = 0; a <= s1.length - 2; a++){
                       this.chunklayers.add(new Chunk(z));
                       if (!this.chunklayers.get(a).loadChunk(z, s1[a + 1])) b = false;
                    }
                    return b;
                }
            }
            
        }catch(Exception e){System.out.println("ERROR in loadchunk = " + e); try{if(r != null) r.close();}catch(Exception ex){}}       
        finally{
            try{if(r != null) r.close();}catch(Exception ex){}
        }
        return false;
    }
    
    public void generateEntities(Zombie z, int chunkPosX, int chunkPosY, String mapName){
        for (Chunk c : this.chunklayers){
            if (c != null) c.generateEntities(z, chunkPosX, chunkPosY, mapName);
        }
    }
    
    
    public void deleteEntities(Zombie z, int ChunkPosX, int ChunkPosY, String mapName){
        
       
        for (int a = z.entities.size() - 1; a >= 0; a--){
            
            //System.out.println("chunkX und Y = " + chunkPosX + " " + chunkPosY + " und entities x & y = " + (int)(z.entities.get(a).X / 16 / 60) + " " +(int)(z.entities.get(a).Y / 16 / 60));
            
            if ((int)(z.entities.get(a).X / 60 / 16) == ChunkPosX && (int)(z.entities.get(a).Y / 60 / 16) == ChunkPosY
                    && !(z.entities.get(a) instanceof EntityPlayer) && z.entities.get(a).onMap.equals(mapName))
            //if ((int)(z.entities.get(a).X / 60 / 16) == minChunkPosX + 2 && (int)(z.entities.get(a).Y / 60 / 16) == minChunkPosY + 2)
            {
                //System.out.println("chunkX und Y = " + chunkPosX + " " + chunkPosY + " und entities x & y = " + (int)(z.entities.get(a).X / 16 / 60) + " " +(int)(z.entities.get(a).Y / 16 / 60));
                z.entities.remove(a);
            }
        }
        
    }
    
}
