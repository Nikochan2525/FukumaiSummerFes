package xyz.fukumaisaba.mc.fukumaisummerfes.Voucher;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.fukumaisaba.mc.fukumaisummerfes.SummerFesPlugin;

import java.util.Arrays;
import java.util.Objects;

public class Voucher {
    private static final NamespacedKey AMOUNT = new NamespacedKey(SummerFesPlugin.getPlugin(), "amount_of_money");

    public static ItemStack createVoucherItem(int amount) {
        ItemStack response = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = Objects.requireNonNull(response.getItemMeta());
        itemMeta.setDisplayName(String.format("§6§l福舞金券 §c%d§r§6§l円分", amount));
        itemMeta.setLore(Arrays.asList("§f§l福舞鯖の夏祭りイベントで", "§f§lゲーム内通貨に換金することが出来るアイテム"));
        itemMeta.getPersistentDataContainer().set(AMOUNT, PersistentDataType.INTEGER, amount);
        response.setItemMeta(itemMeta);
        return response;
    }

    public static int getAmountOfItem(ItemStack itemStack) {
        ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
        Integer amount = itemMeta.getPersistentDataContainer().get(AMOUNT, PersistentDataType.INTEGER);
        if (amount == null) amount = 0;
        return amount * itemStack.getAmount();
    }
}
