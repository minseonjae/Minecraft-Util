package seonjae.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LocationUtil {
	public double getDistance(double X1, double Z1, double X2, double Z2) {
		return Math.sqrt(Math.pow(X1 - X2, 2) + Math.pow(Z1 - Z2, 2));
	}
	public double getDistance(double X1, double Y1, double Z1, double X2, double Y2, double Z2) {
		return Math.sqrt(Math.pow(getDistance(X1, Z1, X2, Z2), 2) + Math.pow(Y1 - Y2, 2));
	}
	public int getQuadrant(double X1, double Z1, double X2, double Z2) {
		boolean xp = X1 < X2, zp = Z1 < Z2;
		return xp && zp ? 1 : !xp && zp ? 2 : !xp && !zp ? 3 : 4;
	}
	public double getSideAngle(double X1, double Z1, double X2, double Z2) {
		int quadrant = getQuadrant(X1, Z1, X2, Z2);
		return (quadrant == 1 ? 270 : quadrant == 2 ? 0 : quadrant == 3 ? 90 : 180) + (Math.toDegrees(Math.atan(quadrant % 2 == 1 ? Math.abs(Z1 - Z2) / Math.abs(X1 - X2) : Math.abs(X1 - X2) / Math.abs(Z1 - Z2))));
	}
	public double getUpDownAngle(double X1, double Y1, double Z1, double X2, double Y2, double Z2) {
		return Math.toDegrees((Y2 - Y1) / getDistance(X1, Y1, Z1, X2, Y2, Z2));
	}
	
	public String toString(Location l) {
		return l.getWorld().getName() + ", " + l.getX() + ", " + l.getY() + ", " + l.getZ() + ", " + l.getYaw() + ", " + l.getPitch();
	}
	public Location toLocation(String n) {
		String[] m = n.split(", ");
		return new Location(Bukkit.getWorld(m[0]), Double.parseDouble(m[1]), Double.parseDouble(m[2]), Double.parseDouble(m[3]), Float.parseFloat(m[4]), Float.parseFloat(m[5]));				
	}
}
