import java.util.ArrayList;
import java.util.Objects;

public class ReadIn {
    private final String bookId, memberId, date;
    private String bookType, memberType;
    private boolean checkBookType, checkMemberTYpe = false;
    private static final ArrayList<ArrayList<String>> readList = new ArrayList<>();

    public ReadIn(String bookId, String memberId, String date){
        this.bookId = bookId;
        this.memberId = memberId;
        this.date = date;
    }

    public static ArrayList<ArrayList<String>> getReadList() {
        return readList;
    }

    public void toReadIn(){
        for (ArrayList<String> book : Book.getBookList()){
            if (Objects.equals(book.get(1), bookId)){
                checkBookType = true;
                bookType = book.get(0);
                break;
            }
        }
        for (ArrayList<String> member : Member.getMemberList()){
            if (Objects.equals(member.get(1), memberId)){
                checkMemberTYpe = true;
                memberType = member.get(0);
                break;
            }
        }
        if (bookType!= null && memberType != null &&bookType.equals("Handwritten")
                && memberType.equals("Student")){
            Main.write_output("Students can not read handwritten books!\n");
            return;
        }

        if (checkBookType && checkMemberTYpe){
            for (ArrayList<String> book : Book.getBookList()) {
                if (Objects.equals(book.get(1), bookId)) {
                    Book.getBookList().remove(book);
                    break;
                }
            }
            ArrayList<String> read = new ArrayList<>();
            read.add(bookId);
            read.add(memberId);
            read.add(date);
            read.add(bookType);
            readList.add(read);
            Main.write_output(String.format("The book [%s] was read in library by member [%s] at %s\n",
                    bookId, memberId, date));
        }
        else {
            Main.write_output("You can not read this book!\n");
        }
    }
}
