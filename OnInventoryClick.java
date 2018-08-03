

public class OnInventoryClick2 implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory i = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		User pUUID = User.get(p.getName());
		if (i != null) {
			if (i.getName().equalsIgnoreCase("§8Zadania")) {
				if (e.getRawSlot() <= 53) {
				  if (e.getCurrentItem() != null) {
				    e.setCancelled(true);
				    if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				      if (e.getCurrentItem().getItemMeta() != null) {
					if (e.getCurrentItem().getType() == Material.CARPET && e.getCurrentItem().getDurability() == 15) {
					  if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§6Zmień sposób sortowania.")) {
					    if (pUUID.getZadaniaSortowanie() == 1) {
					      pUUID.setZadaniaSortowanie(2);
					      i.clear();
					      ZadaniaMethods.openInventoryZadania(p, i, 0);
					      return;
					    }
					    if (pUUID.getZadaniaSortowanie() == 2) {
					      pUUID.setZadaniaSortowanie(3);
					      i.clear();
					      ZadaniaMethods.openInventoryZadania(p, i, 0);
					      return;
					    }
					    if (pUUID.getZadaniaSortowanie() == 3) {
					      pUUID.setZadaniaSortowanie(1);
					      i.clear();
					      ZadaniaMethods.openInventoryZadania(p, i, 0);
					      return;
					    }
					    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					  }
					}
					if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getDurability() == 13) {
					  if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aNastępna strona §e(")) {
					    String line = e.getCurrentItem().getItemMeta().getDisplayName();
					    String cos2 = ChatColor.stripColor(line);

					    cos2 = cos2.replaceAll("Następna strona \\(", "");
					    cos2 = cos2.replaceAll("\\)", "");
					    cos2 = cos2.replaceAll(" ", "");

					    Integer page = Integer.parseInt(cos2);
					    i.clear();
					    ZadaniaMethods.openInventoryZadania(p, i, page -1);
					    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
					    return;

					  }
					}
					if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getDurability() == 14) {
					  if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aPoprzednia strona §e(")) {
					    String line = e.getCurrentItem().getItemMeta().getDisplayName();
					    String cos2 = ChatColor.stripColor(line);

					    cos2 = cos2.replaceAll("Poprzednia strona \\(", "");
					    cos2 = cos2.replaceAll("\\)", "");
					    cos2 = cos2.replaceAll(" ", "");

					    Integer page = Integer.parseInt(cos2);
					    i.clear();
					    ZadaniaMethods.openInventoryZadania(p, i, page -1);
					    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_OFF, 1, 1);
					    return;

					  }
					}
				      }
				    }
				  }
				}
			      }
			    }
			  }
			}
		      }
  		  }
 	 }



}
