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
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
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
				//Create an item variable, null by default so it can fail the null check if nothing happens
				Item targetItem = null;
				HitResult.Type type = client.crosshairTarget.getType();
				if (type == HitResult.Type.BLOCK) {
					//If the target is a block, get the item from it
					BlockPos blockPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
					targetItem = client.world.getBlockState(blockPos).getBlock().asItem();
				} else if (type == HitResult.Type.ENTITY) {
					//If the target is an entity, get the spawn egg from it
					targetItem = SpawnEggItem.forEntity(((EntityHitResult) client.crosshairTarget).getEntity().getType());
				}
				if (targetItem != null) {
					//Build the recipe screen from the item
					ViewSearchBuilder view = ViewSearchBuilder.builder().setInputNotice(EntryStack.create(targetItem)).addRecipesFor(EntryStack.create(targetItem));
					//Feed the results to the screen-opener method
					openScreen(client, view);
				}
            }
            
			if (ARRFABKeys.viewUsagesKey.isPressed()) {
				Item targetItem = null;
				HitResult.Type type = client.crosshairTarget.getType();
				if (type == HitResult.Type.BLOCK) {
					BlockPos blockPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
					targetItem = client.world.getBlockState(blockPos).getBlock().asItem();
				} else if (type == HitResult.Type.ENTITY) {
					targetItem = SpawnEggItem.forEntity(((EntityHitResult) client.crosshairTarget).getEntity().getType());
				}
				if (targetItem != null) {
					ViewSearchBuilder view = ViewSearchBuilder.builder().setOutputNotice(EntryStack.create(targetItem)).addUsagesFor(EntryStack.create(targetItem));
					openScreen(client, view);
				}
			}
		});
    }
}
