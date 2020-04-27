package com.urise.webapp;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10_000;
    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();

//    private static final Object LOCK = new Object();
//    private static final Lock lock = new ReentrantLock();

    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
/*
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        };
    };
*/

//    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));

    private static final ThreadLocal<DateTimeFormatter> DATE_FORMAT = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch countDownLatch = new CountDownLatch(THREADS_NUMBER);

//        ExecutorService executorService = Executors.newCachedThreadPool();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.increment();
//                    System.out.println(DATE_FORMAT.get().format(new Date()));
                }
                countDownLatch.countDown();
                return 5;
            });
//           completionService.poll();
        }

        countDownLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(mainConcurrency.atomicCounter.get());
//        System.out.println(mainConcurrency.counter);

/*
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.increment();
                }
                countDownLatch.countDown();
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
*/

        final String lock1 = "lock1";
        final String lock2 = "lock2";
        deadLock(lock1, lock2);
        deadLock(lock2, lock1);

    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private void increment() {
        atomicCounter.incrementAndGet();
    }

/*
    private void increment() {
        WRITE_LOCK.lock();
        try {
            counter++;
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    private void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    private synchronized void increment() {
        counter++;
    }

    private static void increment() {
        synchronized (LOCK){
            counter++;
        }
    }

    private void increment{
        synchronized (MainConcurrency.class){
            counter++;
        }
    }

 */
}