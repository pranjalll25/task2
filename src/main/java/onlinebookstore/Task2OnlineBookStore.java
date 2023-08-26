package onlinebookstore;

import java.util.*;

public class Task2OnlineBookStore {
}
    class Book {
        private int id;
        private String title;
        private String author;
        private String category;
        private double price;
        private double rating;
        private List<String> reviews;

        public Book(int id, String title, String author, String category, double price) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.category = category;
            this.price = price;
            this.rating = 0.0;
            this.reviews = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }

        public double getRating() {
            return rating;
        }

        public List<String> getReviews() {
            return reviews;
        }

        public void addReview(String review) {
            reviews.add(review);
            updateRating();
        }

        private void updateRating() {
            if (!reviews.isEmpty()) {
                double totalRating = 0.0;
                for (String review : reviews) {
                    totalRating += Double.parseDouble(review.split(":")[1]);
                }
                rating = totalRating / reviews.size();
            }
        }
    }

    class User {
        private String username;
        private String password;
        private List<Book> cart;
        private List<Book> orders;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.cart = new ArrayList<>();
            this.orders = new ArrayList<>();
        }

        public String getUsername() {
            return username;
        }

        public boolean authenticate(String password) {
            return this.password.equals(password);
        }

        public List<Book> getCart() {
            return cart;
        }

        public List<Book> getOrders() {
            return orders;
        }

        public void addToCart(Book book) {
            cart.add(book);
        }

        public void removeFromCart(Book book) {
            cart.remove(book);
        }

        public void placeOrder() {
            orders.addAll(cart);
            cart.clear();
        }
    }

