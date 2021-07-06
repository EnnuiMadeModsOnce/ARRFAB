package io.github.ennuil.arrfab.events;

import io.github.ennuil.arrfab.ARRFABKeys;
import io.github.ennuil.arrfab.screen.PlaceholderScreen;
import me.shedaniel.rei.api.client.ClientHelper;
import me.shedaniel.rei.api.client.view.ViewSearchBuilder;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.impl.client.REIRuntimeImpl;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class ManageKeysEvent {
	private static void getTargetRecipes(MinecraftClient client) {
		ViewSearchBuilder view = ViewSearchBuilder.builder();
		HitResult.Type type = client.crosshairTarget.getType();
		switch (type) {
			case BLOCK -> {
				// If the target is a block, get the item from it
				BlockPos blockPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
				view.addRecipesFor(EntryStacks.of(client.world.getBlockState(blockPos).getBlock().asItem()));
			}
			case ENTITY -> {
				// If the target is an entity, get the spawn egg from it
				view.addRecipesFor(EntryStacks.of(SpawnEggItem.forEntity(((EntityHitResult) client.crosshairTarget).getEntity().getType())));
			}
			default -> { return; }
		};
		openScreen(view);
	}

	private static void getTargetUsages(MinecraftClient client) {
		ViewSearchBuilder view = ViewSearchBuilder.builder();
		HitResult.Type type = client.crosshairTarget.getType();
		switch (type) {
			case BLOCK -> {
				// If the target is a block, get the item from it
				BlockPos blockPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
				view.addUsagesFor(EntryStacks.of(client.world.getBlockState(blockPos).getBlock().asItem()));
			}
			case ENTITY -> {
				// If the target is an entity, get the spawn egg from it
				view.addUsagesFor(EntryStacks.of(SpawnEggItem.forEntity(((EntityHitResult) client.crosshairTarget).getEntity().getType())));
			}
			default -> { return; }
		};
		openScreen(view);
	}

    private static void openScreen(ViewSearchBuilder view) {
		//If there's no recipes, don't do anything
        if (!view.buildMap().isEmpty()) {
			// Open the view, and then set the previous screen to the placeholder screen.
			// REI does not like the null screen at all
			ClientHelper.getInstance().openView(view);
			REIRuntimeImpl.getInstance().setPreviousScreen(new PlaceholderScreen());
        }
    }

    public static void registerEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (ARRFABKeys.viewRecipesKey.isPressed()) {
				getTargetRecipes(client);
            }
            
			if (ARRFABKeys.viewUsagesKey.isPressed()) {
				getTargetUsages(client);
			}
		});
    }
}
