package zombie;

import java.util.ArrayList;
import java.util.List;

public class Feld {

    public int Wert = 0;
    public BlockNormal blocknormal = null;
    public BlockTop blocktop = null;
    public BlockGround blockground = null;
    public List<int[]> renderBlocks = new ArrayList<int[]>();
    public int brightness = 0;
    public int lightemission = 0;
    public int lightAbsorbtion = 1;
    
    public Feld(BlockNormal blockNormal, BlockTop blockTop, BlockGround blockGround) {
        
        if (blockNormal != null) blocknormal = blockNormal;
        if (blockTop != null) blocktop = blockTop;
        if (blockGround != null) blockground = blockGround;
        SetValue();
    }

    void SetValue()
    {   
        int bnV = 0, btV = 0, bgV = 0;
        this.lightAbsorbtion = 0;
        this.lightemission = 0;
        if (blocknormal != null){
            bnV = blocknormal.value;
            lightAbsorbtion += blocknormal.lightAbsorbtion;
            lightemission += blocknormal.lightLevel;
        }
        if (blocktop != null){
            btV = blocktop.value;
            lightAbsorbtion += blocktop.lightAbsorbtion;
            lightemission += blocktop.lightLevel;
        }
        if (blockground != null){
            bgV = blockground.value;
            lightAbsorbtion += blockground.lightAbsorbtion;
            lightemission += blockground.lightLevel;
        }
        if (lightemission > 15) lightemission = 15;
        if (lightAbsorbtion > 15) lightAbsorbtion = 15;
        if (bnV == 1 || btV == 1 || bgV == 1)Wert = 1;
        else if (bnV == 2 || btV == 2 || bgV == 2)Wert = 2;
        else Wert = 0;
    }
}
