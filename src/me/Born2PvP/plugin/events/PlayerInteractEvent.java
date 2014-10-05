package me.Born2PvP.plugin.events;

import me.Born2PvP.plugin.Eat;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEvent implements Listener {
	
	private static Eat plugin;
	
	public PlayerInteractEvent(Eat plugin) {
		PlayerInteractEvent.plugin = plugin;
	}
	
	@EventHandler
	public static void RightClickEvent(PlayerInteractEntityEvent e){
		if (e.getRightClicked() instanceof Player){
			Player p = e.getPlayer();
			ItemStack hand = p.getItemInHand();
			Player target = (Player)e.getRightClicked();
			if (hand.equals(Eat.i)){
				String BroadCast = plugin.getConfig().getString("BroadCast").replace("[player]", target.getDisplayName()).replaceAll("(&([a-f0-9]))", "\u00A7$2");
				String PlayerMsg = plugin.getConfig().getString("PlayerMsg").replace("[player]", target.getDisplayName()).replaceAll("(&([a-f0-9]))", "\u00A7$2");
				if (target.isDead() == false){
					target.sendMessage(PlayerMsg);
					Bukkit.broadcastMessage(BroadCast);
					if (PlayerInteractEvent.plugin.getConfig().getBoolean("BloodEffect")){
						target.getWorld().playEffect(target.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
						target.getWorld().playEffect(target.getLocation().add(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
					}
					target.setHealth(0.0);
				}
			}else{
				return;
			}
		}else{
			return;
		}
	}

}
