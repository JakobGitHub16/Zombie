/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author 123
 */
public class clientthread extends Thread{
    public void run() {
        try{
            
            while(true)
            {
                Socket client = new Socket("192.168.0.4", 10000);
                //System.out.println("[NOTIFICATION]: Client1 started!");
                InetAddress IAp = InetAddress.getLocalHost();
                
                //System.out.println(IAp.toString().split("/")[1]);
                //Streams
                OutputStream oStream = client.getOutputStream();
                PrintWriter w = new PrintWriter(oStream);

                InputStream iStream = client.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
                //---------------------------------------

                    
                    w.write(IAp.toString().split("/")[1] + "#BeamProjectile#100#500#100#100#1000#3#0.5#0.1#10#true#0#0#0#0#0#0#1\n");

                    w.flush();

                    String s = null;
                    while((s = reader.readLine()) != null)
                    {
                        System.out.println("Client1: Empfangen von Server: " + s);
                        break;
                    }
                    Thread.sleep(1000);
                w.close();
                reader.close();
                client.close();
            }
            //System.out.println("ALL CLOSED CLIENT");
            
            
        
        }
        catch(Exception e){System.out.print(e);}
    }
}
