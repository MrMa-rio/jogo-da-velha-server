package org.jogos.services.server;

import java.util.concurrent.BlockingQueue;

public class ConsumingTaskService implements Runnable{

    private BlockingQueue<String> queue;

    public ConsumingTaskService(BlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            String readComand = null;
            while((readComand = queue.take()) != null){
                System.out.println("Consumindo comando " + readComand + ", " + Thread.currentThread().getName());
                Thread.sleep(5000);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
