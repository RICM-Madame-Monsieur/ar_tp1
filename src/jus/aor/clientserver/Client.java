package jus.aor.clientserver;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by matthieu on 15/01/16.
 */
public class Client {

    static int port = 1234;
    static String ip = "127.0.0.1";

    public static void main(String[] args){
        try {
            Socket server = new Socket(ip, port);
            System.out.println("Connected to " + server.getInetAddress());

            OutputStream os = server.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            JFileChooser dialog = new JFileChooser();
            if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = dialog.getSelectedFile();
                byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

                // send the file length
                dos.writeInt(data.length);

                // send file's name
                dos.writeUTF(file.getName());

                // send file content
                os.write(data);
                //System.out.println(file.getAbsolutePath());
            }else{
                // send the file length
                dos.writeInt(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
