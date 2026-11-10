import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class User {
    //    private final String username;
    private final String ID;
    private final ArrayList<Order> ordersLog;
    private final ArrayList<Book> shoppingList;
    boolean isManager = false;
    boolean isCustomer = false;

    private Order lastOrder;

    public User(String id, String role) {
        this.ID = id;
        ordersLog = new ArrayList<>();
        shoppingList = new ArrayList<>();
        if (role.equalsIgnoreCase("Manager"))
            isManager = true;
        else isCustomer = true;
    }


    public String getID() {
        return ID;
    }

    public ArrayList<Order> getOrdersLog() {
        return ordersLog;
    }

    public void printShoppingList() {
        if (shoppingList.isEmpty()) {
            System.out.println("Your shopping list is empty!");
            return;
        }

        // every time creates a new order of books in a priority queue then prints that queue so the output will be sorted

        OrderQueue queue = new OrderQueue(shoppingList.size());
        for (Book book : shoppingList)
            queue.insert(new Order(book, new Date()));
        while (queue.getSize() > 0)
            queue.extractMax().printBookNames();
    }

    public void addToShoppingList(Book book) {
        shoppingList.add(book);
    }

    public boolean order() { // creates a new order of all books in shopping list and adds it to customers orderLog
        if (shoppingList.isEmpty()) {
            System.out.println("Your shopping list is empty!");
            return false;
        } else {
            System.out.println("Please enter delivery date: ");
            Scanner sc = new Scanner(System.in);
            String date;
            do {
                date = sc.next();
            } while (stringToDate(date) == null);
            Order order = new Order(shoppingList, stringToDate(date));
            ordersLog.add(order);
            lastOrder = order;
            shoppingList.clear();
        }
        return true;
    }

    public Date stringToDate(String date) { // converts the format of input date string to Date

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date2;
        try {
            date2 = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("input date is invalid! Enter a valid one: ");
            return null;
        }
        return date2;
    }

    public void printOrdersLog() {
        System.out.println("User: " + this.getID());
        for (Order order : ordersLog)
            order.printDetails();
    }

    public ArrayList<Book> getShoppingList() {
        return shoppingList;
    }

    public Order getLastOrder() {
        return lastOrder;
    }
}
