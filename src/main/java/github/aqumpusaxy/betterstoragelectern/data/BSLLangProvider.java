package github.aqumpusaxy.betterstoragelectern.data;

import github.aqumpusaxy.betterstoragelectern.BSLConfig;
import github.aqumpusaxy.betterstoragelectern.Constants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BSLLangProvider extends LanguageProvider {
    private final String LOCALE;

    public BSLLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
        LOCALE = locale;
    }

    @Override
    protected void addTranslations() {
        if (LOCALE.equals("en_us")) {
            addEnglishTranslations();
        } else if (LOCALE.equals("zh_cn")) {
            addChineseTranslations();
        }
    }

    private void addEnglishTranslations() {
        addConfig(BSLConfig.INSTANCE.AUTO_FOCUS_SEARCH_FIELD_ON_OPENED, "Auto focus search field on opened");
        addConfig(BSLConfig.INSTANCE.AUTO_FOCUS_SEARCH_FIELD_ON_KEY_PRESSED, "Auto focus search field on key pressed");
        addConfig(BSLConfig.INSTANCE.SAVE_SEARCH_FIELD_CONTENT, "Save search field content");
    }

    private void addChineseTranslations() {
        addConfig(BSLConfig.INSTANCE.AUTO_FOCUS_SEARCH_FIELD_ON_OPENED, "打开时自动聚焦搜索栏");
        addConfig(BSLConfig.INSTANCE.AUTO_FOCUS_SEARCH_FIELD_ON_KEY_PRESSED, "按下按键时自动聚焦搜索栏");
        addConfig(BSLConfig.INSTANCE.SAVE_SEARCH_FIELD_CONTENT, "保存搜索栏内容");
    }

    private void addConfig(ModConfigSpec.ConfigValue<?> key, String value) {
        add(Constants.getConfigKey(key), value);
    }
}
