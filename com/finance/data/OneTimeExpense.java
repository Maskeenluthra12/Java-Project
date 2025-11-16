package com.finance.data;

import java.time.LocalDate;

// P5: Inheritance → child of Expense
public class OneTimeExpense extends Expense {

    public OneTimeExpense(String category, LocalDate date, double amount, String description) {
        super(category, date, amount, description);
    }

    @Override
    public String displaySummary() {
        // Format used in "View All"
        return getDate() + " | " + getCategory() + " | " + getAmount() + " | " + getDescription();
    }

    @Override
    public String toFileString() {
        return "ONE," + getCategory() + "," + getDate() + "," + getAmount() + "," + getDescription();
    }
}
