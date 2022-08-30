package xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Command;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import xyz.fukumaisaba.mc.fukumaisummerfes.SummerFesPlugin;
import xyz.fukumaisaba.mc.fukumaisummerfes.Voucher.Voucher;

import java.util.ArrayList;
import java.util.Arrays;
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

                if (args.length != 2) {
                    sender.sendMessage(VoucherErrorMessages.INVALID_ARGUMENTS);
                    return true;
                }
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
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
                    Voucher.giveItem(senderPlayer, Voucher.createVoucherItem(amount));
                    senderPlayer.sendMessage(String.format("§a[福舞金券] %d円分の金券を購入しました", amount));
                    senderPlayer.playSound(senderPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                } else {
                    sender.sendMessage(ChatColor.RED + "エラーが発生しました: " + response.errorMessage);
                }
                break;

            case "sell":
                if (args.length != 1) {
                    sender.sendMessage(VoucherErrorMessages.INVALID_ARGUMENTS);
                    return true;
                }
                senderPlayer.openInventory(Voucher.createSellInventory());
                break;

            case "help":
                senderPlayer.sendMessage("/voucher help: このメッセージを表示します\n/voucher buy [金額]: 指定した金額分金券を購入します\n/voucher sell: 金券売却画面を表示します");
                break;

            default:
                sender.sendMessage(VoucherErrorMessages.INVALID_ARGUMENTS);
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> response = new ArrayList<>(Arrays.asList("buy", "sell", "help"));
        if (args.length <= 1) {
            response.removeIf(s -> !s.startsWith(args[0]));
        }
        else {
            response.clear();
        }
        return response;
    }
}
