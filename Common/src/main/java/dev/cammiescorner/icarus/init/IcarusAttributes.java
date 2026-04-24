package dev.cammiescorner.icarus.init;

import dev.cammiescorner.icarus.Icarus;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.jetbrains.annotations.ApiStatus;

public class IcarusAttributes {
	private static volatile boolean isInitialized = false;
	public static final RegistryHandler<Attribute> ATTRIBUTES = RegistryHandler.create(Registries.ATTRIBUTE, Icarus.MODID);

	public static final RegistrySupplier<Attribute> STAMINA = ATTRIBUTES.register("stamina", () -> new RangedAttribute("attribute.name.icarus.stamina", 600, 0, Float.MAX_VALUE).setSyncable(true));

	@ApiStatus.Internal
	public static synchronized void registerAll() {
		if(!isInitialized) {
			IcarusAttributes.ATTRIBUTES.accept(RegistryService.get());
			isInitialized = true;
		}
	}
}
