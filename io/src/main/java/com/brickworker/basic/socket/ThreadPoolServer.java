package com.brickworker.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author tongzhixiang
 * @create 2019-09-04 16:21
 */
public class ThreadPoolServer {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(100);


   static class IoTask implements Runnable{

        private Socket socket;

        public IoTask(Socket socket) {
            this.socket = socket;
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            int len;
            byte[] data = new byte[1024];
            try {
                InputStream inputStream = socket.getInputStream();
                while ((len = inputStream.read(data)) != -1){
                    System.out.println(new String(data, 0, len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception, IOException {
        ServerSocket serverSocket = new ServerSocket(3333);
        new Thread(() -> {
            while (true){
                try {
                    Socket socket = serverSocket.accept();
                    EXECUTOR_SERVICE.execute(new IoTask(socket));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
