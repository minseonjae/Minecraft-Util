package seonjae.plugin.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import seonjae.plugin.util.category.NMSVersion;
import seonjae.util.ReflectionUtil;

@UtilityClass
public class BukkitUtil {
	
	@Getter
	private static NMSVersion nMSVersion = NMSVersion.UNKNOWN;
	
	public boolean checkKCauldronServer() {
		try {
			Class.forName("kcauldron.KCauldron");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public void loadNMSVersion() {
		try {
			Class<?> clazz = Class.forName("org.bukkit.Bukkit");
			Field f = ReflectionUtil.getDeclaredField(clazz, "server");
			f.setAccessible(true);
			String ver = f.get(null).getClass().getPackage().getName();
			nMSVersion = NMSVersion.getByName(ver.substring(ver.lastIndexOf(".") + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@SneakyThrows(Exception.class)
	public List<Player> getOnlinePlayers() {
		ArrayList<Player> list = new ArrayList<>();
		Object result = ReflectionUtil.getMethod(Bukkit.class, "getOnlinePlayers").invoke(null);
		if (result instanceof Player[]) for (Player p : (Player[]) result) list.add(p);
		else for (Player p : (Collection<Player>) result) list.add(p);
		return list;
	}
	public Object getHandle(World w) {
		try {
			return w.getClass().getMethod("getHandle").invoke(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Object getHandle(Player p) {
		try {
			return p.getClass().getMethod("getHandle").invoke(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void sendPacket(Player player, Object packet) {
		try {
			Object hd = getHandle(player);
			Object pc = hd.getClass().getField("playerConnection").get(hd);
			pc.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(pc, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + nMSVersion.getName() + "." + name);
		} catch (Exception e) {
			return null;
		}
	}
	public Class<?> getBukkitClass(String name) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + nMSVersion.getName() + "." + name);
		} catch (Exception e) {
			return null;
		}
	}
}
