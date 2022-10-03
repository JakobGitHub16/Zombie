package zombie;

import java.sql.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import static zombie.Zombie.OptionsIsActive;
import static zombie.Zombie.GameIsActive;
import static zombie.Zombie.MousePressed;
import static zombie.Zombie.GraphicsIsActive;
import static zombie.Zombie.AudioIsActive;
import static zombie.Zombie.SettingsIsActive;
import static zombie.Zombie.CreditsIsActive;


import essentialThreads.*;

public class Zombie extends JPanel {

    //----------------------------------------------------------------------------------------------------------Metadata
    public static List<BlockNormal> loadedNormalBlocks = new ArrayList<BlockNormal>();
    public static List<BlockTop> loadedTopBlocks = new ArrayList<BlockTop>();
    public static List<BlockGround> loadedGroundBlocks = new ArrayList<BlockGround>();
    public static List<Class<?>> loadedBlockClasses = new ArrayList<Class<?>>();
    public static List<BufferedImage> loadedBlockTextures = new ArrayList<BufferedImage>();
    public static List<Item> loadedItems = new ArrayList<Item>();
    public static List<Entity> loadedEntities = new ArrayList<Entity>();
    public static Map<String, AITaskBase> loadedAITasks = new HashMap<String,AITaskBase>();
    public static List<BufferedImage> loadedProjectileTextures = new ArrayList<BufferedImage>();
    public static List<Projectile> loadedProjectiles = new ArrayList<Projectile>();
    public static List<GUIElement> loadedGUIElements = new ArrayList<GUIElement>();
    public static List<CraftingRecepie> loadedCraftingRecepies = new ArrayList<CraftingRecepie>();
    
    public static List<Chunk> loadedChunks = new ArrayList<Chunk>();
    
    //----------------------------------------------------------------------------------------------------------Metadata End
    
    //----------------------------------------------------------------------------------------------------------Essential Variables
    
    public Escape Esc = new Escape();
    public RuntimeCompiler runtimecompiler = new RuntimeCompiler();
    public EventHandler eventhandler = new EventHandler();
    
    public List<KeyEvent> pressedKeys = new ArrayList<KeyEvent>();
    public Projectile[] projectile = new Projectile[512];
    public static PlayMap Spielkarte;
    public static Map<String, PlayMap> playMaps = new HashMap<String, PlayMap>();
    public String worldName = "welt1";
    public List<Entity> entities = new ArrayList<Entity>();
    public List<GUIElement> guiElements = new ArrayList<GUIElement>();
    public List<Player> controllablePlayers = new ArrayList<Player>();
    public List<HitDetectionBox> hitDetectionBoxes = new ArrayList<HitDetectionBox>();
    public static List<HUDElement> hudElements = new ArrayList<HUDElement>();
    public GraphicsThread graphicsthread;
    public LightThread lightthread;
    public int viewedChunkLayer = 0;
    public int vertical_size = 30; // heigth of the vertical face of blocks in pixels
    public int timeSinceStart = 0;
    public int entitySpawnTick = 0;
    public int loadChunkRadius = 2;
    
    public boolean drawGUIs = true;
    public int gUIScale = 60;
    public int escapeScale = 60;
    
    //----------------------------------------------------------------------------------------------------------Essential Variables End
    
    //----------------------------------------------------------------------------------------------------------fps

    public long startMilliseconds = 0;
    public int frameDelay = 35; //35
    
    //----------------------------------------------------------------------------------------------------------fps End
    public BufferedImage break1, break2, break3, break4, break5;
    public int maxPlayers = 20;
    public serverthread Serverthread = new serverthread(this);
    public ServerServerThread Serverthread2 = new ServerServerThread(this);
    public static clientthread ClientThread = new clientthread();
    public static clientthread2 ClientThread2 = new clientthread2();
    public String[] clientIps = new String[maxPlayers];
    public int TimeToStart = 0;
    public static Generator defaultGenerator = null;
    
    public Toolkit toolkit = Toolkit.getDefaultToolkit();
    public int xscreensize = (int) toolkit.getScreenSize().getWidth();
    public int yscreensize = (int) toolkit.getScreenSize().getHeight();
    public Boolean keycount = false;
    public boolean mousepressed = false;
    //static Feld[][] Spielfeld = Spielkarte.Spielfeld; // 1 Kästchen 60 x 60 px
    public static JFrame frame = new JFrame("Zombie");
    public JPanel frame1 = new JPanel();
    public WorldAccessor worldAccessor = new WorldAccessor();
    public long lastMS = 0;
    
    public int lol = 0;
    public int fps = 0;
    public FPSTEST fpstest = new FPSTEST(this);
    
    //-----------------------------------------screen-badness-effekts
    public double screenshakeRadius = 0; //nothing = 0, very small = 1, small = 2, ok = 3, decent = 5, strong = 10, very strong = 60, extreme = 200
    public int screenshakeX = 0;
    public int screenshakeY = 0;
    public Color ColorGlichColor = new Color(255, 0, 255, 255);
    public double ColorGlichChance = 0.00;
    public int minBrightnessAlpha = 230;
    //-----------------------------------------end screen-badness-effekts
    
    //public static EntityPlayer hero = new EntityPlayer(100, 100, 2);
    
    public static KeyHandler kh;
    public static MouseHandler mh;
    public static WheelHandler mwh;
    public int Z = 0, Q = 0;          //    public int Z = hero.X - (xscreensize / 2), Q = hero.Y - (yscreensize / 2);
    public static boolean GameIsActive = true;
    public static boolean OptionsIsActive = true;
    public static boolean GraphicsIsActive = true;
    public static boolean AudioIsActive = true;
    public static boolean SettingsIsActive = true;  
    public static boolean CreditsIsActive = true;
    public static boolean MousePressed = false;
    public static boolean MouseClicked = false;
    
    //-----------------------------------------debug
    public static boolean ChunkBorders = false;
    public static boolean EntityDebuginfo = false;
    
    public int started = 0;
       
    
    public Zombie() {

        try {
            if (Z < 0) {
                Z = 0;
            }
            if (Z > (6000 - xscreensize)) {
                Z = (6000 - xscreensize);
            }
            if (Q < 0) {
                Q = 0;
            }
            if (Q > (6000 - yscreensize)) {
                Q = (6000 - yscreensize);
            }
            System.out.println("[NOTIFICATION]: Screensize successfull inizialised");
            
            
            loadedBlockTextures.add(ImageIO.read(new File("assets/textures/miscellaneous/noTexture.png")));
            loadedProjectileTextures.add(ImageIO.read(new File("assets/textures/miscellaneous/noTexture.png")));
            
        } catch (Exception e) {
            System.out.println("[ERROR]: Screen-wach-area was out of borders " + e);
        }
        
        //wwdServerthread2.start();ClientThread.start(); ClientThread2.start();
                
        entities.add(new EntityPlayer());
        entities.get(0).X = 360;
        entities.get(0).Y = 360;
        entities.get(0).Speed = 10;
        entities.get(0).chunkLayer = 0;
        
        
        controllablePlayers.add(new Player(entities.get(entities.size() - 1)));
        
        try{
            break1 = ImageIO.read(new File("assets/textures/miscellaneous/break1.png"));
            break2 = ImageIO.read(new File("assets/textures/miscellaneous/break2.png"));
            break3 = ImageIO.read(new File("assets/textures/miscellaneous/break3.png"));
            break4 = ImageIO.read(new File("assets/textures/miscellaneous/break4.png"));
            break5 = ImageIO.read(new File("assets/textures/miscellaneous/break5.png"));
        }catch(Exception e){}
        this.fpstest.start();
        //this.graphicsthread = new GraphicsThread(this);
        //this.graphicsthread.start();
        //this.lightthread = new LightThread(this);
        //this.lightthread.start();
    }

    public void paint(Graphics g) {
        
        this.entitySpawnTick++;
        
        if (this.controllablePlayers.size() > 0 && this.playMaps.get(this.controllablePlayers.get(0).entity.onMap) != null)this.Spielkarte = this.playMaps.get(this.controllablePlayers.get(0).entity.onMap);
        //if (Spielkarte == null) return;
        this.startMilliseconds = System.currentTimeMillis();
        
        /*if (entities.size() == 1){
            WorldAccessor w = new WorldAccessor();
            
            try{
                for (int a = 0; a <= 100; a++)
                w.SpawnEntity(this, w.getEntity(this, "boneling").CreateNew(), 700 + a, 700 + a);
            }catch(Exception e){}
        }*/
        
        fps++;
        try{if (lol == 1){System.out.println("fps: " + fps + " ChunkGenThreads: " + Spielkarte.gct.size() + " UnloadChunkThreads: " + Spielkarte.uct.size() + " time since start: " + timeSinceStart + " Maps: " + playMaps.size()); fps = 0; lol = 0;
        timeSinceStart++;}}catch(Exception exc){}
        //if (started == 0) {this.Spielkarte.GenerateChunks(z);started = 1;}
        //Spielkarte.GenerateChunks(z);
        
        //if (started == 0)
        //{Serverthread2.start();ClientThread.start(); ClientThread2.start(); started += 1; if (started >= 2) {started = 0;}}
        
        //for (int a = 0; a <= Serverthread2.z.clientIps.length - 1; a++)
                        //{
                        //    System.out.print(Serverthread2.z.clientIps[a] + " ");
                        //}
                        //System.out.println("\n" + Serverthread2.CurrentIp);

        lastMS = System.currentTimeMillis();
        
        if (GameIsActive == true) 
        {
        	if (Spielkarte != null) {                
                    
	            try{
	                screenshakeX = (new Random()).nextInt((int)(screenshakeRadius * 2));
	                if (screenshakeX > screenshakeRadius) screenshakeX = (int)(screenshakeX - screenshakeRadius) * -1;
	                if (screenshakeX == 1 || screenshakeX == -1) { screenshakeX = (int)(((new Random()).nextDouble() + 0.1) * screenshakeX); }
	                screenshakeY = (new Random()).nextInt((int)(screenshakeRadius * 2));
	                if (screenshakeY > screenshakeRadius) screenshakeY = (int)(screenshakeY - screenshakeRadius) * -1;
	                if (screenshakeY == 1 || screenshakeY == -1) { screenshakeY = (int)(((new Random()).nextDouble() + 0.1) * screenshakeY); }
	            }catch(Exception e){}
	            
	            try{
	                if (Spielkarte.currentWindEffect == 0)
	                {
	                    Spielkarte.wind = new Random().nextInt(40);
	                    Spielkarte.currentWindEffectTarget = new Random().nextInt((int)Spielkarte.wind * 2);
	                    if (Spielkarte.currentWindEffectTarget > Spielkarte.wind)Spielkarte.currentWindEffectTarget = Spielkarte.currentWindEffectTarget * -1 + Spielkarte.wind;
	                }
	                
	                if (Spielkarte.currentWindEffect > Spielkarte.currentWindEffectTarget) Spielkarte.currentWindEffect -= 0.5;
	                if (Spielkarte.currentWindEffect < Spielkarte.currentWindEffectTarget) Spielkarte.currentWindEffect += 0.5;
	                if (Spielkarte.currentWindEffect == Spielkarte.currentWindEffectTarget) Spielkarte.currentWindEffectTarget = 0;
	                
	            }catch(Exception e){}
	
	            
	            //------------------------------renderer--------------------
	            
	            
	            int layers = 2;
	            try{layers = Spielkarte.ChunkCollumns[(int)(Z / 60 / 16)][(int)(Q / 60 / 16)].chunklayers.size();}catch(Exception e){}
	            
	            for (int layer = layers - 1; layer >= viewedChunkLayer; layer--)
	            {
	                int depthOffset = -(viewedChunkLayer - layer) * vertical_size;
	                List<int[]> renderBlocks = new ArrayList<int[]>();
	                for (int a = (int) Z / 60; a <= (int) ((Z + xscreensize) / 60); a++) {
	                for (int b = (int) Q / 60; b <= (int) ((Q + yscreensize) / 60); b++) {
	                    
                            worldAccessor.setLight(this, a, b, layer, Spielkarte.name);
                            
	                    if (Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)] != null)
	                    {   try{
	                            Feld feld = Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)].chunklayers.get(layer).Spielfeld[(int)(a - (a / 16) * 16)][(int)(b - (b / 16) * 16)];
	                            Feld feldAbove = worldAccessor.GetBlock(this, a, b, layer - 1, Spielkarte.name);
	                            Feld feldNorth = new WorldAccessor().GetBlock(this, a, b - 1, layer, Spielkarte.name);
	                                
                                    if ((feldAbove != null && feldAbove.blocknormal == null) || (feldAbove != null && feldAbove.blocknormal != null && feldAbove.blocknormal.transparent) || feldAbove == null
	                                        || layer == viewedChunkLayer){
                                        
                                            //-------------normal blocks
	                                    if (feld.blocknormal == null || (feld.blocknormal != null && feld.blocknormal.transparent == true)){
	                                        
	                                        if (feldNorth != null && feldNorth.blocknormal != null && feldNorth.blocknormal.transparent == false){
	                                            g.drawImage(loadedBlockTextures.get(feldNorth.blocknormal.verticalTextureId), a * 60 - Z + screenshakeX, b * 60 - Q + screenshakeY + depthOffset, this);
	                                        }
	                                    }
	                                    if ((feld.blocknormal != null && feld.blockground != null && feld.blockground.drawNormalBlock == true) || (feld.blocknormal != null && feld.blockground == null)){
	                                        g.drawImage(loadedBlockTextures.get(feld.blocknormal.textureId), a * 60 - Z + screenshakeX, b * 60 - Q + screenshakeY + depthOffset, this);
                                                if (feld.blocknormal.changetextures != null) feld.blocknormal.ManageAnimation(this);
                                                feld.blocknormal.drawBreakProgress(this, g, a, b, depthOffset);
	                                        if (feld.blocknormal.shouldReceiveGameTicks) feld.blocknormal.OnGameTick(this, g);
	                                    }
                                            
                                            //--------------ground blocks
	                                    if (feld.blockground == null || (feld.blockground != null && feld.blockground.solid == false)){
	                                        if (feldNorth != null && feldNorth.blockground != null && feldNorth.blockground.solid == true){
	                                            g.drawImage(loadedBlockTextures.get(feldNorth.blockground.vertTextureId), (a + feldNorth.blockground.drawoffsetX) * 60 - Z + screenshakeX, (b + feldNorth.blockground.drawoffsetY) * 60 - Q + screenshakeY + depthOffset, this);
	                                        }
	                                    }
	                                    if (feld.blockground != null){
	                                        if (feld.blockground.hidePlayer == false)
	                                        {g.drawImage(loadedBlockTextures.get(feld.blockground.textureId), (a + feld.blockground.drawoffsetX) * 60 - Z + screenshakeX, (b + feld.blockground.drawoffsetY) * 60 - Q + screenshakeY + depthOffset, this);
                                                feld.blockground.drawBreakProgress(this, g, a, b, depthOffset);}
	                                        if (feld.blockground.shouldReceiveGameTicks) feld.blockground.OnGameTick(this, g);
	                                    }
                                            
                                            if (feld.renderBlocks.size() != 0){
	                                        for (int[] tni : feld.renderBlocks){
	                                            if (!renderBlocks.contains(tni) && (tni[0] > (int) ((Z + xscreensize) / 60) || tni[1] > (int) ((Q + yscreensize) / 60))) renderBlocks.add(tni);
	                                        }
	                                    }
	                                    
                                            //---------------top blocks
	                                    if (feld.blocktop != null && layer != viewedChunkLayer)
	                                    {
                                                if (feld.blocktop.windAffected == false) g.drawImage(loadedBlockTextures.get(feld.blocktop.textureId), a * 60 - Z + screenshakeX + depthOffset, b * 60 - Q + screenshakeY + depthOffset, this);
                                                if (feld.blocktop.windAffected == true) g.drawImage(loadedBlockTextures.get(feld.blocktop.textureId), a * 60 - Z + screenshakeX + (int)(Spielkarte.currentWindEffect + Math.random()* 4), b * 60 - Q + screenshakeY + depthOffset, this);
                                            }
	                                }
	                        }
	                        catch(Exception e){}
	                            
	                    }
	                }
	                }
	                for (int i = 0; i <= projectile.length - 1; i++)
	                {
	                    if (projectile[i] != null)
	                    {
	                        if (projectile[i].chunklayer == layer)projectile[i].draw(g, Z, Q, depthOffset);
	                    }
	                }
	
	                for (int i = 0; i <= entities.size() - 1; i++){
	                    if (entities.get(i).chunkLayer == layer && entities.get(i).onMap.equals(Spielkarte.name)){
	                        entities.get(i).draw(this, g, Z, Q, screenshakeX, screenshakeY, depthOffset);
	                        if (EntityDebuginfo)entities.get(i).drawDebugInfo(this, g);
	                    }
	                }
                        //----------topblocks and hideplayer groundblocks
	                for (int a = (int) Z / 60; a <= (int) ((Z + xscreensize) / 60); a++) {
	                for (int b = (int) Q / 60; b <= (int) ((Q + yscreensize) / 60); b++) {
	                            
	                    if (Spielkarte.ChunkCollumns[(int)(a / 16)][(int)(b / 16)] != null)
	                            {
	                                try{        
                                                    Feld feld = worldAccessor.GetBlock(this, a, b, layer, Spielkarte.name);
	                                            if (feld.blockground != null) if (feld.blockground.hidePlayer == true)
	                                            {
	                                                    g.drawImage(loadedBlockTextures.get(feld.blockground.textureId), (a + feld.blockground.drawoffsetX) * 60 - Z + screenshakeX, (b + feld.blockground.drawoffsetY) * 60 - Q + screenshakeY, this);                 
                                                            feld.blockground.drawBreakProgress(this, g, a, b, depthOffset);
	                                            }
	                                            if (feld.blocktop != null)
	                                            {
	                                                Boolean malen = true;
	                                                for (Entity e : entities) 
                                                        {if ((int)(e.X / 60) >= a - 2 && (int)(e.X / 60) <= a + 2 && (int)(e.Y / 60) >= b - 2 && (int)(e.Y / 60) <= b + 2 && e.chunkLayer == viewedChunkLayer){ malen = false; }}
	                                                if (malen == true)
	                                                {
	                                                    if (feld.blocktop.windAffected == false) g.drawImage(loadedBlockTextures.get(feld.blocktop.textureId), a * 60 - Z + screenshakeX, b * 60 - Q + screenshakeY, this);
	                                                    if (feld.blocktop.windAffected == true) g.drawImage(loadedBlockTextures.get(feld.blocktop.textureId), a * 60 - Z + screenshakeX + (int)(Spielkarte.currentWindEffect + Math.random()* 4), b * 60 - Q + screenshakeY, this);
	                                                }
	                                                else
	                                                {
	                                                    if (feld.blocktop.windAffected == false) g.drawImage(loadedBlockTextures.get(feld.blocktop.textureId2), a * 60 - Z + screenshakeX, b * 60 - Q + screenshakeY, this);
	                                                    if (feld.blocktop.windAffected == true) g.drawImage(loadedBlockTextures.get(feld.blocktop.textureId2), a * 60 - Z + screenshakeX + (int)(Spielkarte.currentWindEffect + Math.random()* 4), b * 60 - Q + screenshakeY, this);
	                                                }
	                                                feld.blocktop.drawBreakProgress(this, g, a, b, depthOffset);
                                                        if (feld.blocktop.shouldReceiveGameTicks) feld.blocktop.OnGameTick(this, g);
	                                            }
	                                    }
	                                    catch(Exception e){}
	
	                            }
	                }
	                }   
	                
                        //------------multi block groundblocks
	                for (int[] tni : renderBlocks){
	                    Feld feld = worldAccessor.GetBlock(this, tni[0], tni[1], 0, Spielkarte.name);
	                    if (feld.blockground != null){
	                        g.drawImage(loadedBlockTextures.get(feld.blockground.textureId), (tni[0] + feld.blockground.drawoffsetX) * 60 - Z + screenshakeX, (tni[1] + feld.blockground.drawoffsetY) * 60 - Q + screenshakeY + depthOffset, this);
	                    }
	                }
                        
                        //-----------light
                        int brightness = 0;
                        for (int a = (int) (Z / 60); a <= (int) ((Z + xscreensize) / 60); a++)
	                for (int b = (int) (Q / 60); b <= (int) ((Q + yscreensize) / 60); b++) 
                        {
                            try{brightness = worldAccessor.GetBlock(this, a, b, layer, Spielkarte.name).brightness + Spielkarte.mapTime.light;
                                if (brightness > 15) brightness = 15;
                                g.setColor(new Color(0,0,0,this.minBrightnessAlpha - (int)(this.minBrightnessAlpha / 15 * brightness)));}
                            catch(Exception e){g.setColor(new Color(0,0,0,this.minBrightnessAlpha));}
                            g.fillRect( a * 60 - Z + screenshakeX, b * 60 - Q + screenshakeY + depthOffset, 60, 60);
                        }
	            }
                    
                    if (this.drawGUIs)
                        for (int a = guiElements.size() - 1; a >= 0; a--){
	                    try{
                                guiElements.get(a).update(this, g);
                                guiElements.get(a).draw(this, g);
                            }
                            catch(Exception ex){}
	                }
	                        
	            //---------color glitch and chunk borders
	            if (ChunkBorders == true || ColorGlichChance > 0){
	                for (int a = (int) (Z / 60); a <= (int) ((Z + xscreensize) / 60); a++) {
	                for (int b = (int) (Q / 60); b <= (int) ((Q + yscreensize) / 60); b++) {
	
	                    if (ChunkBorders == true){
	                        for (Player p : controllablePlayers){
	                         if ((int)(a / 16) == (int)((int)(p.entity.X / 60) / 16) && (int)(b / 16) == (int)((int)(p.entity.Y / 60) / 16))
	                         {
	                                //g.drawLine(a / 16 * 60, a / 16 * 60, 500, 500);
	                                g.setColor(Color.black);
	                                g.drawLine(a * 60 - Z + 1, b * 60 - Q, a * 60 - Z + 1, b * 60 - Q + 60);
	                                g.drawLine(a * 60 - Z - 1, b * 60 - Q, a * 60 - Z - 1, b * 60 - Q + 60);
	                                g.drawLine(a * 60 - Z, b * 60 - Q + 1, a * 60 - Z + 60, b * 60 - Q + 1);
	                                g.drawLine(a * 60 - Z, b * 60 - Q - 1, a * 60 - Z + 60, b * 60 - Q - 1);
	                                g.setColor(Color.white);
	                                g.drawLine(a * 60 - Z, b * 60 - Q, a * 60 - Z, b * 60 - Q + 60);
	                                g.drawLine(a * 60 - Z, b * 60 - Q, a * 60 - Z + 60, b * 60 - Q);
	                         }
	                        }
	                    }
	                    if ((double)((new Random()).nextInt(1000000)) / (double)1000000 <= ColorGlichChance){
	                        g.setColor(ColorGlichColor);
	                        g.drawRect( a * 60 - Z + screenshakeX, b * 60 - Q + screenshakeY, 60, 60);
	                        g.drawRect( a * 60 - Z + screenshakeX-1, b * 60 - Q + screenshakeY-1, 62, 62);
	                        g.drawRect( a * 60 - Z + screenshakeX-2, b * 60 - Q + screenshakeY-2, 64, 64);
	                        g.drawRect( a * 60 - Z + screenshakeX-3, b * 60 - Q + screenshakeY-3, 66, 66);
	
	                        g.drawRect( a * 60 - Z + screenshakeX+1, b * 60 - Q + screenshakeY+1, 58, 58);
	                        g.drawRect( a * 60 - Z + screenshakeX+2, b * 60 - Q + screenshakeY+2, 56, 56);
	                        g.drawRect( a * 60 - Z + screenshakeX+3, b * 60 - Q + screenshakeY+3, 54, 54);
	                        g.drawRect( a * 60 - Z + screenshakeX+4, b * 60 - Q + screenshakeY+4, 52, 52);
	                        g.drawRect( a * 60 - Z + screenshakeX+5, b * 60 - Q + screenshakeY+5, 50, 50);
	                    }
	                }
	                }
	            }
	                
	            
	            //-----------------------------end renderer----------------
	
	            
	            
	            if (ChunkBorders == true)
	            {
	                Font stringFont = new Font("Comic Sans MS", Font.PLAIN, 32);
	                Font stringFont2 = new Font("Comic Sans MS", Font.PLAIN, 20);
	
	                for (int a = (int) (Z / 60); a <= (int) ((Z + xscreensize) / 60); a++)
	                if (a % 16 == 0)
	                {
	                    g.setColor(Color.black);
	                    g.drawLine(a * 60 - Z - 2, 0, a * 60 - Z - 2, yscreensize);
	                    g.drawLine(a * 60 - Z + 2, 0, a * 60 - Z + 2, yscreensize);
	                    g.setColor(Color.yellow);
	                    g.drawLine(a * 60 - Z + 1, 0, a * 60 - Z + 1, yscreensize);
	                    g.drawLine(a * 60 - Z - 1, 0, a * 60 - Z - 1, yscreensize);
	                    g.drawLine(a * 60 - Z, 0, a * 60 - Z, yscreensize);
	                }
	                for (int b = (int)(Q / 60); b <= (int)((Q + yscreensize) / 60); b++)
	                if (b % 16 == 0)
	                {
	                    g.setColor(Color.black);
	                    g.drawLine(0 , b * 60 - Q - 2, xscreensize, b * 60 - Q - 2);
	                    g.drawLine(0 , b * 60 - Q + 2, xscreensize, b * 60 - Q + 2);
	                    g.setColor(Color.yellow);
	                    g.drawLine(0 , b * 60 - Q + 1, xscreensize, b * 60 - Q + 1);
	                    g.drawLine(0 , b * 60 - Q - 1, xscreensize, b * 60 - Q - 1);
	                    g.drawLine(0 , b * 60 - Q, xscreensize, b * 60 - Q);
	                }
	                for (int a = (int) (Z / 60); a <= (int) ((Z + xscreensize) / 60); a++)
	                    for (int b = (int)(Q / 60); b <= (int)((Q + yscreensize) / 60); b++)
	                        if (a % 16 == 0)
	                        {
	                            if (b % 16 == 0)
	                            {
	                               g.setColor(Color.yellow);
	                               g.setFont(stringFont);
	                               g.drawString("" + (a / 16), (a * 60 - Z) + 15, (b * 60 - Q) + 30);
	                               g.setColor(Color.black);
	                               g.setFont(stringFont2);
	                               g.drawString("" + (a / 16), (a * 60 - Z) + 20, (b * 60 - Q) + 25);
	
	                               g.setColor(Color.yellow);
	                               g.setFont(stringFont);
	                               g.drawString("" + (b / 16), (a * 60 - Z) + 75, (b * 60 - Q) + 30);
	                               g.setColor(Color.black);
	                               g.setFont(stringFont2);
	                               g.drawString("" + (b / 16), (a * 60 - Z) + 80, (b * 60 - Q) + 25);
	                            }
	                        }
	
	            }
	            if (GameIsActive == true) {
	                
	                
	                for (int i = entities.size() - 1; i >= 0; i--){
	                    try{
	                        entities.get(i).OnGameTick(this, g);
	                        if (entities.get(i).equals(controllablePlayers.get(0).entity))entities.get(i).UpdatePosition(this, this.playMaps.get(entities.get(i).onMap), xscreensize, yscreensize, true);
	                        else entities.get(i).UpdatePosition(this, this.playMaps.get(entities.get(i).onMap), xscreensize, yscreensize, false);
	                    }catch(Exception e){}
	                }
	                for (int i = 0; i <= hitDetectionBoxes.size() - 1; i++){
	                    hitDetectionBoxes.get(i).Update(this);
	                }
	                for (int lol = 0; lol <= projectile.length - 1; lol++)
	                    {
	                        if (projectile[lol] != null)
	                        {
	                            projectile = projectile[lol].update(projectile, Spielkarte);
	                        }
	                    }
	                for (Player p : controllablePlayers){
	                    
                                boolean useItem = true;
                                for (GUIElement gE : guiElements){
                                    if (gE.blockInteractions(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y)) {
                                            if (mousepressed == true){
                                                gE.onHeldClick(this, g);
                                                useItem = false;
                                            }
                                            gE.onHower(this, g);
                                        }
                                    else{
                                        if (gE.wasLastTimeInBorders){
                                            gE.wasLastTimeInBorders = false;
                                            gE.onMouseLeave(this, g);
                                        }
                                    }
                                }
                                if (mousepressed == true){
	                        if (useItem){
                                    if (((EntityPlayer)p.entity).inventory.BackPackOpen == true){if (!((EntityPlayer)p.entity).inventory.testCursorAboveInventory(this.gUIScale, this)) ((EntityPlayer)p.entity).heldClickUseItem(this, g);}
                                    else ((EntityPlayer)p.entity).heldClickUseItem(this, g);
                                }
                                }
	                    
	                    ((EntityPlayer)p.entity).ifSelectedItem(this, g);
	                    //if (p.entity.inNewChunk == true)  {p.entity.inNewChunk = false; playMaps.get(p.entity.onMap).UnloadChunk(this); playMaps.get(p.entity.onMap).GenerateChunks(this, defaultGenerator);}
	                }
	            }            
	            
	            eventhandler.HandleEvents(this, g);
                    Spielkarte.OnGameTick(this);
	            ((EntityPlayer)entities.get(controllablePlayers.get(0).entity.Id)).drawGUI(g, xscreensize, yscreensize, this);
                    //g.drawString("ticks: " + Spielkarte.mapTime.time, 500, 100);
                    //g.drawString("days: " + Spielkarte.mapTime.days, 500, 200);
                    //g.drawString("months: " + Spielkarte.mapTime.months, 500, 300);
                    //g.drawString("years: " + Spielkarte.mapTime.years, 500, 400);
                    //g.drawString("season: " + Spielkarte.mapTime.season, 500, 500);
        	}
                
                
               // if (mousepressed) spawnLightning(g, MouseInfo.getPointerInfo().getLocation().x, 0, 
               //         MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, viewedChunkLayer, Spielkarte.name, true);
                  //System.out.println("---"+ worldAccessor.GetBlockGround(this, (MouseInfo.getPointerInfo().getLocation().x + Z) / 60, (MouseInfo.getPointerInfo().getLocation().y + Q) / 60, 0, Spielkarte.name).name);
            try{Thread.sleep((int)(this.frameDelay - (System.currentTimeMillis() - this.startMilliseconds)));}catch(Exception e){}
            frame.repaint();
        }
        if (GameIsActive == false && Spielkarte != null) {
                
                if (Spielkarte != null) {
                		int size = 10; // GUI SIZE
		                try {
		                    Esc.drawEsc(this, g, xscreensize, yscreensize, 60);
		                } catch (IOException ex) {
		                    Logger.getLogger(Zombie.class.getName()).log(Level.SEVERE, null, ex);
		                }
		        }
                frame.repaint();

            }
        //int dltTime = (int)(System.currentTimeMillis() - lastMS);
        //int sleeptime = 0;
        //try{sleeptime = 2 / dltTime - 1;}catch(Exception e){}
        //try{Thread.sleep(sleeptime);}catch(Exception e){}
        //System.out.println("start = " + startMilliseconds + " all = " + frameDelay + " nsPassed = " + (System.currentTimeMillis() - this.startMilliseconds) + " sleeping = " + 
        //		(this.frameDelay - (System.currentTimeMillis() - this.startMilliseconds)));
        
    }
    
    public static void main(String[] args) throws SQLException {
        
        Zombie z = new Zombie();
        
        newJSONreadmanager nJRM = new newJSONreadmanager(z);
        nJRM.ManageReadProjectiles();
        nJRM.ManageReadItems();
        nJRM.ManageReadBlocks();
        nJRM.ManageReadAITasks();
        nJRM.ManageReadEntities();
        nJRM.ManageReadGUIElements();
        nJRM.ManageReadCraftingRecepies();
        
        WorldAccessor w = new WorldAccessor();
        mwh = new WheelHandler(z);
        mh = new MouseHandler(z);
        kh = new KeyHandler(z);
        
        frame.setUndecorated(true); 
        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setResizable(false);  
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(kh);
        
        frame.addMouseListener(mh);
        frame.addMouseWheelListener(mwh);
        frame.getContentPane().add(z);
        frame.setBackground(Color.BLACK);
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.sync();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        frame.setSize(xsize, ysize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        w.createPlayMap(z, 60, 60, "overworld", "assets/worldgen/maptypes/overworld.txt", true);
        Spielkarte = playMaps.get("overworld"); 
        
        System.out.println("cuz = " + w.getGUIElement(z, "inventoryPlayer"));
        
        z.entities.get(0).inizialize(z);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "lightsaber"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "minigun"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "prism"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "coldbeam"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "demonslayerGun"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "demonslayerSword"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "darksword"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "pick"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "sniper"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "crossbow"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "dungeonOutrance"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "dungeonOutrance"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "dungeonOutrance"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "dungeonOutrance"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "dungeonOutrance"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "torch"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "torch"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "torch"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "torch"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "torch"), z.gUIScale);
        ((EntityPlayer)z.entities.get(0)).inventory.tryGiveItem(new WorldAccessor().GetItem(z, "torch"), z.gUIScale);
        
        ((EntityPlayer)z.entities.get(0)).inventory.SetCursor(z, false);
         
    }

    public void spawnLightning(Graphics g, int startX, int startY, int endX, int endY, int chunklayer, String onMap, boolean light){
        try{
        WorldAccessor w = new WorldAccessor();
        
        List<Point> points = new ArrayList<Point>();
        Point currentPoint = new Point(startX, startY);
        points.add(currentPoint);
        
        Random r = new Random();
        double sideFlashSpawnChance = 0.1;
        int plusY = 100;
        int randX = 50;
        int sideFlashLength = 300;
        int maxSideFlashes = 10;
        int sideFlashes = 0;
        int sideFlashrandX = 200;
        
        while (currentPoint.y < endY){
            currentPoint = new Point(currentPoint.x + r.nextInt(2 * randX) - randX, currentPoint.y + plusY);
            points.add(currentPoint);
            if (sideFlashes <= maxSideFlashes && r.nextDouble() < sideFlashSpawnChance){
                spawnLightning(g, currentPoint.x, currentPoint.y, currentPoint.x + r.nextInt(2 * sideFlashrandX) - sideFlashrandX, currentPoint.y + sideFlashLength, chunklayer, onMap, false);
                sideFlashes++;
            }
        }
        
       // points.set(points.size() - 1, new Point(endX, endY));
        
        for (int a = 1; a <= points.size() -  1; a++){
            g.setColor(Color.yellow);
            g.drawLine(points.get(a).x, points.get(a).y, points.get(a - 1).x, points.get(a - 1).y);
            g.drawLine(points.get(a).x + 1, points.get(a).y, points.get(a - 1).x + 1, points.get(a - 1).y);
            g.drawLine(points.get(a).x - 1, points.get(a).y, points.get(a - 1).x - 1, points.get(a - 1).y);
            g.drawLine(points.get(a).x + 2, points.get(a).y, points.get(a - 1).x + 2, points.get(a - 1).y);
            g.drawLine(points.get(a).x - 2, points.get(a).y, points.get(a - 1).x - 2, points.get(a - 1).y);
        }
        if (light)w.GetBlock(this, (currentPoint.x + Z) / 60, (currentPoint.y + Q) / 60, chunklayer, onMap).brightness = 15;
        }catch (Exception e){}
    }
}

class KeyHandler implements KeyListener  {
        
     public static boolean getGameIsActive() {
        return GameIsActive;
    }

    public static boolean getOptionsIsActive() {
        return OptionsIsActive;
    }

    public static boolean getGraphicsIsActive() {
        return GraphicsIsActive;
    }

    public static boolean getAudioIsActive() {
        return AudioIsActive;
    }

    public static boolean getSettingsIsActive() {
        return SettingsIsActive;
    }

    public static boolean getCreditsIsActive() {
        return CreditsIsActive;
    }
    
    Zombie z;

    KeyHandler(Zombie zombie) {
        z = zombie;

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
        z.pressedKeys.add(ke);
        for (Player p : z.controllablePlayers){
            if ((ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) && p.entity.up == false) {
                p.entity.motions.get("movement")[1] += -p.entity.Speed;
                p.entity.up = true;
            }
            if ((ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) && p.entity.left == false) {
                p.entity.motions.get("movement")[0] += -p.entity.Speed;
                p.entity.left = true;
            }
            if ((ke.getKeyCode() == KeyEvent.VK_S || ke.getKeyCode() == KeyEvent.VK_DOWN) && p.entity.down == false) {
                p.entity.motions.get("movement")[1] += p.entity.Speed;
                p.entity.down = true;
            }
            if ((ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) && p.entity.right == false) {
                p.entity.motions.get("movement")[0] += p.entity.Speed;
                p.entity.right = true;
            }
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            int nonStaticGUIElements = 0;
            if (z.drawGUIs)
            for (int a = 0; a <= z.guiElements.size() - 1; a -= -1){
                if (!z.guiElements.get(a).isStatic) nonStaticGUIElements++;
            }
            if (nonStaticGUIElements <= 0){
                if (z.GameIsActive == true) {
                    z.GameIsActive = false;
                    z.Esc.EscapeIsActive = true;
                    z.OptionsIsActive = false;
                    z.GraphicsIsActive = false;
                    z.AudioIsActive = false;
                    z.SettingsIsActive = false;
                    z.CreditsIsActive = false;

                } else if (z.GameIsActive == false) {
                    z.GameIsActive = true;

                }
                ((EntityPlayer)z.entities.get(0)).inventory.SetCursor(z, false);
            }
        }
        
        for (int a = 0; a <= z.guiElements.size() - 1; a -= -1){
            if (!z.guiElements.get(a).onKeyPressed(z, z.getGraphics(), ke)) break;
        }
        
        boolean f3 = false, strg = false, g = false, b = false;
        for (KeyEvent k : z.pressedKeys) {
            if (k.getKeyCode() == KeyEvent.VK_F3) f3 = true;
            if (k.getKeyCode() == KeyEvent.VK_CONTROL) strg = true;
            if (k.getKeyCode() == KeyEvent.VK_G) g = true;
            if (k.getKeyCode() == KeyEvent.VK_B) b = true;
        }
        if (f3 == true && strg == true && g == true) {
            if (z.ChunkBorders == false) z.ChunkBorders = true;
            else if (z.ChunkBorders == true) z.ChunkBorders = false;
        }
        if (f3 == true && strg == true && b == true) {
            if (z.EntityDebuginfo == false) z.EntityDebuginfo = true;
            else if (z.EntityDebuginfo == true) z.EntityDebuginfo = false;
        }
        if (ke.getKeyCode() == KeyEvent.VK_F4) {
            if (z.drawGUIs == true) {
                z.drawGUIs = false;
            } else if (z.drawGUIs == false) {
                z.drawGUIs = true;
            }
        }
        
        
        if (ke.getKeyCode() == KeyEvent.VK_Q) {
            if (((EntityPlayer)z.entities.get(0)).inventory.getSelectedItemSlot(z.gUIScale) != null
                    && ((EntityPlayer)z.entities.get(0)).inventory.getSelectedItemSlot(z.gUIScale).item != null){
                new WorldAccessor().SpawnItem(z, ((EntityPlayer)z.entities.get(0)).inventory.getSelectedItemSlot(z.gUIScale).item.CreateNew(), (int)z.entities.get(0).X + 60, (int)z.entities.get(0).Y, (int)z.entities.get(0).chunkLayer, z.entities.get(0).onMap);
                ((EntityPlayer)z.entities.get(0)).inventory.takeOutItem(((EntityPlayer)z.entities.get(0)).inventory.getSelectedItemSlot(z.gUIScale), ((EntityPlayer)z.entities.get(0)).inventory.getSelectedItemSlot(z.gUIScale).stackSize);
                ((EntityPlayer)z.entities.get(0)).inventory.SetCursor(z, false);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
        /*for (int a = 0; a <= z.pressedKeys.size() - 1; a++) {
            if (z.pressedKeys[a].getKeyCode() == ke.getKeyCode()) z.pressedKeys.1;
        }*/
        try{
            if (z.drawGUIs)
            for (KeyEvent k : z.pressedKeys){
                 if (k.getKeyCode() == ke.getKeyCode()) z.pressedKeys.remove(k);
            }
        }catch(Exception e ){}
        for (Player p : z.controllablePlayers){
            if ((ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP) && p.entity.up == true) {
                p.entity.motions.get("movement")[1] -= -p.entity.Speed;
                p.entity.up = false;
            }
            if ((ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT) && p.entity.left == true) {
                p.entity.motions.get("movement")[0] -= -p.entity.Speed;
                p.entity.left = false;
            }
            if ((ke.getKeyCode() == KeyEvent.VK_S || ke.getKeyCode() == KeyEvent.VK_DOWN) && p.entity.down == true) {
                p.entity.motions.get("movement")[1] -= p.entity.Speed;
                p.entity.down = false;
            }
            if ((ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT) && p.entity.right == true) {
                p.entity.motions.get("movement")[0] -= p.entity.Speed;
                p.entity.right = false;
            }
        }
    }
   
}

class MouseHandler implements MouseListener
{   Zombie z;

    MouseHandler(Zombie zombie) {
        z = zombie;
        
    }
    public static boolean getMousePressed() {
                return MousePressed;
    }
   
    @Override
    public void mouseClicked(MouseEvent me) {
       z.mousepressed = false;
    }

    @Override
    public void mousePressed(MouseEvent me) {
        z.mousepressed = true;
        
       if (me.getButton() == MouseEvent.BUTTON1)
       {
           MousePressed = true;
       }       
       {
                if (z.drawGUIs)
                for (GUIElement gE : z.guiElements){
                                if (gE.blockInteractions(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y)) {
                                    gE.onClicked(z, z.getGraphics());
                                    return;
                                }
                            }
                for (Player p : z.controllablePlayers) {
                    if (me.getButton() == MouseEvent.BUTTON1)((EntityPlayer)p.entity).leftClickUseItem(z, z.getGraphics());
                }
       }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        z.mousepressed = false;
        if (me.getButton() == MouseEvent.BUTTON1)
       {
           MousePressed = false;
       }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    
}
class WheelHandler implements MouseWheelListener
{   Zombie z;

    WheelHandler(Zombie zombie) {
        z = zombie;
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        ((EntityPlayer)z.entities.get(0)).inventory.changeSelectedSlot(e.getPreciseWheelRotation(), z);
    }
      
}