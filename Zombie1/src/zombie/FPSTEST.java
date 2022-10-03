package zombie;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class FPSTEST extends Thread
{
    Zombie z;
    
    public FPSTEST(Zombie z) {
        this.z = z;
    }
    
         
    
    public void run() {
        while (true){
            try{Thread.sleep(1000);}catch(Exception e){}
            this.z.lol = 1;
        }
    }
}