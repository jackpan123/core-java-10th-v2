package com.jackpan.v2ch04.threaded;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author jackpan
 * @version v1.0 2021/8/29 15:50
 */
public class ThreadedEchoServer {

    public static void main(String[] args) {
        try (ServerSocket s = new ServerSocket(8189))
        {
            int i = 1;

            while (true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

class ThreadedEchoHandler implements Runnable
{
    private Socket incoming;

    /**
     Constructs a handler.
     @param incomingSocket the incoming socket
     */
    public ThreadedEchoHandler(Socket incomingSocket)
    {
        incoming = incomingSocket;
    }

    public void run()
    {
        try (InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream())
        {
            Scanner in = new Scanner(inStream, "UTF-8");
            PrintWriter out = new PrintWriter(
                new OutputStreamWriter(outStream, "UTF-8"),
                true /* autoFlush */);

            out.println( "Hello! Enter BYE to exit." );

            // echo client input
            boolean done = false;
            while (!done && in.hasNextLine())
            {
                String line = in.nextLine();
                out.println("Echo: " + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
