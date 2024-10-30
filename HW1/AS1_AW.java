import java.io.*;
import java.util.*;

public class AS1_AW {
    public static boolean strongPasswordChecker(String password) {
        if(password.length() < 8) {
            return false;
        }
        if (!password.matches(".*\\d+.*") || !password.matches(".*[a-zA-Z]+.*")) {
            return false;
        }

        //check for repeating characters
        for(int i = 0; i < password.length() - 3; i++) {
            char currentChar = password.charAt(i);
            char secondChar = password.charAt(i+1);
            char thirdChar = password.charAt(i+2);
            char fourthChar = password.charAt(i+3);

            if(currentChar == secondChar && secondChar == thirdChar && thirdChar == fourthChar) {
                return false;
            }

            //check if there are consecutive numbers
            if(Character.isDigit(currentChar) && Character.isDigit(secondChar) && Character.isDigit(thirdChar)) {
                int num1 = Character.getNumericValue(currentChar);
                int num2 = Character.getNumericValue(secondChar);
                int num3 = Character.getNumericValue(thirdChar);

                if((num1 == num2 + 1) && (num2 == num3 + 1)) {
                    return false;
                }

                if((num3 == num2 + 1) && (num2 == num1+1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\nEnter the number of an option below:");
            System.out.println("1. Create account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int input = scanner.nextInt();
            scanner.nextLine();

            if(input == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if(strongPasswordChecker(password)) {
                    try(PrintWriter output = new PrintWriter(new FileWriter("passwords.txt", true))) {
                        output.println(username + ":" + password);
                        System.out.println("Successfully created account.");
                    } catch(IOException e) {
                        System.out.println("Unable to find passwords.txt");
                    }
                } else {
                    System.out.println("Invalid password. Password must be at least 8 characters, contain at least one letter and one numer, and cannot contain repetitve charcters or consecutive numbers");
                }
            }
            else if (input == 2) {
                boolean isLoggedIn = false;

                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();

                try(Scanner textFile = new Scanner(new File("passwords.txt"))) {
                    while(textFile.hasNextLine()) {
                        String line = textFile.nextLine();
                        String [] split = line.split(":");

                        if(split[0].equals(username) && split[1].equals(password)) {
                            isLoggedIn = true;
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to find passwords.txt");
                }

                if(isLoggedIn == true) {
                    System.out.println("Succuessfully logged in.");
                } else {
                    System.out.println("Failed to login, please try again.");
                }
            }else if(input == 3) {
                break;
            }
        }
        scanner.close();
    }
}