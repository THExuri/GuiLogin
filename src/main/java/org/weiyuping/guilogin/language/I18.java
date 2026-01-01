package org.weiyuping.guilogin.language;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.weiyuping.guilogin.GuiLogin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class I18 {
    private static final Map<String, YamlConfiguration> languageFiles = new HashMap<>();
    private static String defaultLanguage = "zh_CN";

    public static void loadLanguage() {
        defaultLanguage = GuiLogin.getInstance().getConfig().getString("language","zh_CN");

        loadLanguageFile("zh_CN");
        loadLanguageFile("en_US");
    }
    private static void loadLanguageFile(String language) {
        File languageFile = new File(GuiLogin.getInstance().getDataFolder(), "language" + File.separator + language + ".yml");
        if (!languageFile.exists()) {
            // 如果文件不存在，从资源中复制默认文件
            GuiLogin.getInstance().saveResource("language/" +   language + ".yml", false);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(languageFile);
        languageFiles.put(language,config);
    }
    public static String get(String key) {
        return get(key,defaultLanguage);
    }
    public static String get(String key, String langCode) {
        YamlConfiguration config = languageFiles.get(langCode);
        if (config == null) {
            // 如果指定语言不存在，使用默认语言
            config = languageFiles.get(defaultLanguage);
        }
        if (config != null) {
            String message = config.getString(key);
            if (message != null) {
                return ChatColor.translateAlternateColorCodes('&', message);
            }
        }
        return key;
    }
}
