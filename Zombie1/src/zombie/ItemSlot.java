
package zombie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import static java.awt.MouseInfo.getPointerInfo;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
//import sun.java2d.pipe.BufferedBufImgOps;

public class ItemSlot {
    
    public Boolean visible = true;
    public int X = 0, Y = 0;
    public Item item = null;
    public boolean selected = false;
    public Image io;
    public String imagePath = "";
    public int stackSize = 0;
    
    public ItemSlot(int x, int y)
    {
        this.X = x;
        this.Y = y;
    }
    
    public void draw(Graphics g, int scale, Zombie z)
    {
            if (visible == true)
            {               
                if (this.selected == false){
                    g.setColor(new Color(0, 100, 255, 200));
                    g.fillRect(this.X, this.Y, scale, scale);
                    g.setColor(Color.black);
                    g.fillRect(this.X, this.Y, scale, 3);
                    g.fillRect(this.X, this.Y + scale, scale+3, 3);
                    g.fillRect(this.X, this.Y, 3, scale);
                    g.fillRect(this.X + scale, this.Y, 3, scale);
                }
                if (this.selected == true){
                    g.setColor(new Color(0, 100, 255, 200));
                    g.fillRect(this.X - 5, this.Y - 5, scale + 3 + 10, scale + 3 + 10);
                    g.setColor(Color.black);
                    g.fillRect(this.X - 5, this.Y - 5, scale + 10, 3);
                    g.fillRect(this.X - 5, this.Y + scale + 5, scale+3 + 10, 3);
                    g.fillRect(this.X - 5, this.Y - 5, 3, scale + 10);
                    g.fillRect(this.X + scale + 5, this.Y - 5, 3, scale + 10);
                }
                Image itemimage;
                if (this.item != null) 
                {
                    try{
                        //itemimage = io.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
                        g.drawImage(io, this.X, this.Y, z);
                        
                        g.setFont(new Font("Comic Sans MS", Font.PLAIN, (int)((double)scale / 2.4)));
                        g.setColor(Color.white);
                        int width = g.getFontMetrics().stringWidth(String.valueOf(this.stackSize));
                        int height = g.getFontMetrics().getHeight();
                        g.drawString( String.valueOf(this.stackSize), this.X + scale - width, this.Y + scale - height + (int)(scale / 5 * 3));
                    }catch (Exception e)
                    {}
                }                
                
            }
            
    }
    
    public void drawDescription(Graphics g, int scale, Zombie z){
        if (this.visible == true){
            double x = getPointerInfo().getLocation().getX();
                    double y = getPointerInfo().getLocation().getY();

                    if (this.testClicke((int)x, (int)y, scale)){
                        if (this.item != null){
                                String[] parts = this.item.description.split("\\\\n");
                                int max = g.getFontMetrics().stringWidth(this.item.name);
                                for (int a = 0; a <= parts.length - 1; a++){
                                    if (Math.max(max, g.getFontMetrics().stringWidth(parts[a])) == g.getFontMetrics().stringWidth(parts[a]))max = g.getFontMetrics().stringWidth(parts[a]);
                                }
                                
                                if (this.item.description.equals("")) parts = new String[0];
                                
                                int width = max + (int)(scale / 6 * 5) + 20;
                                int heigth = g.getFontMetrics().getHeight() * (parts.length + 1) + (int)(scale / 6);


                                g.setColor(new Color(0, 100, 255, 200));
                                g.fillRect((int)x, (int)y, width, heigth);
                                g.setColor(Color.black);
                                g.drawRect((int)x, (int)y, width, heigth);
                                g.drawRect((int)x - 1, (int)y - 1, width + 2, heigth + 2);
                                g.drawRect((int)x - 2, (int)y - 2, width + 4, heigth + 4);
                                
                                g.setColor(this.item.NameColor);
                                g.drawString(this.item.name, (int)x + (int)(scale / 6 * 2) + 20, (int)(scale / 6 * 3) + (int)y);
                                
                                g.setColor(Color.black);
                                for (int a = 0; a <= parts.length - 1; a++){
                                    g.drawString(parts[a], (int)x + (int)(scale / 6 * 2) + 20, (int)(scale / 6 * 3) + (int)y + (g.getFontMetrics().getHeight() * (a + 1)));
                                }
                            }
                    }
        }
    }
    
    public void setItem(String type, String Bild, int scale)
    {
        //this.item = new prism(type, Damage, Bild);
        this.imagePath = Bild;
        this.io = new ImageIcon(this.item.imagePath).getImage().getScaledInstance(scale, scale, java.awt.Image.SCALE_FAST);
    }
    
    public boolean testClicke(int x, int y, int scale){
        
        if (x >= this.X && y >= this.Y && x <= this.X + scale && y <= this.Y + scale) return true;
        
        return false;
    }
    
}
