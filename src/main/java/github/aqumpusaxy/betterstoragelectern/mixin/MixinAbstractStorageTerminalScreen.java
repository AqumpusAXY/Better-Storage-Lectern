package github.aqumpusaxy.betterstoragelectern.mixin;

import com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen;
import com.hollingsworth.arsnouveau.client.container.SortSettings;
import com.hollingsworth.arsnouveau.client.container.StorageTerminalMenu;
import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import github.aqumpusaxy.betterstoragelectern.BSLConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractStorageTerminalScreen.class, remap = false)
public abstract class MixinAbstractStorageTerminalScreen<T extends StorageTerminalMenu> extends AbstractContainerScreen<T> {
    @Unique
    private long BSL$lastSearchTime = 0L;
    @Shadow
    public abstract SortSettings getSortSettings();

    private MixinAbstractStorageTerminalScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    //取消自动聚焦搜索框
    @Redirect(
            method = "onPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;setFocused(Z)V"
            )
    )
    private void onOnPacketSetSearchFieldFocused(NoShadowTextField instance, boolean b) {
        instance.setFocused(BSLConfig.INSTANCE.AUTO_FOCUS_SEARCH_FIELD_ON_OPENED.get());
    }

    @Redirect(
            method = "onPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;setValue(Ljava/lang/String;)V"
            )
    )
    private void onInitSetSearchFieldValue(NoShadowTextField instance, String value) {
        if (!BSLConfig.INSTANCE.SAVE_SEARCH_FIELD_CONTENT.get()) return;
        instance.setValue(value);
    }

    @ModifyVariable(
            method = "mouseScrolled",
            at = @At(
                    value = "STORE"
            ),
            name = "i"
    )
    private int modifyMouseScrolledI(int i) {
        //你自己算算就知道是啥了
        return i + (this.getSortSettings() == null || this.getSortSettings().expanded ? -2 : 2);
    }

    @Inject(
            method = "containerTick",
            at = @At("HEAD"),
            cancellable = true)
    private void onContainerTick(CallbackInfo ci) {
        final long CURRENT_TIME = System.currentTimeMillis();
        if (!(CURRENT_TIME - BSL$lastSearchTime > BSLConfig.INSTANCE.SEARCH_UPDATE_DELAY.get())) {
            ci.cancel();
            return;
        }
        BSL$lastSearchTime = CURRENT_TIME;
    }
}
