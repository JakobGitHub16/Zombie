package zombie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class newJSONreader {
    
    public Map ReadNewJSON(String path)
    {
        Map map = new HashMap();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            String prevLine = "";
            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split(":", -1);
                if (splittedLine.length == 2){
                    map.put(splittedLine[0], splittedLine[1]);
                    prevLine = splittedLine[0];
                }
                else{
                    if (map.get(prevLine) != null){
                        map.replace(prevLine, map.get(prevLine) + splittedLine[0]);
                    }
                }
            }
        }
        catch (Exception e){}
        
        return map;
    }
}