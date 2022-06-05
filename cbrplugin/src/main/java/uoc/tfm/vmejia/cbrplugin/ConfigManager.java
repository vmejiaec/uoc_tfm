package uoc.tfm.vmejia.cbrplugin;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration config;

    public static void setupConfig(CBR cbr){
        ConfigManager.config = cbr.getConfig();
        cbr.saveDefaultConfig();
    }

    public static String getData_Path(){
        return config.getString("data_path");
    }

    public static String getProjectName(){
        return config.getString("projectName");
    }

    public static String getConceptName(){
        return config.getString("conceptName");
    }

    public static String getCSV(){
        return config.getString("csv");
    }

    public static String getCaseBase(){
        return config.getString("casebase");
    }

    public static String getColumnSeparator(){
        return config.getString("columnseparator");
    }

    public static String getMultipleValueSeparator(){
        return config.getString("multiplevalueseparator");
    }
}
