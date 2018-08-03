package zadania.methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.Main;
import main.User;

public class ZadaniaMethods {

	public static int allQuests = 40;
	
	
	public static void openInventoryZadania(Player p, Inventory inv, int strona) {
		User pUUID = User.get(p.getName());
		Integer slot = 0;
		Integer liczba = strona * 36;
		Integer fullSizePage = 1;
		
		

		
		List<ItemStack> ZadaniaItems = new ArrayList<ItemStack>();
		List<Integer> ZadaniaKolejnosc = new ArrayList<Integer>();
		List<Integer> ZadaniaLista = new ArrayList<Integer>();
		
		List<String> ZadaniaKolejnosc2 = new ArrayList<String>();
		List<Integer> ZadaniaLista2 = new ArrayList<Integer>();
		
		
		List<ItemStack> ZadaniaZablokowaneItems = new ArrayList<ItemStack>();
		List<ItemStack> ZadaniaNieRozpoczeteItems = new ArrayList<ItemStack>();
		List<ItemStack> ZadaniaRozpoczeteItems = new ArrayList<ItemStack>();
		List<ItemStack> ZadaniaWykonaneItems = new ArrayList<ItemStack>();
		
		
		Main.openConnection();
		if (pUUID.getZadaniaSortowanie() == 1) {
			try {
				ResultSet rs;
				rs = Main.conn.createStatement().executeQuery("SELECT * FROM `zadania`");
				while (rs.next()){
					Integer cos = rs.getInt("Poziom");
					ZadaniaKolejnosc.add(cos);
					ZadaniaLista.add(rs.getInt("ID"));
					
				}
				Collections.sort(ZadaniaKolejnosc);
				
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	
			Integer numerZadania = 1;
			try {
				for (Integer loopItem : ZadaniaKolejnosc) {
					ResultSet rs;
					rs = Main.conn.createStatement().executeQuery("SELECT * FROM `zadania`");
	
					cos : while (rs.next()) {
						Integer poziom = rs.getInt("Poziom");
						String nazwa = rs.getString("NazwaZadania");
						Integer ID = rs.getInt("ID");
						if (loopItem == poziom) {
							if (ZadaniaLista.contains(ID)) {
								
						        String status = "§cWystąpił błąd";
								ItemStack itemi = new ItemStack(Material.BARRIER, 1);

								if (pUUID.getZadanie(ID).equals("Zablokowane")) {
									itemi = new ItemStack(Material.BARRIER, 1);
									status = "§cZablokowane..";
								}
								if (pUUID.getZadanie(ID).equals("Nie rozpoczęte")) {
									itemi = new ItemStack(Material.BOOK, 1);
									status = "§7Nie rozpoczęte..";
								}
								if (pUUID.getZadanie(ID).equals("Rozpoczęte")) {
									itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
									status = "§2Rozpoczęte..";
								}
								if (pUUID.getZadanie(ID).equals("Wykonane")) {
									itemi = new ItemStack(Material.WOOL, 1, (short) 13);
									status = "§aWykonane..";
								}
								else if (!(pUUID.getZadanie(ID).equals("Zablokowane") || pUUID.getZadanie(ID).equals("Nie rozpoczęte") || pUUID.getZadanie(ID).equals("Rozpoczęte") || pUUID.getZadanie(ID).equals("Wykonane"))){
									itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
									status = "§2Rozpoczęte..";
									
								}
								
								
								
								
								
								
								
								
								
								
								
								
								
								ItemMeta meta = itemi.getItemMeta();
								meta.setDisplayName("§8[§7#" + numerZadania + "§8] §3" + nazwa);
								String tresc = rs.getString("Tresc");
								ArrayList<String> Lore = new ArrayList<String>();
								Lore.add(status);
								Lore.add("");
								if (pUUID.getPoziom() >= poziom) {
									Lore.add("§a✔ §7Lv. Min: §f" + poziom);
								} else if (pUUID.getPoziom() < poziom) {
									Lore.add("§c✘ §7Lv. Min: §f" + poziom);
								}
								Lore.add("");
								if (tresc.contains("α")) {
									String[] tresc2 = tresc.split("α");
									if (status.contains("Zablokowane") || (status.contains("Nie rozpoczęte"))) {
										String tresc3 = tresc2[0];
										if (tresc3.contains("β")) {
											for (String s : tresc3.split("β")) {
												Lore.add(s);
											}
										} if (!tresc3.contains("β")) {
											Lore.add(tresc3);
										}
									}
									if (status.contains("Rozpoczęte")) {
										String tresc3 = tresc2[Integer.parseInt(pUUID.getZadanie(ID))];
										if (tresc3.contains("β")) {
											for (String s : tresc3.split("β")) {
												Lore.add(s);
											}
										} if (!tresc3.contains("β")) {
											Lore.add(tresc3);
										}
									}
								}
								meta.setLore(Lore);
								itemi.setItemMeta(meta);
								ZadaniaItems.add(itemi);
								ZadaniaLista.remove(ID);
								numerZadania++;
								break cos;
							}
						} 
					}
				}
	
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}
		
		
		
		if (pUUID.getZadaniaSortowanie() == 2) {
			try {
				ResultSet rs;
				rs = Main.conn.createStatement().executeQuery("SELECT * FROM `zadania`");
				while (rs.next()){
					String cos = rs.getString("NazwaZadania");
					ZadaniaKolejnosc2.add(cos);
					ZadaniaLista2.add(rs.getInt("ID"));
					
				}
				Collections.sort(ZadaniaKolejnosc2);
				
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	
			Integer numerZadania = 1;
			try {
				for (String loopItem : ZadaniaKolejnosc2) {
					ResultSet rs;
					rs = Main.conn.createStatement().executeQuery("SELECT * FROM `zadania`");
	
					cos : while (rs.next()) {
						Integer poziom = rs.getInt("Poziom");
						String nazwa = rs.getString("NazwaZadania");
						Integer ID = rs.getInt("ID");
						if (loopItem.equals(nazwa)) {
							if (ZadaniaLista2.contains(ID)) {

						        String status = "§cWystąpił błąd";
								ItemStack itemi = new ItemStack(Material.BARRIER, 1);

								if (pUUID.getZadanie(ID).equals("Zablokowane")) {
									itemi = new ItemStack(Material.BARRIER, 1);
									status = "§cZablokowane..";
								}
								if (pUUID.getZadanie(ID).equals("Nie rozpoczęte")) {
									itemi = new ItemStack(Material.BOOK, 1);
									status = "§7Nie rozpoczęte..";
								}
								if (pUUID.getZadanie(ID).equals("Rozpoczęte")) {
									itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
									status = "§2Rozpoczęte..";
								}
								if (pUUID.getZadanie(ID).equals("Wykonane")) {
									itemi = new ItemStack(Material.WOOL, 1, (short) 13);
									status = "§aWykonane..";
								}
								else if (!(pUUID.getZadanie(ID).equals("Zablokowane") || pUUID.getZadanie(ID).equals("Nie rozpoczęte") || pUUID.getZadanie(ID).equals("Rozpoczęte") || pUUID.getZadanie(ID).equals("Wykonane"))){
									itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
									status = "§2Rozpoczęte..";
									
								}
								
								
								
								
								
								
								
								
								
								
								
								
								
								ItemMeta meta = itemi.getItemMeta();
								meta.setDisplayName("§8[§7#" + numerZadania + "§8] §3" + nazwa);
								String tresc = rs.getString("Tresc");
								ArrayList<String> Lore = new ArrayList<String>();
								Lore.add(status);
								Lore.add("");
								if (pUUID.getPoziom() >= poziom) {
									Lore.add("§a✔ §7Lv. Min: §f" + poziom);
								} else if (pUUID.getPoziom() < poziom) {
									Lore.add("§c✘ §7Lv. Min: §f" + poziom);
								}
								Lore.add("");
								if (tresc.contains("α")) {
									String[] tresc2 = tresc.split("α");
									if (status.contains("Zablokowane") || (status.contains("Nie rozpoczęte"))) {
										String tresc3 = tresc2[0];
										if (tresc3.contains("β")) {
											for (String s : tresc3.split("β")) {
												Lore.add(s);
											}
										} if (!tresc3.contains("β")) {
											Lore.add(tresc3);
										}
									}
									if (status.contains("Rozpoczęte")) {
										String tresc3 = tresc2[Integer.parseInt(pUUID.getZadanie(ID))];
										if (tresc3.contains("β")) {
											for (String s : tresc3.split("β")) {
												Lore.add(s);
											}
										} if (!tresc3.contains("β")) {
											Lore.add(tresc3);
										}
									}
								}
								meta.setLore(Lore);
								itemi.setItemMeta(meta);
								ZadaniaItems.add(itemi);
								ZadaniaLista2.remove(ID);
								numerZadania++;
								break cos;
							}
						} 
					}
				}
	
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}
		
		
		
		if (pUUID.getZadaniaSortowanie() == 3) {
			try {
				ResultSet rs;
				rs = Main.conn.createStatement().executeQuery("SELECT * FROM `zadania`");
				while (rs.next()){
					Integer cos = rs.getInt("Poziom");
					ZadaniaKolejnosc.add(cos);
					ZadaniaLista.add(rs.getInt("ID"));
					
				}
				Collections.sort(ZadaniaKolejnosc);
				
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	
			Integer numerZadania = 1;
			try {
				for (Integer loopItem : ZadaniaKolejnosc) {
					ResultSet rs;
					rs = Main.conn.createStatement().executeQuery("SELECT * FROM `zadania`");
	
					cos : while (rs.next()) {
						Integer poziom = rs.getInt("Poziom");
						String nazwa = rs.getString("NazwaZadania");
						Integer ID = rs.getInt("ID");
						if (loopItem == poziom) {
							if (ZadaniaLista.contains(ID)) {
								
						        String status = "§cWystąpił błąd";
								ItemStack itemi = new ItemStack(Material.BARRIER, 1);

								if (pUUID.getZadanie(ID).equals("Zablokowane")) {
									itemi = new ItemStack(Material.BARRIER, 1);
									status = "§cZablokowane..";
								}
								if (pUUID.getZadanie(ID).equals("Nie rozpoczęte")) {
									itemi = new ItemStack(Material.BOOK, 1);
									status = "§7Nie rozpoczęte..";
								}
								if (pUUID.getZadanie(ID).equals("Rozpoczęte")) {
									itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
									status = "§2Rozpoczęte..";
								}
								if (pUUID.getZadanie(ID).equals("Wykonane")) {
									itemi = new ItemStack(Material.WOOL, 1, (short) 13);
									status = "§aWykonane..";
								}
								else if (!(pUUID.getZadanie(ID).equals("Zablokowane") || pUUID.getZadanie(ID).equals("Nie rozpoczęte") || pUUID.getZadanie(ID).equals("Rozpoczęte") || pUUID.getZadanie(ID).equals("Wykonane"))){
									itemi = new ItemStack(Material.BOOK_AND_QUILL, 1);
									status = "§2Rozpoczęte..";
									
								}
								
								
								
								
								
								
								
								
								
								
								
								
								
								ItemMeta meta = itemi.getItemMeta();
								meta.setDisplayName("§8[§7#" + numerZadania + "§8] §3" + nazwa);
								String tresc = rs.getString("Tresc");
								ArrayList<String> Lore = new ArrayList<String>();
								Lore.add(status);
								Lore.add("");
								if (pUUID.getPoziom() >= poziom) {
									Lore.add("§a✔ §7Lv. Min: §f" + poziom);
								} else if (pUUID.getPoziom() < poziom) {
									Lore.add("§c✘ §7Lv. Min: §f" + poziom);
								}
								Lore.add("");
								if (tresc.contains("α")) {
									String[] tresc2 = tresc.split("α");
									if (status.contains("Zablokowane") || (status.contains("Nie rozpoczęte"))) {
										String tresc3 = tresc2[0];
										if (tresc3.contains("β")) {
											for (String s : tresc3.split("β")) {
												Lore.add(s);
											}
										} if (!tresc3.contains("β")) {
											Lore.add(tresc3);
										}
									}
									if (status.contains("Rozpoczęte")) {
										String tresc3 = tresc2[Integer.parseInt(pUUID.getZadanie(ID))];
										if (tresc3.contains("β")) {
											for (String s : tresc3.split("β")) {
												Lore.add(s);
											}
										} if (!tresc3.contains("β")) {
											Lore.add(tresc3);
										}
									}
								}
								meta.setLore(Lore);
								itemi.setItemMeta(meta);
								ZadaniaLista.remove(ID);
								numerZadania++;
								
								if (pUUID.getZadanie(ID).equals("Zablokowane")) {
									ZadaniaZablokowaneItems.add(itemi);
								}
								if (pUUID.getZadanie(ID).equals("Nie rozpoczęte")) {
									ZadaniaNieRozpoczeteItems.add(itemi);
								}
								if (pUUID.getZadanie(ID).equals("Wykonane")) {
									ZadaniaWykonaneItems.add(itemi);
								}
								else if (!(pUUID.getZadanie(ID).equals("Zablokowane") || pUUID.getZadanie(ID).equals("Nie rozpoczęte") || pUUID.getZadanie(ID).equals("Rozpoczęte") || pUUID.getZadanie(ID).equals("Wykonane"))){
									ZadaniaRozpoczeteItems.add(itemi);
								}
								break cos;
							}
						} 
					}
				}
				ZadaniaItems.addAll(ZadaniaRozpoczeteItems);
				ZadaniaItems.addAll(ZadaniaNieRozpoczeteItems);
				ZadaniaItems.addAll(ZadaniaZablokowaneItems);
				ZadaniaItems.addAll(ZadaniaWykonaneItems);
	
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}
		
		
		
		
			
		if (ZadaniaItems.size() >= 1) {
			for (int fullSize = ZadaniaItems.size(); fullSize > 36;) {
				fullSizePage++;
				fullSize = fullSize - 36;

			}
			if (ZadaniaItems.size() > liczba) {
				for (ItemStack is : ZadaniaItems) {
					if (liczba >= 1) {
						liczba--;
					} else if (liczba == 0) {

						if (slot <= 35) {

							inv.setItem(slot, is);

							slot++;
						}
					}

				}

			} else if (ZadaniaItems.size() <= liczba) {
				ItemStack item = new ItemStack(Material.BARRIER, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§4Błąd!");
				ArrayList<String> Lore = new ArrayList<String>();
				Lore.add("§cNie ma takiej strony!");
				meta.setLore(Lore);
				item.setItemMeta(meta);

				inv.setItem(22, item);

			} else if (ZadaniaItems.size() == 0) {
				ItemStack item = new ItemStack(Material.BARRIER, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§4Błąd!");
				ArrayList<String> Lore = new ArrayList<String>();
				Lore.add("§cWystąpił problem! Skontaktuj się z administracją!");
				meta.setLore(Lore);
				item.setItemMeta(meta);

				inv.setItem(22, item);
			}

		}

		Integer pageCurrent = strona + 1;
		Integer pageNext = pageCurrent + 1;
		Integer pageBack = pageCurrent - 1;

		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);

		ItemStack item2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.setDisplayName("§aPoprzednia strona §e(" + pageBack + ")");
		item2.setItemMeta(meta2);

		ItemStack item3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
		ItemMeta meta3 = item3.getItemMeta();
		meta3.setDisplayName("§aNastępna strona §e(" + pageNext + ")");
		item3.setItemMeta(meta3);
		
		ItemStack item4 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta meta4 = item4.getItemMeta();
		meta4.setDisplayName(" ");
		item4.setItemMeta(meta4);
		
		ItemStack item5 = new ItemStack(Material.CARPET, 1, (short) 15);
		ItemMeta meta5 = item5.getItemMeta();
		meta5.setDisplayName("§6Zmień sposób sortowania.");
		ArrayList<String> lore5 = new ArrayList<String>();
		String currentSorting = "Brak";
		String nextSorting = "Brak";
		if (pUUID.getZadaniaSortowanie() == 1) {
			currentSorting = "poziomu";
			nextSorting = "nazwy";
		}
		if (pUUID.getZadaniaSortowanie() == 2) {
			currentSorting = "nazwy";
			nextSorting = "statusu";
		}
		if (pUUID.getZadaniaSortowanie() == 3) {
			currentSorting = "statusu";
			nextSorting = "poziomu";
		}
		lore5.add("");
		lore5.add("§7Sortowanie według: §f" + currentSorting + "§7.");
		lore5.add("");
		lore5.add("§7Kliknij aby posortować według §f" + nextSorting + "§7.");
		meta5.setLore(lore5);
		item5.setItemMeta(meta5);
		
		inv.setItem(50, item5);
		
		
		double questsDone = 0;
		int questsDone2 = 0;
		
		for (int i = 0; i <= allQuests;) {
			if (pUUID.getZadanie(i).equals("Wykonane")) {
				questsDone ++;
				questsDone2 ++;
			} 
			i++;
		}
		
		
		
		
		
		double poczatek1 = questsDone;
		double max1 = (double) allQuests;
		double roznica1 = (poczatek1 / max1);
		double nibyProcent1 = (roznica1 * 100);
		int procent1 = (int) nibyProcent1;
		roznica1 = (roznica1 *100) /5;
		String LVLWyglad1 = "";
		String LVLWyglad2 = "";
		for (long n = 1; n < 20;) {
			if (roznica1 >= 1) {
				if (LVLWyglad1.equals("")) {
					LVLWyglad1 = "I";
					roznica1--;
				} else {
					LVLWyglad1 = LVLWyglad1 + "I";
					roznica1--;
				}
			}
			
			if (roznica1 < 1) {
				if (LVLWyglad2.equals("")) {
					LVLWyglad2 = "I";
				} else {
					LVLWyglad2 = LVLWyglad2 + "I";
				}
			}
			n++;
		}
		
		
		
		
		
		
		
		ItemStack item6 = new ItemStack(Material.WOOL, 1, (short) 13);
		ItemMeta meta6 = (ItemMeta) item6.getItemMeta();
		meta6.setDisplayName("§e⚝ §6Wykonane zadania: §7" + questsDone2 + "§8/§7" + allQuests + " §e⚝");
		ArrayList<String> lore6 = new ArrayList<String>();
		lore6.add("§8-----------------------------");
		lore6.add("");
		lore6.add("      §e-----§6[§a" + LVLWyglad1 + "§8" + LVLWyglad2 + "§6]§e-----");
		lore6.add("                             §8(§7" + procent1 + "%§8)");
		lore6.add("");
		lore6.add("§8-----------------------------");
		meta6.setLore(lore6);
		item6.setItemMeta(meta6);
		
		inv.setItem(48, item6);
		
		
		
		
		
		
		

		
		for (int i = 36; i <= 44; i++) {
			inv.setItem(i, item4);
		}
		
		inv.setItem(46, item);
		inv.setItem(47, item);
		inv.setItem(49, item);
		inv.setItem(51, item);
		inv.setItem(52, item);

		if (pageCurrent < fullSizePage) {
			inv.setItem(53, item3);
		}
		if (pageCurrent >= fullSizePage) {
			inv.setItem(53, item);
		}

		if (pageCurrent > 1) {
			inv.setItem(45, item2);
		}
		if (pageCurrent <= 1) {
			inv.setItem(45, item);
		}

		Main.closeConnection();
	}
}
