package com.jackpan.v2ch04.inetaddress;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author jackpan
 * @version v1.0 2021/8/25 13:26
 */
public class InetAddressTest {

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress address : addresses) {
                System.out.println(address);
            }
        } else {
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println(localHostAddress);
        }
    }
}
