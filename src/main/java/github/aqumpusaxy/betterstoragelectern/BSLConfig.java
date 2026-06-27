package github.aqumpusaxy.betterstoragelectern;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class BSLConfig {
    public static final BSLConfig INSTANCE;
    public static final ForgeConfigSpec CONFIG_SPEC;

    public final ForgeConfigSpec.BooleanValue AUTO_FOCUS_SEARCH_FIELD_ON_OPENED;
    public final ForgeConfigSpec.BooleanValue SAVE_SEARCH_FIELD_CONTENT;
    public final ForgeConfigSpec.IntValue SEARCH_UPDATE_DELAY;

    private static final String PATH_AUTO_FOCUS_SEARCH_FIELD_ON_OPENED = "auto_focus_search_field_on_opened";
    private static final String PATH_SAVE_SEARCH_FIELD_CONTENT = "save_search_field_content";
    private static final String PATH_SEARCH_UPDATE_DELAY = "search_update_delay";

    private BSLConfig(ForgeConfigSpec.Builder builder) {
        AUTO_FOCUS_SEARCH_FIELD_ON_OPENED = builder
                .comment("If the search field should be focused when the Storage Lectern GUI is opened")
                .define(PATH_AUTO_FOCUS_SEARCH_FIELD_ON_OPENED, false);
        SAVE_SEARCH_FIELD_CONTENT = builder
                .comment("If the search field content should be saved")
                .define(PATH_SAVE_SEARCH_FIELD_CONTENT, false);
        SEARCH_UPDATE_DELAY = builder
                .comment("The Delay between two search updates")
                .defineInRange(PATH_SEARCH_UPDATE_DELAY, 200, 0, 1000);
    }

    static {
        Pair<BSLConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(BSLConfig::new);

        INSTANCE = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
