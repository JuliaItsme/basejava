package com.urise.webapp;

import java.util.Random;

public class DeadLock {
    public static void main(String[] args) {
        Account account1 = new Account();
        Account account2 = new Account();

        new Thread(() -> transfer(account1, account2)).start();

        new Thread(() -> transfer(account2, account1)).start();

        toString(account1, account2);
    }

    private static void transfer(Account account1, Account account2) {
        synchronized (account1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (account2) {
                Account.moving(account1, account2, new Random().nextInt(100));
            }
        }
        toString(account1, account2);
    }

    private static void toString(Account account1, Account account2) {
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Balance: " + account1.getBalance());
        System.out.println("Balance: " + account2.getBalance());
        System.out.println("Total: " + (account1.getBalance() + account2.getBalance()) + "\n");
    }

    static class Account {
        private int balance = 1000;

        public int getBalance() {
            return balance;
        }

        public void increment(int amount) {
            balance += amount;
        }

        public void decrement(int amount) {
            balance -= amount;
        }

        public static void moving(Account account1, Account account2, int amount) {
            account1.decrement(amount);
            account2.increment(amount);
        }
    }
}

