import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * this class provides methods for plug devices.
 */
public class SmartPlug extends SmartDevices{

    private static SmartPlug instance;
    public static ArrayList<List<String>> plug_list;
    private static final double VOLTAGE = 220;

    /**
     * plug_list is protected.
     */
    public SmartPlug() {
        plug_list = new ArrayList<>();
    }

    /**
     * it creates object just one time to safe data.
     * @return new SmartPlug1()
     */
    public static SmartPlug getInstance() {
        if (instance == null) {
            instance = new SmartPlug();
        }
        return instance;
    }

    /**
     * it adds plug type devices into plug_list if device has appropriate data and unique name.
     * @param plug_info data about adding device from input file
     */
    public void add(List<String> plug_info) {
        if (plug_info.size() < 6) {
            String first_time = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                first_time = Time.getCurrentTime().format(formatter);
                if (plug_list != null && !plug_list.isEmpty()) {
                    for (List<String> plug : plug_list) {
                        if (plug.get(0).equals(plug_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartCamera.camera_list != null && !SmartCamera.camera_list.isEmpty()) {
                    for (List<String> cam : SmartCamera.camera_list) {
                        if (cam.get(0).equals(plug_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartLamp.lamp_list != null && !SmartLamp.lamp_list.isEmpty()) {
                    for (List<String> lamp : SmartLamp.lamp_list) {
                        if (lamp.get(0).equals(plug_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartLampColor.clamp_list != null && !SmartLampColor.clamp_list.isEmpty()) {
                    for (List<String> clamp : SmartLampColor.clamp_list) {
                        if (clamp.get(0).equals(plug_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (!plug_info.get(3).equals("On")) {
                    if (!plug_info.get(3).equals("Off")) {
                        Main.write_output("ERROR: Erroneous command! \n");
                        return;
                    }
                }
                double ampere_val = Double.parseDouble(plug_info.get(4));
                if (ampere_val <= 0) {
                    Main.write_output("ERROR: Ampere value must be a positive number!\n");
                    return;
                }
            } catch (IndexOutOfBoundsException e) {
                if (plug_info.size() == 3) {
                    plug_info.add("Off");
                    plug_info.add("0.0");
                } else if (plug_info.size() == 4) {
                    plug_info.add("0.0");
                } else {
                    Main.write_output("ERROR: Erroneous command! \n");
                    return;
                }
            } catch (NumberFormatException e) {
                Main.write_output("ERROR: Erroneous command! \n");
                return;
            }
            if (plug_info.get(4).equals("0.0")) {
                plug_info.add("out");
            } else {
                plug_info.add("in");
            }
            plug_info.add(first_time);
            plug_info.add("0.0");
            plug_list.add(plug_info.subList(2, plug_info.size()));
        } else {
            Main.write_output("ERROR: Erroneous command! \n");
        }
    }

    /**
     * for switching the plug status.
     * @param plug_switch switch information from input file
     */
    public void switchOnOff(List<String> plug_switch) {
        if (plug_switch.size() == 3){
            for (List<String> plug : plug_list) {
                if (plug.get(0).equals(plug_switch.get(1))) {
                    if (plug.get(1).equals(plug_switch.get(2))) {
                        Main.write_output("ERROR: This device is already switched "
                                + plug_switch.get(2) + "!\n");
                        return;
                    }
                    else {
                        if (plug_switch.get(2).equals("On") || plug_switch.get(2).equals("Off")){
                            plug.set(1, plug_switch.get(2));
                            double ampere = Double.parseDouble(plug.get(2));
                            if (plug.get(1).equals("Off")){
                                try {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    LocalDateTime first_time = LocalDateTime.parse(plug.get(4), formatter);
                                    Duration duration = Duration.between(first_time, Time.getCurrentTime());
                                    double double_duration = duration.getSeconds();
                                    double power = VOLTAGE * ampere;
                                    double energy = Double.parseDouble(plug.get(5));
                                    energy += power * double_duration;
                                    String energy_consume = String.valueOf(energy);
                                    plug.set(5, energy_consume);
                                } catch (Exception e){
                                    Main.write_output("ERROR: Erroneous command!\n");
                                }
                            } else {
                                Main.write_output("ERROR: Erroneous command!");
                            }
                        }
                    }
                    return;
                }
            }
            Main.write_output("ERROR: There is not such a device!\n");
        } else {
            Main.write_output("ERROR: Erroneous command!");
        }
    }

    /**
     * if plug is out, plug will be in with given ampere.
     * @param plug_in gives the name and ampere value of plug from input file.
     */
    public void plugIn(List<String> plug_in){
        if(plug_in.size() == 3){
            for (List<String> plug : plug_list) {
                if (plug.get(0).equals(plug_in.get(1))){
                    if (plug.get(3).equals("in")){
                        Main.write_output("ERROR: There is already an item plugged" +
                                " in to that plug!\n");
                    }
                    else if (plug.get(3).equals("out")) {
                        try {
                            double ampere = Double.parseDouble(plug_in.get(2));
                            if (ampere <= 0){
                                Main.write_output("ERROR: Ampere value must be" +
                                        " a positive number!\n");
                            }
                            else{
                                plug.set(3, "in");
                                plug.set(2, plug_in.get(2));
                            }
                        } catch (NumberFormatException e){
                            Main.write_output("ERROR: Erroneous command!\n");
                        }
                    }else {
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart plug!\n");
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     *
     * @param plug_out second index gives the name of plug.
     */
    public void plugOut(List<String> plug_out){
        if (plug_out.size() == 2){
            for (List<String> plug : plug_list) {
                if (plug.get(0).equals(plug_out.get(1))){
                    if (plug.get(3).equals("out")){
                        Main.write_output("ERROR: This plug has no item to" +
                                " plug out from that plug!\n");
                    }
                    else if (plug.get(3).equals("in")) {
                        plug.set(3, "out");
                        plug.set(2, "0.0");
                    }else {
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart plug!\n");
        }else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }
}
