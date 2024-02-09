import java.util.ArrayList;
import java.util.Objects;

public class Book {
    private static int bookCount = 0;
    private static final ArrayList<ArrayList<String>> bookList = new ArrayList<>();
    private static final ArrayList<ArrayList<String>> bookListStatic = new ArrayList<>();

    public Book(String bookType) {
        bookCount++;
        String bookId = Integer.toString(bookCount);
        String bookTypeStr;
        if (Objects.equals(bookType, "P")){
            bookTypeStr = "Printed";
        } else {
            bookTypeStr = "Handwritten";
        }
        ArrayList<String> book = new ArrayList<>();
        book.add(bookTypeStr);
        book.add(bookId);
        bookList.add(book);
        bookListStatic.add(book);
        Main.write_output("Created new book: " + bookTypeStr + " [id: " + bookId + "]\n");
    }

    public static ArrayList<ArrayList<String>> getBookList() {
        return bookList;
    }

    public static ArrayList<ArrayList<String>> getBookListStatic() {
        return bookListStatic;
    }
}
