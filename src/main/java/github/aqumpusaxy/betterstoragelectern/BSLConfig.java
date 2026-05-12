package github.aqumpusaxy.betterstoragelectern;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BSLConfig {
    public static final BSLConfig INSTANCE;
    public static final ModConfigSpec CLIENT_CONFIG_SPEC;

    public final ModConfigSpec.BooleanValue AUTO_FOCUS_SEARCH_FIELD_ON_OPENED;
    public final ModConfigSpec.BooleanValue AUTO_FOCUS_SEARCH_FIELD_ON_KEY_PRESSED;
    public final ModConfigSpec.BooleanValue SAVE_SEARCH_FIELD_CONTENT;

    private static final String PATH_AUTO_FOCUS_SEARCH_FIELD_ON_OPENED = "auto_focus_search_field_on_opened";
    private static final String PATH_AUTO_FOCUS_SEARCH_FIELD_ON_KEY_PRESSED = "auto_focus_search_field_on_key_pressed";
    private static final String PATH_SAVE_SEARCH_FIELD_CONTENT = "save_search_field_content";

    private BSLConfig(ModConfigSpec.Builder builder) {
        AUTO_FOCUS_SEARCH_FIELD_ON_OPENED = builder
                .comment("If the search field should be focused when the Storage Lectern GUI is opened")
                .define(PATH_AUTO_FOCUS_SEARCH_FIELD_ON_OPENED, false);
        AUTO_FOCUS_SEARCH_FIELD_ON_KEY_PRESSED = builder
                .comment("If the search field should be focused when a key is pressed")
                .define(PATH_AUTO_FOCUS_SEARCH_FIELD_ON_KEY_PRESSED, false);
        SAVE_SEARCH_FIELD_CONTENT = builder
                .comment("If the search field content should be saved")
                .define(PATH_SAVE_SEARCH_FIELD_CONTENT, false);
    }

    static {
        Pair<BSLConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(BSLConfig::new);

        INSTANCE = pair.getLeft();
        CLIENT_CONFIG_SPEC = pair.getRight();
    }
}
