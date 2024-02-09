import java.io.*;
import java.util.*;

public class Main {
    public static boolean stop = false;
    private static boolean z_report_check = true;

    /**
     * it is used to write wanted data into output file.
     * @param write_data is written into output file.
     */
    public static void write_output(String write_data){
        try {
            File file = new File("output.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(write_data);
            fr.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[]args) throws IOException {
        ArrayList<ArrayList<String>> input_list = new ArrayList<>();
        BufferedReader br_file = new BufferedReader(new FileReader("input.txt"));
        String line;

        while ((line = br_file.readLine()) != null) {
            String[] values = line.split("\\s+");
            ArrayList<String> commands = new ArrayList<>(Arrays.asList(values));
            input_list.add(commands);
        }
        Time time = new Time();
        SmartDevices device;

        if (Objects.equals(input_list.get(0).get(0), "SetInitialTime") || input_list.get(0).get(0).isEmpty()){
            for (int i = 0; i < input_list.size(); i++){
                if (!stop){
                    if (input_list.get(i).get(0).isEmpty()) {
                        continue;
                    } else {
                        write_output("COMMAND: " + String.join("\t", input_list.get(i)) + "\n");
                    }

                    String commandType = input_list.get(i).get(0);
                    if (commandType.equals("SetInitialTime")) {
                        time.SetInitialTime(input_list.get(i));
                    } else if (commandType.equals("Add")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.add(input_list.get(i));
                    } else if (commandType.equals("Switch")) {
                        device = SmartDevices.checkDeviceType2(input_list.get(i));
                        assert device != null;
                        device.switchOnOff(input_list.get(i));
                    } else if (commandType.equals("PlugIn")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.plugIn(input_list.get(i));
                    } else if (commandType.equals("PlugOut")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.plugOut(input_list.get(i));
                    } else if (commandType.equals("SetTime")) {
                        time.SetTime(input_list.get(i));
                    } else if (commandType.equals("SkipMinutes")) {
                        time.SkipMinutes(input_list.get(i));
                    } else if (commandType.equals("SetKelvin")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.SetKelvin(input_list.get(i));
                    } else if (commandType.equals("SetBrightness")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.SetBrightness(input_list.get(i));
                    } else if (commandType.equals("SetWhite")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.SetWhite(input_list.get(i));
                    } else if (commandType.equals("SetColorCode")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.SetColorCode(input_list.get(i));
                    } else if (commandType.equals("SetColor")) {
                        device = SmartDevices.checkDeviceType(input_list.get(i));
                        assert device != null;
                        device.SetColor(input_list.get(i));
                    } else if (commandType.equals("Remove")) {
                        SmartDevices.remove(input_list.get(i));
                    } else if (commandType.equals("ZReport")) {
                        SmartDevices.z_report();
                        z_report_check = false;
                    } else if (commandType.equals("ChangeName")) {

                    }else if (commandType.equals("SetSwitchTime")) {

                    } else if (commandType.equals("Nop")) {

                    } else {
                        write_output("ERROR: Erroneous command!\n");
                    }
                }
            }
            if (z_report_check && !stop){
                write_output("ZReport:\n");
                SmartDevices.z_report();
            }
        }else {
            write_output("COMMAND: " + String.join("\t", input_list.get(0)) + "\n");
            write_output("ERROR: First command must be set initial time! " +
                    "Program is going to terminate!");
        }
    }
}
