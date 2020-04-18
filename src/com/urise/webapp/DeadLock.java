package com.urise.webapp;

import java.util.Random;

public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account();
        Account account2 = new Account();

        //Lock lock1 = new ReentrantLock();
        //Lock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (account1) {
                    synchronized (account2) {
                        Account.moving(account1, account2, new Random().nextInt(100));
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (account2) {
                    synchronized (account1) {
                        Account.moving(account2, account1, new Random().nextInt(100));
                    }
                }
            }
        });

        /*Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lock1.lock();
                lock2.lock();
                try {
                    Account.moving(account1, account2, new Random().nextInt(100));
                } finally {
                    lock1.unlock();
                    lock2.unlock();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lock2.lock();
                lock1.lock();
                try {
                    Account.moving(account1, account2, new Random().nextInt(100));
                } finally {
                    lock1.unlock();
                    lock2.unlock();
                }
            }
        });*/

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        total(account1, account2);
    }

    private static void total(Account account1, Account account2) {
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println("Total: " + (account1.getBalance() + account2.getBalance()));
    }

    static class Account {
        private int balance = 1000;

        public int getBalance() {
            return balance;
        }

        public void increment(int inc) {
            balance += inc;
        }

        public void decrement(int dec) {
            balance -= dec;
        }

        public static void moving(Account account1, Account account2, int total) {
            account1.decrement(total);
            account2.increment(total);
        }
    }
}

