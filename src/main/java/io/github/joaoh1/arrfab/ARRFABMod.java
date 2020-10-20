package io.github.joaoh1.arrfab;

import io.github.joaoh1.arrfab.events.ManageKeysEvent;
import net.fabricmc.api.ClientModInitializer;

public class ARRFABMod implements ClientModInitializer {
	public static boolean openedByVrrfab = false;

	@Override
	public void onInitializeClient() {
		//Register the keys and the event
		ARRFABKeys.registerKeys();
		ManageKeysEvent.registerEvent();
	}
}
