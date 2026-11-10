
import java.util.ArrayList;

public class LibraryTree {
    Node root;

    public static class Node {
        Object obj;  // object of node can be either category or book
        Node parent;
        ArrayList<Node> children;
        boolean isBook;

        public Node(Object obj) {
            children = new ArrayList<>();
            this.obj = obj;
            this.parent = null;
            isBook = false;
        }


        public void addChild(Node obj) {
            children.add(obj);
        }

        public void removeChild(Node obj) {
            children.remove(obj);
        }

        public ArrayList<Node> getChildren() {
            return children;
        }
    }

    public LibraryTree() {
        this.root = new Node(new Category("Library"));
    }

    public boolean addCategory(String categoryName) {
        Node node = findNode(root, categoryName, 'c'); // checks if category already exists
        if (node != null) {
            System.out.println("Category[" + categoryName + "] already exists.");
            return false;
        }
        node = new Node(new Category(categoryName));  // creates new node of category and adds it to root's children
        node.parent = root;
        root.addChild(node);
        return true;
    }

    public boolean addSubCategory(String subCategoryName, String categoryName) {  // creates and adds new node to the input category's children
        Node node = findNode(root, categoryName, 'c');
        if (node == null) {
            System.out.println("Category[" + categoryName + "] not found.");
            return false;
        }
        if (findNode(root, subCategoryName, 'c') != null) {
            System.out.println("SubCategory[" + subCategoryName + "] already exists.");
            return false;
        }
        Node subNode = new Node(new Category(subCategoryName));
        subNode.parent = node;
        node.addChild(subNode);
        return true;
    }

    public boolean addBook(Book book, String categoryName) {  // creates and adds new node of book to the input category's children
        Node node = findNode(root, categoryName, 'c');
        if (node == null) {
            System.out.println("Category[" + categoryName + "] not found.");
            return false;
        }
        if (findNode(node, book.getName(), 'b') != null) {
            System.out.println("Book[" + book.getName() + "] already exists.");
            return false;
        }
        Node bookNode = new Node(book);
        bookNode.isBook = true;
        bookNode.parent = node;
        node.addChild(bookNode);
        //       book.setCategory((Category) node.obj);
        return true;
    }

    public boolean removeBook(String bookName) { // finds book node and removes it from its parent's children

        Node bookNode = findNode(root, bookName, 'b');
        if (bookNode == null) {
            System.out.println("Book[" + bookName + "] not found.");
            return false;
        }
        Node categoryNode = bookNode.parent;
        categoryNode.removeChild(bookNode);
        return true;
    }

    public boolean removeCategory(String categoryName) { // removes category's node from its parent's children
        Node node = findNode(root, categoryName, 'c');
        if (node == null) {
            System.out.println("Category[" + categoryName + "] not found.");
            return false;
        }
        node.parent.removeChild(node);
        return true;
    }

    private Node findNode(Node root, String name, char type) {  // finds the node of an entered name for adding and removing operations
        if (root != null) {                                    // separates books name from categories name by an added char (b or c)
            for (Node node : root.children) {
                if (node.isBook && type == 'b') {
                    Book book = (Book) node.obj;
                    if (book.getName().equalsIgnoreCase(name))
                        return node;
                } else if (!node.isBook && type == 'c') {
                    Category category = (Category) node.obj;
                    if (category.getName().equalsIgnoreCase(name))
                        return node;
                }
                if (findNode(node, name, type) != null)
                    return findNode(node, name, type);
            }
        }
        return null;
    }

    public void listBooks(Node root) { // prints all books name which are the leaves of tree
        ArrayList<Node> nodes = root.getChildren();
        for (Node node : nodes) {
            if (node.isBook) {
                Book book = (Book) node.obj;
                System.out.println(book.getName());
            }
            if (node.getChildren().size() > 0)
                listBooks(node);
        }
    }

    public Book searchBook(String name) { // searches for the object of entered name in tree nodes
        Node node = findNode(root, name, 'b');
        if (node == null)
            return null;
        else return (Book) node.obj;
    }

    public Category searchCategory(String name) {
        Node node = findNode(root, name, 'c');
        if (node == null)
            return null;
        else return (Category) node.obj;
    }

    public void listCategoryBooks(String categoryName) {
        Node node = findNode(root, categoryName, 'c');
        if (node == null) {
            System.out.println("Category not found.");
            return;
        }
        listBooks(node);
    }

}
