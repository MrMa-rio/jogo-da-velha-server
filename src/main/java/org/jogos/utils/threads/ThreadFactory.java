package org.jogos.utils.threads;

import org.jogos.utils.threads.ExceptionHandlers.UncaughtException.UncaughtException;

public class ThreadFactory implements java.util.concurrent.ThreadFactory {
    private static int number = 1;

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Task server thread " + number);
        number++;
        thread.setUncaughtExceptionHandler(new UncaughtException());
        thread.setDaemon(true);
        return thread;
    }
}
