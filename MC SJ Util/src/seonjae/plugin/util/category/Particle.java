package seonjae.plugin.util.category;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import seonjae.plugin.util.BukkitUtil;
import seonjae.plugin.util.LocationUtil;
import seonjae.util.ReflectionUtil;

public enum Particle {
	EXPLODE_NORMAL("explode", 0, NMSVersion.UNKNOWN),
	EXPLODE_LARGE("largeexplode", 1, NMSVersion.UNKNOWN),
	EXPLODE_HUGE("hugeeexplosion", 2, NMSVersion.UNKNOWN),
	FIREWORKS_SPARK("fireworksSpark", 3, NMSVersion.UNKNOWN),
	WATER_BUBBLE("bubble", 4, NMSVersion.UNKNOWN),
	WATER_SPLASH("splash", 5, NMSVersion.UNKNOWN),
	WATER_WAKE("wake", 6, NMSVersion.v1_7_R1),
	SUSPENDED("suspended", 7, NMSVersion.v1_7_R1),
	SUSPENDED_DEPTH("depthSuspend", 8, NMSVersion.v1_8_R1),
	CRIT("crit", 9, NMSVersion.UNKNOWN),
	CIRT_MAGIC("magicCrit", 10, NMSVersion.UNKNOWN),
	SMOKE("smoke", 11, NMSVersion.UNKNOWN),
	SMOKE_LARGE("largesmoke", 12, NMSVersion.UNKNOWN),
	SPELL("spell", 13, NMSVersion.UNKNOWN),
	SPELL_INSTANT("instantSpell", 14, NMSVersion.UNKNOWN),
	SPELL_MOB("mobSpell", 15, NMSVersion.UNKNOWN),
	SPELL_MOB_AMBIENT("mobSpellAmbient", 16, NMSVersion.UNKNOWN),
	SPELL_WITCH("witchMagic", 17, NMSVersion.UNKNOWN),
	DRIP_WATER("dripWater", 18, NMSVersion.UNKNOWN),
	DRIP_LAVA("dripLava", 19, NMSVersion.UNKNOWN),
	VILLAGER_ANGRY("angryVillager", 20, NMSVersion.UNKNOWN),
	VILLAGER_HAPPY("happyVillager", 21, NMSVersion.UNKNOWN),
	TOWN_AURA("townarua", 22, NMSVersion.UNKNOWN),
	NOTE("note", 23, NMSVersion.UNKNOWN),
	PORTAL("portal", 24, NMSVersion.UNKNOWN),
	ENCHANT_TABLE("enchantmenttable", 25, NMSVersion.UNKNOWN),
	FLAME("flame", 26, NMSVersion.UNKNOWN),
	LAVA("lava", 27, NMSVersion.UNKNOWN),
	FOOTSTEP("footstep", 28, NMSVersion.UNKNOWN),
	CLOUD("cloud", 29, NMSVersion.UNKNOWN),
	RED_DUST("reddust", 30, NMSVersion.UNKNOWN),
	SNOWBALL("snowballpoof", 31, NMSVersion.UNKNOWN),
	SNOW_SHOVEL("snowshovel", 32, NMSVersion.UNKNOWN),
	SLIME("slime", 33, NMSVersion.UNKNOWN),
	HEART("heart", 34, NMSVersion.UNKNOWN),
	BARRIER("barrier", 35, NMSVersion.v1_8_R1),
	ITEM_CRACK("iconcrack", 36, NMSVersion.UNKNOWN),
	BLOCK_CRACK("blockcrack", 37, NMSVersion.UNKNOWN),
	BLOCK_DUST("blockdust", 38, NMSVersion.v1_7_R1),
	WATER_DROP("droplet", 39, NMSVersion.v1_8_R1),
	ITEM_TAKE("take", 40, NMSVersion.v1_8_R1),
	MOB_APPEARANCE("mobappearance", 41, NMSVersion.v1_8_R1),
	DRAGON_BREATH("dragonbreath", 42, NMSVersion.v1_9_R1),
	END_ROD("endRod", 43, NMSVersion.v1_9_R1),
	DAMAGE_INDICATOR("damageIndicator", 44, NMSVersion.v1_9_R1),
	SWEEP_ATTACK("sweepAttack", 45, NMSVersion.v1_9_R1),
	FALLING_DUST("fallingdust", 46, NMSVersion.v1_10_R1);
	
	@Getter
	private String name;
	@Getter
	private int id;
	@Getter
	private NMSVersion version;
	
	private Particle(String name, int id, NMSVersion version) {
		this.name = name;
		this.id = id;
		this.version = version;
	}
	
	public static boolean isSupported(Particle type) {
		return BukkitUtil.getNMSVersion().getId() >= type.getVersion().getId();
	}
	public static List<Particle> getSupportedParticles() {
		List<Particle> list = new ArrayList<>();
		for (Particle type : values()) if (isSupported(type)) list.add(type);
		return list;
	}
	public static Particle getByName(String name) {
		for (Particle p : values()) if (p.getName().equalsIgnoreCase(name)) return p;
		return null;
	}
	
	public void play(Player p, Location location, float speed, int count) {
		if (p.getWorld().equals(location.getWorld())) play(p, location.getX(), location.getY(), location.getZ(), 0, 0, 0, speed, count);
	}
	public void play(Player p, Location location, float offsetx, float offsety, float offsetz, float speed, int count) {
		if (p.getWorld().equals(location.getWorld())) play(p, location.getX(), location.getY(), location.getZ(), offsetx, offsety, offsetz, speed, count);
	}
	public void play(Location location, float speed, int count) {
		play(location.getX(), location.getY(), location.getZ(), speed, count);
	}
	public void play(Location location, float offsetx, float offsety, float offsetz, float speed, int count) {
		play(location.getX(), location.getY(), location.getZ(), offsetx, offsety, offsetz, speed, count);
	}
	public void play(double x, double y, double z, float speed, int count) {
		play(x, y, z, 0, 0, 0, speed, count);
	}
	public void play(double x, double y, double z, float offsetx, float offsety, float offsetz, float speed, int count) {
		for(Player player : BukkitUtil.getOnlinePlayers()) play(player, x, y, z, offsetx, offsety, offsetz, speed, count);
	}
	public void play(Player p, double x, double y, double z, float offsetx, float offsety, float offsetz, float speed, int count) {
		try {
			if (LocationUtil.getDistance(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), x, y, z) > 24) return;
			if (!Particle.isSupported(this)) return;
			NMSVersion ver = BukkitUtil.getNMSVersion();
			Object packet = BukkitUtil.getNMSClass((ver.getId() < 171 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles")).newInstance();
			for(Field field : packet.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				switch(field.getName()) {
	                case "a": 
	                	if (ver.getId() < 181) field.set(packet, name);
	                	else {
	                		Class<?> clazz = BukkitUtil.getNMSClass("EnumParticle");
	                		field.set(packet, ReflectionUtil.getMethod(clazz, "a", int.class).invoke(clazz, id));
	                	}
	                break;
	                case "b": 
	                	field.setFloat(packet, (float) x); 
	                break;
	                case "c": 
	                	field.setFloat(packet, (float) y); 
	                break;
	                case "d": 
	                	field.setFloat(packet, (float) z); 
	                break;
	                case "e": 
	                	field.setFloat(packet, offsetx); 
	                break;
	                case "f": 
	                	field.setFloat(packet, offsety); 
	                break;
	                case "g": 
	                	field.setFloat(packet, offsetz); 
	                break;
	                case "h": 
	                	field.setFloat(packet, speed); 
	                break;
	                case "i": 
	                	field.setInt(packet, count); 
	                break;
	            }
	        }
			BukkitUtil.sendPacket(p, packet);
        } catch(Exception e) {
		    e.printStackTrace();
	    }
	}
}
