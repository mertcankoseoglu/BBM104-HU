import java.util.ArrayList;

public class GetTheHistory {
    private int countStd, countAcd, countP, countH;

    public void printHistory(){
        Main.write_output("History of library:\n\n");
        for (ArrayList<String> member : Member.getMemberList()){
            if (member.get(0).equals("Student")){
                countStd++;
            }else{
                countAcd++;
            }
        }
        Main.write_output(String.format("Number of students: %d\n", countStd));
        for (ArrayList<String> member : Member.getMemberList()){
            if (member.get(0).equals("Student")) {
                Main.write_output(String.format("Student [id: %s]\n", member.get(1)));
            }
        }
        Main.write_output("\n");

        Main.write_output(String.format("Number of academics: %d\n", countAcd));
        for (ArrayList<String> member : Member.getMemberList()){
            if (member.get(0).equals("Academic")) {
                Main.write_output(String.format("Academic [id: %s]\n", member.get(1)));
            }
        }
        Main.write_output("\n");

        for (ArrayList<String> book : Book.getBookListStatic()){
            if (book.get(0).equals("Printed")){
                countP++;
            }else{
                countH++;
            }
        }
        Main.write_output(String.format("Number of printed books: %d\n", countP));
        for (ArrayList<String> book : Book.getBookListStatic()){
            if (book.get(0).equals("Printed")) {
                Main.write_output(String.format("Printed [id: %s]\n", book.get(1)));
            }
        }
        Main.write_output("\n");

        Main.write_output(String.format("Number of handwritten books: %d\n", countH));
        for (ArrayList<String> book : Book.getBookListStatic()){
            if (book.get(0).equals("Handwritten")) {
                Main.write_output(String.format("Handwritten [id: %s]\n", book.get(1)));
            }
        }
        Main.write_output("\n");

        Main.write_output("Number of borrowed books: " + Borrow.getBorrowedList().size() + "\n");
        for (ArrayList<String> borrow : Borrow.getBorrowedList()){
            Main.write_output(String.format("The book [%s] was borrowed by member [%s] at %s\n",
                    borrow.get(0), borrow.get(1), borrow.get(2)));
        }
        Main.write_output("\n");

        Main.write_output("Number of books read in library: " + ReadIn.getReadList().size() + "\n");
        for (ArrayList<String> read : ReadIn.getReadList()){
            Main.write_output(String.format("The book [%s] was read in library by member [%s] at %s\n",
                    read.get(0), read.get(1), read.get(2)));
        }
    }
}
