import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class StatisticsLoader {
    private HashMap<String, String> stats;

    public StatisticsLoader(){
        stats = new HashMap<>();
    }

    public void load(){
        String path = "/sys/class/power_supply/BAT0/uevent";
        String content = "";
        try {
            content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            content = "Failed to retrieve statistics: " + e.toString();
        }
        for(String stat : content.split("\n")){
            String[] pair = stat.split("=");
            String prefix = pair[0]; String val = pair[1];
            switch(prefix) {
                case "POWER_SUPPLY_POWER_NOW":
                    stats.put("discharge", val);
                    break;
                case "POWER_SUPPLY_CAPACITY":
                    stats.put("percentage", val);
                    break;
                case "POWER_SUPPLY_ENERGY_FULL":
                    stats.put("full_capacity", val);
                    break;
                case "POWER_SUPPLY_ENERGY_FULL_DESIGN":
                    stats.put("design_capacity", val);
                    break;
            }
        }
    }

    public HashMap<String, String> getStats() {
        return stats;
    }
}
