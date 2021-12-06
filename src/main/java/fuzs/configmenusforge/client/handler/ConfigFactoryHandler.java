package fuzs.configmenusforge.client.handler;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.gui.ModMenuOptionsScreen;
import fuzs.configmenusforge.ConfigMenusForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigFactoryHandler implements ModMenuApi {
    @Override
    public com.terraformersmc.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreenFactory.createConfigScreen(ConfigMenusForge.MOD_ID, new ResourceLocation("textures/block/cobblestone.png")).orElse(screen -> null)::apply;
    }

    @Override
    public Map<String, com.terraformersmc.modmenu.api.ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, com.terraformersmc.modmenu.api.ConfigScreenFactory<?>> factories = new HashMap<>();
        ConfigTracker.INSTANCE.configSets().values().stream().flatMap(Collection::stream).map(ModConfig::getModId).distinct().forEach(modId -> {
            ConfigScreenFactory.createConfigScreen(modId).ifPresent(screenFactory -> {
                factories.put(modId, screenFactory::apply);
            });
        });
        return factories;
    }
}
