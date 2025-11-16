package com.finance.data;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
// P7: Array of Objects
public class ExpenseManager {

    private List<Expense> expenses = new ArrayList<>();
    private double monthlyBudget = 0;
    private String activeUser = null;

    public void setActiveUser(String user) { this.activeUser = user; }
    private String fileName() { return activeUser + "_expenses.txt"; }
    // Load user file
    public void loadFromFile() {
        try {
            File f = new File(fileName());
            if (!f.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                String type = p[0];
                String category = p[1];
                LocalDate date = LocalDate.parse(p[2]);
                double amount = Double.parseDouble(p[3]);
                String desc = p[4];

                if (type.equals("ONE")) {
                    expenses.add(new OneTimeExpense(category, date, amount, desc));
                } else {
                    LocalDate next = LocalDate.parse(p[5]);
                    expenses.add(new RecurringExpense(category, date, amount, desc, next));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading expenses.");
        }
    }
    // Save user file
    public void saveToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName()));
            for (Expense e : expenses) pw.println(e.toFileString());
            pw.close();
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }
    // P8: Object as Argument
    public void addExpense(Expense e) {
        expenses.add(e);
    }
    public List<Expense> getExpenses() {
        return expenses;
    }
    public void setBudget(double b) { monthlyBudget = b; }

    public double getBudget() { return monthlyBudget; }
    // Budget check
    public double getMonthTotal(int m, int y) {
        double sum = 0;
        for (Expense e : expenses)
            if (e.getDate().getYear() == y && e.getDate().getMonthValue() == m)
                sum += e.getAmount();
        return sum;
    }
}
