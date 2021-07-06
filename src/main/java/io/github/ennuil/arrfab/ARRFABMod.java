package io.github.ennuil.arrfab;

import io.github.ennuil.arrfab.events.ManageKeysEvent;
import net.fabricmc.api.ClientModInitializer;

public class ARRFABMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//Register keys and the event
		ARRFABKeys.registerKeys();
		ManageKeysEvent.registerEvent();
	}
}
