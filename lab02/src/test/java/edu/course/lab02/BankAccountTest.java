package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void withdrawsMoney() {
        BankAccount account = new BankAccount(1000);
        account.withdraw(300);
        assertEquals(700, account.getBalance());
    }

    @Test
    void rejectsWithdrawalOverBalance() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(200));
        assertEquals(100, account.getBalance());
    }

    @Test
    void depositsMoney() {
        BankAccount account = new BankAccount(100);
        account.deposit(50);
        assertEquals(150, account.getBalance());
    }

    @Test
    void rejectsNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-10));
    }

    @Test
    void rejectsInvalidAmounts() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-5));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-5));
        assertEquals(100, account.getBalance());
    }

    @Test
    void rejectsBalanceOverflow() {
        BankAccount account = new BankAccount(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(1));
    }
}
