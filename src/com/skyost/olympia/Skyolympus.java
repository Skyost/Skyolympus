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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Skyolympus extends JavaPlugin implements Listener{
	
	private final HashMap<Player, String> Olympmod = new HashMap<Player, String>();
	public static Inventory olympusInv;
	
	@Override
	public final void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		loadConfiguration();
		loadOlympus();
	}
	
	@Override
	public final void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()) {
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
	}
	
	private final void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	private final void loadOlympus() {
		olympusInv = Bukkit.createInventory(null, InventoryType.PLAYER);
		ItemStack olympusIE = new ItemStack(Material.GOLD_SWORD); // Zeus
		ItemMeta olympusMeta = olympusIE.getItemMeta();
		olympusMeta.setDisplayName(ChatColor.GOLD + getConfig().getString("Items.1"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
		olympusIE = new ItemStack(Material.DIAMOND_AXE); // Poseidon
		olympusMeta = olympusIE.getItemMeta();
		olympusMeta.setDisplayName(ChatColor.AQUA + getConfig().getString("Items.2"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
		olympusIE = new ItemStack(Material.BLAZE_ROD); // Hades
		olympusMeta = olympusIE.getItemMeta();
		olympusMeta.setDisplayName(ChatColor.RED + getConfig().getString("Items.3"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
		olympusIE = new ItemStack(Material.SEEDS); // Demeter
		olympusMeta = olympusIE.getItemMeta();
		olympusMeta.setDisplayName(ChatColor.GREEN + getConfig().getString("Items.4"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
		olympusIE = new ItemStack(Material.WOOD_SPADE); // Gaia
		olympusMeta = olympusIE.getItemMeta();
		olympusMeta.setDisplayName(ChatColor.DARK_GRAY + getConfig().getString("Items.5"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
		olympusIE = new ItemStack(Material.STONE_HOE); // Thanatos
		olympusMeta = olympusIE.getItemMeta();
		olympusMeta.setDisplayName(ChatColor.BLACK + getConfig().getString("Items.6"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
		olympusIE = new ItemStack(Material.DIAMOND_SWORD); // Ares
		olympusMeta = olympusIE.getItemMeta();
		olympusMeta.addEnchant(Enchantment.DAMAGE_ALL, 20, true);
		olympusMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 20, true);
		olympusMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 20, true);
		olympusMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 20, true);
		olympusMeta.addEnchant(Enchantment.KNOCKBACK, 20, true);
		olympusMeta.addEnchant(Enchantment.FIRE_ASPECT, 20, true);
		olympusMeta.addEnchant(Enchantment.DURABILITY, 20, true);
		olympusMeta.setDisplayName(ChatColor.YELLOW + getConfig().getString("Items.7"));
		olympusIE.setItemMeta(olympusMeta);
		olympusInv.addItem(olympusIE);
	}
	
	@SuppressWarnings({ "incomplete-switch", "deprecation" })
	@EventHandler
    public final void onPlayerInteract(final PlayerInteractEvent event) {
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		Player player = event.getPlayer();
    		if(Olympmod.get(player) != null) {
    			Location loc = event.getClickedBlock().getLocation();
    			switch(player.getItemInHand().getType()) {
    			case GOLD_SWORD:
    				player.getWorld().strikeLightning(loc);
    				player.getWorld().createExplosion(loc, 15.0F, false);
    				break;
    			case DIAMOND_AXE:
    				player.getTargetBlock(null, 100).setType(Material.WATER);
    				player.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4.0F, false, false);
    				break;
    			case BLAZE_ROD:
    				player.getWorld().createExplosion(loc, 10.0F, true);
    				event.getClickedBlock().setType(Material.LAVA);
    				break;
    			case SEEDS:
    				player.getWorld().generateTree(loc, TreeType.TREE);
    				player.getTargetBlock(null, 100).setType(Material.GRASS);
    				break;
    			case WOOD_SPADE:
    				for(int i = event.getClickedBlock().getY(); i != 0; i--) {
    					player.getWorld().getBlockAt(event.getClickedBlock().getX(), i, event.getClickedBlock().getZ()).setType(Material.AIR);
    				}
    				break;
    			case STONE_HOE:
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
	
	@SuppressWarnings("incomplete-switch")
	@EventHandler
	private final void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
    	Player player = event.getPlayer();
    	if(Olympmod.get(player) != null) {
    		Entity entity = event.getRightClicked();
    		switch(player.getItemInHand().getType()) {
    		case BLAZE_ROD:
        		entity.setFireTicks(entity.getMaxFireTicks());
        		event.setCancelled(true);
    			break;
    		case SEEDS:
    			entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.SAPLING));
    			entity.teleport(entity.getLocation().add(0, -10, 0));
    			event.setCancelled(true);
    			break;
			case WOOD_SPADE:
				entity.teleport(new Location(player.getWorld(), 0.0, -10.0, 0.0));
				event.setCancelled(true);
				break;
    		}
    	}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private final void onPlayerDeath(final PlayerDeathEvent event) {
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
	
	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if(Olympmod.get(event.getPlayer()) != null) {
			event.setCancelled(true);
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
            			for(ItemStack ie : player.getInventory().getContents()) {
            				if(ie != null) {
            					player.getInventory().removeItem(ie);
            				}
            			}
            			for(ItemStack ie : inv.getContents()) {
            				if(ie != null) {
            					player.getInventory().addItem(ie);
            				}
            			}
	            		player.updateInventory();
	            		Olympmod.remove(player);
	            		sender.sendMessage(ChatColor.RED + getConfig().getString("Messages.2"));
            		}
            		catch(Exception ex) {
            			sender.sendMessage(ChatColor.RED + getConfig().getString("Messages.4"));
            			for(ItemStack ie : player.getInventory().getContents()) {
            				if(ie != null) {
            					player.getInventory().removeItem(ie);
            				}
            			}
	            		player.updateInventory();
	            		Olympmod.remove(player);
            		}
            	}
            	else {
            		Olympmod.put(player, InvUtils.InventoryToString(player.getInventory()));
        			for(ItemStack ie : player.getInventory().getContents()) {
        				if(ie != null) {
        					player.getInventory().removeItem(ie);
        				}
        			}
            		player.getInventory().setContents(olympusInv.getContents());
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
