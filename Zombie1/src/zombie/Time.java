/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class Time {
    
    public int time = 0;
    public int maxTime = 100;
    public int maxLight = 15, minLight = 0, light = 0;
    public int days = 0, months = 0, years = 0;
    public int daysInMonth = 3, monthsInYear = 12;
    public int season = 0;  // 0 -> spring, 1 -> summer, 2 -> fall, 3 -> winter
    public int sunSetRiseDuration = 2;
    public boolean enabled = true;
    
    public Time(Map<String,String> properties){
        
        if (properties == null) return;
        else {
            if (properties.containsKey("enabled"))enabled = Boolean.parseBoolean(properties.get("enabled"));
            if (properties.containsKey("ticks_per_day"))maxTime = Integer.parseInt(properties.get("ticks_per_day"));
            if (properties.containsKey("days_per_month"))daysInMonth = Integer.parseInt(properties.get("days_per_month"));
            if (properties.containsKey("months_per_year"))monthsInYear = Integer.parseInt(properties.get("months_per_year"));
            if (properties.containsKey("sun_set_rise_ticks"))sunSetRiseDuration = Integer.parseInt(properties.get("sun_set_rise_ticks"));
            if (properties.containsKey("day_light_level"))maxLight = Integer.parseInt(properties.get("day_light_level"));
            if (properties.containsKey("night_light_level"))minLight = Integer.parseInt(properties.get("night_light_level"));
        }        
    }
    
    public void plus1Tick(Zombie z){
        if (enabled){
            this.time++;
            if (this.time >= this.maxTime) {this.time = 0; plus1Day(z);}
            if (this.time > 0 && this.time <= this.sunSetRiseDuration){
                this.light = this.minLight + (int)(((double)this.maxLight - (double)this.minLight) / ((double)this.sunSetRiseDuration) * ((double)this.time));
            }
            else if (this.time > (int)(maxTime / 2) && this.time <= (int)(this.maxTime / 2) + this.sunSetRiseDuration){
                this.light = this.maxLight - (int)(((double)this.maxLight - (double)this.minLight) / ((double)this.sunSetRiseDuration) * ((double)this.time - ((double)this.maxTime / 2)));
            }
        }
    }
    
    public void plus1Day(Zombie z){
        this.days++;
        if (this.days >= this.daysInMonth){this.days = 0; this.plus1Month(z);}
        
        for (BlockNormal n : z.loadedNormalBlocks){
            if (!n.seasonColorMapPath.equals("")){
                BufferedImage i = null;
                try{i = ImageIO.read(new File(n.seasonColorMapPath));}catch(Exception e){}
                try{
                    if (i != null){
                        BufferedImage i2 = ImageIO.read(new File(n.texturePath));
                        BufferedImage finalImage = setBlockColor(i2, i);
                        if (finalImage != null) z.loadedBlockTextures.set(n.textureId, finalImage);
                    }
                }catch(Exception e){}
            }
        }
        for (BlockGround n : z.loadedGroundBlocks){
            if (!n.seasonColorMapPath.equals("")){
                BufferedImage i = null;
                try{i = ImageIO.read(new File(n.seasonColorMapPath));}catch(Exception e){}
                try{
                    if (i != null){
                        BufferedImage i2 = ImageIO.read(new File(n.texturePath));
                        BufferedImage finalImage = setBlockColor(i2, i);
                        if (finalImage != null) z.loadedBlockTextures.set(n.textureId, finalImage);
                    }
                }catch(Exception e){}
            }
        }
        for (BlockTop n : z.loadedTopBlocks){
            if (!n.seasonColorMapPath.equals("")){
                BufferedImage i = null;
                try{i = ImageIO.read(new File(n.seasonColorMapPath));}catch(Exception e){}
                try{
                    if (i != null){
                        BufferedImage i2 = ImageIO.read(new File(n.texturePath));
                        BufferedImage finalImage = setBlockColor(i2, i);
                        if (finalImage != null) z.loadedBlockTextures.set(n.textureId, finalImage);
                        i2 = ImageIO.read(new File(n.texturePath2));
                        finalImage = setBlockColor(i2, i);
                        if (finalImage != null) z.loadedBlockTextures.set(n.textureId2, finalImage);
                    }
                }catch(Exception e){}
            }
        }
    }
    
    public BufferedImage setBlockColor(BufferedImage blockTexture, BufferedImage colorMap){
        int transparentrgb = new Color(0,0,0,0).getRGB();
        int w = blockTexture.getWidth();
        int h = blockTexture.getHeight();
        int rgb = colorMap.getRGB((int)(colorMap.getWidth() / (daysInMonth * monthsInYear) * (days + months * daysInMonth)), 1);
        BufferedImage finalImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        int textureRgb = 0;
        for (int a = 0; a < w; a++){
            for (int b = 0; b < h; b++){
                textureRgb = blockTexture.getRGB(a, b);
                int alpha = ((textureRgb >> 24) & 0xff);
                if (((textureRgb>>16) & 0xff) == ((textureRgb>>8) & 0xff) && ((textureRgb>>16) & 0xff) == (textureRgb & 0xff) && alpha > 0){
                    int brightness = (255 - ((textureRgb>>16) & 0xff));
                    int red = ((rgb>>16) & 0xff) - brightness;
                    int green = ((rgb>>8) & 0xff) - brightness;
                    int blue = ((rgb) & 0xff) - brightness;
                    if (red > 255) red = 255;
                    if (green > 255) green = 255;
                    if (blue > 255) blue = 255;
                    if (red < 0) red = 0;
                    if (green < 0) green = 0;
                    if (blue < 0) blue = 0;
                    textureRgb = 0;
                    textureRgb = textureRgb | (alpha<<24);
                    textureRgb = textureRgb | (red<<16);
                    textureRgb = textureRgb | (green<<8);
                    textureRgb = textureRgb | blue;

                    finalImage.setRGB(a, b, textureRgb);
                }
                else{
                    if (alpha > 0){finalImage.setRGB(a, b, textureRgb);}
                    else finalImage.setRGB(a, b, transparentrgb);
                }
            }
        }
        return finalImage;
    }
    
    public void plus1Month(Zombie z){
        this.months++;
        if (this.months >= 0 && this.months < this.monthsInYear / 4) this.season = 0;
        if (this.months >= this.monthsInYear / 4 && this.months < this.monthsInYear / 2) this.season = 1;
        if (this.months >= this.monthsInYear / 2 && this.months < this.monthsInYear / 4 * 3) this.season = 2;
        if (this.months >= this.monthsInYear / 4 * 3 && this.months < this.monthsInYear) this.season = 3;
        if (this.months >= this.monthsInYear){this.months = 0; this.plus1Year(z);}
    }
    
    public void plus1Year(Zombie z){
        this.years++;
    }
}
