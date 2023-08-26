package onlinebookstore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BookStoreSystem {
    private static Map<Integer, Book> books = new HashMap<>();
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        /*initializeBooks();*/
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void initializeBooks() {
        books.put(1, new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 15.99));
        books.put(2, new Book(2, "To Kill a Mockingbird", "Harper Lee", "Fiction", 12.99));
        books.put(3, new Book(3, "1984", "George Orwell", "Science Fiction", 10.99));

    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Registration successful!");
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (!users.containsKey(username)) {
            System.out.println("Username not found. Please register first.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);

        if (!user.authenticate(password)) {
            System.out.println("Invalid password. Please try again.");
            return;
        }

        handleUserActions(user);
    }

    private static void handleUserActions(User user) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\n1. Browse Books\n2. View Cart\n3. Place Order\n4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    browseBooks(user);
                    break;
                case 2:
                    viewCart(user);
                    break;
                case 3:
                    placeOrder(user);
                    break;
                case 4:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void browseBooks(User user) {
        System.out.println("Available books:");
        for (Book book : books.values()) {
            System.out.println(book.getId() + ". " + book.getTitle() + " by " + book.getAuthor() + " - $" + book.getPrice());
        }

        System.out.print("Enter book ID to view details (0 to go back): ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        if (bookId == 0) {
            return;
        }

        Book book = books.get(bookId);

        if (book != null) {
            System.out.println("\nTitle: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Category: " + book.getCategory());
            System.out.println("Price: $" + book.getPrice());
            System.out.println("Rating: " + book.getRating());

            if (!book.getReviews().isEmpty()) {
                System.out.println("Reviews:");
                for (String review : book.getReviews()) {
                    System.out.println(review);
                }
            } 
            else
            {
                System.out.println("No reviews yet.");
            }

            System.out.println("\n1. Add to Cart\n2. Back");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                user.addToCart(book);
                System.out.println(book.getTitle() + " added to cart.");
            }
        } 
        else 
        {
            System.out.println("Invalid book ID.");
        }
    }

    private static void viewCart(User user) {
        List<Book> cart = user.getCart();

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Your cart:");
        double total = 0.0;

        for (Book book : cart) {
            System.out.println(book.getTitle() + " - $" + book.getPrice());
            total += book.getPrice();
        }

        System.out.println("Total: $" + total);
    }

    private static void placeOrder(User user) {
        List<Book> cart = user.getCart();

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Cannot place an order.");
            return;
        }

        System.out.println("Confirm your order:");
        double total = 0.0;

        for (Book book : cart) {
            System.out.println(book.getTitle() + " - $" + book.getPrice());
            total += book.getPrice();
        }

        System.out.println("Total: $" + total);

        System.out.print("Proceed to place the order? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            user.placeOrder();
            System.out.println("Order placed successfully.");
        }
    }
}
