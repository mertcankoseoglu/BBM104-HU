import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Time{
    private static LocalDateTime currentTime;
    private static boolean first_call = true;

    public static LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void SetInitialTime(ArrayList<String> command) {
        if (first_call){
            try {
                String timeString = command.get(1);
                if (command.size() == 2){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    currentTime = LocalDateTime.parse(timeString, formatter);
                    Main.write_output("SUCCESS: Time has been set to " + currentTime + "\n");
                } else {
                    Main.write_output("ERROR: Erroneous command! \n");
                }
            } catch (Exception e){
                Main.write_output("ERROR: Format of the initial date is wrong! " +
                        "Program is going to terminate!\n");
                Main.stop = true;
            }
            first_call = false;
        }else {
            Main.write_output("ERROR: Erroneous command! \n");
        }
    }

    public void SetTime(ArrayList<String> command) {
        try {
            String timeString = command.get(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            LocalDateTime newTime = LocalDateTime.parse(timeString, formatter);
            if (command.size() == 2){
                if (newTime.isAfter(currentTime)) {
                    currentTime = newTime;
                } else if (newTime.isBefore(currentTime)) {
                    Main.write_output("ERROR: Time cannot be reversed!\n");
                } else {
                    Main.write_output("ERROR: There is nothing to change!\n");
                }
            }else {
                Main.write_output("ERROR: Erroneous command!\n");
            }
        }catch (Exception e){
            Main.write_output("ERROR: Time format is not correct!\n");
        }
    }

    public  void SkipMinutes(ArrayList<String> command) {
        try {
            String p_minute = command.get(1);
            try {
                int minute = Integer.parseInt(p_minute);
                LocalDateTime newTime = currentTime.plusMinutes(minute);
                if (command.size() == 2) {
                    if (newTime.isAfter(currentTime)){
                        currentTime = newTime;
                    } else if (newTime.isBefore(currentTime)) {
                        Main.write_output("ERROR: Time cannot be reversed!\n");
                    } else {
                        Main.write_output("ERROR: There is nothing to skip!\n");
                    }
                }else {
                    Main.write_output("ERROR: Erroneous command!\n");
                }
            } catch (Exception e){
                Main.write_output("ERROR: Erroneous command!\n");
            }
        }catch (IndexOutOfBoundsException e){
            Main.write_output("ERROR: Time format is not correct!\n");
        }
    }
}
