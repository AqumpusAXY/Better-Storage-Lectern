package github.aqumpusaxy.betterstoragelectern;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class BSLConfig {
    public static final BSLConfig INSTANCE;
    public static final ForgeConfigSpec CONFIG_SPEC;

    public final ForgeConfigSpec.BooleanValue AUTO_FOCUS_SEARCH_FIELD_ON_OPENED;
    public final ForgeConfigSpec.BooleanValue SAVE_SEARCH_FIELD_CONTENT;

    private static final String PATH_AUTO_FOCUS_SEARCH_FIELD_ON_OPENED = "auto_focus_search_field_on_opened";
    private static final String PATH_SAVE_SEARCH_FIELD_CONTENT = "save_search_field_content";

    private BSLConfig(ForgeConfigSpec.Builder builder) {
        AUTO_FOCUS_SEARCH_FIELD_ON_OPENED = builder
                .comment("If the search field should be focused when the Storage Lectern GUI is opened")
                .define(PATH_AUTO_FOCUS_SEARCH_FIELD_ON_OPENED, false);
        SAVE_SEARCH_FIELD_CONTENT = builder
                .comment("If the search field content should be saved")
                .define(PATH_SAVE_SEARCH_FIELD_CONTENT, false);
    }

    static {
        Pair<BSLConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(BSLConfig::new);

        INSTANCE = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
