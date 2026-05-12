package github.aqumpusaxy.betterstoragelectern;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class BetterStorageLectern {
    private static final Logger LOGGER = LogUtils.getLogger();

    public BetterStorageLectern(IEventBus modEventBus, ModContainer modContainer) {
        //注册配置
        modContainer.registerConfig(ModConfig.Type.CLIENT, BSLConfig.CLIENT_CONFIG_SPEC);
        //注册配置屏幕
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
