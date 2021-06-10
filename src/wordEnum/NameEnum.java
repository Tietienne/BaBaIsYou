package wordEnum;

import java.util.Objects;

public enum NameEnum {
	Baba("Baba_Name"),
	Flag("Flag_Name"),
	Wall("Wall_Name"),
	Water("Water_Name"),
	Skull("Skull_Name"),
	Lava("Lava_Name"),
	Rock("Rock_Name"),
	Brain("Brain_Name");
	
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
