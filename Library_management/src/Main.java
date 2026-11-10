
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static HashMap usersMap;   // saving users as value to their id key
    static OrderQueue ordersQueue;   // queue of registered orders sorted by price and date
    static LibraryTree tree;   // main tree

    public static void main(String[] args) {

        usersMap = new HashMap<String, User>();
        ordersQueue = new OrderQueue();

        testInit();


        start();

    }

    static void testInit() {   // initializing part of main tree
        Book b1 = new Book("sakhteman dade", "writer1", 500);
        Book b2 = new Book("system amel", "writer2", 500);
        Book b3 = new Book("assembly", "writer3", 300);
        Book b4 = new Book("janatan book", "writer4", 200);
        Book b5 = new Book("arbab halqeha", "writer5", 1000);
        tree = new LibraryTree();
        tree.addCategory("daneshgahi");
        tree.addCategory("omomi");
        tree.addSubCategory("fani", "daneshgahi");
        tree.addSubCategory("ensani", "daneshgahi");
        tree.addSubCategory("paye", "daneshgahi");
        tree.addSubCategory("barq", "fani");
        tree.addSubCategory("computer", "fani");
        tree.addSubCategory("fantezi", "omomi");
        tree.addBook(b1, "computer");
        tree.addBook(b2, "computer");
        tree.addBook(b3, "computer");
        tree.addBook(b4, "omomi");
        tree.addBook(b5, "fantezi");
        User u1 = new User("user1", "customer");
        User u2 = new User("user2", "customer");
        User u3 = new User("manager1", "manager");
        usersMap.put(u1.getID(), u1);
        usersMap.put(u2.getID(), u2);
        usersMap.put(u3.getID(), u3);
        u1.addToShoppingList(b1);
        u1.addToShoppingList(b2);
    }

    static void start() {
        int key;

        while (true) {
            System.out.println("1. Sign up\n2. Log in\n3. Exit");
            key = scanner.nextInt();
            switch (key) {
                case 1 -> {
                    clearScreen();
                    signUp();
                }
                case 2 -> {
                    clearScreen();
                    signIn();
                }
                case 3 -> System.exit(0);
                default -> {
                    clearScreen();
                    System.out.println("Invalid input!");
                }
            }
        }
    }

    static void signUp() {   // every customer or manager signs up with a unique id which will be saved in users Hashmap as key
        System.out.println("Enter an ID: ");
        String id = scanner.next();
        while (usersMap.get(id) != null) {
            System.out.println("ID has already token. Please enter another: ");
            id = scanner.next();
        }
        System.out.println("Enter your role: (Customer / Manager)");
        String role = scanner.next();
        while (!role.equalsIgnoreCase("manager") && !role.equalsIgnoreCase("customer")) {
            System.out.println("Invalid input. Please enter again: ");
            role = scanner.next();
        }
        usersMap.put(id, new User(id, role));
        clearScreen();
        System.out.println("Signed up successfully.");
    }

    static void signIn() {  // users can sign in using their unique id
        System.out.println("Please enter your ID: ");
        String id = scanner.next();
        while (usersMap.get(id) == null) {
            System.out.println("User not found! try again: ");
            id = scanner.next();
        }
        clearScreen();
        System.out.println("Logged in.\n");
        User user = (User) usersMap.get(id);
        if (user.isCustomer) customersMenu(user);
        else managersMenu();
    }

    static void customersMenu(User user) {
        int key;

        while (true) {
            System.out.println("""
                    1. List all books
                    2. List books from a specific category
                    3. Book details
                    4. Add books to shopping list
                    5. Order your shopping list
                    6. Show shopping list
                    7. Log out""");
            key = scanner.nextInt();
            switch (key) {
                case 1 -> listBooks();
                case 2 -> listCategoryBooks();
                case 3 -> bookDetails();
                case 4 -> addToShoppingList(user);
                case 5 -> order(user);
                case 6 -> printShoppingList(user);
                case 7 -> {
                    clearScreen();
                    return;
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid input! try again\n ");
                }
            }
        }
    }

    static void managersMenu() {
        int key;

        while (true) {
            System.out.println("""
                    1. Add new category
                    2. Add new subCategory to an existing category
                    3. Remove category
                    4. Add new book to a category
                    5. Remove book from a category
                    6. Show orders list
                    7. Show orders of a specific customer
                    8. Log out""");
            key = scanner.nextInt();
            switch (key) {
                case 1 -> addCategory();
                case 2 -> addSubCategory();
                case 3 -> removeCategory();
                case 4 -> addBook();
                case 5 -> removeBook();
                case 6 -> listOrders();
                case 7 -> customerOrders();
                case 8 -> {
                    clearScreen();
                    return;
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid input! try again ");
                }
            }
        }
    }


    private static void addCategory() { // adds input category as a child of main tree's root

        System.out.println("Enter category name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        clearScreen();
        if (tree.addCategory(name))  // checks if category not exists yet
            System.out.println("New category added.");
        System.out.println();

    }

    private static void addSubCategory() {  // adds input subcategory to input category's children
        System.out.println("Enter an existing category name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter new SubCategory name:");
        String subName = scanner.nextLine();
        clearScreen();
        if (tree.addSubCategory(subName, name))  // checks if subcategory not exists yet
            System.out.println("New subCategory added.");
        System.out.println();
    }

    private static void removeCategory() {  // removes a category and all its children from its parents children in tree
        System.out.println("Enter an existing category name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        clearScreen();
        if (tree.removeCategory(name))
            System.out.println("Category removed.");
        System.out.println();
    }


    private static void addBook() {  // adds input book to the input category as child
        System.out.println("Enter an existing category name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter new book name:");
        String bookName = scanner.nextLine();
        System.out.println("Enter writer's name:");
        String writerName = scanner.nextLine();
        System.out.println("Enter price:");
        long price = scanner.nextLong();
        Book book = new Book(bookName, writerName, price);
        clearScreen();
        if (tree.addBook(book, name))
            System.out.println("New book added.");
        System.out.println();
    }

    private static void removeBook() { // removes the book's leaf of tree
        System.out.println("Enter an existing book name:");
        scanner.nextLine();
        String bookName = scanner.nextLine();
        clearScreen();
        if (tree.removeBook(bookName))
            System.out.println("Book removed.");
        System.out.println();
    }

    private static void listOrders() {  // prints a sorted list of all orders
        clearScreen();
        if (ordersQueue.getSize() < 1)
            System.out.println("Orders list is empty!");
        else ordersQueue.print();
        scanner.next();
        clearScreen();
    }

    private static void customerOrders() {  // prints all previous orders of the customer which manager chose by id
        System.out.println("Enter customer's ID:");
        scanner.nextLine();
        String id = scanner.nextLine();
        clearScreen();
        if (usersMap.get(id) != null) {
            User enteredUser = (User) usersMap.get(id);
            if (enteredUser.getOrdersLog().isEmpty())
                System.out.println("This customer has no orders.");
            else {
                enteredUser.printOrdersLog();
                scanner.next();
                clearScreen();
            }
        } else System.out.println("User not found.");
        System.out.println();
    }

    private static void listCategoryBooks() {  // lists all available books name in the input category
        clearScreen();
        System.out.println("Enter category name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        while (tree.searchCategory(name) == null) {
            System.out.println("Category not found. enter again:");
            name = scanner.nextLine();
        }
        clearScreen();
        tree.listCategoryBooks(name);
        scanner.next();
        clearScreen();
    }

    private static void bookDetails() { // prints all data of input book's detail
        clearScreen();
        System.out.println("Enter book name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        while (tree.searchBook(name) == null) {
            System.out.println("Book not found. enter again:");
            name = scanner.nextLine();
        }
        clearScreen();
        Book book = tree.searchBook(name);
        book.printDetails();
        scanner.next();
        clearScreen();
    }

    private static void addToShoppingList(User user) { // adds input book to users shopping list
        clearScreen();
        System.out.println("Enter book name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        while (tree.searchBook(name) == null) {
            System.out.println("Book not found. enter again:");
            name = scanner.nextLine();
        }
        for (Book book : user.getShoppingList()) {
            if (book.getName().equalsIgnoreCase(name)) {
                clearScreen();
                System.out.println("The book already exist in your shopping list.\n");
                return;
            }
        }
        Book book = tree.searchBook(name);
        user.addToShoppingList(book);
        clearScreen();
        System.out.println("Book added to your shopping list.\n");
    }

    private static void order(User user) { // sets a new order for customer and adds it to orders priority queue
        clearScreen();
        if (user.order()) {
            ordersQueue.insert(user.getLastOrder());
            System.out.println("Your order registered.\n");
        }
    }

    private static void printShoppingList(User user) {
        clearScreen();
        user.printShoppingList();
        scanner.next();
        clearScreen();
    }

    private static void listBooks() { // lists all books available in library's tree
        clearScreen();
        tree.listBooks(tree.root);
        scanner.next();
        clearScreen();
    }

    public static void clearScreen() {
        for (int i = 0; i < 100; i++) System.out.println();
    }

}