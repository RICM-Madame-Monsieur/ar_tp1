package jus.aor.clientserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by matthieu on 15/01/16.
 */
public class Server {

    static int port = 1234;

    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(port);
            while (true){
                Socket client = server.accept();
                System.out.println("Client " + client.getInetAddress() + " connected.");

                InputStream is = client.getInputStream();
                DataInputStream dis = new DataInputStream(is);

                // read file length
                int length = dis.readInt();

                if(length != -1) {

                    // read file name
                    String filename = dis.readUTF();

                    System.out.println("File size : " + length + ", File name : " + filename);

                    byte[] data = new byte[length];
                    byte[] b = new byte[100];
                    int start = 0;
                    int num;
                    while (start < length) {
                        num = is.read(b);
                        System.arraycopy(b, 0, data, start, num);
                        start += num;
                        b = new byte[100];
                    }

                    FileOutputStream fos = new FileOutputStream("output/" + filename);
                    fos.write(data);
                    fos.close();

                    System.out.println("Transfer finished for " + filename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
