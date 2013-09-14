package com.skyost.olympia;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Skyolympus extends JavaPlugin implements Listener{
	
	private HashMap<Player, String> Olympmod = new HashMap<Player, String>();
	private ItemStack Zeus = new ItemStack(Material.GOLD_SWORD);
	private ItemStack Poseidon = new ItemStack(Material.DIAMOND_AXE);
	private ItemStack Hades = new ItemStack(Material.BLAZE_ROD);
	private ItemStack Demeter = new ItemStack(Material.SEEDS);
	private ItemStack Gaia = new ItemStack(Material.WOOD_SPADE);
	private ItemStack Thanatos = new ItemStack(Material.STONE_HOE);
	private ItemStack Ares = new ItemStack(Material.DIAMOND_SWORD);
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		loadConfiguration();
		loadOlympus();
	}
	
	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void loadOlympus() {
		ItemMeta ZeusMeta = Zeus.getItemMeta();
		ZeusMeta.setDisplayName(ChatColor.GOLD + getConfig().getString("Items.1"));
		Zeus.setItemMeta(ZeusMeta);
		ItemMeta PoseidonMeta = Poseidon.getItemMeta();
		PoseidonMeta.setDisplayName(ChatColor.AQUA + getConfig().getString("Items.2"));
		Poseidon.setItemMeta(PoseidonMeta);
		ItemMeta HadesMeta = Hades.getItemMeta();
		HadesMeta.setDisplayName(ChatColor.RED + getConfig().getString("Items.3"));
		Hades.setItemMeta(HadesMeta);
		ItemMeta DemeterMeta = Demeter.getItemMeta();
		DemeterMeta.setDisplayName(ChatColor.GREEN + getConfig().getString("Items.4"));
		Demeter.setItemMeta(DemeterMeta);
		ItemMeta GaiaMeta = Gaia.getItemMeta();
		GaiaMeta.setDisplayName(ChatColor.DARK_GRAY + getConfig().getString("Items.5"));
		Gaia.setItemMeta(GaiaMeta);
		ItemMeta ThanatosMeta = Thanatos.getItemMeta();
		ThanatosMeta.setDisplayName(ChatColor.BLACK + getConfig().getString("Items.6"));
		Thanatos.setItemMeta(ThanatosMeta);
		ItemMeta AresMeta = Ares.getItemMeta();
		AresMeta.addEnchant(Enchantment.DAMAGE_ALL, 20, true);
		AresMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 20, true);
		AresMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 20, true);
		AresMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 20, true);
		AresMeta.addEnchant(Enchantment.KNOCKBACK, 20, true);
		AresMeta.addEnchant(Enchantment.FIRE_ASPECT, 20, true);
		AresMeta.addEnchant(Enchantment.DURABILITY, 20, true);
		AresMeta.setDisplayName(ChatColor.YELLOW + getConfig().getString("Items.7"));
		Ares.setItemMeta(AresMeta);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		Player player = event.getPlayer();
    		if(Olympmod.get(player) != null) {
    			Location loc = event.getClickedBlock().getLocation();
    			switch(player.getItemInHand().getTypeId()) {
    			case 283:
    				player.getWorld().strikeLightning(loc);
    				player.getWorld().createExplosion(loc, 15.0F, false);
    				break;
    			case 279:
    				player.getTargetBlock(null, 100).setType(Material.WATER);
    				player.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4.0F, false, false);
    				break;
    			case 369:
    				player.getWorld().createExplosion(loc, 10.0F, true);
    				event.getClickedBlock().setType(Material.LAVA);
    				break;
    			case 295:
    				player.getWorld().generateTree(loc, TreeType.TREE);
    				player.getTargetBlock(null, 100).setType(Material.GRASS);
    				break;
    			case 269:
    				for(int i = event.getClickedBlock().getY(); i != 0; i--) {
    					player.getWorld().getBlockAt(event.getClickedBlock().getX(), i, event.getClickedBlock().getZ()).setType(Material.AIR);
    				}
    				break;
    			case 291:
    				Player[] online = getServer().getOnlinePlayers();
    				int random = new Random().nextInt(getServer().getOnlinePlayers().length);
    				if(online[random] != player) {
    					online[random].setHealth(0);
        				ItemStack Skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        				SkullMeta SkullMeta = (SkullMeta) Skull.getItemMeta();
        				SkullMeta.setOwner(online[random].getName());
        				Skull.setItemMeta(SkullMeta);
        				player.getInventory().addItem(Skull);
        				player.updateInventory();
    				}
    				event.setCancelled(true);
    				break;
    			}
    		}
    	}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    	Player player = event.getPlayer();
    	if(Olympmod.get(player) != null) {
    		Entity entity = event.getRightClicked();
    		switch(player.getItemInHand().getTypeId()) {
    		case 369:
        		entity.setFireTicks(entity.getMaxFireTicks());
        		event.setCancelled(true);
    			break;
    		case 295:
    			entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.SAPLING));
    			entity.teleport(entity.getLocation().add(0, 100, 0));
    			event.setCancelled(true);
    			break;
			case 269:
				entity.teleport(new Location(player.getWorld(), 0.0, -10.0, 0.0));
				event.setCancelled(true);
				break;
    		}
    	}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		try {
			if(Olympmod.get(player) != null) {
				Inventory inv = InvUtils.StringToInventory(Olympmod.get(player));
				for(int i = 0; i != player.getInventory().getSize(); i++) {
	    			if(player.getInventory().getItem(i) != null) {
	    				player.getInventory().removeItem(player.getInventory().getItem(i));
	    			}
	    		}
	    		for(int i = 0; i != inv.getSize(); i++) {
	    			if(inv.getItem(i) != null) {
	    				player.getInventory().addItem(inv.getItem(i));
	    			}
	    		}
				Olympmod.remove(player);
			}
		}
		catch(Exception ex) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Skyolympus] [" + player.getName() + "]" + getConfig().getString("Messages.4"));
			for(int i = 0; i != player.getInventory().getSize(); i++) {
    			if(player.getInventory().getItem(i) != null) {
    				player.getInventory().removeItem(player.getInventory().getItem(i));
    			}
    		}
    		player.updateInventory();
    		Olympmod.remove(player);
		}
	}
	
	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(Olympmod.get(player) != null) {
			Inventory inv = InvUtils.StringToInventory(Olympmod.get(player));
			for(int i = 0; i != player.getInventory().getSize(); i++) {
    			if(player.getInventory().getItem(i) != null) {
    				player.getInventory().removeItem(player.getInventory().getItem(i));
    			}
    		}
    		for(int i = 0; i != inv.getSize(); i++) {
    			if(inv.getItem(i) != null) {
    				player.getInventory().addItem(inv.getItem(i));
    			}
    		}
			Olympmod.remove(player);
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        
        if(sender instanceof Player) {
            player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("olympmod")) {
            	if(Olympmod.get(player) != null) {
            		try {
	            		Inventory inv = InvUtils.StringToInventory(Olympmod.get(player));
	            		for(int i = 0; i != player.getInventory().getSize(); i++) {
	            			if(player.getInventory().getItem(i) != null) {
	            				player.getInventory().removeItem(player.getInventory().getItem(i));
	            			}
	            		}
	            		for(int i = 0; i != inv.getSize(); i++) {
	            			if(inv.getItem(i) != null) {
	            				player.getInventory().addItem(inv.getItem(i));
	            			}
	            		}
	            		player.updateInventory();
	            		Olympmod.remove(player);
	            		sender.sendMessage(ChatColor.RED + getConfig().getString("Messages.2"));
            		}
            		catch(Exception ex) {
            			sender.sendMessage(ChatColor.RED + getConfig().getString("Messages.4"));
            			for(int i = 0; i != player.getInventory().getSize(); i++) {
                			if(player.getInventory().getItem(i) != null) {
                				player.getInventory().removeItem(player.getInventory().getItem(i));
                			}
                		}
	            		player.updateInventory();
	            		Olympmod.remove(player);
            		}
            	}
            	else {
            		Olympmod.put(player, InvUtils.InventoryToString(player.getInventory()));
            		for(int i = 0; i != player.getInventory().getSize(); i++) {
            			if(player.getInventory().getItem(i) != null) {
            				player.getInventory().removeItem(player.getInventory().getItem(i));
            			}
            		}
            		player.getInventory().addItem(Zeus);
            		player.getInventory().addItem(Poseidon);
            		player.getInventory().addItem(Hades);
            		player.getInventory().addItem(Demeter);
            		player.getInventory().addItem(Gaia);
            		player.getInventory().addItem(Thanatos);
            		player.getInventory().addItem(Ares);
            		player.updateInventory();
            		sender.sendMessage(ChatColor.GREEN + getConfig().getString("Messages.1"));
            	}
            }
        }
        else {
        	sender.sendMessage(ChatColor.RED + "[Skyolympus] " + getConfig().getString("Messages.3"));
        }
        return true;
	}

}
