package me.Born2PvP.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.Born2PvP.plugin.commands.EatCommand;
import me.Born2PvP.plugin.commands.EatreloadCommand;
import me.Born2PvP.plugin.commands.EatstickCommand;
import me.Born2PvP.plugin.events.DeathEvent;
import me.Born2PvP.plugin.events.PlayerInteractEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;


@SuppressWarnings("deprecation")
public class Eat extends JavaPlugin {
	public static ItemStack i;
	
	public ArrayList<String> eaten = new ArrayList<String>();
  
  @Override
  public void onDisable()
  {
	  PluginDescriptionFile pdfFile = getDescription();
	  getLogger().info(pdfFile.getName() + " " + pdfFile.getVersion() +" was successfully disabled. Goodbye!");
  }
  
  @Override
  public void onEnable()
  {
	  try {
		Metrics m = new Metrics(this);
		m.start();
	} catch (IOException e) {
		getLogger().warning("Couldnt Conncet to mcstats :(");
		e.printStackTrace();
	}
	  Eat.i = new ItemStack(Material.getMaterial(getConfig().getInt("StickId")));
	  ItemMeta meta = i.getItemMeta();
	  meta.setDisplayName(getConfig().getString("StickName").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
	  ArrayList<String> Lore = new ArrayList<String>();
	  Lore.addAll(getConfig().getStringList("Lores"));
	  meta.setLore(Lore);
	  i.setItemMeta(meta);
	  
	  
	  getCommand("eat").setExecutor(new EatCommand(this));
	  
	  getCommand("eat").setTabCompleter(new TabCompleter() {
		
		@Override
		public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
			List<String> l = new ArrayList<String>();
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!(p.getName().equals(arg0.getName()))){
					l.add(p.getName());
				}
			}
			return l;
		}
	  });
	  
	  getCommand("eatstick").setExecutor(new EatstickCommand(this));
	  getCommand("eatreload").setExecutor(new EatreloadCommand(this));
	  
	  getServer().getPluginManager().registerEvents(new PlayerInteractEvent(this), this);
	  getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
	  
	  getConfig().options().copyDefaults(true);
	  saveConfig();
      PluginDescriptionFile pdfFile = getDescription();
      getLogger().info(pdfFile.getName() + " " + pdfFile.getVersion() + " Tell your players to get hungry!");
      

  }
}
