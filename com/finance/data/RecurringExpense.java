package com.finance.data;

import java.time.LocalDate;

// P5: Inheritance + P6: Polymorphism
public class RecurringExpense extends Expense {

    private LocalDate nextDue;

    public RecurringExpense(String category, LocalDate date, double amount, String description, LocalDate nextDue) {
        super(category, date, amount, description);
        this.nextDue = nextDue;
    }

    public LocalDate getNextDue() { return nextDue; }

    @Override
    public String displaySummary() {
        return getDate() + " | " + getCategory() + " | " + getAmount() +
               " | " + getDescription() + " | Due again: (" + nextDue + ")";
    }

    @Override
    public String toFileString() {
        return "REC," + getCategory() + "," + getDate() + "," + getAmount() + "," + getDescription() + "," + nextDue;
    }
}
