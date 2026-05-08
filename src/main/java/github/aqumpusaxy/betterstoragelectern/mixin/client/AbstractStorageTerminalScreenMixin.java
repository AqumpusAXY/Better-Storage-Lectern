package github.aqumpusaxy.betterstoragelectern.mixin.client;

import com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen;
import com.hollingsworth.arsnouveau.client.container.SortSettings;
import com.hollingsworth.arsnouveau.client.container.StoredItemStack;
import com.hollingsworth.arsnouveau.setup.config.Config;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = AbstractStorageTerminalScreen.class, remap = false)
public abstract class AbstractStorageTerminalScreenMixin {
    @Shadow
    protected float currentScroll;

    @Shadow
    public abstract SortSettings getSortSettings();

    @Shadow
    List<StoredItemStack> itemsSorted;

    @Redirect(
            method = "mouseScrolled",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/hollingsworth/arsnouveau/client/container/AbstractStorageTerminalScreen;currentScroll:F",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 0
            )
    )
    private void modifyScrollValue(AbstractStorageTerminalScreen<?> instance, float value, double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_, double scrollY) {
        int screenLines = this.getSortSettings() == null || !this.getSortSettings().expanded() ? 3 : 7;
        int remainingLines = (this.itemsSorted.size() + 9 - 1) / 9 - screenLines;
        this.currentScroll = (float) (this.currentScroll + (Config.INVERT_LECTERN_SCROLLING.getAsBoolean() ? -scrollY : scrollY) / remainingLines);
    }
}
