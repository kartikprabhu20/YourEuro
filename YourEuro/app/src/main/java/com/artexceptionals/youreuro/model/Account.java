package com.artexceptionals.youreuro.model;

import java.util.Objects;

public class Account {

    String currency;
    double balance;

    public Account(String currency, double balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public Account() {

    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency);
    }

}
