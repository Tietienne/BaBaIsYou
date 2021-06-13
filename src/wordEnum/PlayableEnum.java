package wordEnum;

import java.util.Objects;

/**
 * Enumeration of all PlayableElem existing
 * @author Etienne
 * @version 1.0
 */
public enum PlayableEnum {
	/**
	 * PlayableElem of Baba
	 */
	baba("Baba_Play"),
	/**
	 * PlayableElem of Flag
	 */
	flag("Flag_Play"),
	/**
	 * PlayableElem of Wall
	 */
	wall("Wall_Play"),
	/**
	 * PlayableElem of Water
	 */
	water("Water_Play"),
	/**
	 * PlayableElem of Skull
	 */
	skull("Skull_Play"),
	/**
	 * PlayableElem of Lava
	 */
	lava("Lava_Play"),
	/**
	 * PlayableElem of Rock
	 */
	rock("Rock_Play"),
	/**
	 * PlayableElem of Floor
	 */
	floor("Floor_Play"),
	/**
	 * PlayableElem of Grass
	 */
	grass("Grass_Play"),
	/**
	 * PlayableElem of Brain
	 */
	brain("Brain_Play");
	
	/**
	 * The file name of the Enumeration as a String
	 */
	private final String file_name;
	
	private PlayableEnum(String name) {
		Objects.requireNonNull(name);
		this.file_name = name;
	}
	
	@Override
	public String toString() {
		return file_name;
	}
}
