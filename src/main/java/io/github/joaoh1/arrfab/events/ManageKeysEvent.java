package io.github.joaoh1.arrfab.events;

import io.github.joaoh1.arrfab.ARRFABKeys;
import io.github.joaoh1.arrfab.ARRFABMod;
import me.shedaniel.rei.api.ClientHelper;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.ClientHelper.ViewSearchBuilder;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class ManageKeysEvent {
    private static void openScreen(MinecraftClient client, ViewSearchBuilder view) {
		//If there's no recipes, don't do anything
        if (!view.buildMap().isEmpty()) {
			//Open the inventory first (in order to prevent problems), then open the recipe screen
            client.openScreen(new InventoryScreen(client.player));
			ClientHelper.getInstance().openView(view);
			//Mark the recipe screen in order to fix ESC and BACKSPACE
            ARRFABMod.openedByVrrfab = true;
        }
    }

    public static void registerEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (ARRFABKeys.viewRecipesKey.isPressed()) {
				//If there's no block in the aim, do nothing
				if (client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
					//Get the item form of the selected block
					BlockPos blockPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
					Item item = client.world.getBlockState(blockPos).getBlock().asItem();
					//Build the recipe screen from the item
					ViewSearchBuilder view = ViewSearchBuilder.builder().setInputNotice(EntryStack.create(item)).addRecipesFor(EntryStack.create(item));
					//Feed the results to the screen-opener method
					openScreen(client, view);
				}
            }
            
			if (ARRFABKeys.viewUsagesKey.isPressed()) {
				if (client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
					BlockPos blockPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
					Item item = client.world.getBlockState(blockPos).getBlock().asItem();
					ViewSearchBuilder view = ViewSearchBuilder.builder().setOutputNotice(EntryStack.create(item)).addUsagesFor(EntryStack.create(item));
					openScreen(client, view);
				}
			}
		});
    }
}
