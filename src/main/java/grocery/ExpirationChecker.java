package grocery;

import email.EmailNotifier;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Represents a checker that checks for groceries nearing expiration.
 */
public class ExpirationChecker {
    private GroceryList groceryList;

    /**
     * Constructs an ExpirationChecker.
     *
     * @param groceryList GroceryList to check for expiration.
     */
    public ExpirationChecker(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * Runs the expiration checker.
     */
    public void run() {
        StringBuilder emailContent = new StringBuilder("Items nearing expiration:\n\n");
        boolean hasExpiringItems = false;

        System.out.println("Checking for groceries nearing expiration...");
        List<Grocery> groceries = groceryList.getGroceries();
        LocalDate today = LocalDate.now();
        for (Grocery grocery : groceries) {
            LocalDate expirationDate = grocery.getExpiration();
            if (expirationDate != null && expirationDate.isBefore(today.plusDays(3))) {
                System.out.println(grocery.getName() + " is nearing expiration on " + expirationDate);
                emailContent.append(grocery.getName()).append(" expires on ").append(expirationDate).append("\n");
                System.out.println("Do you wish to send a notification email? (y/n)");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("y")) {
                    hasExpiringItems = true;
                } else if(response.equals("n")){
                    continue;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                    continue;
                }
            }
        }

        if (hasExpiringItems) {
            sendExpiryNotification(emailContent.toString());
        } else {
            System.out.println("No items are nearing expiration within the next 3 days.");
        }
    }

    /**
     * Sends an email notification to the user.
     *
     * @param content Content of the email.
     */
    private void sendExpiryNotification(String content) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Please enter your email to receive notifications:");
            String recipient = scanner.nextLine();

            // Validate the email address
            if (!isValidEmail(recipient)) {
                System.out.println("The email address entered is invalid.");
                return;
            }

            System.out.println("Sending notification email...");

            String subject = "Grocery Expiry Notification";
            EmailNotifier.sendEmail(recipient, subject, content); // Send the email
        } catch (Exception e) {
            System.err.println("An error occurred while sending the email notification: " + e.getMessage());
            e.printStackTrace();
        } 
    }

    /**
     * Validates the email address.
     *
     * @param email Email address to validate.
     * @return True if the email address is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    
}
