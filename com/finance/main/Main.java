package com.finance.main;

import com.finance.data.*;
import com.finance.reports.ReportGenerator;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ExpenseManager manager = new ExpenseManager();
    public static void main(String[] args) {
        login();
        menuLoop();
    }
    // ---------------- LOGIN SYSTEM ----------------
    static void login() {
        try {
            File f = new File("users.txt");
            if (!f.exists()) f.createNewFile();

            Map<String,String> users = new HashMap<>();

            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                users.put(p[0], p[1]);
            }
            br.close();

            System.out.println("1. Log In\n2. Sign Up");
            int c = Integer.parseInt(sc.nextLine());

            if (c == 2) {
                System.out.print("New username: ");
                String u = sc.nextLine();
                System.out.print("New password: ");
                String p = sc.nextLine();

                PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true));
                pw.println(u + "," + p);
                pw.close();

                users.put(u, p);
                System.out.println("Account created!");
            }
            while (true) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();

                if (users.containsKey(u) && users.get(u).equals(p)) {
                    System.out.println("Login successful!");

                    manager.setActiveUser(u);
                    manager.loadFromFile();
                    return;
                }
                System.out.println("Invalid. Try again.");
            }
        }
        catch (Exception e) {
            System.out.println("Login error.");
        }
    }
    // ---------------- MENU ---------------------
    static void menuLoop() {
        while (true) {
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Set Monthly Budget");
            System.out.println("4. Filter Expenses");
            System.out.println("5. Summary");
            System.out.println("6. Save & Exit");

            int c = Integer.parseInt(sc.nextLine());

            switch(c) {
                case 1 -> addExpense();
                case 2 -> viewAll();
                case 3 -> setBudget();
                case 4 -> filterMenu();
                case 5 -> ReportGenerator.showSummary(manager.getExpenses());
                case 6 -> {
                    manager.saveToFile();
                    System.out.println("Saved. Bye!");
                    return;
                }
            }
        }
    }
    // ---------------- ADD EXPENSE (your order) -----------------
    static void addExpense() {
        try {
            System.out.print("Category: ");
            String cat = sc.nextLine();

            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate d = LocalDate.parse(sc.nextLine());

            System.out.print("Amount: ");
            double amt = Double.parseDouble(sc.nextLine()); // P3 Exception handling

            System.out.print("Description: ");
            String desc = sc.nextLine();

            System.out.print("Recurring? (Y/N): ");
            String r = sc.nextLine();

            Expense e;

            if (r.equalsIgnoreCase("Y")) {
                System.out.print("Next due date (YYYY-MM-DD): ");
                LocalDate nd = LocalDate.parse(sc.nextLine());
                e = new RecurringExpense(cat, d, amt, desc, nd);
            } else {
                e = new OneTimeExpense(cat, d, amt, desc);
            }

            // P8 object passed
            manager.addExpense(e);

            // Budget alert (P9 conditionals)
            double monthTotal = manager.getMonthTotal(d.getMonthValue(), d.getYear());
            if (manager.getBudget() > 0 && monthTotal > manager.getBudget()) {
                System.out.println("\n⚠ ALERT: Monthly budget exceeded!\n");
            }

        } catch (Exception ex) {
            System.out.println("Invalid input.");
        }
    }
    // ---------------- VIEW ALL -----------------
    static void viewAll() {
        for (Expense e : manager.getExpenses())
            System.out.println(e.displaySummary());
    }
    // ---------------- Set Budget ----------------
    static void setBudget() {
        System.out.print("Enter monthly budget: ");
        manager.setBudget(Double.parseDouble(sc.nextLine()));
        System.out.println("Budget updated.");
    }
    // ---------------- FILTER MENU ----------------
    static void filterMenu() {
        System.out.println("1. Filter by Category");
        System.out.println("2. Filter by Month");

        int c = Integer.parseInt(sc.nextLine());

        if (c == 1) {
            System.out.print("Category: ");
            String cat = sc.nextLine();
            ReportGenerator.filterByCategory(manager.getExpenses(), cat);
        }
        else {
            System.out.print("Enter Month (1-12): ");
            int m = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Year: ");
            int y = Integer.parseInt(sc.nextLine());

            ReportGenerator.filterByMonth(manager.getExpenses(), m, y);
        }
    }
}
