package github.aqumpusaxy.betterstoragelectern.mixin.client;

import com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen;
import com.hollingsworth.arsnouveau.client.container.SortSettings;
import com.hollingsworth.arsnouveau.client.container.StoredItemStack;
import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.hollingsworth.arsnouveau.setup.config.Config;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = AbstractStorageTerminalScreen.class, remap = false)
public abstract class MixinAbstractStorageTerminalScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    @Shadow
    protected float currentScroll;

    private MixinAbstractStorageTerminalScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Shadow
    public abstract SortSettings getSortSettings();

    @Shadow
    List<StoredItemStack> itemsSorted;

    @Shadow
    protected NoShadowTextField searchField;

    @Shadow
    public abstract boolean charTyped(char codePoint, int modifiers);

    //取消保存文本框内容
    @Redirect(
            method = "onPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;setValue(Ljava/lang/String;)V"
            )
    )
    private void onPacketSetValue(NoShadowTextField instance, String s) {}

    //取消上次关闭时文本框有内容,下次打开GUI聚焦
    @Redirect(
            method = "onPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;setFocused(Z)V"
            )
    )
    private void onPacketSetFocus(NoShadowTextField instance, boolean b) {}

    //更改滚动逻辑,一次滚一行
    @Redirect(
            method = "mouseScrolled",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/hollingsworth/arsnouveau/client/container/AbstractStorageTerminalScreen;currentScroll:F",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 0
            )
    )
    private void onMouseScrolled(AbstractStorageTerminalScreen<?> instance, float value, double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_, double scrollY) {
        int screenLines = this.getSortSettings() == null || !this.getSortSettings().expanded() ? 3 : 7;
        int remainingLines = (this.itemsSorted.size() + 9 - 1) / 9 - screenLines;
        this.currentScroll = (float) (this.currentScroll + (Config.INVERT_LECTERN_SCROLLING.getAsBoolean() ? -scrollY : scrollY) / remainingLines);
    }


    //修改GUI初始化设置
    @SuppressWarnings("SpellCheckingInspection")
    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        searchField.active = true;
        searchField.setFocused(false);
        setFocused(null);
    }

    //重写按键逻辑
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            cir.setReturnValue(true);
            return;
        }

        if (searchField.isFocused() && searchField.active) {
            boolean handled = searchField.keyPressed(keyCode, scanCode, modifiers);
            cir.setReturnValue(handled);
            return;
        }

        cir.setReturnValue(super.keyPressed(keyCode, scanCode, modifiers));
    }

    //重写字符输入逻辑
    @Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
    private void onCharTyped(char codePoint, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (searchField.isFocused() && searchField.active) {
            boolean handled = searchField.charTyped(codePoint, modifiers);
            cir.setReturnValue(handled);
            return;
        }

        cir.setReturnValue(false);
    }

    //修改鼠标点击逻辑
    @Redirect(
            method = "mouseClicked",
            at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;mouseClicked(DDI)Z")
    )
    private boolean onMouseClicked(NoShadowTextField instance, double mouseX, double mouseY, int mouseButton) {
        if (instance.mouseClicked(mouseX, mouseY, mouseButton)) {
            setFocused(instance);
            return true;
        }
        setFocused(null);
        return false;
    }
}
