package me.Born2PvP.plugin.events;

import me.Born2PvP.plugin.Eat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathEvent implements Listener {
	
	private Eat plugin;
	
	public DeathEvent(Eat plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Death(PlayerDeathEvent event){
		if ((event.getEntityType() == EntityType.PLAYER))
		{
			String pname = event.getEntity().getName();
			if (this.plugin.eaten.contains(pname))
			{
				if (this.plugin.getConfig().getBoolean("FleshDrop") == true)
				{
					ItemStack flesh = new ItemStack(Material.ROTTEN_FLESH, 1);
					ItemMeta meta = flesh.getItemMeta();
					meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + pname + "'s" + ChatColor.RESET + "" + ChatColor.DARK_GREEN +" Flesh");
					flesh.setItemMeta(meta);
					event.getDrops().add(flesh);
				}
				event.setDeathMessage("");
				this.plugin.eaten.remove(pname);
			}
		}
	}
}
