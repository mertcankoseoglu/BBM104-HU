import java.util.ArrayList;
import java.util.Objects;

public class Borrow {
    private final String bookId, memberId, date;
    private String memberType;
    private boolean checkBookType, checkMemberTYpe, checkMaxLimit = false;
    private static final ArrayList<ArrayList<String>> borrowedList = new ArrayList<>();

    public Borrow(String bookId, String memberId, String date){
        this.bookId = bookId;
        this.memberId = memberId;
        this.date = date;
    }

    public static ArrayList<ArrayList<String>> getBorrowedList() {
        return borrowedList;
    }

    public void toBorrow() {
        int maxBooks;
        for (ArrayList<String> book : Book.getBookList()) {
            if (Objects.equals(book.get(1), bookId) && Objects.equals(book.get(0), "Printed")) {
                checkBookType = true;
                break;
            }
        }

        for (ArrayList<String> member : Member.getMemberList()) {
            if (Objects.equals(member.get(1), memberId)) {
                checkMemberTYpe = true;
                if (Objects.equals(member.get(0), "Academic")) {
                    maxBooks = 4;
                    memberType = "Academic";
                } else {
                    maxBooks = 2;
                    memberType = "Student";
                }
                int borrowedBooks = 0;

                for (ArrayList<String> borrowed : borrowedList) {
                    if (Objects.equals(borrowed.get(1), memberId) && Objects.equals(borrowed.get(2), date)) {
                        borrowedBooks++;
                    }
                }
                if (borrowedBooks >= maxBooks) {
                    checkMaxLimit = true;
                }
            }
        }

        if (!checkBookType || !checkMemberTYpe) {
            Main.write_output("You can not borrow this book!\n");
        }
        else if (checkMaxLimit) {
            Main.write_output("You have exceeded the borrowing limit!\n");
        }
        else {
            for (ArrayList<String> book : Book.getBookList()) {
                if (Objects.equals(book.get(1), bookId)) {
                    Book.getBookList().remove(book);
                    break;
                }
            }
            ArrayList<String> borrowed = new ArrayList<>();
            borrowed.add(bookId);
            borrowed.add(memberId);
            borrowed.add(date);
            borrowed.add(memberType);
            borrowedList.add(borrowed);
            Main.write_output(String.format("The book [%s] was borrowed by member [%s] at %s\n",
                    bookId, memberId, date));
        }
    }
}
