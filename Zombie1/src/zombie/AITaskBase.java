/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class AITaskBase {
    
    public Entity owningEntity = null;
    public List<String> conditions = new ArrayList<String>();
    
    public double priority = 1;
    public int duration = 0;
    public boolean cancel = false;
    public String name = "";
    public boolean cancelOnHurt = false;
    public animationManager animation = null;
    
    
    public AITaskBase(){
        
    }
    
    public void run(Zombie z, Graphics g){
        
    }
    
    public boolean canExecute(Zombie z, Graphics g){
        try{
        boolean matching = false;
        if (this.owningEntity instanceof IntelligentMob)
            for (String strg : conditions){
                
                if (strg.contains(">"))
                for (IntelligencePropertie p : ((IntelligentMob)this.owningEntity).intelligenceProperties)
                {
                    if (p.name.equals(strg.split(">")[0])){
                        if (p.value > Integer.parseInt(strg.split(">")[1])) return true;
                    }
                }
                else if (strg.contains("<"))
                for (IntelligencePropertie p : ((IntelligentMob)this.owningEntity).intelligenceProperties)
                {
                    if (p.name.equals(strg.split("<")[0])){
                        if (p.value < Integer.parseInt(strg.split("<")[1])) return true;
                    }
                }
                else if (strg.contains("="))
                for (IntelligencePropertie p : ((IntelligentMob)this.owningEntity).intelligenceProperties)
                {
                    if (p.name.equals(strg.split("=")[0])){
                        if (p.value == Integer.parseInt(strg.split("=")[1])) return true;
                    }
                }
                else return true;
            }
        else return true;
        
        }catch (Exception e){}
        return false;
    }
    
    public void onNotExecuting(Zombie z, Graphics g){
        
    }
    
    public void showDebugInfo(Zombie z, Graphics g){
        
    }
    
    public int getDuration(){
        return this.duration;
    }
    
    public boolean cancelAITask(){
        if (this.cancel == true)
        {
            this.cancel = false;
            return true;
        }
        return false;
    }
    
    public void onLoad(Map<String, String> params){
        
        if (params.containsKey("animation"))
        {
            Map<String, String> m = new HashMap<String, String>();
            String[] s = params.get("animation").split("\\|");            
            for (String st : s){
                m.put(st.split("->")[0], st.split("->")[1]);
            }
            try{
                this.animation = new animationManager(m.get("sprite"), Integer.parseInt(m.get("time")), Integer.parseInt(m.get("frames")), Integer.parseInt(m.get("frame_heigth")));
            }catch(Exception e){}
        }
        
    }
    
    public AITaskBase CreateNew(){
        
        AITaskBase a = new AITaskBase();
        a.priority = this.priority;
        a.duration = this.duration;
        a.name = this.name;
        a.cancelOnHurt = this.cancelOnHurt;
        if (this.animation != null) a.animation = this.animation.CreateNew(); else a.animation = null;
        for (int i = 0; i <= this.conditions.size() - 1; i++){
            a.conditions.add(String.valueOf(this.conditions.get(i)));
        }
        
        OnCreateNew(a);
        
        return a;
        
    }
    
    public void OnCreateNew(AITaskBase newCreatedAiTask){
        
    }
}
