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
public class clientthread2 extends Thread{
    public void run() {
        try{
            
            Socket client;
            OutputStream oStream;
            PrintWriter w;
            BufferedReader reader;
            
            while(true)
            {
                                InetAddress IAp = InetAddress.getLocalHost();
                //System.out.println(IAp.toString().split("/")[1]);
                client = new Socket("192.168.0.4", 10000);
                //System.out.println("[NOTIFICATION]: Client2  started!");

                //Streams
                oStream = client.getOutputStream();
                w = new PrintWriter(oStream);

                InputStream iStream = client.getInputStream();
                reader = new BufferedReader(new InputStreamReader(iStream));
                //---------------------------------------


                    w.write("192.168.0.3#BeamProjectile#500#500#100#100#1000#3#0.5#0.1#10#true#0#0#0#0#0#0#1\n");

                    w.flush();

                    String s = null;
                    while((s = reader.readLine()) != null)
                    {
                        System.out.println("Client2: Empfangen von Server: " + s);
                        break;
                    }
                    Thread.sleep(100);
                    w.close();
                    reader.close();
                    client.close();
            }
            //System.out.println("ALL CLOSED CLIENT");
            //
            
        
        }
        catch(Exception e){System.out.println(e);}
    }
}
