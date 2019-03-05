package seonjae.plugin.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.experimental.UtilityClass;
import seonjae.util.NumberUtil;

@UtilityClass
public class ItemUtil {
	public ItemStack make(ItemStack is, String name) {
		return make(is, name, null);
	}
	public ItemStack make(ItemStack is, List<String> lore) {
		return make(is, null, lore);
	}
	public ItemStack make(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		if (name != null) im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + name));
		if (lore != null) {
			List<String> list = new ArrayList<>();
			for (String l : lore) list.add(ChatColor.translateAlternateColorCodes('&', "&f" + l));
			im.setLore(list);
		}
		is.setItemMeta(im);
		return is;
	}
	public ItemStack make(int code) {
		return make(code, 1, (short) 0, null, null);
	}
	public ItemStack make(int code, int amount) {
		return make(code, amount, (short) 0, null, null);
	}
	public ItemStack make(int code, int amount, short damage) {
		return make(code, amount, damage, null, null);
	}
	public ItemStack make(int code, int amount, short damage, String name) {
		return make(code, amount, damage, name, null);
	}
	public ItemStack make(int code, int amount, short damage, List<String> lore) {
		return make(code, amount, damage, null, lore);
	}
	public ItemStack make(int code, int amount, String name) {
		return make(code, amount, (short) 0, name, null);
	}
	public ItemStack make(int code, int amount, String name, List<String> lore) {
		return make(code, amount, (short) 0, name, lore);
	}
	public ItemStack make(int code, int amount, List<String> lore) {
		return make(code, amount, (short) 0, null, lore);
	}
	public ItemStack make(int code, short damage) {
		return make(code, 1, damage, null, null);
	}
	public ItemStack make(int code, short damage, String name) {
		return make(code, 1, damage, name, null);
	}
	public ItemStack make(int code, short damage, String name, List<String> lore) {
		return make(code, 1, damage, name, lore);
	}
	public ItemStack make(int code, short damage, List<String> lore) {
		return make(code, 1, damage, null, lore);
	}
	public ItemStack make(int code, String name) {
		return make(code, 1, (short) 0, name, null);
	}
	public ItemStack make(int code, String name, List<String> lore) {
		return make(code, 1, (short) 0, name, lore);
	}
	public ItemStack make(int code, List<String> lore) {
		return make(code, 1, (short) 0, null, lore);
	}
	public ItemStack make(int code, int amount, short damage, String name, List<String> lore) {
		ItemStack is = new ItemStack(code, amount, damage);
		ItemMeta im = is.getItemMeta();
		if (name != null) im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + name));
		if (lore != null) {
			List<String> list = new ArrayList<>();
			for (String l : lore) list.add(ChatColor.translateAlternateColorCodes('&', "&f" + l));
			im.setLore(list);
		}
		is.setItemMeta(im);
		return is;
	}
	
	public boolean equals(ItemStack is1, ItemStack is2) {
		ItemStack a = is1.clone();
		ItemStack b = is2.clone();
		a.setAmount(1);
		b.setAmount(1);
		return a.equals(b);
	}
	public boolean equals(ItemStack is, int code) {
		return is.getTypeId() == code;
	}
	public boolean equals(ItemStack is, int code, short damage) {
		return is.getTypeId() == code && is.getDurability() == damage;
	}
	
	public void addEnchant(ItemStack is, int enchantcode, int level) {
		is.addUnsafeEnchantment(Enchantment.getById(enchantcode), level);
	}
	public void addEnchant(ItemStack is, Enchantment enchant, int level) {
		is.addUnsafeEnchantment(enchant, level);
	}
	public boolean hasEnchant(ItemStack is, int enchantcode) {
		return is.getEnchantments().containsKey(Enchantment.getById(enchantcode));
	}
	public boolean hasEnchant(ItemStack is, Enchantment enchant) {
		return is.getEnchantments().containsKey(enchant);
	}
	public ItemStack addEnchantOnEnchantBook(ItemStack is, int type, int level) {
		return addEnchantOnEnchantBook(is, Enchantment.getById(type), level);
	}
	public ItemStack addEnchantOnEnchantBook(ItemStack is, String name, int level) {
		return addEnchantOnEnchantBook(is, Enchantment.getByName(name), level);
	}
	public ItemStack addEnchantOnEnchantBook(ItemStack is, Enchantment enchant, int level) {
		EnchantmentStorageMeta esm = (EnchantmentStorageMeta) is.getItemMeta();
		esm.addStoredEnchant(enchant, level, false);
		is.setItemMeta(esm);
		return is;
	}
	public String toString(ItemStack is) {
		return is.getTypeId() + (is.getDurability() > 0 ? ":" + is.getDurability() : "") + (is.getAmount() > 1 ? " " + is.getAmount() : "");
	}
	public ItemStack toItemStack(String n) {
		String code;
		int amount = 1;
		if (n.contains(" ")) {
			code = n.split(" ")[0];
			Integer amoun = NumberUtil.getInteger(n.split(" ")[1]);
			if (amoun == null) return null;
			amount = amoun;
		} else code = n;
		if (code.contains(":")) {
			Integer item = NumberUtil.getInteger(code.split(":")[0]);
			Short damage = NumberUtil.getShort(code.split(":")[1]);
			if (item == null || damage == null) return null;
			return new ItemStack(item, amount, damage);
		} else {
			Integer item = NumberUtil.getInteger(code);
			if (item == null) return null;
			return new ItemStack(item, amount);
		}
	}
}
