package com.akcome.ethereum.starter.model;

import java.math.BigInteger;

/**
 * Created by zhou on 16-9-2.
 */
public class AccountInfo {
    private String address;
    private BigInteger balance;

    public AccountInfo(String address, BigInteger balance) {
        this.address = address;
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}
