package dev.cammiescorner.icarus.forge.registry;

import dev.cammiescorner.icarus.Icarus;
import dev.cammiescorner.icarus.util.Registrar;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class IcarusDeferredRegister<T> implements Registrar<T> {

    private final DeferredRegister<T> delegate;

    public static <T> IcarusDeferredRegister<T> create(IForgeRegistry<T> registry) {
        return new IcarusDeferredRegister<>(DeferredRegister.create(registry, Icarus.MODID));
    }

    public static <T> IcarusDeferredRegister<T> create(ResourceKey<Registry<T>> registryKey) {
        return new IcarusDeferredRegister<>(DeferredRegister.create(registryKey, Icarus.MODID));
    }

    private IcarusDeferredRegister(DeferredRegister<T> delegate) {
        this.delegate = delegate;
    }

    public void subscribe(IEventBus bus) {
        delegate.register(bus);
    }

    @Override
    public <V extends T> Supplier<V> register(String name, Supplier<? extends V> factory) {
        return delegate.register(name, factory);
    }
}
