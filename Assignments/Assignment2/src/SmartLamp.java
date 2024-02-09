import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SmartLamp extends SmartDevices{
    private static SmartLamp instance;
    public static ArrayList<List<String>> lamp_list;

    /**
     * lamp_list(lamp devices list) in constructor.
     */
    public SmartLamp() {
        lamp_list = new ArrayList<>();
    }

    /**
     * call the constructor one time.
     * @return SmartLamp()
     */
    public static SmartLamp getInstance() {
        if (instance == null) {
            instance = new SmartLamp();
        }
        return instance;
    }

    /**
     * adding lamp into lamp_list if it is correct input format and unique name.
     * @param lamp_info is the line in input file which is about adding lamp.
     */
    public void add(List<String> lamp_info) {
        if (lamp_info.size() == 3 || lamp_info.size() == 4 || lamp_info.size() == 6){
            String first_time = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                first_time = Time.getCurrentTime().format(formatter);
                if (lamp_list != null &&!lamp_list.isEmpty()) {
                    for (List<String> lamp : lamp_list) {
                        if (lamp.get(0).equals(lamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartPlug.plug_list != null && !SmartPlug.plug_list.isEmpty()){
                    for (List<String> plug : SmartPlug.plug_list) {
                        if (plug.get(0).equals(lamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartCamera.camera_list != null && !SmartCamera.camera_list.isEmpty()){
                    for (List<String> cam : SmartCamera.camera_list) {
                        if (cam.get(0).equals(lamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartLampColor.clamp_list != null && !SmartLampColor.clamp_list.isEmpty()) {
                    for (List<String> clamp : SmartLampColor.clamp_list) {
                        if (clamp.get(0).equals(lamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (!lamp_info.get(3).equals("On")){
                    if (!lamp_info.get(3).equals("Off")) {
                        Main.write_output("ERROR: Erroneous command! \n");
                        return;
                    }
                }
                int kelvin = Integer.parseInt(lamp_info.get(4));
                if (kelvin < 2000 || kelvin > 6500){
                    Main.write_output("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                    return;
                }
                int brightness = Integer.parseInt(lamp_info.get(5));
                if (brightness < 0 || brightness > 100){
                    Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                    return;
                }
            } catch (IndexOutOfBoundsException e){
                if (lamp_info.size() == 3){
                    lamp_info.add("Off");
                    lamp_info.add("4000");
                    lamp_info.add("100");
                } else if (lamp_info.size() == 4) {
                    lamp_info.add("4000");
                    lamp_info.add("100");
                }
            } catch (NumberFormatException e){
                Main.write_output("ERROR: Erroneous command!\n");
                return;
            }
            lamp_info.add(first_time);
            lamp_list.add(lamp_info.subList(2, lamp_info.size()));
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * for switching the lamp status.
     * @param lamp_switch switch information from input file
     */
    public void switchOnOff(List<String> lamp_switch) {
        if (lamp_switch.size() == 3){
            for (List<String> lamp : lamp_list) {
                if (lamp.get(0).equals(lamp_switch.get(1))) {
                    if (lamp.get(1).equals(lamp_switch.get(2))) {
                        Main.write_output("ERROR: This device is already switched "
                                + lamp_switch.get(2) + "!\n");
                    }
                    else {
                        if (lamp_switch.get(2).equals("On") || lamp_switch.get(2).equals("Off")){
                            lamp.set(1, lamp_switch.get(2));
                        }else {
                            Main.write_output("ERROR: Erroneous command!");
                        }
                    }
                }
            }
        } else {
            Main.write_output("ERROR: Erroneous command!");
        }
    }

    /**
     * it is used to change the kelvin value with given in input file.
     * @param set_kelvin is the line in input file which is about set kelvin.
     */
    public void SetKelvin(List<String> set_kelvin){
        if (set_kelvin.size() == 3){
            for (List<String> lamp : lamp_list){
                if (lamp.get(0).equals(set_kelvin.get(1))){
                    try {
                        int kelvin = Integer.parseInt(set_kelvin.get(2));
                        if (kelvin < 2000 || kelvin > 6500){
                            Main.write_output("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        } else {
                            lamp.set(2, set_kelvin.get(2));
                        }
                    } catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart lamp!\n");
        }
        else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * it is used to set brightness.
     * @param set_brightness is the line in input file which is about set brightness.
     */
    public void SetBrightness(List<String> set_brightness){
        if (set_brightness.size() == 3){
            for (List<String> lamp : lamp_list){
                if (lamp.get(0).equals(set_brightness.get(1))){
                    try {
                        int brightness = Integer.parseInt(set_brightness.get(2));
                        if (brightness < 0 || brightness > 100){
                            Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                        } else {
                            lamp.set(3, set_brightness.get(2));
                        }
                    } catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart lamp!\n");
        }
        else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * it is used to change both kelvin and brightness.
     * @param set_white is the line in input file which is about set white.
     */
    public void SetWhite(List<String> set_white){
        if (set_white.size() == 4){
            for (List<String> lamp : lamp_list){
                if (lamp.get(0).equals(set_white.get(1))){
                    try {
                        int kelvin = Integer.parseInt(set_white.get(2));
                        if (kelvin < 2000 || kelvin > 6500){
                            Main.write_output("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                            return;
                        } else {
                            lamp.set(2, set_white.get(2));
                        }
                        int brightness = Integer.parseInt(set_white.get(3));
                        if (brightness < 0 || brightness > 100){
                            Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                            return;
                        } else {
                            lamp.set(3, set_white.get(3));
                        }
                    } catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart lamp!\n");
        }
        else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }
}
