/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;
import enumClasses.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class IntelligencePropertie {
    
    public Map<enumIntelligencePropertieChangeConditions, Integer> changeConditions = new HashMap<enumIntelligencePropertieChangeConditions, Integer>();
    public String name = "";
    public int value = 0;
    public int variation = 0;
    
    public IntelligencePropertie(String name, int value, Map<enumIntelligencePropertieChangeConditions, Integer> changeChonditions){
        this.changeConditions = changeChonditions;
        this.name = name;
        this.value = value;
    }
    
    public IntelligencePropertie CreateNew()
    {
        IntelligencePropertie i = new IntelligencePropertie(this.name, this.value, new HashMap<enumIntelligencePropertieChangeConditions, Integer>());
        for (enumIntelligencePropertieChangeConditions e : this.changeConditions.keySet()){
            i.changeConditions.put(e, this.changeConditions.get(e).intValue());
        }
        i.value = this.value;
        i.variation = this.variation;
        return i;
    }
     
}
