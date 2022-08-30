package xyz.fukumaisaba.mc.fukumaisummerfes.SpawnCommand;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
    // Name: world
    // Location:
    //  x: 64.500
    //  y: 30
    //  z: -238.5
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("spawn")){
            if(sender instanceof Player) {
                Player player = (Player) sender;
                Location location = new Location(Bukkit.getWorld("world"), 64.5, 30, -238.5);
                player.teleport(location);
                sender.sendMessage(Color.YELLOW+"スポーン地点に転送するよ！");
                return true;
            }else{
                sender.sendMessage(Color.RED+"てめーサーバーコンソールだな？サーバーコンソールがTPできると思ってんのか？");
                return false;
            }
        }
        return false;
    }
}
