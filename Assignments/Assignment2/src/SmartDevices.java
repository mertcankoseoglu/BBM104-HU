import java.util.ArrayList;
import java.util.List;

public class SmartDevices {

    public void add(List<String> strings) {}

    public void switchOnOff(List<String> strings) {}

    public void plugIn(List<String> strings){}

    public void plugOut(List<String> strings){}

    public void SetKelvin(List<String> set_kelvin){}

    public void SetBrightness(List<String> set_brightness){}

    public void SetWhite(List<String> set_white){}

    public void SetColorCode(List<String> color_code){}

    public void SetColor(List<String> set_code){}

    /**
     * it is used to call the appropriate class for commands in input file.
     * @param deviceList input line list for checking the command and calling the class.
     * @return the new object
     */
    public static SmartDevices checkDeviceType(ArrayList<String> deviceList){
        try{
            if(deviceList.get(1).equals("SmartPlug") || deviceList.get(0).equals("PlugIn")
                    || deviceList.get(0).equals("PlugOut")){
                return SmartPlug.getInstance();
            }
            else if (deviceList.get(1).equals("SmartCamera"))
            {
                return SmartCamera.getInstance();
            }
            else if (deviceList.get(1).equals("SmartLamp")) {
                return SmartLamp.getInstance();
            }
            else if (deviceList.get(1).equals("SmartColorLamp") || deviceList.get(0).equals("SetColorCode")
                    || deviceList.get(0).equals("SetColor")) {
                return SmartLampColor.getInstance();
            }
            else if (deviceList.get(0).equals("SetKelvin") || deviceList.get(0).equals("SetBrightness")
                    || deviceList.get(0).equals("SetWhite")) {
                if (SmartLampColor.clamp_list != null && SmartLampColor.clamp_list.stream().flatMap(List::stream).
                        anyMatch(element -> element.equals(deviceList.get(1)))) {
                    return SmartLampColor.getInstance();
                } else {
                    return SmartLamp.getInstance();
                }
            }else{
                return SmartPlug.getInstance();
            }
        } catch (IndexOutOfBoundsException e){
            Main.write_output("ERROR: Erroneous command!\n");
        }
        return null;
    }

    /**
     *  calling the correct device class for switching
     * @param deviceList input line list
     * @return
     */
    public static SmartDevices checkDeviceType2(ArrayList<String> deviceList){
        try{
             if(SmartPlug.plug_list != null && SmartPlug.plug_list.stream().flatMap(List::stream).
                    anyMatch(element -> element.equals(deviceList.get(1)))){
                return SmartPlug.getInstance();
            }
            else if (SmartCamera.camera_list != null && SmartCamera.camera_list.stream().flatMap(List::stream).
                    anyMatch(element -> element.equals(deviceList.get(1))))
            {
                return SmartCamera.getInstance();
            }
            else if (SmartLamp.lamp_list != null && SmartLamp.lamp_list.stream().flatMap(List::stream).
                    anyMatch(element -> element.equals(deviceList.get(1)))) {
                return SmartLamp.getInstance();
            }
            else if (SmartLampColor.clamp_list != null && SmartLampColor.clamp_list.stream().flatMap(List::stream).
                     anyMatch(element -> element.equals(deviceList.get(1)))) {
                return SmartLamp.getInstance();
            }
            else return SmartPlug.getInstance();
        }
        catch (IndexOutOfBoundsException e){
            Main.write_output("ERROR: Erroneous command!\n");
        }
        return null;
    }

    /**
     * it writes the information about devices in output file.
     */
    public static void z_report(){
        Main.write_output("Time is:\t" + Time.getCurrentTime() + "\n");
        if (SmartPlug.plug_list != null){
            for (List<String> plug: SmartPlug.plug_list){
                Main.write_output("Smart Plug " + plug.get(0) + " is " + plug.get(1)
                        + " and consumed " + plug.get(5) + "W so far (excluding current device)," +
                        " and its time to switch its status is null.\n");
            }
        }
        if (SmartCamera.camera_list != null){
            for (List<String> cam : SmartCamera.camera_list){
                Main.write_output("Smart Camera " + cam.get(0) + " is " + cam.get(2)
                        + " and used " + cam.get(4) + " MB of storage so far " +
                        "(excluding current device), and its time to switch its status is null.\n");
            }
        }
        if (SmartLamp.lamp_list != null){
            for (List<String> lamp : SmartLamp.lamp_list){
                Main.write_output("Smart Lamp " + lamp.get(0) + " is " + lamp.get(1) +
                        " and its kelvin value is " + lamp.get(2) + "K with " + lamp.get(3) +
                        "% brightness, and its time to switch its status is null.\n");
            }
        }
        if (SmartLampColor.clamp_list != null){
            for (List<String> clamp : SmartLampColor.clamp_list){
                Main.write_output("Smart Color Lamp " + clamp.get(0) + " is " + clamp.get(1) +
                        " and its color value is " + clamp.get(2) + " with " + clamp.get(3) +
                        "% brightness, and its time to switch its status is null.\n");
            }
        }
    }

    /**
     * it removes the device from its list.
     * @param remove_info is the line from input file
     */
    public static void remove(List<String> remove_info){
        if (remove_info.size() == 2){
            if (SmartPlug.plug_list != null){
                for (List<String> plug: SmartPlug.plug_list){
                    if (remove_info.get(1).equals(plug.get(0))){
                        Main.write_output("SUCCESS: Information about removed smart device is as follows:\n");
                        Main.write_output("Smart Plug " + plug.get(0) + " is " + plug.get(1)
                                + " and consumed " + plug.get(5) + "W so far (excluding current device)," +
                                " and its time to switch its status is null.\n");
                        int index = SmartPlug.plug_list.indexOf(plug);
                        SmartPlug.plug_list.remove(index);
                        return;
                    }
                }
            }
            if (SmartCamera.camera_list != null){
                for (List<String> cam : SmartCamera.camera_list){
                    if(remove_info.get(1).equals(cam.get(0))){
                        Main.write_output("SUCCESS: Information about removed smart device is as follows:\n");
                        Main.write_output("Smart Camera " + cam.get(0) + " is " + cam.get(2)
                                + " and used " + cam.get(4) + " MB of storage so far " +
                                "(excluding current device), and its time to switch its status is null.\n");
                        int index = SmartCamera.camera_list.indexOf(cam);
                        SmartCamera.camera_list.remove(index);
                        return;
                    }
                }
            }
            if (SmartLamp.lamp_list != null){
                for (List<String> lamp : SmartLamp.lamp_list){
                    if (remove_info.get(1).equals(lamp.get(0))){
                        Main.write_output("SUCCESS: Information about removed smart device is as follows:\n");
                        Main.write_output("Smart Lamp " + lamp.get(0) + " is " + lamp.get(1) +
                                " and its kelvin value is " + lamp.get(2) + "K with " + lamp.get(3) +
                                "% brightness, and its time to switch its status is null.\n");
                        int index = SmartLamp.lamp_list.indexOf(lamp);
                        SmartLamp.lamp_list.remove(index);
                        return;
                    }
                }
            }
            if (SmartLampColor.clamp_list != null){
                for (List<String> clamp : SmartLampColor.clamp_list){
                    if (remove_info.get(1).equals(clamp.get(0))){
                        Main.write_output("SUCCESS: Information about removed smart device is as follows:\n");
                        Main.write_output("Smart Color Lamp " + clamp.get(0) + " is " + clamp.get(1) +
                                " and its color value is " + clamp.get(2) + "K with " + clamp.get(3) +
                                "% brightness, and its time to switch its status is null.\n");
                        int index = SmartLampColor.clamp_list.indexOf(clamp);
                        SmartLampColor.clamp_list.remove(index);
                        return;
                    }
                }
            }
            Main.write_output("There is nothing to remove\n");
        } else {
            Main.write_output("ERROR: Erroneous command! \n");
        }
    }
}
