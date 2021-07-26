package top.dreamcenter.epoch.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10087);
        Socket socket = null;
        while(true) {
            socket = serverSocket.accept();
            runThat(socket);
        }
    }

    public static void runThat(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        String temp;
        while((temp = reader.readLine())!=null&& !temp.equals("")){
            System.out.println(temp);
        }

        writer.println("HTTP/1.1 401 OK");
        writer.println("content-type:text/plain;charset=utf8");
        writer.println();
        writer.println("you are cool");

        writer.close();
        reader.close();
        socket.close();
    }
}
