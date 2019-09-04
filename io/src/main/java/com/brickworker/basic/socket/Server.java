package com.brickworker.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author tongzhixiang
 * @create 2019-09-04 15:45
 */
public class Server {

    public static void main(String[] args) throws Exception, IOException {
        ServerSocket serverSocket = new ServerSocket(3333);

        new Thread(() -> {
            while (true){
                try {
                    Socket socket = serverSocket.accept();

                    new Thread(() -> {
                        int len;
                        byte[] data = new byte[1024];
                        try {
                            Thread.sleep(1000);
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1){
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
