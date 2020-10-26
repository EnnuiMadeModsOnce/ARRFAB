package io.github.joaoh1.arrfab;

import io.github.joaoh1.arrfab.events.ManageKeysEvent;
import net.fabricmc.api.ClientModInitializer;

public class ARRFABMod implements ClientModInitializer {
	public static boolean returnToNoScreen = false;

	@Override
	public void onInitializeClient() {
		//Register the event
		ManageKeysEvent.registerEvent();
	}
}
