package dev.cammiescorner.icarus.forge.compat;

import dev.cammiescorner.icarus.util.IcarusCompatService;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class IcarusForgeCompatService implements IcarusCompatService {

    private final Object2BooleanMap<String> loadedMods = new Object2BooleanArrayMap<>();

    @Override
    public boolean isModLoaded(String modid) {
        return loadedMods.computeIfAbsent(modid, key -> {
            var modList = ModList.get();
            if(modList != null) {
                return modList.isLoaded((String) key);
            }
            for (var mod : FMLLoader.getLoadingModList().getMods()) {
                if(key.equals(mod.getModId())) {
                    return true;
                }
            }
            return false;
        });
    }
}
