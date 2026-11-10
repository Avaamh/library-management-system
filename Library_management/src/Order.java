import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Comparable<Order> {
    ArrayList<Book> orderedBooks = new ArrayList<>();
    Date date;

    public Order(Book book, Date date) {
        this.orderedBooks.add(book);
        this.date = date;
    }

    public Order(ArrayList<Book> books, Date date) {  // each order can have one book or a list of books
        orderedBooks.addAll(books);
        this.date = date;
    }


    public void printDetails() { // prints the book's data
        ArrayList<String> names = new ArrayList<>();
        for (Book book : orderedBooks)
            names.add(book.getName());
        System.out.println("Ordered Books: " + names);
        System.out.println("Total price: " + getOrderPrice());
        System.out.println("Will be sent on: " + this.getDate() + "\n");

    }

    public void printBookNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Book book : orderedBooks)
            names.add(book.getName());
        System.out.println(names);

    }

    public String getDate() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }

    public long getOrderPrice() { // returns total price of all ordered books
        int sumOfPrices = 0;
        for (Book book : orderedBooks)
            sumOfPrices += book.getPrice();
        return sumOfPrices;
    }

    @Override
    public int compareTo(Order another) {
        if (this.getOrderPrice() < another.getOrderPrice())
            return -1;
        else if (this.getOrderPrice() > another.getOrderPrice())
            return 1;
        else if (this.date.after(another.date))
            return -1;
        else if (this.date.before(another.date))
            return 1;
        return 0;
    }
}
