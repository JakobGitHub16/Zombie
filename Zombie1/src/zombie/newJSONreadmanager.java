

package zombie;

import enumClasses.enumIntelligencePropertieChangeConditions;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
//import static jdk.nashorn.internal.objects.NativeFunction.function;
import static zombie.Zombie.loadedNormalBlocks;

/**
 *
 * @author 123
 */
public class newJSONreadmanager {

    Zombie z;

    public newJSONreadmanager(Zombie Z) {
        z = Z;
    }

    //------------------------------------------------Read and inizialize all block files
    public void ManageReadBlocks() {

        ReadNormalBlocks();
        ReadTopBlocks();
        ReadGroundBlocks();
    }
    
    public void ManageReadItems()
    {
        ReadItems("assets/itemtypes/weapons");
        ReadItems("assets/itemtypes/food");
        ReadItems("assets/itemtypes/others");
        ReadItems("assets/itemtypes/tools");
    }
    
    public void ManageReadEntities()
    {
        ReadEntities("assets/entities/humanoid");
    }
    
    public void ManageReadAITasks()
    {
        ReadAITasks("assets/classes/ai_tasks");
    }
    
    public void ManageReadProjectiles()
    {
        ReadProjectiles("assets/projectiletypes");
    }
    
    public void ManageReadCraftingRecepies()
    {
        ReadCraftingRecepies("assets/recepies/workbench");
    }
    
    public void ManageReadGUIElements(){
        ReadGUIElements("assets/classes/gui_elements");
    }

    private void ReadNormalBlocks() {
        //File[] allFiles = new File("assets/blocktypes/normal").listFiles();
        //File[] txtFiles = allFiles.filter(file -> !file.isDirectory());
        List<File> fileSet = Stream.of(new File("assets/blocktypes/normal").listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " NormalBlock files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            List<String> variants = new ArrayList<String>();
            List<BlockNormal> b = new ArrayList<BlockNormal>();
            
            Class blockClass = BlockNormal.class.getClass();
            
            if (m.containsKey("class_file_path")) {                           
                
                try{
                    String className = (m.get("class_file_path").split("/")[m.get("class_file_path").split("/").length - 1].split("[.]"))[0];
                    RuntimeCompiler r = new RuntimeCompiler();
                    r.addClass(className, ReadClasses(r, m.get("class_file_path")));
                    r.compile();
                    blockClass = r.getCompiledClass(className);
                    
                }
                catch(Exception e){System.out.println("[ERROR] error while reading class of block " + m.get("name") + " = " + e);}
            }
            if (m.containsKey("variants")) {
                String[] s = m.get("variants").split(";");
                for (int i = 0; i <= s.length - 1; i++) {
                    variants.add(s[i]);
                    try{b.add((BlockNormal)blockClass.newInstance());}catch(Exception e){b.add(new BlockNormal());}
                }
            }
            if (variants.size() == 0) {
                variants.add("");
            }
            if (b.size() == 0) {
                try{b.add((BlockNormal)blockClass.newInstance());}catch(Exception e){b.add(new BlockNormal());}
            }
            if (m.containsKey("name")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] name = m.get("name").split("#");
                    String realname = "";
                    for (int c = 0; c <= name.length - 1; c++) {
                        if (name[c].equals("variant")) {
                            name[c] = variants.get(i);
                        }
                        realname = realname + name[c];
                    }
                    b.get(i).name = realname;
                }
            }
            if (m.containsKey("value")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).value = Integer.parseInt(m.get("value"));
                }
            }
            if (m.containsKey("texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                        b.get(i).textureId = z.loadedBlockTextures.size() - 1;
                        b.get(i).texturePath = "assets/textures/" + realtexture + ".png";
                        if (!m.containsKey("vertical_texture")){
                            BufferedImage buffimg = ImageIO.read(new File("assets/textures/" + realtexture + ".png"));
                            BufferedImage img = new BufferedImage(60, z.vertical_size, BufferedImage.TYPE_INT_ARGB);
                            AffineTransform at = new AffineTransform();
                            at.scale(1, (double)z.vertical_size / (double)60);
                            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                            img = scaleOp.filter(buffimg, img);
                            z.loadedBlockTextures.add(img);
                            b.get(i).verticalTextureId = z.loadedBlockTextures.size() - 1;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            if (m.containsKey("vertical_texture")){
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("vertical_texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                        b.get(i).verticalTextureId = z.loadedBlockTextures.size() - 1;
                    } catch (Exception e) {
                    }
                }
            }
            if (m.containsKey("change_texture_to")) {                           
                
                for (int i = 0; i <= b.size() - 1; i++) 
                {
                    b.get(i).texturechangechance = new HashMap<String, String>();
                    b.get(i).changetextures = new HashMap<String, String>();
                    b.get(i).Animationtimer = 0;
                    String[] changes = m.get("change_texture_to").split(";");
                    for (int ii = 0; ii <= changes.length - 1; ii++){
                        String texture = changes[ii].split("->")[0];
                        String time = changes[ii].split("->")[1];
                        String chance = null;
                        boolean back = false;
                        try{chance = time.split("#")[1];}catch(Exception e){}
                        String[] textureparts = texture.split("#");
                        String realtexture = "";
                        for (int c = 0; c <= textureparts.length - 1; c++) {
                            if (textureparts[c].equals("variant")) {
                                textureparts[c] = variants.get(i);
                            }
                            if (textureparts[c].equals("back")) {
                                back = true;
                            }
                            realtexture = realtexture + textureparts[c];
                        }
                        try {
                            if (back == false)
                            {
                                z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                                b.get(i).changetextures.put("" + time.split("#")[0], "" + (z.loadedBlockTextures.size() - 1));
                                if (chance == null) b.get(i).texturechangechance.put("" + time.split("#")[0], "0");
                                else b.get(i).texturechangechance.put("" + time.split("#")[0], "" + chance);
                                if (b.get(i).Animationtimer < Integer.parseInt(time.split("#")[0])) b.get(i).Animationtimer = Integer.parseInt(time.split("#")[0]);
                            }
                            if (back == true)
                            {
                                b.get(i).changetextures.put("" + time.split("#")[0], "" + b.get(i).textureId);
                                if (chance == null) b.get(i).texturechangechance.put("" + time.split("#")[0], "0");
                                else b.get(i).texturechangechance.put("" + time.split("#")[0], "" + chance);
                                if (b.get(i).Animationtimer < Integer.parseInt(time.split("#")[0])) b.get(i).Animationtimer = Integer.parseInt(time.split("#")[0]);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
            if (m.containsKey("transparent")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).transparent = Boolean.parseBoolean(m.get("transparent"));
                }
            }
            if (m.containsKey("light_level")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).lightLevel = Integer.parseInt(m.get("light_level"));
                }
            }
            if (m.containsKey("light_absorbtion")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).lightAbsorbtion = Integer.parseInt(m.get("light_absorbtion"));
                }
            }
            if (m.containsKey("material")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).material = m.get("material");
                }
            }
            if (m.containsKey("resistance")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).resistance = Integer.parseInt(m.get("resistance"));
                }
            }
            if (m.containsKey("drops")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    for (String str : m.get("drops").split(";")){
                        String[] name = str.split("#");
                        String realname = "";
                        for (int c = 0; c <= name.length - 1; c++) {
                            if (name[c].equals("variant")) {
                                name[c] = variants.get(i);
                            }
                            realname = realname + name[c];
                        }
                        b.get(i).drops.add(new WorldAccessor().GetItem(z, realname));
                    }
                }
            }
            if (m.containsKey("resistance")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).resistance = Integer.parseInt(m.get("resistance"));
                }
            }
            if (m.containsKey("season_color_map_path")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).seasonColorMapPath = m.get("season_color_map_path");
                }
            }
            for (int i = 0; i <= b.size() - 1; i++) {
                z.loadedNormalBlocks.add(b.get(i));
            }
            //z.loadedNormalBlocks.add(new Norma)
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    private void ReadTopBlocks() {
        //File[] allFiles = new File("assets/blocktypes/normal").listFiles();
        //File[] txtFiles = allFiles.filter(file -> !file.isDirectory());
        List<File> fileSet = Stream.of(new File("assets/blocktypes/topblock").listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " TopBlock files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            List<String> variants = new ArrayList<String>();
            List<BlockTop> b = new ArrayList<BlockTop>();

            Class blockClass = BlockTop.class.getClass();
            
            if (m.containsKey("class_file_path")) {                           
                
                try{
                    String className = (m.get("class_file_path").split("/")[m.get("class_file_path").split("/").length - 1].split("[.]"))[0];
                    RuntimeCompiler r = new RuntimeCompiler();
                    r.addClass(className, ReadClasses(r, m.get("class_file_path")));
                    r.compile();
                    blockClass = r.getCompiledClass(className);
                }
                catch(Exception e){System.out.println("[ERROR] error while reading class of block " + m.get("name"));}
            }
            if (m.containsKey("variants")) {
                String[] s = m.get("variants").split(";");
                for (int i = 0; i <= s.length - 1; i++) {
                    variants.add(s[i]);
                    try{b.add((BlockTop)blockClass.newInstance());}catch(Exception e){b.add(new BlockTop());}
                }
            }
            if (variants.size() == 0) {variants.add("");}
            if (b.size() == 0) {try{b.add((BlockTop)blockClass.newInstance());}catch(Exception e){b.add(new BlockTop());}}
            if (m.containsKey("name")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] name = m.get("name").split("#");
                    String realname = "";
                    for (int c = 0; c <= name.length - 1; c++) {
                        if (name[c].equals("variant")) {
                            name[c] = variants.get(i);
                        }
                        realname = realname + name[c];
                    }
                    b.get(i).name = realname;
                }
            }
            if (m.containsKey("value")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).value = Integer.parseInt(m.get("value"));
                }
            }
            if (m.containsKey("texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                    } catch (Exception e) {
                    }
                    b.get(i).textureId = z.loadedBlockTextures.size() - 1;
                    b.get(i).textureId2 = z.loadedBlockTextures.size() - 1;
                    b.get(i).texturePath = "assets/textures/" + realtexture + ".png";
                    b.get(i).texturePath2 = "assets/textures/" + realtexture + ".png";
                }
            }
            if (m.containsKey("transparent_texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("transparent_texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                    } catch (Exception e) {
                    }
                    b.get(i).textureId2 = z.loadedBlockTextures.size() - 1;
                    b.get(i).texturePath2 = "assets/textures/" + realtexture + ".png";
                }
            }
            if (m.containsKey("wind_affected")) {                           
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).windAffected = Boolean.parseBoolean(m.get("wind_affected"));
                }
            }
            if (m.containsKey("material")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).material = m.get("material");
                }
            }
            if (m.containsKey("light_level")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).lightLevel = Integer.parseInt(m.get("light_level"));
                }
            }
            if (m.containsKey("light_absorbtion")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).lightAbsorbtion = Integer.parseInt(m.get("light_absorbtion"));
                }
            }
            if (m.containsKey("resistance")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).resistance = Integer.parseInt(m.get("resistance"));
                }
            }
            if (m.containsKey("drops")) {
               for (int i = 0; i <= b.size() - 1; i++) {
                    for (String str : m.get("drops").split(";")){
                        String[] name = str.split("#");
                        String realname = "";
                        for (int c = 0; c <= name.length - 1; c++) {
                            if (name[c].equals("variant")) {
                                name[c] = variants.get(i);
                            }
                            realname = realname + name[c];
                        }
                        b.get(i).drops.add(new WorldAccessor().GetItem(z, realname));
                    }
                }
            }
            if (m.containsKey("season_color_map_path")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).seasonColorMapPath = m.get("season_color_map_path");
                }
            }
            for (int i = 0; i <= b.size() - 1; i++) {
                z.loadedTopBlocks.add(b.get(i));
            }
            //z.loadedNormalBlocks.add(new Norma)
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    private void ReadGroundBlocks() {
        //File[] allFiles = new File("assets/blocktypes/normal").listFiles();
        //File[] txtFiles = allFiles.filter(file -> !file.isDirectory());
        List<File> fileSet = Stream.of(new File("assets/blocktypes/onground").listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " GroundBlocks files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            List<String> variants = new ArrayList<String>();
            List<BlockGround> b = new ArrayList<BlockGround>();
            Class blockClass = BlockGround.class.getClass();
            
            if (m.containsKey("class_file_path")) {                           
                
                try{
                    String className = (m.get("class_file_path").split("/")[m.get("class_file_path").split("/").length - 1].split("[.]"))[0];
                    RuntimeCompiler r = new RuntimeCompiler();
                    r.addClass(className, ReadClasses(r, m.get("class_file_path")));
                    r.compile();
                    blockClass = r.getCompiledClass(className);
                }
                catch(Exception e){System.out.println("[ERROR] error while reading class of block " + m.get("name"));}
            }
            if (m.containsKey("variants")) {
                String[] s = m.get("variants").split(";");
                for (int i = 0; i <= s.length - 1; i++) {
                    variants.add(s[i]);
                    try{b.add((BlockGround)blockClass.newInstance());}catch(Exception e){b.add(new BlockGround());}
                }
            }
            if (variants.size() == 0) {variants.add("");}
            if (b.size() == 0) {try{b.add((BlockGround)blockClass.newInstance());}catch(Exception e){b.add(new BlockGround());}}
            if (m.containsKey("name")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] name = m.get("name").split("#");
                    String realname = "";
                    for (int c = 0; c <= name.length - 1; c++) {
                        if (name[c].equals("variant")) {
                            name[c] = variants.get(i);
                        }
                        realname = realname + name[c];
                    }
                    b.get(i).name = realname;
                    //System.out.println(realname);
                }
            }
            if (m.containsKey("value")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    try{b.get(i).value = Integer.parseInt(m.get("value"));}catch(Exception e){b.get(i).valueInString =m.get("value");}
                }
            }
            if (m.containsKey("hideplayer")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).hidePlayer = Boolean.parseBoolean(m.get("hideplayer"));
                }
            }
            if (m.containsKey("drawoffsetY")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).drawoffsetY = Integer.parseInt(m.get("drawoffsetY"));
                }
            }
            if (m.containsKey("drawoffsetX")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).drawoffsetX = Integer.parseInt(m.get("drawoffsetX"));
                }
            }
            if (m.containsKey("texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                        b.get(i).textureId = z.loadedBlockTextures.size() - 1;
                        b.get(i).texturePath = "assets/textures/" + realtexture + ".png";
                        if (!m.containsKey("vertical_texture") && m.containsKey("solid") && m.get("solid").equals("true")){
                            BufferedImage buffimg = ImageIO.read(new File("assets/textures/" + realtexture + ".png"));
                            BufferedImage img = new BufferedImage(60, z.vertical_size, BufferedImage.TYPE_INT_ARGB);
                            AffineTransform at = new AffineTransform();
                            at.scale(1, (double)z.vertical_size / (double)60);
                            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                            img = scaleOp.filter(buffimg, img);
                            z.loadedBlockTextures.add(img);
                            b.get(i).vertTextureId = z.loadedBlockTextures.size() - 1;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            if (m.containsKey("vertical_texture") && m.containsKey("solid") && m.get("solid").equals("true")){
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("vertical_texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        z.loadedBlockTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                        b.get(i).vertTextureId = z.loadedBlockTextures.size() - 1;
                    } catch (Exception e) {
                    }
                }
            }
            if (m.containsKey("material")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).material = m.get("material");
                }
            }
            if (m.containsKey("light_level")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).lightLevel = Integer.parseInt(m.get("light_level"));
                }
            }
            if (m.containsKey("light_absorbtion")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).lightAbsorbtion = Integer.parseInt(m.get("light_absorbtion"));
                }
            }
            if (m.containsKey("resistance")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).resistance = Integer.parseInt(m.get("resistance"));
                }
            }
            if (m.containsKey("solid")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).solid = Boolean.parseBoolean(m.get("solid"));
                }
            }
            if (m.containsKey("drops")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    for (String str : m.get("drops").split(";")){
                        String[] name = str.split("#");
                        String realname = "";
                        for (int c = 0; c <= name.length - 1; c++) {
                            if (name[c].equals("variant")) {
                                name[c] = variants.get(i);
                            }
                            realname = realname + name[c];
                        }
                        b.get(i).drops.add(new WorldAccessor().GetItem(z, realname));
                    }
                }
            }
            if (m.containsKey("draw_normal_block")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).drawNormalBlock = Boolean.parseBoolean(m.get("draw_normal_block"));
                }
            }
            if (m.containsKey("season_color_map_path")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).seasonColorMapPath = m.get("season_color_map_path");
                }
            }
            for (int i = 0; i <= b.size() - 1; i++) {
                z.loadedGroundBlocks.add(b.get(i));
            }
            //z.loadedNormalBlocks.add(new Norma)
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
        //------------------------------------------------Read and inizialize all blockpatch generation files
    public List<Map<String,String>> ReadBlockPatches() {

        List<Map<String,String>> patches = new ArrayList<Map<String,String>>();
        List<File> fileSet = Stream.of(new File("assets/worldgen/blockpatches").listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        //System.out.println(fileSet.size() + " files found");
        
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            
            newJSONreader jsonreader = new newJSONreader();
            patches.add(jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath()));
        }
        
        return patches;
    }

    //------------------------------------------------Read and inizialize  all item files
    
    private void ReadItems(String path) {

        List<File> fileSet = Stream.of(new File(path).listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " Item files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            Map<String, String> attributes = new HashMap<String, String>();
            List<String> variants = new ArrayList<String>();
            List<Item> b = new ArrayList<Item>();
            Class itemClass = Item.class.getClass();

            if (m.containsKey("class_file_path")) {                           
                
                try{
                    String className = (m.get("class_file_path").split("/")[m.get("class_file_path").split("/").length - 1].split("[.]"))[0];
                    RuntimeCompiler r = new RuntimeCompiler();
                    r.addClass(className, ReadClasses(r, m.get("class_file_path")));
                    r.compile();
                    itemClass = r.getCompiledClass(className);
                }
                catch(Exception e){System.out.println("[ERROR] error while reading class of item " + m.get("name") + ": " + e);}
            }
            
            if (m.containsKey("variants")) {
                String[] s = m.get("variants").split(";");
                for (int i = 0; i <= s.length - 1; i++) {
                    variants.add(s[i]);
                    try{
                        b.add((Item)itemClass.newInstance());
                    }catch(Exception e){
                        b.add(new Item());                        
                    }
                }
            }
            if (variants.size() == 0) {variants.add("");}
            if (b.size() == 0) {try{b.add((Item)itemClass.newInstance());}catch(Exception e){b.add(new Item());}}
            if (m.containsKey("texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        b.get(i).image = ImageIO.read(new File("assets/textures/" + realtexture + ".png"));
                        b.get(i).imagePath = "assets/textures/" + realtexture + ".png";
                    } catch (Exception e) {
                    	try{b.get(i).image = ImageIO.read(new File("assets/textures/miscellaneous/noTexture.png"));
                    	b.get(i).imagePath = "assets/textures/miscellaneous/noTexture.png";}catch(Exception ex) {}
                    }
                }
            }
            else {
                for (int i = 0; i <= b.size() - 1; i++) {
                    try {
                        b.get(i).image = ImageIO.read(new File("assets/textures/miscellaneous/noTexture.png"));
                        b.get(i).imagePath = "assets/textures/miscellaneous/noTexture.png";
                    } catch (Exception e) {
                    }
                }
            }
            
            if (m.containsKey("name")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] name = m.get("name").split("#");
                    String realname = "";
                    for (int c = 0; c <= name.length - 1; c++) {
                        if (name[c].equals("variant")) {
                            name[c] = variants.get(i);
                        }
                        realname = realname + name[c];
                    }
                    b.get(i).name = realname;
                    //System.out.println(realname);
                }
            }
            if (m.containsKey("type")){
                String t = m.get("type");
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).type = t;
                }
            }
            
            if (m.containsKey("attributes")) {
                String[] at = m.get("attributes").split("}");
                for (int c = 0; c <= at.length - 1; c++) {
                    attributes.put(at[c].replace("{", "").split(";")[0], at[c].replace("{", "").split(";")[1]);
                }
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).Inizialize(attributes);
                }
            }
            if (m.containsKey("max_stack_size")) {
                int stackSize = Integer.parseInt(m.get("max_stack_size"));
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).maxStackSize = stackSize;
                }
            }
            if (m.containsKey("description")) {
                String[] descs = m.get("description").split("}");
                for (int t = 0; t <= descs.length - 1; t++)
                {
                    String[] s = descs[t].replace("{", "").split(";");
                    for (int i = 0; i <= b.size() - 1; i++) {
                        if (b.get(i).name.equals(s[0])){ b.get(i).description = s[1]; break; }
                    }
                }
            }            
            if (m.containsKey("namecolor")) {
                try{
                    String[] colors = m.get("namecolor").split(",");
                    for (int i = 0; i <= b.size() - 1; i++) {
                        b.get(i).NameColor = new Color(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
                    }
                }catch(Exception e){}
            }
            for (int i = 0; i <= b.size() - 1; i++) {
                z.loadedItems.add(b.get(i));
            }
            //z.loadedNormalBlocks.add(new Norma)
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    //-------------------------------------------------Read and and inizialize all Entity-related files
    
    private void ReadEntities(String path) {

        List<File> fileSet = Stream.of(new File(path).listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " Entity files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            List<String> variants = new ArrayList<String>();
            List<Entity> b = new ArrayList<Entity>();
            Class entityClass = Entity.class;
            
            if (m.containsKey("class_file_path")) {                           
                
                try{
                    String className = (m.get("class_file_path").split("/")[m.get("class_file_path").split("/").length - 1].split("[.]"))[0];
                    RuntimeCompiler r = new RuntimeCompiler();
                    r.addClass(className, ReadClasses(r, m.get("class_file_path")));
                    r.compile();
                    entityClass = r.getCompiledClass(className);
                }
                catch(Exception e){System.out.println("[ERROR] error while reading class of item " + m.get("name"));}
            }
            else{
                if (m.containsKey("type")){
                    if (m.get("type").equals("Entity"))entityClass = Entity.class;
                    if (m.get("type").equals("Mob"))entityClass = Mob.class;
                    if (m.get("type").equals("IntelligentMob"))entityClass = IntelligentMob.class;
                }
            }
            
            if (m.containsKey("variants")) {
                String[] s = m.get("variants").split(";");
                for (int i = 0; i <= s.length - 1; i++) {
                    variants.add(s[i]);
                    try{
                        b.add((Entity)entityClass.newInstance());
                    }catch(Exception e){
                        b.add(new Entity());                        
                    }
                }
            }
            if (variants.size() == 0) {variants.add("");}
            if (b.size() == 0) {try{b.add((Entity)entityClass.newInstance());}catch(Exception e){b.add(new Entity());}}
            
            if (m.containsKey("speed")) {
               for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).Speed = Integer.parseInt(m.get("speed"));
                }
            }
            
            if (m.containsKey("texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        b.get(i).image = ImageIO.read(new File("assets/textures/" + realtexture + ".png"));
                        b.get(i).imagePath = "assets/textures/" + realtexture + ".png";
                    } catch (Exception e) {
                    	try{b.get(i).image = ImageIO.read(new File("assets/textures/miscellaneous/noTexture.png"));
                    	b.get(i).imagePath = "assets/textures/miscellaneous/noTexture.png";}catch(Exception ex) {}
                    }
                }
            }
            else {
                for (int i = 0; i <= b.size() - 1; i++) {
                    try {
                        b.get(i).image = ImageIO.read(new File("assets/textures/miscellaneous/noTexture.png"));
                        b.get(i).imagePath = "assets/textures/miscellaneous/noTexture.png";
                    } catch (Exception e) {
                    }
                }
            }
            
            if (m.containsKey("type")){
                if (m .get("type").equals("IntelligentMob") || m.get("type").equals("Mob")){
                    
                }
                    if (m.containsKey("idle_animation")) {

                        for (int i = 0; i <= b.size() - 1; i++) {
                            String[] anim = m.get("idle_animation").split(";");
                            Map<String, String> map = new HashMap<String, String>();
                            for (String s : anim){
                                map.put(s.split("=")[0], s.split("=")[1]);
                            }
                            String realtexture = "";
                            String[] texture = map.get("sprite").split("#");
                            for (int c = 0; c <= texture.length - 1; c++) {
                                if (texture[c].equals("variant")) {
                                    texture[c] = variants.get(i);
                                }
                                realtexture = realtexture + texture[c];
                            }
                            try {
                                b.get(i).idleAnimPath = map.get(realtexture);
                                ((Mob)b.get(i)).animation = new animationManager(realtexture, Integer.parseInt(map.get("time")), Integer.parseInt(map.get("frames")), Integer.parseInt(map.get("frame_heigth")));
                                ((Mob)b.get(i)).idleAnimation = new animationManager(realtexture, Integer.parseInt(map.get("time")), Integer.parseInt(map.get("frames")), Integer.parseInt(map.get("frame_heigth")));
                            } catch (Exception e) {
                            }
                        }
                    }
            }
            
            if (m.containsKey("drawoffsetX")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).drawoffsetX = Integer.parseInt(m.get("drawoffsetX"));
                }
            }
            
            if (m.containsKey("drawoffsetY")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).drawoffsetY = Integer.parseInt(m.get("drawoffsetY"));
                }
            }
            
            if (m.containsKey("hitboxes")) {
                String[] hitboxes = m.get("hitboxes").split(";");
                
                for (int i = 0; i <= b.size() - 1; i++) {
                    try{
                        for (int c = 0; c <= hitboxes.length - 1; c++) {
                            if (!hitboxes[c].equals("")) 
                            {
                                String[] hitboxParams = hitboxes[c].split(",");
                                if (b.get(i).hitboxes.size() == 1 && c == 0)
                                    b.get(i).hitboxes.remove(0);
                                b.get(i).hitboxes.add(new Hitbox(Integer.parseInt(hitboxParams[0]), Integer.parseInt(hitboxParams[1]), Integer.parseInt(hitboxParams[2]), Integer.parseInt(hitboxParams[3])));
                            }
                        }
                    }
                    catch(Exception e){}
                }
            }            
            
            if (m.containsKey("type")){
                
                if (m.get("type").equals("IntelligentMob")){
                
                    if (m.containsKey("intelligence_properties")){
                        String[] iProperties = m.get("intelligence_properties").split("}");
                        for (String s : iProperties){
                            if (!s.equals("")){
                                s = s.replace("{", "").replace("}", "");

                                for (Entity e : b){
                                    String[] propParams = s.split(";");
                                    Map map = new HashMap();
                                    for (int i = 3; i <= propParams.length - 1; i++){
                                        enumIntelligencePropertieChangeConditions eIpCc = null;
                                        if (propParams[i].split("#")[0].equals("OnCollideWithProjectile")) eIpCc = enumIntelligencePropertieChangeConditions.OnCollideWithProjectile;
                                        if (propParams[i].split("#")[0].equals("OnCollideWithEntity")) eIpCc = enumIntelligencePropertieChangeConditions.OnCollideWithEntity;
                                        if (propParams[i].split("#")[0].equals("OnProjDamage")) eIpCc = enumIntelligencePropertieChangeConditions.OnProjDamage;
                                        if (propParams[i].split("#")[0].equals("OnMeleeDamage")) eIpCc = enumIntelligencePropertieChangeConditions.OnMeleeDamage;
                                        if (propParams[i].split("#")[0].equals("OnKillFriend")) eIpCc = enumIntelligencePropertieChangeConditions.OnKillFriend;
                                        if (propParams[i].split("#")[0].equals("OnKillPet")) eIpCc = enumIntelligencePropertieChangeConditions.OnKillPet;
                                        if (propParams[i].split("#")[0].equals("OnSaveFriend")) eIpCc = enumIntelligencePropertieChangeConditions.OnSaveFriend;
                                        if (propParams[i].split("#")[0].equals("OnSavePet")) eIpCc = enumIntelligencePropertieChangeConditions.OnSavePet;
                                        if (propParams[i].split("#")[0].equals("OnSecure")) eIpCc = enumIntelligencePropertieChangeConditions.OnSecure;
                                        map.put(eIpCc, Integer.parseInt(propParams[i].split("#")[1]));
                                    }
                                    if (e instanceof IntelligentMob)
                                    {
                                        ((IntelligentMob)e).intelligenceProperties.add(new IntelligencePropertie(propParams[0], Integer.parseInt(propParams[1]), map));
                                        ((IntelligentMob)e).intelligenceProperties.get(((IntelligentMob)e).intelligenceProperties.size() - 1).variation = Integer.parseInt(propParams[2]);
                                    }
                                }
                            }
                        }
                        
                        
                    }
                }
                
            }
            
            if (m.containsKey("attributes")){
                        String[] iProperties = m.get("attributes").split("}");
                        for (String s : iProperties){
                            if (!s.equals("")){
                                s = s.replace("{", "").replace("}", "");
                                
                                for (Entity e : b){
                                    String[] attrubutes = s.split(";");
                                    double[] d = new double[1];
                                    d[0] = Double.parseDouble(attrubutes[1]);
                                    try{e.attributes.put(attrubutes[0], d);}catch(Exception excptn){}
                                }
                            }
                        }
                        
                    }
            
            if (m.containsKey("world_gen_spawn_conditions")){
                        String[] iProperties = m.get("world_gen_spawn_conditions").split("}");
                        for (String s : iProperties){
                            if (!s.equals("")){
                                s = s.replace("{", "").replace("}", "");
                                Map map = new HashMap();
                                for (String st : s.split(";")){
                                    map.put(st.split("=")[0], st.split("=")[1]);
                                }
                                for (Entity e : b){
                                    double chance = 0; if (map.containsKey("chance"))chance = Double.parseDouble(map.get("chance").toString());
                                    int groupSize = 1; if (map.containsKey("group_size"))groupSize = Integer.parseInt(map.get("group_size").toString());
                                    int maxEntAtOnePlace = 0; if (map.containsKey("max_ent_on_loaded_chunks"))maxEntAtOnePlace = Integer.parseInt(map.get("max_ent_on_loaded_chunks").toString());
                                    int groupRadius = 0; if (map.containsKey("group_radius"))groupRadius = Integer.parseInt(map.get("group_radius").toString());
                                    String companions = ""; if (map.containsKey("companions"))companions = map.get("companions").toString();
                                    boolean needsFreeBlock = true; if (map.containsKey("needs_free_block"))needsFreeBlock = Boolean.parseBoolean(map.get("needs_free_block").toString());
                                    String block = ""; if (map.containsKey("block"))block = map.get("block").toString();
                                    try{
                                        EntitySpawnCondition spanwCon = new EntitySpawnCondition();
                                        spanwCon.chance = chance; spanwCon.block = block;
                                        spanwCon.groupSize = groupSize; spanwCon.maxEntAtOnePlace = maxEntAtOnePlace;
                                        spanwCon.needsFreeBlock = needsFreeBlock; spanwCon.companions = companions;
                                        spanwCon.groupRadius = groupRadius;
                                        e.worldGenSpawnConditions.add(spanwCon);
                                    }catch(Exception excptn){}
                                }
                            }
                        }                       
                        
                    }
            if (m.containsKey("runtime_spawn_conditions")){
                        String[] iProperties = m.get("runtime_spawn_conditions").split("}");
                        for (String s : iProperties){
                            if (!s.equals("")){
                                s = s.replace("{", "").replace("}", "");
                                Map map = new HashMap();
                                for (String st : s.split(";")){
                                    map.put(st.split("=")[0], st.split("=")[1]);
                                }
                                for (Entity e : b){
                                    double chance = 0; if (map.containsKey("chance"))chance = Double.parseDouble(map.get("chance").toString());
                                    double maxLightLevel = 30; if (map.containsKey("max_light_level"))maxLightLevel = Double.parseDouble(map.get("max_light_level").toString());
                                    double minLightLevel = 0; if (map.containsKey("min_light_level"))minLightLevel = Double.parseDouble(map.get("min_light_level").toString());
                                    int groupSize = 1; if (map.containsKey("group_size"))groupSize = Integer.parseInt(map.get("group_size").toString());
                                    int groupRadius = 0; if (map.containsKey("group_radius"))groupRadius = Integer.parseInt(map.get("group_radius").toString());
                                    int maxPlayerDistance = 30; if (map.containsKey("max_player_distance"))maxPlayerDistance = Integer.parseInt(map.get("max_player_distance").toString());
                                    int minPlayerDistance = 30; if (map.containsKey("min_player_distance"))minPlayerDistance = Integer.parseInt(map.get("min_player_distance").toString());
                                    int maxEntAtOnePlace = 0; if (map.containsKey("max_ent_on_loaded_chunks"))maxEntAtOnePlace = Integer.parseInt(map.get("max_ent_on_loaded_chunks").toString());
                                    boolean needsFreeBlock = true; if (map.containsKey("needs_free_block"))needsFreeBlock = Boolean.parseBoolean(map.get("needs_free_block").toString());
                                    String companions = ""; if (map.containsKey("companions"))companions = map.get("companions").toString();
                                    String block = ""; if (map.containsKey("block"))block = map.get("block").toString();
                                    try{
                                        EntitySpawnCondition spanwCon = new EntitySpawnCondition();
                                        spanwCon.chance = chance; spanwCon.block = block; spanwCon.maxLightLevel = maxLightLevel; spanwCon.minLightLevel = minLightLevel;
                                        spanwCon.groupSize = groupSize; spanwCon.groupRadius = groupRadius; spanwCon.maxEntAtOnePlace = maxEntAtOnePlace;
                                        spanwCon.maxPlayerDistance = maxPlayerDistance; spanwCon.minPlayerDistance = minPlayerDistance;
                                        spanwCon.needsFreeBlock = needsFreeBlock; spanwCon.companions = companions;
                                        e.runtimeSpawnConditions.add(spanwCon);
                                    }catch(Exception excptn){}
                                }
                            }
                        }                       
                        
                    }
            
            if (m.containsKey("despawn_conditions")){
                        String[] iProperties = m.get("despawn_conditions").split("}");
                        for (String s : iProperties){
                            if (!s.equals("")){
                                s = s.replace("{", "").replace("}", "");
                                Map map = new HashMap();
                                for (String st : s.split(";")){
                                    map.put(st.split("=")[0], st.split("=")[1]);
                                }
                                for (Entity e : b){
                                    double chance = 1; if (map.containsKey("chance"))chance = Double.parseDouble(map.get("chance").toString());
                                    int maxLightLevel = -1; if (map.containsKey("max_light_level"))maxLightLevel = Integer.parseInt(map.get("max_light_level").toString());
                                    int minLightLevel = -1; if (map.containsKey("min_light_level"))minLightLevel = Integer.parseInt(map.get("min_light_level").toString());
                                    int maxPlayerDistance = -1; if (map.containsKey("max_player_distance"))maxPlayerDistance = Integer.parseInt(map.get("max_player_distance").toString());
                                    int minPlayerDistance = -1; if (map.containsKey("min_player_distance"))minPlayerDistance = Integer.parseInt(map.get("min_player_distance").toString());
                                    try{
                                        EntityDespawnCondition despanwCon = new EntityDespawnCondition();
                                        despanwCon.chance = chance; despanwCon.maxLightLevel = maxLightLevel; despanwCon.minLightLevel = minLightLevel;
                                        despanwCon.maxPlayerDistance = maxPlayerDistance; despanwCon.minPlayerDistance = minPlayerDistance;
                                        e.despawnConditions.add(despanwCon);
                                    }catch(Exception excptn){}
                                }
                            }
                        }                       
                        
                    }
            
            if (m.containsKey("type")){
                
                if (m.get("type").equals("IntelligentMob") || m.get("type").equals("Mob")){
                
                    if (m.containsKey("ai_tasks")){
                        String[] aTasks = m.get("ai_tasks").split("}");
                        for (String s : aTasks){
                            if (!s.equals("")){
                                s = s.replace("{", "").replace("}", "");

                                for (Entity e : b){
                                    String[] aTasksParams = s.split(";");
                                    if (e instanceof Mob || e instanceof IntelligentMob)
                                    {
                                        ((Mob)e).AITasks.add(new WorldAccessor().getAITask(z, aTasksParams[0]).CreateNew());
                                        Map map = new HashMap();
                                        for (int i = 3; i <= aTasksParams.length - 1; i++){
                                            String[] string = aTasksParams[i].split("=");
                                            map.put(string[0], string[1]);
                                        }
                                        ((Mob)e).AITasks.get(((Mob)e).AITasks.size() - 1).onLoad(map);
                                        ((Mob)e).AITasks.get(((Mob)e).AITasks.size() - 1).owningEntity = e;
                                        ((Mob)e).AITasks.get(((Mob)e).AITasks.size() - 1).priority = Integer.parseInt(aTasksParams[1]);
                                        for (String str : aTasksParams[2].split("#"))
                                        ((Mob)e).AITasks.get(((Mob)e).AITasks.size() - 1).conditions.add(str);
                                    }
                                }
                            }
                        }
                        
                        
                    }
                }
                
            }
            
            if (m.containsKey("name")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] name = m.get("name").split("#");
                    String realname = "";
                    for (int c = 0; c <= name.length - 1; c++) {
                        if (name[c].equals("variant")) {
                            name[c] = variants.get(i);
                        }
                        realname = realname + name[c];
                    }
                    b.get(i).name = realname;
                    //System.out.println(realname);
                }
            }
            for (int i = 0; i <= b.size() - 1; i++) {
                z.loadedEntities.add(b.get(i));
            }
            //z.loadedNormalBlocks.add(new Norma)
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    public void ReadAITasks(String path){
        
        List<File> fileSet = Stream.of(new File(path).listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " AITask files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
                
            String className = "";
            try{
                System.out.println("classpath AITASKS = " + fileSet.get(a).getAbsolutePath());
                //if (System.getProperty("os.name").equals("Windows"))
                className = fileSet.get(a).getAbsolutePath().split("\\\\")[fileSet.get(a).getAbsolutePath().split("\\\\").length - 1].split("[.]")[0];//  \\\\
                if (System.getProperty("os.name").equals("Linux"))
                className = fileSet.get(a).getAbsolutePath().split("/")[fileSet.get(a).getAbsolutePath().split("/").length - 1].split("[.]")[0];//  \\\\
                System.out.println("classname int AITASKS = ______________________ " + className);
                RuntimeCompiler r = new RuntimeCompiler();
                r.addClass(className, ReadClasses(r, fileSet.get(a).getAbsolutePath()));
                r.compile();
                z.loadedAITasks.put(className, (AITaskBase)r.getCompiledClass(className).newInstance());
            }
            catch(Exception e){System.out.println("[ERROR] error while reading class of aiTask " + className + ": " + e);}
        }
        
    }
    
    //--------------------------------------------------Read and inizialize all Projectiles
    
    public void ReadProjectiles(String path) {

        List<File> fileSet = Stream.of(new File(path).listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " Projectile files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            List<String> variants = new ArrayList<String>();
            List<Projectile> b = new ArrayList<Projectile>();
            Class projectileClass = Entity.class;
            
            if (m.containsKey("class_file_path")) {                           
                
                try{
                    String className = (m.get("class_file_path").split("/")[m.get("class_file_path").split("/").length - 1].split("[.]"))[0];
                    RuntimeCompiler r = new RuntimeCompiler();
                    r.addClass(className, ReadClasses(r, m.get("class_file_path")));
                    r.compile();
                    projectileClass = r.getCompiledClass(className);
                }
                catch(Exception e){System.out.println("[ERROR] error while reading class of projectile " + m.get("name"));}
            }
            else{
                projectileClass = Projectile.class;
            }
            
            if (m.containsKey("variants")) {
                String[] s = m.get("variants").split(";");
                for (int i = 0; i <= s.length - 1; i++) {
                    variants.add(s[i]);
                    try{
                        b.add((Projectile)projectileClass.newInstance());
                    }catch(Exception e){
                        b.add(new Projectile());                        
                    }
                }
            }
            if (variants.size() == 0) {variants.add("");}
            if (b.size() == 0) {try{b.add((Projectile)projectileClass.newInstance());}catch(Exception e){b.add(new Projectile());}}
            
            
            if (m.containsKey("texture")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] texture = m.get("texture").split("#");
                    String realtexture = "";
                    for (int c = 0; c <= texture.length - 1; c++) {
                        if (texture[c].equals("variant")) {
                            texture[c] = variants.get(i);
                        }
                        realtexture = realtexture + texture[c];
                    }
                    try {
                        
                        z.loadedProjectileTextures.add(ImageIO.read(new File("assets/textures/" + realtexture + ".png")));
                        b.get(i).imagePath = "assets/textures/" + realtexture + ".png";
                    } catch (Exception e) {
                    	b.get(i).imagePath = "assets/textures/miscellaneous/noTexture.png";
                    }
                }
            }
            else for (int i = 0; i <= b.size() - 1; i++) { b.get(i).imagePath = "assets/textures/miscellaneous/noTexture.png";}
            
            if (m.containsKey("attributes")) {
                Map<String, String> attributes = new HashMap<String, String>();                
                String[] at = m.get("attributes").split("}");
                for (int c = 0; c <= at.length - 1; c++) {
                    attributes.put(at[c].replace("{", "").split(";")[0], at[c].replace("{", "").split(";")[1]);
                }
                for (int i = 0; i <= b.size() - 1; i++) {
                    b.get(i).inizialize(z, attributes);
                }
            }
            
            if (m.containsKey("name")) {
                for (int i = 0; i <= b.size() - 1; i++) {
                    String[] name = m.get("name").split("#");
                    String realname = "";
                    for (int c = 0; c <= name.length - 1; c++) {
                        if (name[c].equals("variant")) {
                            name[c] = variants.get(i);
                        }
                        realname = realname + name[c];
                    }
                    b.get(i).name = realname;
                    //System.out.println(realname);
                }
            }
            for (int i = 0; i <= b.size() - 1; i++) {
                z.loadedProjectiles.add(b.get(i));
            }
            //z.loadedNormalBlocks.add(new Norma)
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    //-----------------------------------------Reand and inizialize all GUI Elements
    
    public void ReadGUIElements(String path) {

        List<File> fileSet = Stream.of(new File(path).listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " GUI files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            
            GUIElement e = new GUIElement();
            Class guiClass;
            
            try{
                String className = (fileSet.get(a).getAbsolutePath().split("\\\\")[fileSet.get(a).getAbsolutePath().split("\\\\").length - 1].split("[.]"))[0];
                if (System.getProperty("os.name").equals("Linux"))
                className = (fileSet.get(a).getAbsolutePath().split("/")[fileSet.get(a).getAbsolutePath().split("/").length - 1].split("[.]"))[0];
                RuntimeCompiler r = new RuntimeCompiler();
                r.addClass(className, ReadClasses(r, fileSet.get(a).getAbsolutePath()));
                r.compile();
                guiClass = r.getCompiledClass(className);
                e = (GUIElement)guiClass.newInstance();
                
                z.loadedGUIElements.add(e);
            }
            catch(Exception ex){System.out.println("[ERROR] error while reading class of GUIElement: " + ex);}
            
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    
    //-----------------------------------------Reand and inizialize all GUI Elements
    
    public void ReadCraftingRecepies(String path) {

        List<File> fileSet = Stream.of(new File(path).listFiles()).filter(file -> !file.isDirectory()).collect(Collectors.toList());

        System.out.println(fileSet.size() + " Recepie files found");
        for (int a = 0; a <= fileSet.size() - 1; a++) {
            
            newJSONreader jsonreader = new newJSONreader();
            Map<String, String> m;
            m = jsonreader.ReadNewJSON(fileSet.get(a).getAbsolutePath());
            
            CraftingRecepie c = new CraftingRecepie();
            try{
                if (m.containsKey("output")) c.output = m.get("output");
                if (m.containsKey("output_quantity")) c.outputQuantity = Integer.parseInt(m.get("output_quantity"));
                if (m.containsKey("crafting_station")) c.craftingStation = m.get("crafting_station");
                Set s = m.keySet();
                System.out.println("length = " + s.size());
                for (Object o : s){
                    if (!((String)o).equals("output") && !((String)o).equals("crafting_station")){
                        c.data.put((String)o, m.get((String)o));
                    }
                }
                z.loadedCraftingRecepies.add(c);
            }
            catch(Exception ex){System.out.println("[ERROR] error while reading Crafting recepie: " + ex);}
            
        }

        //.filter(file -> !file.isDirectory())
        //.map(File::getName)
        //.collect(Collectors.toSet());
    }
    
    //-----------------------------------------
    
    public String ReadClasses(RuntimeCompiler runtimecompiler, String classPath){
               
        File f = new File(classPath);
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            String code = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] lineParts = line.split("//");
                if (lineParts.length == 1)code += line;
                else
                {
                    line = lineParts[0] + "/*";
                    for (int a = 1; a <= lineParts.length - 1; a++) { line += lineParts[a]; }
                    line += "*/";
                    code += line;
                }
            }
            System.out.println("[NOTIFICATION] scucsessfully read class " + f.getName());

            return code;
        }
        catch (Exception e){}
        
        return null;
    }
   
    
    
}
