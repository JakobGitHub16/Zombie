
import zombie.*;
import java.awt.*;


public class block3 extends BlockTop{
    
            int param = 1;
    	    int classId = 5;

            @Override
            public block3 CreateNew() {
                
                block3 b = new block3();
		b.name = this.name;
	        b.value = this.value;
        	b.textureId = this.textureId;
	        b.textureId2 = this.textureId2;
		b.param = this.param;
		b.classId = this.classId;
	        for (Item o : this.drops){
        	    b.drops.add(o.CreateNew());
	        }
                return b;
            }
            
            @Override
            public void OnStepOn(Zombie z, Graphics g, int blockX, int blockY){
                  System.out.println("Uff...");
            }
}
