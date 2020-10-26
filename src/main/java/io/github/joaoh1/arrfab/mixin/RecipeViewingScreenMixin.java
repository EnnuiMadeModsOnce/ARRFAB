package io.github.joaoh1.arrfab.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.joaoh1.arrfab.ARRFABMod;
import me.shedaniel.rei.gui.RecipeViewingScreen;
import me.shedaniel.rei.gui.VillagerRecipeViewingScreen;
import me.shedaniel.rei.impl.ScreenHelper;

@Mixin({ RecipeViewingScreen.class, VillagerRecipeViewingScreen.class })
public abstract class RecipeViewingScreenMixin extends Screen {
	protected RecipeViewingScreenMixin(Text title) {
		super(title);
	}

	// Makes REI return to no screen at all instead of the inventory when leaving.
	// Affects ESC
	@Inject(at = @At(value = "INVOKE", target = "net/minecraft/client/MinecraftClient.getInstance()Lnet/minecraft/client/MinecraftClient;"), method = "keyPressed(III)Z", cancellable = true)
	private boolean returnNullOnEsc(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
		if (ARRFABMod.returnToNoScreen == true) {
			ARRFABMod.returnToNoScreen = false;
			this.client.openScreen(null);
			cir.setReturnValue(true);
		}
		return false;
	}

	//Same as above but for BACKSPACE
	@Inject(at = @At(value = "INVOKE", target = "me/shedaniel/rei/impl/ScreenHelper.hasLastRecipeScreen()Z"), method = "keyPressed(III)Z", cancellable = true)
	private boolean returnNullOnBackspace(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
		if (ARRFABMod.returnToNoScreen == true) {
			if (!ScreenHelper.hasLastRecipeScreen()) {
				ARRFABMod.returnToNoScreen = false;
				MinecraftClient client = MinecraftClient.getInstance();
				client.openScreen(null);
				cir.setReturnValue(true);
			}
		}
		return false;
	}
}
