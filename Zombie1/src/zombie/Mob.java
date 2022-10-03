/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import enumClasses.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;


public class Mob extends Entity {

    public List<AITaskBase> AITasks = new ArrayList<AITaskBase>();
    public List<Entity> targetingEntities = new ArrayList<Entity>();
    public AITaskBase nowExecutingAITask = null;
    public int AITaskDuration = 0;
    public animationManager animation = null;
    public animationManager idleAnimation = null;
    
    public Mob() {
        super();
        if (this.idleAnimPath != ""){animation = new animationManager(this.idleAnimPath, 20, 3, 40);idleAnimation = new animationManager(this.idleAnimPath, 20, 3, 40);}
        else if (this.imagePath != "") {animation = new animationManager(this.imagePath, 20, 3, 40);idleAnimation = new animationManager(this.imagePath, 20, 3, 40);}
    }
    
    @Override
    public void OnGameTick(Zombie z, Graphics g){
        super.OnGameTick(z, g);
        
        try{
            if (AITaskDuration <= 0){
                        //------------------------------------------------------------------------Getting aiTasks which can be executed and inizialising other importtant things
                        if (nowExecutingAITask != null) nowExecutingAITask.onNotExecuting(z, g);
                        double totalPrioritie = 0;
                        List<AITaskBase> ExecutableAITasks = new ArrayList<AITaskBase>();
                        
                        for (AITaskBase a : AITasks){
                            if(a.canExecute(z, g)){
                             totalPrioritie += a.priority;
                             ExecutableAITasks.add(a);
                            }
                            else a.onNotExecuting(z, g);
                        }
                        if (totalPrioritie == 0) return;
                        //--------------------------------------------------------------------------Run the AITask
                        
                        double i = new Random().nextInt((int)totalPrioritie);
                        
                        //System.out.println("total... " + totalPrioritie);
                        totalPrioritie = 0;
                        int id = 0;
                        
                        for (AITaskBase a : ExecutableAITasks){
                            if (i < totalPrioritie + a.priority && i >= totalPrioritie){
                                nowExecutingAITask = a;
                                AITaskDuration = a.getDuration();
                                if (nowExecutingAITask.animation != null) this.animation = nowExecutingAITask.animation;
                                break;
                            }
                            totalPrioritie += a.priority;
                            id++;
                        }
                        //System.out.println("id = " + id + " " + ExecutableAITasks.get(0) + " " + totalPrioritie + " " + i + " " + ExecutableAITasks.get(0).priority);
                        
            }
            else {
                if (nowExecutingAITask != null) {nowExecutingAITask.run(z, g);}//System.out.println("name = " + nowExecutingAITask.name);
                AITaskDuration--;
                if (nowExecutingAITask.cancelAITask()){AITaskDuration = 0;animation = idleAnimation;}
            }
            //System.out.println("name = " + this.name);
        }
        catch(Exception e){System.out.println("ewrror in onGame Tick at Mob : " + e.getMessage());}
    }
    
    @Override
    public void draw(Zombie z, Graphics g, int Z, int Q, int shakeX, int shakeY, int depthOffset) {
        
        super.draw(z, g, Z, Q, shakeX, shakeY, depthOffset);
        
        if (this.animation == null){    
            if (this.image == null)
            try {
                g.setColor(Color.blue);
                //g.fillRect(X - Z - 120, Y - Q - 120, 240, 240);
                g.fillRect((int)this.X - Z + shakeX, (int)this.Y - Q + shakeY + depthOffset, 60, 60);
            } catch (Exception e) {

            }
            else g.drawImage(this.image, (int)this.X - Z + shakeX + this.drawoffsetX, (int)this.Y - Q + shakeY + this.drawoffsetY + depthOffset, z);
        }
        else this.animation.updateAndDraw(z, g, this.X + this.drawoffsetX, this.Y + this.drawoffsetY + depthOffset, true, this.facing);
    }
    
    @Override
    public void OnProjDamage(Zombie z, Graphics g, Entity byEntity){
        super.OnProjDamage(z, g, byEntity);
        if (nowExecutingAITask != null) if (nowExecutingAITask.cancelOnHurt == true)AITaskDuration = 0;
    }
    
    @Override
    public void OnMeleeDamage(Zombie z, Graphics g, Entity byEntity){
        super.OnMeleeDamage(z, g, byEntity);
        if (nowExecutingAITask != null) if (nowExecutingAITask.cancelOnHurt == true)AITaskDuration = 0;
    }
       
    @Override
    public void drawDebugInfo(Zombie z, Graphics g){
        super.drawDebugInfo(z, g);
        if (nowExecutingAITask != null) nowExecutingAITask.showDebugInfo(z, g);
    }
    
    @Override
    public void receiveDamage(double amount, Entity sourceEntity){
        super.receiveDamage(amount, sourceEntity);
        this.targetingEntities.add(sourceEntity);
    }
    
    
    @Override
    public Mob CreateNew(){
        
        Mob e = new Mob();
        e.X = this.X;
        e.Y = this.Y;
        e.drawoffsetX = this.drawoffsetX;
        e.drawoffsetY = this.drawoffsetY;
        e.Speed = this.Speed;
        for (String s : this.motions.keySet()){
            e.motions.put(s, this.motions.get(s).clone());
        }
        for (String s : this.attributes.keySet()){
            e.attributes.put(s, this.attributes.get(s).clone());
        }
        e.name = this.name;
        e.Id = this.Id;
        for (Hitbox b : this.hitboxes){
            e.hitboxes.add(new Hitbox(b.sizeX, b.sizeY, b.offsetX, b.offsetY));
        }
        this.despawnConditions.forEach((d) -> {
            e.despawnConditions.add(d.CreateNew());
        });
        e.image = this.image;
        e.imagePath = this.imagePath;
        e.idleAnimPath = this.idleAnimPath;
        e.canClimb = this.canClimb;
        e.invincible = this.invincible;
        e.canReceiveKnockBack = this.canReceiveKnockBack;
        e.chunkLayer = this.chunkLayer;
        if (this.animation != null) e.animation = this.animation.CreateNew(); else e.animation = null;
        if (this.idleAnimation != null) e.idleAnimation = this.idleAnimation.CreateNew(); else e.idleAnimation = null;
        
        for (AITaskBase a : this.AITasks){
            e.AITasks.add(a.CreateNew());
        }
        
        this.OnCreateNew(e);
        
        return e;
    }
    
    public void OnCreateNew(Mob newCreatedEntity){
        
    }
}
