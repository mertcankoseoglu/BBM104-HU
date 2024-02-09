import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SmartCamera extends SmartDevices{
    private static SmartCamera instance;
    public static ArrayList<List<String>> camera_list;
    private static final double VOLTAGE = 220;

    /**
     * camera_list(camera devices list) in constructor.
     */
    public SmartCamera() {
        camera_list = new ArrayList<>();
    }

    /**
     * camera_list(camera devices list) in constructor.
     */
    public static SmartCamera getInstance() {
        if (instance == null) {
            instance = new SmartCamera();
        }
        return instance;
    }

    /**
     * adding camera into camrea_list if it is correct input format and unique name.
     * @param cam_info is the line in input file which is about adding camera.
     */
    public void add(List<String> cam_info) {
        if (cam_info.size() == 4 || cam_info.size() == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String first_time = Time.getCurrentTime().format(formatter);
            try {
                if (camera_list != null && !camera_list.isEmpty()) {
                    for (List<String> cam : camera_list) {
                        if (cam.get(0).equals(cam_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart device" +
                                    " with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartPlug.plug_list != null && !SmartPlug.plug_list.isEmpty()){
                    for (List<String> plug : SmartPlug.plug_list) {
                        if (plug.get(0).equals(cam_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartLamp.lamp_list != null && !SmartLamp.lamp_list.isEmpty()){
                    for (List<String> lamp : SmartLamp.lamp_list) {
                        if (lamp.get(0).equals(cam_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                if (SmartLampColor.clamp_list != null && !SmartLampColor.clamp_list.isEmpty()) {
                    for (List<String> clamp : SmartLampColor.clamp_list) {
                        if (clamp.get(0).equals(cam_info.get(2))) {
                            Main.write_output("ERROR: There is already a smart" +
                                    " device with the same name!\n");
                            return;
                        }
                    }
                }
                double megabyte = Double.parseDouble(cam_info.get(3));
                if (0.0 >= megabyte){
                    Main.write_output("ERROR: Megabyte value must be a positive number!\n");
                    return;
                }

                if (!cam_info.get(4).equals("On")){
                    if (!cam_info.get(4).equals("Off")) {
                        Main.write_output("ERROR: Erroneous command! \n");
                        return;
                    }
                }
            } catch (IndexOutOfBoundsException e){
                cam_info.add("Off");
            } catch (NumberFormatException e){
                Main.write_output("ERROR: Erroneous command! \n");
                return;
            }
            cam_info.add(first_time);
            cam_info.add("0.0");
            camera_list.add(cam_info.subList(2, cam_info.size()));
        }else {
            Main.write_output("ERROR: Erroneous command! \n");
        }
    }

    /**
     * for switching the camera status.
     * @param cam_switch switch information from input file
     */
    public void switchOnOff(List<String> cam_switch) {
        if (cam_switch.size() == 3){
            for (List<String> cam : camera_list) {
                if (cam.get(0).equals(cam_switch.get(1))) {
                    if (cam.get(2).equals(cam_switch.get(2))) {
                        Main.write_output("ERROR: This device is already switched "
                                + cam_switch.get(2) + "!\n");
                    }
                    else {
                        if (cam_switch.get(2).equals("On") || cam_switch.get(2).equals("Off")){
                            cam.set(2, cam_switch.get(2));
                            double megabyte = Double.parseDouble(cam.get(1));
                            if (cam.get(2).equals("Off")){
                                try {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    LocalDateTime first_time = LocalDateTime.parse(cam.get(3), formatter);
                                    Duration duration = Duration.between(first_time, Time.getCurrentTime());
                                    double double_duration = duration.getSeconds();
                                    double power = VOLTAGE * megabyte;
                                    double energy = Double.parseDouble(cam.get(4));
                                    energy += power * double_duration;
                                    String energy_consume = String.valueOf(energy);
                                    cam.set(4, energy_consume);
                                } catch (Exception e){
                                    Main.write_output("ERROR: Erroneous command!\n");
                                }
                            }else{
                                Main.write_output("ERROR: Erroneous command!\n");
                            }
                        }
                    }
                }
            }
        } else {
            Main.write_output("ERROR: Erroneous command!\n");
        }
    }
}
