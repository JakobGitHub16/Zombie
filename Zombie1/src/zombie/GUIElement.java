/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Map;

/**
 *
 * @author admin
 */
public class GUIElement {
    
    public int x = 0, y = 0;
    public boolean visible = true;
    public String type = "";
    public boolean isStatic = false;
    public boolean wasLastTimeInBorders = false;
    
    public void onClicked(Zombie z, Graphics g){
        
    }
    
    /**
     * is invoked, when a key is pressed and this guiElement is opened
     * @param z
     * @param g
     * @return return false, when other procesess shouldn't happen after invoking this method, for example if you press ESC, and
     * then you want to close this GUI, but dont want other guis to close too, return false. else return true
     */
    public boolean onKeyPressed(Zombie z, Graphics g, KeyEvent k){
        return true;
    }
    
    /**
     * is invoked, when a key is released and this guiElement is opened
     * @param z
     * @param g
     * @return return false, when other procesess, triggered by pressing this key, shouldn't happen after invoking this method. return true otherwhise.
     */
    public boolean onKeyReleased(Zombie z, Graphics g, KeyEvent k){
        return true;
    }
    
    public void onHeldClick(Zombie z, Graphics g){
        
    }
    
    public void onHower(Zombie z, Graphics g){
        wasLastTimeInBorders = true;
        ((EntityPlayer)z.entities.get(0)).inventory.SetCursor(z, true);
    }
    
    public void onMouseLeave(Zombie z, Graphics g){
        ((EntityPlayer)z.entities.get(0)).inventory.SetCursor(z, false);
    }
    
    public void onMouseHover(Zombie z, Graphics g){
        
    }
    
    public void draw(Zombie z, Graphics g){
        
    }
    
    public void update(Zombie z, Graphics g){
        
    }
    
    public void inizialize(Zombie z, Map<String, Object> m){
        
    }
    
    public GUIElement CreateNew(){
        return new GUIElement();
    }
    
    /**
     * 
     * @param mX mouseX
     * @param mY mouseY
     * @return returns, whether the gui should block all the further mouse interactions, when the mouse is pressed at the given coordinates
     */
    public boolean blockInteractions(int mX, int mY){
        return false;
    }
    
    public boolean mouseInside(int mX, int mY){
        return false;
    }            
}
