/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class defaultNonMeleeWeapon extends Item {

    int time = 0;
    int beamlength = 100;
    public String type;
    public Color color1, color2;
    public int shootoffset = 0, projectileLifelength = 300, projectileThickness, Motiondevider;
    boolean breakOnWalls = false;
    
    public defaultNonMeleeWeapon(String Type, int Damage, String type, Color c1, Color c2, int t, int shtoffset, int prLifelength, int projectilethickness, boolean breakonwalls, int motiondevider) {
        super();
        color1 = c1;
        color2 = c2;
        time = t;
        shootoffset = shtoffset;
        projectileLifelength = prLifelength;
        projectileThickness = projectilethickness;
        breakOnWalls = breakonwalls;
        Motiondevider = motiondevider;
    }
    
    public void LeftClickUse(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity) {
        
            int i1 = 0, i2 = 0;
            if (this.shootoffset > 0){
                Random r = new Random();
                i1 = r.nextInt(this.shootoffset * 60);
                if (i1 > 120) {
                    i1 = i1 * (-1);
                }
                i2 = r.nextInt(this.shootoffset * 60);
                if (i2 > 120) {
                    i2 = i2 * (-1);
                }
            }
            
            for (int a = 0; a <= z.projectile.length - 1; a++) {
                if (z.projectile[a] == null) {
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

                motion[0] = dltX / this.Motiondevider;
                motion[1] = dltY / this.Motiondevider;
                double startX = usingEntity.X + 30 + dltX; if (startX < 0) startX = 0;
                double startY = usingEntity.Y + 30 + dltY; if (startY < 0) startY = 0;
                    //z.projectile[a] = new BeamProjectile(usingEntity.X + 30, usingEntity.Y + 30, startX, startY, this.projectileLifelength, a, motion, this.damage, this.breakOnWalls, this.color1, this.color2, this.projectileThickness, usingEntity);
                    break;
                }
            }
    }

    public void HeldClick(Zombie z, int targetX, int targetY, Graphics g, Entity usingEntity) {
        this.time++;
        if (this.time >= 10) {
            int i1 = 0, i2 = 0;
            if (this.shootoffset > 0){
                Random r = new Random();
                i1 = r.nextInt(this.shootoffset * 60);
                if (i1 > 120) {
                    i1 = i1 * (-1);
                }
                i2 = r.nextInt(this.shootoffset * 60);
                if (i2 > 120) {
                    i2 = i2 * (-1);
                }
            }
            
            for (int a = 0; a <= z.projectile.length - 1; a++) {
                if (z.projectile[a] == null) {
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

                motion[0] = dltX / this.Motiondevider;
                motion[1] = dltY / this.Motiondevider;
                double startX = usingEntity.X + 30 + dltX; if (startX < 0) startX = 0;
                double startY = usingEntity.Y + 30 + dltY; if (startY < 0) startY = 0;
//                    z.projectile[a] = new BeamProjectile(usingEntity.X + 30, usingEntity.Y + 30, startX, startY, this.projectileLifelength, a, motion, this.damage, this.breakOnWalls, this.color1, this.color2, this.projectileThickness, usingEntity);
                    break;
                }
            }
            this.time = 0;
        }

    }
    
    @Override
    public defaultNonMeleeWeapon CreateNew(){
        
        defaultNonMeleeWeapon i = new defaultNonMeleeWeapon(this.name, this.damage, this.type, this.color1, this.color2, this.time, this.shootoffset, this.projectileLifelength, this.projectileThickness, this.breakOnWalls, this.Motiondevider);
        i.imagePath = this.imagePath;
        i.time = this.time;
        i.beamlength = this.beamlength;
        i.breakOnWalls = this.breakOnWalls;
        i.color1 = this.color1;
        i.color2 = this.color2;
        i.shootoffset = this.shootoffset;
        i.projectileLifelength = this.projectileLifelength;
        i.projectileThickness = this.projectileThickness;
        i.Motiondevider = this.Motiondevider;
        
        return i;
    }
}
