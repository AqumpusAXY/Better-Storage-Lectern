package github.aqumpusaxy.betterstoragelectern.data;

import github.aqumpusaxy.betterstoragelectern.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(modid = Constants.MODID)
public class BSLDataGenerators {
    private static final Set<String> LANG_KEYS = Set.of(
            "en_us",
            "zh_cn"
    );

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        for (String langKey : LANG_KEYS) {
            generator.addProvider(event.includeClient(), new BSLLangProvider(output, Constants.MODID, langKey));
        }
    }
}
