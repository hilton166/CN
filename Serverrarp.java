// Serverrarp.java
import java.io.*;
import java.net.*;

public class Serverrarp {
    public static void main(String[] args) {
        try {
            DatagramSocket server = new DatagramSocket(1309);
            while (true) {
                byte[] sendbyte;
                byte[] receivebyte = new byte[1024];

                DatagramPacket receiver = new DatagramPacket(receivebyte, receivebyte.length);
                server.receive(receiver);

                String str = new String(receiver.getData(), 0, receiver.getLength()).trim();

                InetAddress addr = receiver.getAddress();
                int port = receiver.getPort();

                String[] ip = { "165.165.80.80", "165.165.79.1" };
                String[] mac = { "6A:08:AA:C2", "8A:BC:E3:FA" };

                boolean found = false;
                for (int i = 0; i < ip.length; i++) {
                    if (str.equals(mac[i])) {
                        sendbyte = ip[i].getBytes();
                        DatagramPacket sender = new DatagramPacket(sendbyte, sendbyte.length, addr, port);
                        server.send(sender);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    String notFoundMsg = "MAC address not found";
                    sendbyte = notFoundMsg.getBytes();
                    DatagramPacket sender = new DatagramPacket(sendbyte, sendbyte.length, addr, port);
                    server.send(sender);
                }
                
                break; // remove if server should continue to listen forever
            }
            server.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
