package com.jackpan.v2ch04.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author jackpan
 * @version v1.0 2021/8/25 13:14
 */
public class SocketTest {

    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket("time-a.nist.gov", 13);
            Scanner in = new Scanner(s.getInputStream(), "UTF-8")) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}
