package github.aqumpusaxy.betterstoragelectern.mixin.client;

import com.hollingsworth.arsnouveau.setup.config.Config;
import com.llamalad7.mixinextras.sugar.Local;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Config.class, remap = false)
public abstract class ANConfigMixin {
    @Shadow
    public static ModConfigSpec.BooleanValue INVERT_LECTERN_SCROLLING;

    //默认反转存储讲台GUI的滚轮逻辑
    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/hollingsworth/arsnouveau/setup/config/Config;INVERT_LECTERN_SCROLLING:Lnet/neoforged/neoforge/common/ModConfigSpec$BooleanValue;",
                    opcode = Opcodes.PUTSTATIC,
                    shift = At.Shift.AFTER
            )
    )
    private static void InvertLecternScrollingByDefault(CallbackInfo ci, @Local(name = "CLIENT_BUILDER") ModConfigSpec.Builder CLIENT_BUILDER) {
        INVERT_LECTERN_SCROLLING = CLIENT_BUILDER.comment("If scrolling in the Storage Lectern should be inverted").define("invertLecternScrolling", true);
    }
}
