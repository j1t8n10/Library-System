import java.util.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed = false;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isBorrowed() { return isBorrowed; }

    public void borrow() { isBorrowed = true; }
    public void returnBook() { isBorrowed = false; }

    @Override
    public String toString() {
        return String.format("%s by %s (ISBN: %s)%s", title, author, isbn, isBorrowed ? " [Borrowed]" : "");
    }
}

class User {
    private String name;
    private String userId;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() { return name; }
    public String getUserId() { return userId; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.borrow();
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.returnBook();
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s)", name, userId);
    }
}

class LibrarySystem {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, User> users = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public Book getBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    public boolean borrowBook(String userId, String isbn) {
        User user = users.get(userId);
        Book book = books.get(isbn);
        if (user != null && book != null && !book.isBorrowed()) {
            user.borrowBook(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(String userId, String isbn) {
        User user = users.get(userId);
        Book book = books.get(isbn);
        if (user != null && book != null && user.getBorrowedBooks().contains(book)) {
            user.returnBook(book);
            return true;
        }
        return false;
    }

    public void listBooks() {
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    public void listUsers() {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }
}

public class main {
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();

        library.addBook(new Book("1999", "Amit Shah", "1234567890"));
        library.addBook(new Book("How to drive car", "Salman Khan", "2345678901"));
        library.addBook(new Book("The champ is here", "John Cena", "3456789012"));

        library.addUser(new User("Ram", "U001"));
        library.addUser(new User("Sita ", "U002"));

        System.out.println("Books in library:");
        library.listBooks();

        System.out.println("\nUsers:");
        library.listUsers();

        System.out.println("\nSita  borrows '1999':");
        if (library.borrowBook("U001", "1234567890")) {
            System.out.println("Borrowed successfully.");
        } else {
            System.out.println("Borrow failed.");
        }

        System.out.println("\nBooks in library after borrowing:");
        library.listBooks();


        System.out.println("\nSita returns '1999':");
        if (library.returnBook("U001", "1234567890")) {
            System.out.println("Returned successfully.");
        } else {
            System.out.println("Return failed.");
        }

        System.out.println("\nBooks in library after returning:");
        library.listBooks();
    }
}
