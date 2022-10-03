import zombie.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
        
public class AITaskWander extends AITaskBase {
 
    public List<int[]> markers = new ArrayList<int[]>();
    double previousX = 0;
    double previousY = 0;
    int onSamePlace = 0;
    int searchingRadius = 5;
    int seekingRange = 10;
    int speed = 0;
    
    public AITaskWander(){
        super();
        
        previousX = 0;
        previousY = 0;
        this.duration = 300;
        this.name = "AITaskWander";
    }

    @Override
    public void run(Zombie z, Graphics g){
        
        //System.out.println("markers = " + markers.size() + " prevX & Y = " + previousX + " " + previousY + " x & Y = " + this.owningEntity.X + " " + this.owningEntity.Y);
            if (owningEntity.immunityFrames == 0){
                
                if (markers.size() == 0){
                    
                    getRandomPos(z);
                }
                
                if (markers.size() > 0){
                    
                    if (z.EntityDebuginfo)
                    for (int a = 0; a <= markers.size() - 1; a++){
                        g.fillRect(markers.get(a)[0] - 10 - z.Z, markers.get(a)[1] - 10 - z.Q, 20, 20);
                        if (a != 0) g.drawLine(markers.get(a)[0] - z.Z, markers.get(a)[1] - z.Q, markers.get(a - 1)[0] - z.Z, markers.get(a - 1)[1] - z.Q);
                        if (a == markers.size()-  1) g.drawLine(markers.get(a)[0] - z.Z, markers.get(a)[1] - z.Q, (int)owningEntity.X - z.Z, (int)owningEntity.Y - z.Q);
                    }


                    if (this.owningEntity.X >= markers.get(markers.size() - 1)[0] - 10 && this.owningEntity.X < markers.get(markers.size() - 1)[0] + 10)
                    if (this.owningEntity.Y >= markers.get(markers.size() - 1)[1] - 10 && this.owningEntity.Y < markers.get(markers.size() - 1)[1] + 10){
                        markers.remove(markers.size() - 1);
                    }
                    //System.out.println("markers = " + markers.size() + " " + markers.get(0)[0] + " " + markers.get(0)[1]);
                    if (markers.size() > 0){
                        if (previousX == owningEntity.X && previousY == owningEntity.Y){
                            onSamePlace++;
                            if (onSamePlace >= 5){
                                onSamePlace = 0;
                                int i = 0;
                                WorldAccessor w = new WorldAccessor();
                                while (i < 100){
                                    int X = this.owningEntity.X + (ThreadLocalRandom.current().nextInt(-searchingRadius, searchingRadius + 1)) * 60;
                                    int Y = this.owningEntity.Y + (ThreadLocalRandom.current().nextInt(-searchingRadius, searchingRadius + 1)) * 60;
                                    if (z.EntityDebuginfo)g.fillRect(X - 10 - z.Z, Y - 10 - z.Q, 20, 20);
                                    //if(canGo(z, markers.get(markers.size() - 1)[0], e.Y, X, Y))System.out.println("canGo = true");
                                    if (canGo(z,markers.get(markers.size() - 1)[0], markers.get(markers.size() - 1)[1], (int)(X/60)*60, (int)(Y/60)*60)
                                            && canGo(z, this.owningEntity.X, this.owningEntity.Y, (int)(X/60)*60, (int)(Y/60)*60) && w.GetBlockValue(z, X / 60, Y / 60, this.owningEntity.chunkLayer, this.owningEntity.onMap) == 0){this.markers = addMarker((int)(X/60)*60, (int)(Y/60)*60);break;}
                                    i++;
                                }
                                if (i >= 100){
                                    i = 0;
                                    g.setColor(Color.red);
                                    while (i < 100){
                                        int X = (int)this.owningEntity.X + (ThreadLocalRandom.current().nextInt(-searchingRadius * 2, searchingRadius * 2 + 1)) * 60;
                                        int Y = (int)this.owningEntity.Y + (ThreadLocalRandom.current().nextInt(-searchingRadius * 2, searchingRadius * 2 + 1)) * 60;

                                        if (z.EntityDebuginfo) g.fillRect(X - 10 - z.Z, Y - 10 - z.Q, 20, 20);
                                        //if(canGo(z, markers.get(markers.size() - 1)[0], e.Y, X, Y))System.out.println("canGo = true");
                                        if (canGo(z, (int)this.owningEntity.X, (int)this.owningEntity.Y, (int)(X/60)*60, (int)(Y/60)*60) && w.GetBlockValue(z, X / 60, Y / 60, this.owningEntity.chunkLayer, this.owningEntity.onMap) == 0){this.markers = addMarker((int)(X/60)*60, (int)(Y/60)*60);break;}

                                        i++;
                                    }
                                    g.setColor(Color.blue);
                                }
                            }
                        }
                        else onSamePlace = 0;

                        int moveToX = markers.get(markers.size() - 1)[0];
                        int moveToY = markers.get(markers.size() - 1)[1];

                        if (moveToX < owningEntity.X - owningEntity.Speed) owningEntity.motions.get("movement")[0] = -this.speed;
                        else if (moveToX > owningEntity.X + owningEntity.Speed) owningEntity.motions.get("movement")[0] = this.speed;
                        else owningEntity.motions.get("movement")[0] = 0;
                        if (moveToY < owningEntity.Y - owningEntity.Speed) owningEntity.motions.get("movement")[1] = -this.speed;
                        else if (moveToY > owningEntity.Y + owningEntity.Speed) owningEntity.motions.get("movement")[1] = this.speed;
                        else owningEntity.motions.get("movement")[1] = 0;

                        previousX = this.owningEntity.X;
                        previousY = this.owningEntity.Y;
                    }
                }
            }        
    }
    
    public List<int[]> addMarker(double targetX, double targetY){
        
        List<int[]> mrkrs = this.markers;
        int[] marker = new int[2];
        marker[0] = (int)targetX;
        marker[1] = (int)targetY;
        mrkrs.add(marker);
        
        
        
        return mrkrs;
    }
    
    public boolean canGo(Zombie z, double targetX, double targetY, double fromX, double fromY){
        WorldAccessor w = new WorldAccessor();

        double dltX = targetX - fromX;//240 - 240 = 0 
        double dltY = targetY - fromY;//420 - 540 = - 120
        double dltXAlt = dltX;
        double length = Math.sqrt((dltX * dltX) + (dltY * dltY)); // = 120
        
        double testX = 0;
        double xPlus = dltX / length; //= 0
        
        if (dltX != 0 && dltY != 0)
        for (int b = 0; b <= (int)length; b++){
                testX += xPlus;
                try{
                    
                        if (w.GetBlockValue(z, (int)((int)(fromX + testX) / 60), (int)((int)(fromY + (int)(dltY / dltX * testX)) / 60), this.owningEntity.chunkLayer, this.owningEntity.onMap) != 0){return false;}
                   
                }catch(Exception ex){}
            }
        else if (dltX == 0)
        for (int b = 0; b <= (int)length; b++){
                try{
                        if (w.GetBlockValue(z, (int)((int)(fromX) / 60), (int)((int)(fromY + b) / 60), this.owningEntity.chunkLayer, this.owningEntity.onMap) != 0) return false;
                   
                }catch(Exception ex){}
            }
        else if (dltY == 0)
        for (int b = 0; b <= (int)length; b++){
                try{
                        if (w.GetBlockValue(z, (int)((int)(fromX + b) / 60), (int)((int)(fromY) / 60), this.owningEntity.chunkLayer, this.owningEntity.onMap) != 0) return false;
                   
                }catch(Exception ex){}
            }

        
        return true;
    }
    
    public int getDistanceAndSightInBlocks(Zombie z, double targetX, double targetY, double fromX, double fromY){
        WorldAccessor w = new WorldAccessor();

        double dltX = targetX - fromX;//240 - 240 = 0 
        double dltY = targetY - fromY;//420 - 540 = - 120
        double dltXAlt = dltX;
        double length = Math.sqrt((dltX * dltX) + (dltY * dltY)); // = 120
        
        double testX = 0;
        double xPlus = dltX / length; //= 0
        
        if (dltX != 0 && dltY != 0)
        for (int b = 0; b <= (int)length; b++){
                testX += xPlus;
                try{
                    
                        if (w.GetBlockValue(z, (int)((int)(fromX + testX) / 60), (int)((int)(fromY + (int)(dltY / dltX * testX)) / 60), this.owningEntity.chunkLayer, this.owningEntity.onMap) == 1){return -1;}
                   
                }catch(Exception ex){}
            }
        else if (dltX == 0)
        for (int b = 0; b <= (int)length; b++){
                try{
                        if (w.GetBlockValue(z, (int)((int)(fromX) / 60), (int)((int)(fromY + b) / 60), this.owningEntity.chunkLayer, this.owningEntity.onMap) == 1) return -1;
                   
                }catch(Exception ex){}
            }
        else if (dltY == 0)
        for (int b = 0; b <= (int)length; b++){
                try{
                        if (w.GetBlockValue(z, (int)((int)(fromX + b) / 60), (int)((int)(fromY) / 60), this.owningEntity.chunkLayer, this.owningEntity.onMap) == 1) return -1;
                   
                }catch(Exception ex){}
            }

        
        return (int)(length / 60);
    }
    
    public void getRandomPos(Zombie z){
        int i = 0;
        WorldAccessor w = new WorldAccessor();
        while (i < 100){
            int X = this.owningEntity.X + (ThreadLocalRandom.current().nextInt(-searchingRadius, searchingRadius + 1)) * 60;
            int Y = this.owningEntity.Y + (ThreadLocalRandom.current().nextInt(-searchingRadius, searchingRadius + 1)) * 60;
            if (canGo(z, this.owningEntity.X, this.owningEntity.Y, (int)(X/60)*60, (int)(Y/60)*60) && w.GetBlockValue(z, X / 60, Y / 60, this.owningEntity.chunkLayer, this.owningEntity.onMap) == 0){this.markers = addMarker((int)(X/60)*60, (int)(Y/60)*60);break;}
            i++;
        }
    }
    
    public void clearMarkers(){
        int size = markers.size();
        for (int a = 0; a <= size - 1; a++){
            markers.remove(0);
        }
        
    }
    
    /*@Override
    public boolean canExecute(Zombie z, Graphics g){
        return true;
    }*/
    
    @Override
    public void onNotExecuting(Zombie z, Graphics g){
        this.clearMarkers();
        this.previousX = this.owningEntity.X + 1;
        this.previousY = this.owningEntity.X + 1;
        this.onSamePlace = 0;
        this.owningEntity.motions.get("movement")[0] = 0;
        this.owningEntity.motions.get("movement")[1] = 0;
    }
    
    @Override
    public void onLoad(Map<String, String> params){
        super.onLoad(params);
        if (params.containsKey("duration")) this.duration = Integer.parseInt(params.get("duration"));
        if (params.containsKey("speed")) this.speed = Integer.parseInt(params.get("speed"));
        if (params.containsKey("searchingRadius")) this.searchingRadius = Integer.parseInt(params.get("searchingRadius"));
        if (params.containsKey("cancelOnHurt")) this.cancelOnHurt = Boolean.parseBoolean(params.get("cancelOnHurt"));
    }
    
    @Override
    public void showDebugInfo(Zombie z, Graphics g){
        super.showDebugInfo(z, g);
        if (z.EntityDebuginfo)
            try{
                    for (int a = 0; a <= markers.size() - 1; a++){
                        g.fillRect(markers.get(a)[0] - 10 - z.Z, markers.get(a)[1] - 10 - z.Q, 20, 20);
                        if (a != 0) g.drawLine(markers.get(a)[0] - z.Z, markers.get(a)[1] - z.Q, markers.get(a - 1)[0] - z.Z, markers.get(a - 1)[1] - z.Q);
                        if (a == markers.size()-  1) g.drawLine(markers.get(a)[0] - z.Z, markers.get(a)[1] - z.Q, (int)owningEntity.X - z.Z, (int)owningEntity.Y - z.Q);
                    }
            }catch(Exception e){}
    }
    
    @Override
    public AITaskWander CreateNew(){
        
        AITaskWander a = new AITaskWander();
        
        a.priority = this.priority;
        a.duration = this.duration;
        a.name = this.name;
        a.cancelOnHurt = this.cancelOnHurt;
        for (int i = 0; i <= this.conditions.size() - 1; i++){
            a.conditions.add(String.valueOf(this.conditions.get(i)));
        }
	if (this.animation != null) a.animation = this.animation.CreateNew(); else a.animation = null;
        this.OnCreateNew(a);
        
        return a;
    }
    
     public void OnCreateNew(AITaskWander newCreatedAiTask){
        newCreatedAiTask.searchingRadius = this.searchingRadius;
        newCreatedAiTask.seekingRange = this.seekingRange;
        newCreatedAiTask.speed = this.speed;
    }
}
