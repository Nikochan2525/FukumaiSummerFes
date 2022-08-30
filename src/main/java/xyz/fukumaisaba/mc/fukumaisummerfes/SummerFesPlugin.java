package xyz.fukumaisaba.mc.fukumaisummerfes;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Command.VoucherCommand;
import xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Listener.VoucherSellEvent;

import java.util.Objects;

public final class SummerFesPlugin extends JavaPlugin {

    private static Plugin plugin = null;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        plugin = this;
        if (!setupEconomy()) {
            getLogger().warning("[FukumaiSummerFes] 依存関係が解決できませんでした。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Objects.requireNonNull(getCommand("voucher")).setExecutor(new VoucherCommand());
        Objects.requireNonNull(getCommand("voucher")).setTabCompleter(new VoucherCommand());
        getCommand("spawn").setExecutor(new xyz.fukumaisaba.mc.fukumaisummerfes.SpawnCommand.CommandListener());
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new VoucherSellEvent(), getPlugin());
    }

    @Override
    public void onDisable() {

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Economy getEconomy() {
        return economy;
    }
}
