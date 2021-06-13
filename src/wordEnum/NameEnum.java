package wordEnum;

import java.util.Objects;

/**
 * Enumeration of all Name Blocks existing
 * @author Etienne
 * @version 1.0
 */
public enum NameEnum {
	/**
	 * Block Name of Baba
	 */
	Baba("Baba_Name"),
	/**
	 * Block Name of Flag
	 */
	Flag("Flag_Name"),
	/**
	 * Block Name of Wall
	 */
	Wall("Wall_Name"),
	/**
	 * Block Name of Water
	 */
	Water("Water_Name"),
	/**
	 * Block Name of Skull
	 */
	Skull("Skull_Name"),
	/**
	 * Block Name of Lava
	 */
	Lava("Lava_Name"),
	/**
	 * Block Name of Rock
	 */
	Rock("Rock_Name"),
	/**
	 * Block Name of Brain
	 */
	Brain("Brain_Name");
	
	/**
	 * The file name of the Enumeration as a String
	 */
	private final String file_name;
	
	private NameEnum(String name) {
		Objects.requireNonNull(name);
		this.file_name = name;
	}
	
	@Override
	public String toString() {
		return file_name;
	}
}
