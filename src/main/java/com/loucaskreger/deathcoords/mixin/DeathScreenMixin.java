package com.loucaskreger.deathcoords.mixin;

import com.loucaskreger.deathcoords.DeathCoords;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {


    @Inject(method = "render", at = @At("TAIL"), cancellable = true)
    private void onRender(MatrixStack matricies, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        var screen = (DeathScreen) (Object) this;
        var pos = client.player.getPos();
        var dimType = client.world.getDimension();
        String dimension = "";
        if (!dimType.isBedWorking()) {
            if (dimType.hasEnderDragonFight()) {
                dimension = "End";
            } else {
                dimension = "Nether";
            }
        } else {
            dimension = "Overworld";
        }
        var text = String.format("You died at X: %.2f, Y: %.2f, Z: %.2f in the %s.", pos.x, pos.y, pos.z, dimension);
        DeathCoords.deathCoords = text;
        client.textRenderer.drawWithShadow(matricies, text, (screen.width / 2) - (client.textRenderer.getWidth(text) / 2), 115, 0xFFFFFF);
    }


}
