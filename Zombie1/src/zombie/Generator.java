package zombie;

import java.util.ArrayList;
import java.util.List;

public class Generator {
	
	public BlockNormal currentNormalBlock = null;
	public BlockGround currentGroundBlock = null;
	public BlockTop currentTopBlock = null;
	
        public List<String[]> layers = new ArrayList<String[]>();
        
	public Generator(String[] generatorData) {
            
            String[] splitData;
            
            for (String s : generatorData){
                if (!s.equals("")){
                    String[] oneLayerData = new String[3];
                    splitData = s.split("=");
                    System.out.println("s = " + s);
                    oneLayerData[0] = splitData[0].split("-")[0];
                    oneLayerData[1] = splitData[0].split("-")[1];
                    oneLayerData[2] = splitData[1];
                    layers.add(oneLayerData);
                }
                
            }       
	}
        
    /**
     * gets the blocks which should generate at the given chunklayer and stores them at
     * the currentNormalBlock, currentGroundBlock, currentTopBlock parameters of this object.
     * @param z
     * @param layerNumber the number of the layer of which the block that should be generated should be returned
     */
    public void getBlocksForLayer(Zombie z, int layerNumber){
        
        for (String[] s : layers){
                
                if (s[0].equals(".")){
                    if (layerNumber <= Integer.parseInt(s[1])){
                        String[] splitLayerBlockData = s[2].split(",", -1);
                        this.currentNormalBlock = z.worldAccessor.GetBlockNormal(z, splitLayerBlockData[0]);
                        this.currentGroundBlock = z.worldAccessor.GetBlockGround(z, splitLayerBlockData[1]);
                        this.currentTopBlock = z.worldAccessor.GetBlockTop(z, splitLayerBlockData[2]);
                        return;
                    }
                }
                else if (s[1].equals(".")){
                    if (layerNumber >= Integer.parseInt(s[0])){
                        String[] splitLayerBlockData = s[2].split(",", -1);
                        this.currentNormalBlock = z.worldAccessor.GetBlockNormal(z, splitLayerBlockData[0]);
                        this.currentGroundBlock = z.worldAccessor.GetBlockGround(z, splitLayerBlockData[1]);
                        this.currentTopBlock = z.worldAccessor.GetBlockTop(z, splitLayerBlockData[2]);
                        return;
                    }
                }
                else if (layerNumber >= Integer.parseInt(s[0]) && layerNumber <= Integer.parseInt(s[1])){
                    String[] splitLayerBlockData = s[2].split(",", -1);
                    this.currentNormalBlock = z.worldAccessor.GetBlockNormal(z, splitLayerBlockData[0]);
                    this.currentGroundBlock = z.worldAccessor.GetBlockGround(z, splitLayerBlockData[1]);
                    this.currentTopBlock = z.worldAccessor.GetBlockTop(z, splitLayerBlockData[2]);
                    return;
                }
                    
            }
            
        }
}
