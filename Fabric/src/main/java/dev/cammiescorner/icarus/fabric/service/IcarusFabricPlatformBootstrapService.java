package dev.cammiescorner.icarus.fabric.service;

import com.google.auto.service.AutoService;
import dev.cammiescorner.icarus.util.IcarusPlatformBootstrapService;
import net.fabricmc.loader.api.FabricLoader;

@AutoService(IcarusPlatformBootstrapService.class)
public class IcarusFabricPlatformBootstrapService implements IcarusPlatformBootstrapService {

    @Override
    public boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
