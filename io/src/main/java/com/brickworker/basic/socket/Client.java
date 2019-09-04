package com.brickworker.basic.socket;

import java.net.Socket;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author tongzhixiang
 * @create 2019-09-04 15:42
 */
public class Client {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(1);

    public static void main(String[] args) throws Exception{
        while (true){
            ATOMIC_INTEGER.getAndIncrement();
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 3333);
                    while (true){
                        socket.getOutputStream().write((new Date() + "; hello world").getBytes());
                        Thread.sleep(2000);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(10);
            System.out.println("Thread sum:" + ATOMIC_INTEGER.get());
        }
    }
}
