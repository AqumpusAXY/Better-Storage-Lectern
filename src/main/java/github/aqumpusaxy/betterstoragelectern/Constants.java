package github.aqumpusaxy.betterstoragelectern;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Constants {
    public static final String MODID = "betterstoragelectern";

    public static String getConfigKey(ModConfigSpec.ConfigValue<?> key) {
        return MODID + ".configuration" + "." + String.join(".", key.getPath());
    }

    public static String getConfigCommentKey(ModConfigSpec.ConfigValue<?> configValue) {
        return MODID + ".configuration.comment" + "." + String.join(".", configValue.getPath());
    }

    public static String getConfigCommentKey(String path) {
        return MODID + ".configuration.comment" + "." + path;
    }
}
