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
        var dim = client.world.getRegistryKey();
        var capitalize = true;
        String dimension = dim.toString().substring(dim.toString().lastIndexOf(":") + 1, dim.toString().lastIndexOf("]")).replace("_", " ").toLowerCase();
        if (dimension.startsWith("the")) dimension = dimension.substring(3);
        if (dimension.startsWith(" ")) dimension = dimension.substring(1);
        for (var i=0; i<dimension.length(); i++) {
        	if (capitalize) dimension = dimension.substring(i,i+1).toUpperCase() + dimension.substring(i+1);
        	capitalize = false;
        	if (dimension.substring(i,i+1).equals(" ")) capitalize = true;
        }
        var text = String.format("You died at X: %.0f, Y: %.0f, Z: %.0f in the %s.", pos.x, pos.y, pos.z, dimension);
        DeathCoords.deathCoords = text;
        client.textRenderer.drawWithShadow(matricies, text, (screen.width / 2) - (client.textRenderer.getWidth(text) / 2), 115, 0xFFFFFF);
    }
    
}
