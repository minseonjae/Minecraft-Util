package seonjae.plugin.util.category;

import lombok.Getter;

public enum NMSVersion {
	UNKNOWN("Unknown", 0),
	v1_5_R3("v1_5_R3", 153),
	v1_6_R1("v1_6_R1", 161),
	v1_6_R2("v1_6_R2", 162),
	v1_6_R3("v1_6_R3", 163),
	v1_7_R1("v1_7_R1", 171),
	v1_7_R2("v1_7_R2", 172),
	v1_7_R3("v1_7_R3", 173),
	v1_7_R4("v1_7_R4", 174),
	v1_8_R1("v1_8_R1", 181),
	v1_8_R2("v1_8_R2", 182),
	v1_8_R3("v1_8_R3", 183),
	v1_8_R4("v1_8_R4", 184),
	v1_9_R1("v1_9_R1", 191),
	v1_9_R2("v1_9_R2", 192),
	v1_10_R1("v1_10_R1", 1101),
	v1_11_R1("v1_11_R1", 1111),
	v1_12_R1("v1_12_R1", 1121),
	v1_12_R2("v1_12_R2", 1122);

	@Getter
	private String name;
	@Getter
	private int id;
	  
	private NMSVersion(String name, int id) {
		this.name = name;
	    this.id = id;
	}
	
	public boolean isLowerThan(NMSVersion target) {
		return id < target.getId();
	}
	public boolean isHigherThan(NMSVersion target) {
		return id > target.getId();
	}
	public boolean isInRange(NMSVersion oldVersion, NMSVersion newVersion) {
		return (oldVersion.getId() <= id) && (id <= newVersion.getId());
	}
	
	public static NMSVersion getByName(String name) {
	    for (NMSVersion version : values()) if (version.getName().equals(name)) return version;
	    return null;
	}
	public static NMSVersion getById(int id) {
	    for (NMSVersion version : values()) if (version.getId() == id) return version;
	    return null;
	}
}
