package wordEnum;

import java.util.Objects;

public enum PlayableEnum {
	baba("Baba_Play"),
	flag("Flag_Play"),
	wall("Wall_Play"),
	water("Water_Play"),
	skull("Skull_Play"),
	lava("Lava_Play"),
	rock("Rock_Play"), 
	floor("Floor_Play"),
	grass("Grass_Play");
	
	
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
