package github.aqumpusaxy.betterstoragelectern;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Constants {
    public static final String MODID = "betterstoragelectern";

    public static String getConfigKey(ModConfigSpec.ConfigValue<?> key) {
        return MODID + ".configuration" + "." + String.join(".", key.getPath());
    }
}
