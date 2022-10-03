package zombie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClientThread extends Thread
{
    Zombie z;
    String s = "";
    String CurrentIp = "";
    
    public ServerClientThread(Zombie Z, String S, String currentIp){
        this.z = Z;
        this.CurrentIp = currentIp;
        this.s = S;
    }
    
    public void run() {
        try{
            
            for (int a = 0; a <= z.clientIps.length - 1; a++)
            {
                if (z.clientIps[a] != null)
                if (!this.CurrentIp.equals(z.clientIps[a]))
                {
                    //System.out.println("Resent to Client " + a + ": his Ip is " + z.clientIps[a] + "; and the Current Ip is: " + this.CurrentIp);
                    Socket client = new Socket(z.clientIps[a], 10000);



                    //Streams
                    OutputStream oStream = client.getOutputStream();
                    PrintWriter w = new PrintWriter(oStream);

                    InputStream iStream = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
                    //---------------------------------------

                    w.write(this.s);
                    w.flush();
                    
                    w.close();
                    reader.close();
                    client.close();
                }      

            }

            
        
        }
        catch(Exception e){}
    }
}

