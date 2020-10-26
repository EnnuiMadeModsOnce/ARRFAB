package io.github.joaoh1.arrfab;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;

public class ARRFABKeys {
    //The "View Recipes" key
    public static KeyBinding viewRecipesKey = KeyBindingHelper.registerKeyBinding(
        new KeyBinding("key.arrfab.view_recipes", GLFW.GLFW_KEY_PAGE_UP, "key.arrfab.category")
    );
    //The "View Usages" key
    public static KeyBinding viewUsagesKey = KeyBindingHelper.registerKeyBinding(
        new KeyBinding("key.arrfab.view_usages", GLFW.GLFW_KEY_PAGE_DOWN, "key.arrfab.category")
    );
}
