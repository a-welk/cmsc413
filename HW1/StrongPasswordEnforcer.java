import java.io.*;
import java.util.*;

public class StrongPasswordEnforcer {

    public static boolean isStrongPassword(String password) {
        // Check if the password meets the specified criteria
        if (password.length() < 8) {
            return false;
        }

        // Check if the password contains at least one letter and one digit
        if (!password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*")) {
            return false;
        }

        // Check if the password contains repetitive characters or consecutive numbers
        for (int i = 0; i < password.length() - 3; i++) {
            char currentChar = password.charAt(i);
            char nextChar = password.charAt(i + 1);
            char nextNextChar = password.charAt(i + 2);
            char nextNextNextChar = password.charAt(i + 3);

            // Check for repetitive characters
            if (currentChar == nextChar && nextChar == nextNextChar && nextNextChar == nextNextNextChar) {
                return false;
            }

            // Check for consecutive numbers (e.g., 1234 or 4321)
            if (Character.isDigit(currentChar) && Character.isDigit(nextChar) && Character.isDigit(nextNextChar)) {
                int num1 = Character.getNumericValue(currentChar);
                int num2 = Character.getNumericValue(nextChar);
                int num3 = Character.getNumericValue(nextNextChar);
                if (num2 - num1 == 1 && num3 - num2 == 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create an account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice == 1) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                if (isStrongPassword(password)) {
                    try (PrintWriter writer = new PrintWriter(new FileWriter("passwords.txt", true))) {
                        writer.println("\n" + username + ":" + password);
                        System.out.println("Account created successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Invalid password. Please follow the password criteria.");
                }
            } else if (choice == 2) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                boolean loggedIn = false;

                try (Scanner fileScanner = new Scanner(new File("passwords.txt"))) {
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] parts = line.split(":");
                        if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                            loggedIn = true;
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (loggedIn) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid username/password combination. Please try again.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}
