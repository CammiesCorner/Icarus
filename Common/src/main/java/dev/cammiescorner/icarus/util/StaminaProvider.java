package dev.cammiescorner.icarus.util;

public interface StaminaProvider {
	float icarus$getMaxStamina();
	float icarus$getStamina();
	void icarus$setStamina(float stamina);
	void icarus$modifyStamina(float amount);
}
