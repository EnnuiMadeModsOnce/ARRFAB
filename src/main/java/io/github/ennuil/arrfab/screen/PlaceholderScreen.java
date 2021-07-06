package io.github.ennuil.arrfab.screen;

import net.minecraft.client.gui.screen.Screen;

// A screen meant to stand for the "null" screen.
public class PlaceholderScreen extends Screen {
    public PlaceholderScreen() {
        super(null);
    }

    @Override
    protected void init() {
        this.onClose();
    }

    @Override
    protected void narrateScreenIfNarrationEnabled(boolean useTranslationsCache) {}
}
