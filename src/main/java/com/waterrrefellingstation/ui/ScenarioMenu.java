package com.waterrrfellingstation.ui;

import com.waterrrfellingstation.dao.ScenarioDao;
import com.waterrrfellingstation.model.ScenarioEntity;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {

    private final ScenarioDao dao;
    private final Scanner scanner;

    public ScenarioMenu(ScenarioDao dao, Scanner scanner) {
        this.dao = dao;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("--------------------------------------------------");
            System.out.println("       WATER REFILLING STATION ORDER SYSTEM       ");
            System.out.println("--------------------------------------------------");
            System.out.println("[1] Add Delivery Order");
            System.out.println("[2] View Active Orders");
            System.out.println("[3] Exit System");
            System.out.println("--------------------------------------------------");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addOrderPrompt();
                case "2" -> viewOrders();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nInvalid choice! Please enter 1, 2, or 3.\n");
            }
        }
    }

    private void addOrderPrompt() {
        System.out.println("\n--- ADD DELIVERY ORDER ---");

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine().trim();

        int qtySlim = readIntInput("Enter Quantity (Slim Containers): ");
        int qtyRound = readIntInput("Enter Quantity (Round Containers): ");

        System.out.print("Enter Status (PENDING / DELIVERED): ");
        String status = scanner.nextLine().trim().toUpperCase();

        boolean success = dao.addOrder(name, qtySlim, qtyRound, status);

        if (success) {
            System.out.println("\nSUCCESS: Water delivery order logged!\n");
        } else {
            System.out.println("\nFAILED: Could not log water delivery order.\n");
        }
    }

    private void viewOrders() {
        List<ScenarioEntity> orders = dao.getAllOrders();

        System.out.println("\n----------------------------------------------------------------------------------");
        System.out.println("                              ACTIVE WATER ORDERS                                 ");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("+-----+----------------------+----------+-----------+------------+");
        System.out.printf("| %-3s | %-20s | %-8s | %-9s | %-10s |%n", "ID", "CUSTOMER NAME", "QTY SLIM", "QTY ROUND", "STATUS");
        System.out.println("+-----+----------------------+----------+-----------+------------+");

        if (orders.isEmpty()) {
            System.out.println("|                            No records found.                           |");
        } else {
            for (ScenarioEntity o : orders) {
                System.out.printf("| %-3d | %-20s | %-8d | %-9d | %-10s |%n",
                        o.id(), o.customerName(), o.qtySlim(), o.qtyRound(), o.status());
            }
        }

        System.out.println("+-----+----------------------+----------+-----------+------------+");
        System.out.println("Total Records Displayed: " + orders.size() + "\n");
    }

    private int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer number.");
            }
        }
    }
}