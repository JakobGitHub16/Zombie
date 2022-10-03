package zombie;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerServerThread extends Thread
{
    public  Zombie z = null;
    String CurrentIp = "";
    
    ServerServerThread(Zombie Z){
        z = Z;
    }
    
    public void run() {
        
        ExecutorService exe = Executors.newFixedThreadPool(20);
        
        try{
            ServerSocket server = new ServerSocket(10000);
            System.out.println("[NOTIFICATION]: Server started!");
            
            while(true)
            {

            
                Socket client = server.accept();
                exe.execute(new clientthread());

                //Streams
                OutputStream oStream = client.getOutputStream();
                PrintWriter w = new PrintWriter(oStream);

                InputStream iStream = client.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
                //------------------------------------

                String s = null;

                while((s = reader.readLine()) != null)
                {
                    
                    if (s.split("#")[1].equals("BeamProjectile"))       //----------------------------------------If MEssage is to summon a BeamBullet
                    {
                        boolean writeIp = true;
                        for (int a = 0; a <= this.z.clientIps.length - 1; a++){
                            if (this.z.clientIps[a] != null)
                            if (this.z.clientIps[a].equals(s.split("#")[0])) {writeIp = false;break;}
                        }
                        if (writeIp == true){
                        
                            for (int a = 0; a <= this.z.clientIps.length - 1; a++){
                            if (this.z.clientIps[a] == null) {this.z.clientIps[a] = s.split("#")[0];break; }
                        }
                        
                        }
                         this.CurrentIp = s.split("#")[0];
                         
                         for (int a = 0; a <= z.projectile.length - 1; a++)
                         if (z.projectile[a] == null)
                            {
                                double[] motion = new double[2];
                                motion[0]  = Double.parseDouble(s.split("#")[8]);
                                motion[1]  = Double.parseDouble(s.split("#")[9]);
                               /* z.projectile[a] = new BeamProjectile(
                                        Integer.parseInt(s.split("#")[2]),
                                        Integer.parseInt(s.split("#")[3]),
                                        Integer.parseInt(s.split("#")[4]),
                                        Integer.parseInt(s.split("#")[5]),
                                        Integer.parseInt(s.split("#")[6]),
                                        a,
                                        motion,
                                        Integer.parseInt(s.split("#")[10]),
                                        Boolean.parseBoolean(s.split("#")[11]),
                                        new Color(Integer.parseInt(s.split("#")[12]),Integer.parseInt(s.split("#")[13]),Integer.parseInt(s.split("#")[14])),
                                        new Color(Integer.parseInt(s.split("#")[15]),Integer.parseInt(s.split("#")[16]),Integer.parseInt(s.split("#")[17])),
                                        Integer.parseInt(s.split("#")[18]), null);   */                   
                                break;
                            }
                         new ServerClientThread(this.z, s, CurrentIp).start();
                         break;
                    }
                    else if (s.split(".").length == 4)              //-----------------------------------------------If the message is the Ip-adress of the client
                    {
                        boolean writeIp = true;
                        for (int a = 0; a <= this.z.clientIps.length - 1; a++){
                            if (this.z.clientIps[a].equals(s)) writeIp = false;
                        }
                        if (writeIp == true)
                        for (int a = 0; a <= this.z.clientIps.length - 1; a++){
                            if (this.z.clientIps[a] == null) {this.z.clientIps[a] = "LOL";break; }
                        }
                        
                        break;
                    }

                }
                w.close();
                reader.close();
                client.close();
                Thread.sleep(10);
            }
        }
        catch(Exception e){System.out.println(e);}
    }
    
    public void GetStrings(String s){
        
    }
}
