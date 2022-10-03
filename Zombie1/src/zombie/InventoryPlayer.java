/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import static java.awt.MouseInfo.*;
import java.awt.Point;
import java.util.Random;
//import sun.swing.BakedArrayList;

/**
 *
 * @author admin
 */
public class InventoryPlayer {
    
    public ItemSlot[] Hotbar = new ItemSlot[10];
    public ItemSlot[][] BackPack = new ItemSlot[10][5];
    public ItemSlot mouseslot = null;
    
    public ItemSlot[] Itemslots = new ItemSlot[Hotbar.length + BackPack.length * BackPack[BackPack.length - 1].length];
    
    public int HotbarSlotSelected = 2;
    public boolean BackPackOpen = false;
    public Entity owningEntity;
    
    public InventoryPlayer(int scaleGui, Entity e){
        int i = 0;
        owningEntity = e;
        for (int a = 0; a <= 9; a++) {
            Hotbar[a] = new ItemSlot(a * scaleGui + 20, 20);
            Itemslots[i] = Hotbar[a];
            i++;
        }
        for (int a = 0; a <= 9; a++) {
            for (int b = 0; b <= 4; b++) {
                BackPack[a][b] = new ItemSlot(a * scaleGui + 20, 40 + b * scaleGui + scaleGui);
                if (new Random().nextInt(5) == 0)
                {
                    //BackPack[a][b].item = new prism("Prism", "assets/textures/items/weapons/prism.png");
                    //BackPack[a][b].setItem("Prism", "assets/textures/items/weapons/prism.png", scaleGui);
                }
                BackPack[a][b].visible = false;
                Itemslots[i] = BackPack[a][b];
                i++;
            }
        }
        mouseslot = new ItemSlot(0, 0);
        
        
        Hotbar[2].selected = true;
        /*Hotbar[2].item = new prism("Prism", "assets/textures/items/weapons/prism.png");
        Hotbar[2].setItem("Prism", "assets/textures/items/weapons/prism.png", scaleGui);
        Hotbar[2].selected = true;
        Hotbar[0].item = new lightsaber("Lightsaber", "assets/textures/items/weapons/lightsamber.png");
        Hotbar[0].setItem("Lightsaber", "assets/textures/items/weapons/lightsamber.png", scaleGui);
        Hotbar[1].item = new minigun("Minigun", "assets/textures/items/weapons/minigun.png");
        Hotbar[1].setItem("Lightsaber", "assets/textures/items/weapons/minigun.png", scaleGui);
        Hotbar[3].item = new coldbeam("Coldbeam", "assets/textures/items/weapons/lightsamber.png");
        Hotbar[3].setItem("Coldbeam", "assets/textures/items/weapons/lightsamber.png", scaleGui);
        Hotbar[4].item = new defaultNonMeleeWeapon("Coldbeam", 10, "melee", new Color(0,255,255), new Color(0,0,255),1,0,500, 10, false, 50);
        
        Hotbar[5].item = new demonslayerSword("Demonslayersword", "assets/textures/items/weapons/demonslayer.png");
        Hotbar[5].setItem("Demonslayersword", "assets/textures/items/weapons/demonslayer.png", scaleGui);
        Hotbar[6].item = new demonslayerGun("Demonslayergun", "assets/textures/items/weapons/demonslayerGun.png");
        Hotbar[6].setItem("Demonslayergun", "assets/textures/items/weapons/demonslayerGun.png", scaleGui);*/
    }
    
    public void draw(Graphics g, int scaleGui, Zombie z){
        
        for (int a = 0; a <= 9; a++) {
            Hotbar[a].draw(g, scaleGui, z);
        }
        
        for (int a = 0; a <= 9; a++) {
            for (int b = 0; b <= 4; b++) {
                if (BackPack[a][b].visible == true) BackPack[a][b].draw(g, scaleGui, z);
            }
        }
        for (int a = 0; a <= 9; a++) {
            Hotbar[a].drawDescription(g, scaleGui, z);
        }
        
        for (int a = 0; a <= 9; a++) {
            for (int b = 0; b <= 4; b++) {
                if (BackPack[a][b].visible == true) BackPack[a][b].drawDescription(g, scaleGui, z);
            }
        }
        
        if (mouseslot != null && mouseslot.item != null){
            mouseslot.X = getPointerInfo().getLocation().x;
            mouseslot.Y = getPointerInfo().getLocation().y;
            mouseslot.draw(g, scaleGui, z);
        }
    }
    
    public void SetCursor(Zombie z, boolean  toDefault){
        if (!z.GameIsActive || toDefault)
        {
             Image image = z.toolkit.getImage("miscellaneous/images/default_cursor.png");
             Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
             z.frame.setCursor(c);
             return;
        }
        if (Hotbar[HotbarSlotSelected].item != null)
        {
                    if("melee".equals(Hotbar[HotbarSlotSelected].item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/melee_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("magic".equals(Hotbar[HotbarSlotSelected].item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/magic_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("ranged".equals(Hotbar[HotbarSlotSelected].item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/arrow_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("tool".equals(Hotbar[HotbarSlotSelected].item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/tool_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("others".equals(Hotbar[HotbarSlotSelected].item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/default_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("food".equals(Hotbar[HotbarSlotSelected].item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/food_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                                                            
        }
        if (mouseslot.item != null){
                    if("melee".equals(mouseslot.item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/melee_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("magic".equals(mouseslot.item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/magic_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("ranged".equals(mouseslot.item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/arrow_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("tool".equals(mouseslot.item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/tool_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("others".equals(mouseslot.item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/default_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
                    else if("food".equals(mouseslot.item.type)){
                    Image image = z.toolkit.getImage("miscellaneous/images/food_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
                    }
        }
        if (Hotbar[HotbarSlotSelected].item == null && mouseslot.item == null)
        {
                    Image image = z.toolkit.getImage("miscellaneous/images/default_cursor.png");
                    Cursor c = z.toolkit.createCustomCursor(image , new Point(z.frame.getX(), z.frame.getY()), "img");
                    z.frame.setCursor(c);
        }
    }
    
    public void changeSelectedSlot(double getPreciseWheelRotation, Zombie z){
        
        if (getPreciseWheelRotation > 0)
        {
            Hotbar[HotbarSlotSelected].selected = false;
            HotbarSlotSelected += 1;
            if (HotbarSlotSelected >= 10)HotbarSlotSelected = 0;
            Hotbar[HotbarSlotSelected].selected = true;
            SetCursor(z, false);
        }
        if (getPreciseWheelRotation < 0)
        {
            Hotbar[HotbarSlotSelected].selected = false;
            HotbarSlotSelected -= 1;
            if (HotbarSlotSelected < 0)HotbarSlotSelected = 9;
            Hotbar[HotbarSlotSelected].selected = true;
            SetCursor(z, false);
        }
        if (Hotbar[HotbarSlotSelected] != null)if (Hotbar[HotbarSlotSelected].item != null)Hotbar[HotbarSlotSelected].item.OnSelectedInSlot(z, getPointerInfo().getLocation().x, getPointerInfo().getLocation().y, z.frame1.getGraphics(), this.owningEntity);
    }
    
    public void toggelBackPackOpening(){
        
        if (BackPackOpen == false){
            for (int a = 0; a <= 9; a++) {
                for (int b = 0; b <= 4; b++) {
                    BackPack[a][b].visible = true;
                }
            }
            BackPackOpen = true;
        }
        else if (BackPackOpen == true){
            for (int a = 0; a <= 9; a++) {
                for (int b = 0; b <= 4; b++) {
                    BackPack[a][b].visible = false;
                }
            }
            BackPackOpen = false;
        }
    }
    
    public boolean trySwichItems(int scale, Zombie z){
        
        if (z.drawGUIs == false) return false;
        if (BackPackOpen == true){
            
            int X = getPointerInfo().getLocation().x;
            int Y = getPointerInfo().getLocation().y;
            
            if (z.MousePressed){
                for (int a = 0; a <= 9; a++) {
                    if (Hotbar[a].testClicke(X, Y, scale)) {
                        if(Hotbar[a].item != null && mouseslot != null && mouseslot.item != null && Hotbar[a].item.name.equals(mouseslot.item.name))
                        {
                            int plus = (Hotbar[a].item.maxStackSize - Hotbar[a].stackSize); if (plus < 0) plus = 0; if (mouseslot.stackSize < plus) plus = mouseslot.stackSize;
                            Hotbar[a].stackSize += plus; this.takeOutItem(mouseslot, plus);
                        }
                        else {
                            excangeItemsInSlots(Hotbar[a], mouseslot, scale, z);
                        }
                        SetCursor(z, false); return true;
                    }
                }
                for (int a = 0; a <= 9; a++) {
                    for (int b = 0; b <= 4; b++) {
                        if(BackPack[a][b].testClicke(X, Y, scale)) {
                            if(BackPack[a][b].item != null && mouseslot != null && mouseslot.item != null && BackPack[a][b].item.name.equals(mouseslot.item.name))
                            {
                                int plus = (BackPack[a][b].item.maxStackSize - BackPack[a][b].stackSize); if (plus < 0) plus = 0; if (mouseslot.stackSize < plus) plus = mouseslot.stackSize;
                                BackPack[a][b].stackSize += plus; this.takeOutItem(mouseslot, plus);
                            }
                            else {
                                excangeItemsInSlots(BackPack[a][b], mouseslot, scale, z);
                            }
                            SetCursor(z, false); return true;
                        }
                    }
                }
            }
            if (!z.MousePressed){
                if (mouseslot.item != null) return false;
                
                for (int a = 0; a <= 9; a++) {
                    if (Hotbar[a].testClicke(X, Y, scale)) {
                        if (Hotbar[a].item != null && Hotbar[a].stackSize > 1){
                            mouseslot.item = Hotbar[a].item.CreateNew();
                            mouseslot.setItem(mouseslot.item.type, mouseslot.item.imagePath, z.gUIScale);
                            mouseslot.stackSize = (int)(Hotbar[a].stackSize / 2);
                            Hotbar[a].stackSize = (int)(Hotbar[a].stackSize / 2 + Hotbar[a].stackSize % 2);
                            SetCursor(z, false); return true;
                        }
                    }
                }
                for (int a = 0; a <= 9; a++) {
                    for (int b = 0; b <= 4; b++) {
                        if(BackPack[a][b].testClicke(X, Y, scale)) {
                            if (BackPack[a][b].item != null && BackPack[a][b].stackSize > 1){
                                mouseslot.item = BackPack[a][b].item.CreateNew();
                                mouseslot.setItem(mouseslot.item.type, mouseslot.item.imagePath, z.gUIScale);
                                mouseslot.stackSize = (int)(BackPack[a][b].stackSize / 2);
                                BackPack[a][b].stackSize = (int)(BackPack[a][b].stackSize / 2 + BackPack[a][b].stackSize % 2);
                                SetCursor(z, false); return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean testCursorAboveInventory(int scale, Zombie z){
        
        if (z.drawGUIs == false) return false;
        if (BackPackOpen == true){
            int X = getPointerInfo().getLocation().x;
            int Y = getPointerInfo().getLocation().y;
            
            for (int a = 0; a <= 9; a++) {
                if (Hotbar[a].testClicke(X, Y, scale)) { return true;}
            }
            for (int a = 0; a <= 9; a++) {
                for (int b = 0; b <= 4; b++) {
                    if(BackPack[a][b].testClicke(X, Y, scale)) { return true;}
                }
            }
        }
        return false;
    }
    
    public void excangeItemsInSlots(ItemSlot slot1, ItemSlot slot2, int guiScale, Zombie z){
        
        if (slot1 == null) slot1 = new ItemSlot(0, 0);
        if (slot2 == null) slot2 = new ItemSlot(0, 0);
        
        int quantity = 0;
        
        ItemSlot s = new ItemSlot(0, 0);
        s.item = slot1.item;
        quantity = slot1.stackSize;
        if (slot1.item != null) s.setItem(slot1.item.type, slot1.item.imagePath, guiScale);
        else s.item = null;
        
        slot1.item = slot2.item;
        slot1.stackSize = slot2.stackSize;
        if (slot2.item != null)slot1.setItem(slot2.item.type, slot2.item.imagePath, guiScale);
        else slot1.item = null;
        slot2.item = s.item;
        slot2.stackSize = quantity;
        if (s.item != null)slot2.setItem(s.item.type, s.item.imagePath, guiScale);
        else slot2.item = null;
    }
    
    public void tryGiveItem(Item item, int scaleGui){
     
        for (ItemSlot i : this.Itemslots){
            if (i.item == null || (i.item != null && i.item.name.equals(item.name) && i.stackSize < i.item.maxStackSize)){
                i.item = item.CreateNew();
                System.out.println("item is " + item.name);
                i.stackSize++;
                i.setItem(item.type, item.imagePath, scaleGui);
                break;
            }
        }
    }
    
    public ItemSlot getSelectedItemSlot(int scale){
        
        ItemSlot s = null;
        
        if (mouseslot != null && mouseslot.item != null) return mouseslot;
        
        if (this.BackPackOpen == false) return this.Hotbar[HotbarSlotSelected];
        else {
            int X = getPointerInfo().getLocation().x;
            int Y = getPointerInfo().getLocation().y;
            
            for (int a = 0; a <= 9; a++) 
                if (Hotbar[a].testClicke(X, Y, scale)) return Hotbar[a];
            for (int a = 0; a <= 9; a++) 
                for (int b = 0; b <= 4; b++) 
                    if(BackPack[a][b].testClicke(X, Y, scale)) return BackPack[a][b];
        }
        
        return null;
    }
    
    public boolean takeOutItem(ItemSlot itemslot, int quantity){
        
        if (itemslot != null){
            for (int a = 0; a < quantity; a++){
                if (itemslot.item != null){
                    itemslot.stackSize--;
                    if (itemslot.stackSize <= 0){
                        itemslot.stackSize = 0;
                        itemslot.item = null;
                        return true;
                    }
                }
                else{
                    itemslot.stackSize = 0;
                    return true;
                }
            }
            return true;
        }
        
        return false;
    }
    
}
