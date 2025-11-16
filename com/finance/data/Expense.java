package com.finance.data;

import java.time.LocalDate;

// P4: Encapsulation – private fields + getters/setters
public abstract class Expense {

    private String category;
    private LocalDate date;
    private double amount;
    private String description;

    public Expense(String category, LocalDate date, double amount, String description) {
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }
    // Encapsulation getters/setters
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    // P6: Polymorphism → overridden in subclasses
    public abstract String displaySummary();
    // Used for writing to file
    public abstract String toFileString();
}
