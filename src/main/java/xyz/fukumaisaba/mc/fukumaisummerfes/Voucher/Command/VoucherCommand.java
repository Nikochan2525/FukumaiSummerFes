package xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Command;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import xyz.fukumaisaba.mc.fukumaisummerfes.SummerFesPlugin;
import xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Voucher;

import java.util.List;

public class VoucherCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player senderPlayer = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(VoucherErrorMessages.INVALID_ARGUMENTS);
            return true;
        }
        switch (args[0]) {
            case "buy":
                int amount;
                Economy economy = SummerFesPlugin.getEconomy();
                World world = senderPlayer.getWorld();

                if (args.length != 2) {
                    sender.sendMessage(VoucherErrorMessages.INVALID_ARGUMENTS);
                    return true;
                }
                try {
                    amount = Integer.parseInt(args[1]);
                }
                catch (NumberFormatException e) {
                    sender.sendMessage(VoucherErrorMessages.INVALID_NUMBER);
                    return true;
                }
                if (amount <= 0) {
                    sender.sendMessage(VoucherErrorMessages.INVALID_NUMBER);
                    return true;
                }

                if (economy.getBalance(senderPlayer) < amount) {
                    sender.sendMessage(VoucherErrorMessages.MONEY_NOT_ENOUGH);
                    return true;
                }
                EconomyResponse response = economy.withdrawPlayer(senderPlayer, amount);
                if (response.transactionSuccess()) {
                    Item item = world.spawn(senderPlayer.getLocation(), Item.class);
                    item.setItemStack(Voucher.createVoucherItem(amount));
                }
                else {
                    sender.sendMessage(ChatColor.RED + "エラーが発生しました: " + response.errorMessage);
                }
                break;

            case "sell":
                break;

            default:
                sender.sendMessage(VoucherErrorMessages.INVALID_ARGUMENTS);
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
