import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void write_output(String writeData){
        try {
            File file = new File("output.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(writeData);
            fr.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<String>> inputList = new ArrayList<>();
        BufferedReader brFile = new BufferedReader(new FileReader(args[0]));
        String line;

        while ((line = brFile.readLine()) != null) {
            String[] values = line.split("\\s+");
            ArrayList<String> commands = new ArrayList<>(Arrays.asList(values));
            inputList.add(commands);
        }

        for (ArrayList<String> commands : inputList) {
            String commandType = commands.get(0);
            if (commandType.equals("addBook")) {
                new Book(commands.get(1));
            } else if (commandType.equals("addMember")) {
                new Member(commands.get(1));
            } else if (commandType.equals("borrowBook")) {
                Borrow borrow = new Borrow(commands.get(1), commands.get(2), commands.get(3));
                borrow.toBorrow();
            } else if (commandType.equals("returnBook")) {
                ReturnBook returnBook = new ReturnBook(commands.get(1), commands.get(2),commands.get(3));
                returnBook.toReturn();
            } else if (commandType.equals("extendBook")) {
                Extend extend = new Extend(commands.get(1), commands.get(2), commands.get(3));
                extend.toExtend();
            } else if (commandType.equals("readInLibrary")) {
                ReadIn readIn = new ReadIn(commands.get(1), commands.get(2), commands.get(3));
                readIn.toReadIn();
            } else if (commandType.equals("getTheHistory")) {
                GetTheHistory getTheHistory = new GetTheHistory();
                getTheHistory.printHistory();
            }
        }
    }
}
