package org.jogos;

import org.jogos.services.server.ConsumingTaskService;
import org.jogos.utils.threads.ThreadFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private ServerSocket server;
    private ExecutorService threadPool;
    private AtomicBoolean executing;
    private BlockingQueue queue;

    public Server(BlockingQueue queue) throws IOException {
        this.queue = queue;
        this.server = new ServerSocket(4000);
        this.threadPool = Executors.newCachedThreadPool(new ThreadFactory());
        this.executing = new AtomicBoolean(true);

    }
    public static void main(String[] args) {}
    public void stopRunning() throws IOException {
        System.out.println("\n-----Servidor finalizado-----");
        this.executing.set(false);
        this.threadPool.shutdown();
        this.server.close();
    }

    private void startConsumers(){
        int consumers = 2;
        for(int counter = 0; counter < consumers; counter++){
            ConsumingTaskService consumingTask = new ConsumingTaskService(queue);
            this.threadPool.execute(consumingTask);
        }
    }
}
