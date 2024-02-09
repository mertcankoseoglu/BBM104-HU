import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * this class provides methods and information for color lamp devices.
 */
public class SmartLampColor extends SmartDevices{
    private static SmartLampColor instance;
    public static ArrayList<List<String>> clamp_list;

    /**
     * clamp_list is saved.
     */
    public SmartLampColor() {
        clamp_list = new ArrayList<>();
    }

    /**
     *
     * @return the new constructor only one time.
     */
    public static SmartLampColor getInstance() {
        if (instance == null) {
            instance = new SmartLampColor();
        }
        return instance;
    }

    /**
     * it checks if the given format is correct and adds the device into clamp_list.
     * @param clamp_info gives the data for lamp which will be added.
     */
    public void add(List<String> clamp_info) {
        if (clamp_info.size() == 3 || clamp_info.size() == 4 || clamp_info.size() == 6){
            String first_time = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                first_time = Time.getCurrentTime().format(formatter);
                if (clamp_list != null &&!clamp_list.isEmpty()) {
                    for (List<String> lamp : clamp_list) {
                        if (lamp.get(0).equals(clamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartPlug.plug_list != null && !SmartPlug.plug_list.isEmpty()){
                    for (List<String> plug : SmartPlug.plug_list) {
                        if (plug.get(0).equals(clamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartCamera.camera_list != null && !SmartCamera.camera_list.isEmpty()){
                    for (List<String> cam : SmartCamera.camera_list) {
                        if (cam.get(0).equals(clamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartLamp.lamp_list != null && !SmartLamp.lamp_list.isEmpty()) {
                    for (List<String> lamp : SmartLamp.lamp_list) {
                        if (lamp.get(0).equals(clamp_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (!clamp_info.get(3).equals("On")){
                    if (!clamp_info.get(3).equals("Off")) {
                        Main.write_output("ERROR: Erroneous command! \n");
                        return;
                    }
                }
                try {
                    int kelvin = Integer.parseInt(clamp_info.get(4));
                    if (kelvin < 2000 || kelvin > 6500){
                        Main.write_output("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        return;
                    }
                } catch (NumberFormatException e){
                        int color = Integer.parseInt(clamp_info.get(4).substring(2), 16);
                        if (color < 0 || color > 0xFFFFFF){
                            Main.write_output("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                            return;
                        }
                }
                int brightness = Integer.parseInt(clamp_info.get(5));
                if (brightness < 0 || brightness > 100){
                    Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                    return;
                }
            } catch (IndexOutOfBoundsException e){
                if (clamp_info.size() == 3){
                    clamp_info.add("Off");
                    clamp_info.add("4000");
                    clamp_info.add("100");
                } else if (clamp_info.size() == 4) {
                    clamp_info.add("4000");
                    clamp_info.add("100");
                }
            } catch (NumberFormatException e){
                Main.write_output("ERROR: Erroneous command!\n");
                return;
            }
            clamp_info.add(first_time);
            clamp_list.add(clamp_info.subList(2, clamp_info.size()));
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     *
     * @param clamp_switch is data about switching device from input file.
     */
    public void switchOnOff(List<String> clamp_switch) {
        if (clamp_switch.size() == 3){
            for (List<String> clamp : clamp_list) {
                if (clamp.get(0).equals(clamp_switch.get(1))) {
                    if (clamp.get(1).equals(clamp_switch.get(2))) {
                        Main.write_output("ERROR: This device is already switched "
                                + clamp_switch.get(2) + "!\n");
                        return;
                    }
                    else {
                        if (clamp_switch.get(2).equals("On") || clamp_switch.get(2).equals("Off")){
                            clamp.set(1, clamp_switch.get(2));
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
            for (List<String> clamp : clamp_list){
                if (clamp.get(0).equals(set_kelvin.get(1))){
                    try {
                        int kelvin = Integer.parseInt(set_kelvin.get(2));
                        if (kelvin < 2000 || kelvin > 6500){
                            Main.write_output("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                            return;
                        } else {
                            clamp.set(2, set_kelvin.get(2));
                        }
                    } catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                }
            }
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * it is used to set brightness.
     * @param set_brightness is the line in input file which is about set brightness.
     */
    public void SetBrightness(List<String> set_brightness){
        if (set_brightness.size() == 3){
            for (List<String> clamp : clamp_list){
                if (clamp.get(0).equals(set_brightness.get(1))){
                    try {
                        int brightness = Integer.parseInt(set_brightness.get(2));
                        if (brightness < 0 || brightness > 100){
                            Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                            return;
                        } else {
                            clamp.set(3, set_brightness.get(2));
                        }
                    } catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                }
            }
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * it is used to change both kelvin(color) and brightness.
     * @param set_white is the line in input file which is about set white.
     */
    public void SetWhite(List<String> set_white){
        if (set_white.size() == 4){
            for (List<String> clamp : clamp_list){
                if (clamp.get(0).equals(set_white.get(1))){
                    try {
                        int kelvin = Integer.parseInt(set_white.get(2));
                        if (kelvin < 2000 || kelvin > 6500){
                            Main.write_output("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                            return;
                        } else {
                            clamp.set(2, set_white.get(2));
                        }
                        int brightness = Integer.parseInt(set_white.get(3));
                        if (brightness < 0 || brightness > 100){
                            Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                            return;
                        } else {
                            clamp.set(3, set_white.get(3));
                        }
                    } catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                }
            }
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * it changes the color of lamp.
     * @param color_code is the line in input file which is about color code.
     */
    public void SetColorCode(List<String> color_code){
        if (color_code.size() == 3){
            for (List<String> clamp : clamp_list){
                if (clamp.get(0).equals(color_code.get(1))){
                    try {
                        int color = Integer.parseInt(color_code.get(2).substring(2), 16);
                        if (color < 0 || color > 0xFFFFFF){
                            Main.write_output("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                        } else {
                            clamp.set(2, color_code.get(2));
                        }
                    }catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart color lamp!\n");
        }
        else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }

    /**
     * it changes the color and brightness.
     * @param set_color is the line in input file which is about set color.
     */
    public void SetColor(List<String> set_color){
        if (set_color.size() == 4){
            for (List<String> clamp : clamp_list){
                if (clamp.get(0).equals(set_color.get(1))){
                    try {
                        int color = Integer.parseInt(set_color.get(2).substring(2), 16);
                        if (color < 0 || color > 0xFFFFFF){
                            Main.write_output("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                            return;
                        } else {
                            clamp.set(2, set_color.get(2));
                        }
                        int brightness = Integer.parseInt(set_color.get(3));
                        if (brightness < 0 || brightness > 100){
                            Main.write_output("ERROR: Brightness must be in range of 0%-100%!\n");
                            return;
                        } else {
                            clamp.set(3, set_color.get(3));
                        }
                    }catch (NumberFormatException e){
                        Main.write_output("ERROR: Erroneous command!\n");
                        return;
                    }
                    return;
                }
            }
            Main.write_output("ERROR: This device is not a smart color lamp!\n");
        }
        else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }
}
