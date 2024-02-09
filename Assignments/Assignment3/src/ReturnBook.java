import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReturnBook {
    private final String bookId, memberId;
    private final LocalDateTime returnDate;

    public ReturnBook(String bookId, String memberId, String returnDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.returnDate = LocalDate.parse(returnDate, formatter).atStartOfDay();
    }

    public void toReturn(){
        long fee;
        for (ArrayList<String> borrow : Borrow.getBorrowedList()) {
            if (borrow.get(0).equals(bookId) && borrow.get(1).equals(memberId)){
                String memberType = borrow.get(3);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime deadline;
                if(memberType.equals("Student")){
                    deadline = LocalDate.parse(borrow.get(2), formatter).atStartOfDay().plusDays(7);
                }
                else {
                    deadline = LocalDate.parse(borrow.get(2), formatter).atStartOfDay().plusDays(14);
                }
                Borrow.getBorrowedList().remove(borrow);
                ArrayList<String> book = new ArrayList<>();
                book.add("Printed");
                book.add(bookId);
                Book.getBookList().add(book);

                if (returnDate.isBefore(deadline)){
                    fee = 0;
                }
                else{
                    fee = ChronoUnit.DAYS.between(deadline, returnDate);
                    Main.write_output("You must pay a penalty!\n");
                }
                Main.write_output(String.format("The book [%s] was returned by member [%s] at " +
                        "%tF Fee: %d\n", bookId, memberId, returnDate, fee));
                break;
            }
        }
        for (ArrayList<String> read : ReadIn.getReadList()){
            if (read.get(0).equals(bookId) && read.get(1).equals(memberId)){
                ReadIn.getReadList().remove(read);
                ArrayList<String> book = new ArrayList<>();
                book.add(read.get(3));
                book.add(bookId);
                Book.getBookList().add(book);
                fee = 0;
                Main.write_output(String.format("The book [%s] was returned by member [%s] at " +
                        "%tF Fee: %d\n", bookId, memberId, returnDate, fee));
                break;
            }
        }
    }
}
