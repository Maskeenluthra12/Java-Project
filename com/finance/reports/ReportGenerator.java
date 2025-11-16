package com.finance.reports;

import com.finance.data.Expense;
import java.util.List;

// Handles Summary + Filters
public class ReportGenerator {

    // Filter by category → format EXACTLY as you requested
    public static void filterByCategory(List<Expense> list, String cat) {

        System.out.println("\nDATE | DESCRIPTION | AMOUNT\n");

        double total = 0;
        for (Expense e : list) {
            // P9: Conditional logic
            if (e.getCategory().equalsIgnoreCase(cat)) {
                System.out.println(e.getDate() + " | " + e.getDescription() + " | " + e.getAmount());
                total += e.getAmount();
            }
        }
        System.out.println("Total spent: " + total);
    }

    // Filter by Month
    public static void filterByMonth(List<Expense> list, int m, int y) {

        System.out.println("\nDATE | CATEGORY | AMOUNT | DESCRIPTION\n");

        double total = 0;
        for (Expense e : list) {
            if (e.getDate().getYear() == y && e.getDate().getMonthValue() == m) {
                System.out.println(e.getDate() + " | " + e.getCategory() +
                                   " | " + e.getAmount() + " | " + e.getDescription());
                total += e.getAmount();
            }
        }
        System.out.println("Total spent: " + total);
    }

    // Summary by months
    public static void showSummary(List<Expense> list) {

        double[] months = new double[12];

        for (Expense e : list) {
            int m = e.getDate().getMonthValue();
            months[m - 1] += e.getAmount();
        }

        System.out.println("\n--- Monthly Totals ---");
        String[] names = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        for (int i = 0; i < 12; i++)
            System.out.println(names[i] + ": " + months[i]);
    }
}
