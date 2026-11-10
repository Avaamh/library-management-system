public class Book implements Comparable<Book> {
    private final String name;
    private final String writer;
    private final long price;

    public Book(String name, String writer, long price) {
        this.name = name;
        this.writer = writer;
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public String getWriter() {
        return writer;
    }

    public String getName() {
        return name;
    }

    public void printDetails() {
        System.out.println(getName() + "\t Writer : " + getWriter() + "\t Price : " + getPrice());
    }

    @Override
    public int compareTo(Book book) {
        if (this.getPrice() < book.getPrice())
            return -1;
        else if (this.getPrice() > book.getPrice())
            return 1;
        else return 0;
    }
}
