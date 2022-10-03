/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author admin
 */
public class animationManager {
    
    public Image currentFrame = null;
    public List<Image> frames = new ArrayList<Image>();
    public String spritePath = null;
    public int frameHeight = 0;
    public int frameCount = 0;
    public int totalTime = 0;
    public int frame = 0;
    public int nextFrameTime = 0;
    public int frameTime = 0;
    public int facing = 0;
    
    public animationManager(String spritePath, int totalTime, int frameCount, int frameHeigth){
        
        this.spritePath = spritePath;
        this.totalTime = totalTime;
        this.frameCount = frameCount;
        this.frameHeight = frameHeigth;
        this.nextFrameTime = this.totalTime / this.frameCount;
        
        try{
            BufferedImage imgSprite = ImageIO.read(new File(spritePath));
            
            for (int a = 0; a <= this.frameCount - 1; a++){
                frames.add(imgSprite.getSubimage(0, a * frameHeight, imgSprite.getWidth(), frameHeight));
            }
            
            
        }catch(Exception e){}
        
    }
    
    public void updateAndDraw(Zombie z, Graphics g, int X, int Y, boolean cameraDependent, int facing){
        
        if (this.frames.size() > 0){
            
            int width = this.frames.get(this.frame).getWidth(null);
            int heigth = this.frames.get(this.frame).getHeight(null);

            if (cameraDependent == false) if (facing == 1) g.drawImage(this.frames.get(this.frame), X + width, Y, -width, heigth, null); else g.drawImage(this.frames.get(this.frame), X, Y, null);
            if (cameraDependent == true) if (facing == 1) g.drawImage(this.frames.get(this.frame), X + width - z.Z, Y - z.Q, -width, heigth, null); else g.drawImage(this.frames.get(this.frame), X - z.Z, Y - z.Q, null);
            
            this.frameTime++;
            if (this.frameTime >= this.nextFrameTime){this.frame++; this.frameTime = 0;}
            if (this.frame >= this.frameCount) this.frame = 0;
        }
    }
    
    public animationManager CreateNew(){
        
        animationManager a = new animationManager(this.spritePath, this.totalTime, this.frameCount, this.frameHeight);
        //a.frames = this.frames;
        return a;
        
    }
    
}
