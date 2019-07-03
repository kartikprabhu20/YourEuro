package com.artexceptionals.youreuro.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void constructorTesting() {
        Account account = new Account("RUPEE",50);
        assertEquals("Fetching Account name failed", "RUPEE", account.getCurrency());
        assertEquals("Fetching balance failed", 50, account.getBalance(), 0.001);
    }

    @Test
    public void setterGetterTesting() {
        Account account = new Account();

        account.setCurrency("POUND");
        account.setBalance(450.80f);
        assertEquals("Fetching Account name failed", "POUND", account.getCurrency());
        assertEquals("Fetching balance failed", 450.80f, account.getBalance(), 0.001);
    }
}