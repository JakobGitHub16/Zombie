import zombie.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
        
public class AITaskIdle extends AITaskBase { 
    
    public AITaskIdle(){
        super();
        this.duration = 500;
        this.name = "AITaskIdle";
    }

    @Override
    public void run(Zombie z, Graphics g){
            this.owningEntity.motions.get("movement")[0] = 0;
            this.owningEntity.motions.get("movement")[1] = 0;
    }
    
    @Override
    public boolean canExecute(Zombie z, Graphics g){
        return super.canExecute(z, g);
    }
       
    
    @Override
    public void onLoad(Map<String, String> params){
        super.onLoad(params);
        if (params.containsKey("cancelOnHurt")) this.cancelOnHurt = Boolean.parseBoolean(params.get("cancelOnHurt"));
    }
    
    @Override
    public void showDebugInfo(Zombie z, Graphics g){
        super.showDebugInfo(z, g);
    }
    
    @Override
    public AITaskIdle CreateNew(){
        
        AITaskIdle a = new AITaskIdle();
	a.priority = this.priority;
        a.duration = this.duration;
        a.name = this.name;
        a.cancelOnHurt = this.cancelOnHurt;
        if (this.animation != null) a.animation = this.animation.CreateNew(); else a.animation = null;
        for (int i = 0; i <= this.conditions.size() - 1; i++){
            a.conditions.add(String.valueOf(this.conditions.get(i)));
        }
        this.OnCreateNew(a);
        
        return a;
    }
    
    public void OnCreateNew(AITaskIdle newCreatedAiTask){        
        
    }
}
