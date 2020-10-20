package io.github.joaoh1.arrfab.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.joaoh1.arrfab.ARRFABMod;
import me.shedaniel.rei.gui.RecipeViewingScreen;
import me.shedaniel.rei.gui.VillagerRecipeViewingScreen;
import me.shedaniel.rei.impl.ScreenHelper;

@Mixin({RecipeViewingScreen.class, VillagerRecipeViewingScreen.class})
public class RecipeViewingScreenMixin {
	//Makes REI return to no screen at all instead of the inventory when leaving. Affects ESC
	@Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/MinecraftClient.getInstance()Lnet/minecraft/client/MinecraftClient;"), method = "keyPressed(III)Z", cancellable = true)
	private boolean returnNullOnEsc(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
		if (ARRFABMod.openedByVrrfab == true) {
			ARRFABMod.openedByVrrfab = false;
			MinecraftClient client = MinecraftClient.getInstance();
			client.openScreen(null);
			cir.setReturnValue(true);
		}
		return false;
	}

	//Same as above but for BACKSPACE
	@Inject(at = @At(value = "INVOKE", target = "me/shedaniel/rei/impl/ScreenHelper.hasLastRecipeScreen()Z"), method = "keyPressed(III)Z", cancellable = true)
	private boolean returnNullOnBackspace(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
		if (ARRFABMod.openedByVrrfab == true) {
			if (!ScreenHelper.hasLastRecipeScreen()) {
				ARRFABMod.openedByVrrfab = false;
				MinecraftClient client = MinecraftClient.getInstance();
				client.openScreen(null);
				cir.setReturnValue(true);
			}
		}
		return false;
	}
}
