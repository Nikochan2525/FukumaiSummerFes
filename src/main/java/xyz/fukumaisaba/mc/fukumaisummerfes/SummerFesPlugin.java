package xyz.fukumaisaba.mc.fukumaisummerfes;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SummerFesPlugin extends JavaPlugin {

    private static Plugin plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
