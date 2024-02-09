import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Extend{
    private final String bookId, memberId, current;

    public Extend(String bookId, String memberId, String current) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.current = current;
    }

    public void toExtend(){
        for (ArrayList<String> borrow : Borrow.getBorrowedList()) {
            if (borrow.size() == 5){
                Main.write_output("You cannot extend the deadline!\n");
                return;
            }
            if (borrow.get(0).equals(bookId) && borrow.get(1).equals(memberId)){
                String memberType = borrow.get(3);
                borrow.set(2, current);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime deadline;
                if (memberType.equals("Student")){
                    deadline = LocalDate.parse(current, formatter).atStartOfDay().plusDays(7);
                }else{
                    deadline = LocalDate.parse(current, formatter).atStartOfDay().plusDays(14);
                }
                borrow.add("Extended");
                Main.write_output(String.format("The deadline of book [%s] was extended by member" +
                        " [%s] at %s\n", bookId, memberId, current));
                Main.write_output(String.format("New deadline of book [%s] is %tF\n", bookId, deadline));
            }
        }
    }
}
