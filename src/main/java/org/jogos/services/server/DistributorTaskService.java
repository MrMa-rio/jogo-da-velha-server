package org.jogos.services.server;

import org.jogos.Server;

import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class DistributorTaskService implements Runnable {
    private Socket socket;
    private Server server;
    private ExecutorService threadPool;
    private BlockingQueue<String> queue;

    public DistributorTaskService(Socket socket, BlockingQueue<String> queue, Server server, ExecutorService threadPool) {
        this.socket = socket;
        this.server = server;
        this.threadPool = threadPool;
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            Scanner clientEntry = new Scanner(socket.getInputStream());
            PrintStream clientExit = new PrintStream(socket.getOutputStream());


            while (clientEntry.hasNextLine()) {
                String readLine = clientEntry.nextLine();

                switch (readLine.trim()) {

                    case "c1":
                        clientExit.println("Comando C1 enviado com sucesso!");

                        this.threadPool.execute(() -> System.out.println("Chegou aqui C1"));
                        break;

                    case "c3":
                        this.queue.put(readLine); //bloqueia
                        clientExit.println("Comando c3 adicionado na fila!");
                        break;

                    case "exit":
                        clientExit.println("Encerrando servidor...");
                        this.server.stopRunning();
                        break;

                    default:
                        clientExit.println("Enviado com sucesso!");
                        break;
                }

            }

            clientExit.close();
            clientEntry.close();
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
