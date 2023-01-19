package com.example.pinkpack.models;

public class Customer {
    public String firstName;
    public String surName;
    public String dogName;
    public String email;
    public Double price;
    public int invoiceNumber;
    public int numberOfDaysWeek;
    public String type;
    public String id;

    public Customer(String firstName, String surName, String dogName, String email, Double price, int invoiceNumber, int numberOfDaysWeek, String type) {
        this.firstName = firstName;
        this.surName = surName;
        this.dogName = dogName;
        this.email = email;
        this.price = price;
        this.invoiceNumber = invoiceNumber;
        this.numberOfDaysWeek = numberOfDaysWeek;
        this.type = type;
    }

    public Customer() {}
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getNumberOfDaysWeek() {
        return numberOfDaysWeek;
    }

    public void setNumberOfDaysWeek(int numberOfDaysWeek) {
        this.numberOfDaysWeek = numberOfDaysWeek;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


