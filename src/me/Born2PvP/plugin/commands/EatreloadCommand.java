package me.Born2PvP.plugin.commands;

import me.Born2PvP.plugin.Eat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EatreloadCommand implements CommandExecutor {

	private final Eat plugin;
	  
	public EatreloadCommand(Eat plugin)
	{
	   this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		String reload = this.plugin.getConfig().getString("ReloadMsg").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		String SyntaxError = this.plugin.getConfig().getString("SyntaxError").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		String PermissionError = this.plugin.getConfig().getString("PermissionError").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		if (args.length == 0){
			if (sender.hasPermission("eat.reload")){
				this.plugin.reloadConfig();
				this.plugin.saveConfig();
				sender.sendMessage(reload);
				return true;
			}else{
				sender.sendMessage(PermissionError);
				return false;
			}
		}else{
			sender.sendMessage(SyntaxError);
			return false;
		}
	}

}
