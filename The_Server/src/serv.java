import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


class Serv{

    private ServerSocket ss;

    private Socket s;
    private DataInputStream din;
    private DataOutputStream dout;
    private BufferedReader br;

    public Serv( ) {

    }



    public void Send(String ch) throws IOException {
        System.out.println("SERVER: "+ch);
        dout.flush();
        dout.writeUTF(ch);
        dout.flush();
    }

    public String receive() throws IOException {
        String str2=din.readUTF();
        System.out.println("CLIENT: "+str2);
        return str2;
    }


    public void close() throws IOException {

        din.close();
        dout.close();
        s.close();
        ss.close();

    }

    public void activate() throws IOException {

        /*frame.activated.setText("SERVER ACTIVATED!!");
        frame.activated.setStyle("-fx-text-fill: #008000");
        frame.activated.setFont(Font.font(null, FontWeight.BOLD, 20));*/

        ss = new ServerSocket(3333);

        System.out.println("server running");

        s = ss.accept();
        System.out.println("client accepted");
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());


    }

}