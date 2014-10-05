package me.Born2PvP.plugin.commands;

import me.Born2PvP.plugin.Eat;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EatCommand implements CommandExecutor {
	
	private final Eat plugin;
	  
	public EatCommand(Eat plugin)
	{
	   this.plugin = plugin;
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String SyntaxError = this.plugin.getConfig().getString("SyntaxError").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		String PermissionError = this.plugin.getConfig().getString("PermissionError").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		if (sender.hasPermission("eat.main")){
				if ((args.length == 0) || (args.length != 1))
				{
					sender.sendMessage(SyntaxError);
					return false;
				}else{
					if (args.length == 1){
						String BroadCast = this.plugin.getConfig().getString("BroadCast").replace("[player]", args[0]).replaceAll("(&([a-f0-9]))", "\u00A7$2");
						String PlayerMsg = this.plugin.getConfig().getString("PlayerMsg").replace("[player]", args[0]).replaceAll("(&([a-f0-9]))", "\u00A7$2");
						String OfflineError = this.plugin.getConfig().getString("OfflineError").replace("[offline]", args[0]).replaceAll("(&([a-f0-9]))", "\u00A7$2");
						@SuppressWarnings("deprecation")
						Player target = this.plugin.getServer().getPlayerExact(args[0]);
						if (target != null && target.isOnline() && target.isDead() == false){
							sender.sendMessage(PlayerMsg);
							Bukkit.broadcastMessage(BroadCast);
							if (this.plugin.getConfig().getBoolean("BloodEffect") == true){
								target.getWorld().playEffect(target.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
								target.getWorld().playEffect(target.getLocation().add(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
							}
							target.setHealth(0.0);
							return true;
						}else{
							sender.sendMessage(OfflineError);
							return false;
						}
					}
					return false;
				}
		}else{
			sender.sendMessage(PermissionError);
			return false;
		}
	}
}
