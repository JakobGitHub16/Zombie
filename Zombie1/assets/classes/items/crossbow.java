/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import zombie.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class crossbow extends Item {

    int time = 0;
    int beamlength = 100;
    public boolean breakOnWalls = true;
    
    public crossbow() {
        super();
        this.type = "ranged";
    }
        
    public void LeftClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        for (int a = 0; a <= z.projectile.length - 1; a++)
        if (z.projectile[a] == null)
        {
            boolean shoot = true;
            double dltX = 0, dltY = 0;
            double x2, y2;
            dltX = targetX - (usingEntity.X - z.Z + 30);
            dltY = targetY - (usingEntity.Y - z.Q + 30);

            double dltXAlt = dltX;

            dltX = (beamlength / (Math.sqrt(dltXAlt*dltXAlt + dltY*dltY))) * dltXAlt;
            dltY = (beamlength / (Math.sqrt(dltXAlt*dltXAlt + dltY*dltY))) * dltY;

            if (this.breakOnWalls == true){
                double testX = 0;
                double xPlus = dltX / beamlength;
                for (int b = 0; b <= beamlength; b++){
                    testX += xPlus;
                    try{
                        if (this.getBlock(z.Spielkarte, ((usingEntity.X + 30 + (int)testX)), ((usingEntity.Y + 30 + (int)(dltY / dltX * testX)))).Wert == 1)
                        {
                            dltY = (dltY / dltX * testX);
                            dltX = testX;
                            break;
                        }
                    }catch(Exception e){}
                }
            }
        
        double[] motion = new double[2];
        motion[0] = dltX / 5;
        motion[1] = dltY / 5;
        int startX = (int)(usingEntity.X + 30 + dltX); if (startX < 0) startX = 0;
        int startY = (int)(usingEntity.Y + 30 + dltY); if (startY < 0) startY = 0;
        z.projectile[a] = new WorldAccessor().getProjectile(z, "arrow");
        Map<String, Object> m2 = new HashMap<String, Object>(); 
        m2.put("x", (usingEntity.X + 30));m2.put("y", (usingEntity.Y + 30));m2.put("startx", startX);m2.put("starty", startY);m2.put("chunklayer", usingEntity.chunkLayer);m2.put("id", a);m2.put("motion", motion);m2.put("entity", usingEntity);
        z.projectile[a].summon(z, m2);
        break;
        }
    }
 
    public void HeldClick(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity)
    {
        this.time++;
        if (this.time >= 10){
        for (int a = 0; a <= z.projectile.length - 1; a++)
        if (z.projectile[a] == null)
        {
            boolean shoot = true;
            double dltX = 0, dltY = 0;
            double x2, y2;
            dltX = targetX - (usingEntity.X - z.Z + 30);
            dltY = targetY - (usingEntity.Y - z.Q + 30);

            double dltXAlt = dltX;

            dltX = (beamlength / (Math.sqrt(dltXAlt*dltXAlt + dltY*dltY))) * dltXAlt;
            dltY = (beamlength / (Math.sqrt(dltXAlt*dltXAlt + dltY*dltY))) * dltY;

            if (this.breakOnWalls == true){
                double testX = 0;
                double xPlus = dltX / beamlength;
                for (int b = 0; b <= beamlength; b++){
                    testX += xPlus;
                    try{
                        if (this.getBlock(z.Spielkarte, ((usingEntity.X + 30 + (int)testX)), ((usingEntity.Y + 30 + (int)(dltY / dltX * testX)))).Wert == 1)
                        {
                            dltY = (dltY / dltX * testX);
                            dltX = testX;
                            break;
                        }
                    }catch(Exception e){}
                }
            }
        
        double[] motion = new double[2];
        motion[0] = dltX / 5;
        motion[1] = dltY / 5;
        int startX = (int)(usingEntity.X + 30 + dltX); if (startX < 0) startX = 0;
        int startY = (int)(usingEntity.Y + 30 + dltY); if (startY < 0) startY = 0;
        z.projectile[a] = new WorldAccessor().getProjectile(z, "arrow");
        Map<String, Object> m2 = new HashMap<String, Object>();
        m2.put("x", (usingEntity.X + 30));m2.put("y", (usingEntity.Y + 30));m2.put("startx", startX);m2.put("starty", startY);m2.put("chunklayer", usingEntity.chunkLayer);m2.put("id", a);m2.put("motion", motion);m2.put("entity", usingEntity);
        z.projectile[a].summon(z, m2);
        break;
        }
        this.time = 0;
        }
        
    }
    
    @Override
    public crossbow CreateNew(){
        
    	crossbow i = new crossbow();
        i.name = this.name;
        i.imagePath = this.imagePath;
        i.time = this.time;
        i.beamlength = this.beamlength;
        i.breakOnWalls = this.breakOnWalls;
        i.maxStackSize = this.maxStackSize;
        i.description = this.description;
        i.NameColor = new Color(this.NameColor.getRGB());
        return i;
    }
}
