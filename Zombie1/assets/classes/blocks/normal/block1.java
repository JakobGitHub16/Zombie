
import zombie.*;
import java.awt.*;


public class block1 extends BlockNormal{
    
            int param = 1;
    	    int classId = 5;
            int i2 = 0;
            
	    public static void setClassId(Integer id, Object o){
		((block1)o).sCId(id);
	    }

	    public void sCId(int id){
		this.classId = id;
		System.out.println("succsessfully set classId");
	    }

            @Override
            public block1 CreateNew() {
                
                block1 b = new block1();
                b.Animationtimer = this.Animationtimer;
                b.cAnimationtimer = 0;
                b.name = this.name;
                b.value = this.value;
                b.textureId = this.textureId;
                b.changetextures = this.changetextures;
                b.texturechangechance = this.texturechangechance;
                b.param = this.param;
                b.classId = this.classId;
	        for (Item o : this.drops){
        	    b.drops.add(o.CreateNew());
	        }
                return b;
            }
            
            @Override
            public void OnStepOn(Zombie z, Graphics g, int blockX, int blockY, Entity byEntity){
                
                
                g.setColor(Color.RED);
                g.drawRect(blockX * 60 - z.Z + 30 - 50, blockY * 60 - z.Q + 30 - 50, 100, 100);
                g.drawRect(blockX * 60 - z.Z + 30 - 51, blockY * 60 - z.Q + 30 - 51, 102, 102);
                g.drawRect(blockX * 60 - z.Z + 30 - 52, blockY * 60 - z.Q + 30 - 52, 104, 104);
                g.drawRect(blockX * 60 - z.Z + 30 - 53, blockY * 60 - z.Q + 30 - 53, 106, 106);
                //g.drawRect(0,0, 106, 106);
                
                i2++;
                if (i2 >= 10)
                {
                    i2 = 0;
                                
                int i = 0;
                
                
                blockX *= 60;
                blockY *= 60;
                
                
                for (int a = 0; a <= z.projectile.length - 1; a++){
                    if (z.projectile[a] == null && i == 0){
                        double[] motion = new double[2];
                        motion[0] = 0; motion[1] = -5;
                        z.projectile[a] = new BeamProjectile(blockX + 30, blockY + 30, blockX + 30, blockY + 30 - 60, 100, a, motion, 0, true, Color.black, Color.white, 5, null);
                        i++;
                    }
                    else if (z.projectile[a] == null && i == 1){
                        double[] motion = new double[2];
                        motion[0] = 5; motion[1] = 0;
                        z.projectile[a] = new BeamProjectile(blockX + 30, blockY + 30, blockX + 30 + 60, blockY + 30, 100, a, motion, 0, true, Color.black, Color.white, 5, null);
                        i++;
                    }
                    else if (z.projectile[a] == null && i == 2){
                        double[] motion = new double[2];
                        motion[0] = 0; motion[1] = 5;
                        z.projectile[a] = new BeamProjectile(blockX + 30, blockY + 30, blockX + 30, blockY + 30 + 60, 100, a, motion, 0, true, Color.black, Color.white, 5, null);
                        i++;
                    }
                    else if (z.projectile[a] == null && i == 3){
                        double[] motion = new double[2];
                        motion[0] = -5; motion[1] = 0;
                        z.projectile[a] = new BeamProjectile(blockX + 30, blockY + 30, blockX + 30 - 60, blockY + 30, 100, a, motion, 0, true, Color.black, Color.white, 5, null);
                        i++;
                    }
                }
                }
            }
}
