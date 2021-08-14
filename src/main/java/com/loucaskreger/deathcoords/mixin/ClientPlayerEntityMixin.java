package com.loucaskreger.deathcoords.mixin;

import com.loucaskreger.deathcoords.DeathCoords;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "requestRespawn", at = @At("TAIL"))
    public void requestRespawn(CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        var deathCoordsLiteral = new LiteralText(DeathCoords.deathCoords);
        deathCoordsLiteral.setStyle(deathCoordsLiteral.getStyle().withColor(Formatting.GOLD));
        client.player.sendMessage(deathCoordsLiteral, false);
        DeathCoords.deathCoords = "";
    }
}
