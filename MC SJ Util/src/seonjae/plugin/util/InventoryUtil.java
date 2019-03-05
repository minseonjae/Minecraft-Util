package seonjae.plugin.util;

import java.util.ListIterator;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InventoryUtil {
	public boolean isFull(Inventory inventory) {
		for (int i = 0; i < inventory.getSize(); i++) if (inventory.getItem(i) != null) return false;
		return true;
	}

	public boolean hasItem(Inventory inventory, int code) {
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) if (ItemUtil.equals(list.next(), code)) return true;
		return false;
	}
	public boolean hasItem(Inventory inventory, int code, short damage) {
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) if (ItemUtil.equals(list.next(), code, damage)) return true;
		return false;
	}
	public boolean hasItem(Inventory inventory, ItemStack is) {
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) if (ItemUtil.equals(is, list.next())) return true;
		return false;
	}
	public ItemStack getItem(Inventory inventory, int code) {
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) {
			ItemStack next = list.next();
			if (ItemUtil.equals(next, code)) return next;
		}
		return null;
	}
	public ItemStack getItem(Inventory inventory, int code, short damage) {
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) {
			ItemStack next = list.next();
			if (ItemUtil.equals(next, code, damage)) return next;
		}
		return null;
	}
	public ItemStack getItem(Inventory inventory, ItemStack is) {
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) {
			ItemStack next = list.next();
			if (ItemUtil.equals(is, next)) return next;
		}
		return null;
	}
	public Integer getItemSlot(Inventory inventory, int code) {
		for (int i = 0; i < inventory.getSize(); i++) if (ItemUtil.equals(inventory.getItem(i), code)) return i;
		return null;
	}
	public Integer getItemSlot(Inventory inventory, int code, short damage) {
		for (int i = 0; i < inventory.getSize(); i++) if (ItemUtil.equals(inventory.getItem(i), code, damage)) return i;
		return null;
	}
	public Integer getItemSlot(Inventory inventory, ItemStack is) {
		for (int i = 0; i < inventory.getSize(); i++) if (ItemUtil.equals(is, inventory.getItem(i))) return i;
		return null;
	}
	public int getItemAmount(Inventory inventory, int code) {
		int amount = 0;
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) {
			ItemStack next = list.next();
			if (ItemUtil.equals(next, code)) amount += next.getAmount();
		}
		return amount;
	}
	public int getItemAmount(Inventory inventory, int code, short damage) {
		int amount = 0;
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) {
			ItemStack next = list.next();
			if (ItemUtil.equals(next, code, damage)) amount += next.getAmount();
		}
		return amount;
	}
	public int getItemAmount(Inventory inventory, ItemStack is) {
		int amount = 0;
		for (ListIterator<ItemStack> list = inventory.iterator(); list.hasNext();) {
			ItemStack next = list.next();
			if (ItemUtil.equals(is, next)) amount += next.getAmount();
		}
		return amount;
	}
	public void removeItem(Inventory inventory, int code, int amount) {
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack il = inventory.getItem(i);
			if (il == null) continue;
			if (!ItemUtil.equals(il, code)) continue;
			if (il.getAmount() > amount) {
				il.setAmount(il.getAmount() - amount);
				break;
			} else {
				inventory.setItem(i, null);
				amount -= il.getAmount();
			}
		}
	}
	public void removeItem(Inventory inventory, int code, short damage, int amount) {
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack il = inventory.getItem(i);
			if (il == null) continue;
			if (!ItemUtil.equals(il, code, damage)) continue;
			if (il.getAmount() > amount) {
				il.setAmount(il.getAmount() - amount);
				break;
			} else {
				inventory.setItem(i, null);
				amount -= il.getAmount();
			}
		}
	}
	public void removeItem(Inventory inventory, ItemStack is) {
		int amount = is.getAmount();
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack il = inventory.getItem(i);
			if (il == null) continue;
			if (!ItemUtil.equals(is, il)) continue;
			if (il.getAmount() > amount) {
				il.setAmount(il.getAmount() - amount);
				break;
			} else {
				inventory.setItem(i, null);
				amount -= il.getAmount();
			}
		}
	}
}
