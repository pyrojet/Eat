package me.Born2PvP.plugin.commands;

import java.util.ArrayList;
import java.util.Map;

import me.Born2PvP.plugin.Eat;
import me.Born2PvP.plugin.Utils.InventoryWorkaround;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EatstickCommand implements CommandExecutor {
	private final Eat plugin;
	  
	public EatstickCommand(Eat plugin)
	{
	   this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		String NoSpace = this.plugin.getConfig().getString("NoSpaceMsg").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		String SyntaxError = this.plugin.getConfig().getString("SyntaxError").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		String PermissionError = this.plugin.getConfig().getString("PermissionError").replaceAll("(&([a-f0-9]))", "\u00A7$2");
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (p.hasPermission("eat.stick")){
				if (args.length == 0){
					ItemStack i = new ItemStack(Material.getMaterial(this.plugin.getConfig().getInt("StickId")));
					ItemMeta meta = i.getItemMeta();
					meta.setDisplayName(this.plugin.getConfig().getString("StickName").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
					ArrayList<String> Lore = new ArrayList<String>();
					Lore.addAll(this.plugin.getConfig().getStringList("Lores"));
					meta.setLore(Lore);
					i.setItemMeta(meta);
					if (p.getInventory().firstEmpty() == -1 && p.getInventory().containsAtLeast(i, 1) == false){
						p.sendMessage(NoSpace);
					}else{
						Map<Integer, ItemStack> leftovers = InventoryWorkaround.addItems(p.getInventory(), i);
						for (@SuppressWarnings("unused") ItemStack it : leftovers.values()) {
							p.sendMessage(NoSpace);
						}
						p.updateInventory();
					}
					return true;
				}else{
					p.sendMessage(SyntaxError);
					return false;
				}
			}else{
				p.sendMessage(PermissionError);
				return false;
			}
		}else{
			this.plugin.getLogger().info("This Command Can Only Be Run By Players!");
			return false;
		}
	}
}
