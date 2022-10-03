import zombie.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
        
public class AITaskSUS extends AITaskBase { 
    
    int value = 0;
    int sValue = 90;

    public AITaskSUS(){
        super();
        this.duration = 120;
        this.name = "AITaskSUS";
    }

    @Override
    public void run(Zombie z, Graphics g){
        //System.out.println("dum dum dum dum... dddum... dum dun dum");
        this.value++;
        if (this.value == this.sValue){
	        try{
        	    WorldAccessor w = new WorldAccessor();
	            w.SpawnEntity(z, w.getEntity(z, "smallamogus"), this.owningEntity.X, this.owningEntity.Y - 2 * 60, this.owningEntity.onMap, this.owningEntity.chunkLayer);
	        }catch(Exception e){System.out.println("bruh");}                
        }
    }
    
    @Override
    public boolean canExecute(Zombie z, Graphics g){
        this.value = 0;
        return super.canExecute(z, g);
    }
    
    
    
    @Override
    public void onLoad(Map<String, String> params){
        super.onLoad(params);
        if (params.containsKey("cancelOnHurt")) this.cancelOnHurt = Boolean.parseBoolean(params.get("cancelOnHurt"));
        if (params.containsKey("spawn_On_Value")) this.sValue = Integer.parseInt(params.get("spawn_On_Value"));
    }
    
    @Override
    public void showDebugInfo(Zombie z, Graphics g){
        super.showDebugInfo(z, g);
    }
    
    @Override
    public AITaskSUS CreateNew(){
        
        AITaskSUS a = new AITaskSUS();
	a.priority = this.priority;
        a.duration = this.duration;
        a.name = this.name;
        a.cancelOnHurt = this.cancelOnHurt;
        if (this.animation != null) a.animation = this.animation.CreateNew(); else a.animation = null;
        for (int i = 0; i <= this.conditions.size() - 1; i++){
            a.conditions.add(String.valueOf(this.conditions.get(i)));
        }
        a.sValue = this.sValue;
        this.OnCreateNew(a);
        
        return a;
    }
    
    public void OnCreateNew(AITaskSUS newCreatedAiTask){        
        
    }
}
