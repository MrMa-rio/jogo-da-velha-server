package org.jogos.utils.threads.ExceptionHandlers.UncaughtException;

import java.lang.Thread.UncaughtExceptionHandler;
public class UncaughtException implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        System.out.println("Exceção na thread " + thread.getName() + ", " + throwable.getMessage());
    }
}
