package xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Listener;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.fukumaisaba.mc.fukumaisummerfes.SummerFesPlugin;
import xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Voucher;

import java.util.ArrayList;
import java.util.List;

public class VoucherSellEvent implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Economy economy = SummerFesPlugin.getEconomy();
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        if (event.getView().getTitle().equals(Voucher.getSellInventoryTitle())) {
            int purchasePrice = 0;
            final List<ItemStack> returnItems = new ArrayList<>();
            for (ItemStack item : inventory.getContents()) {
                int price = Voucher.getAmountOfItem(item);
                if (price == 0) returnItems.add(item);
                purchasePrice += price;
            }
            economy.depositPlayer(player, purchasePrice);
            for (ItemStack returnItem : returnItems) {
                Voucher.giveItem(player, returnItem);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);
            player.sendMessage(String.format("[福舞金券] %d円分換金しました", purchasePrice));
        }
    }
}
