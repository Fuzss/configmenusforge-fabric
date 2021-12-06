package fuzs.configmenusforge;

import fuzs.configmenusforge.config.TestConfig;
import fuzs.configmenusforge.lib.core.ModLoaderEnvironment;
import fuzs.configmenusforge.lib.network.MessageDirection;
import fuzs.configmenusforge.lib.network.NetworkHandler;
import fuzs.configmenusforge.network.client.message.C2SAskPermissionsMessage;
import fuzs.configmenusforge.network.client.message.C2SSendConfigMessage;
import fuzs.configmenusforge.network.message.S2CGrantPermissionsMessage;
import fuzs.configmenusforge.network.message.S2CUpdateConfigMessage;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ConfigMenusForge implements ModInitializer {
    public static final String MOD_ID = "configmenusforge";
    public static final String MOD_NAME = "Config Menus Forge";
    public static final String MOD_URL = "https://www.curseforge.com/minecraft/mc-mods/config-menus-forge";
    public static final Logger LOGGER = LogManager.getLogger(ConfigMenusForge.MOD_NAME);

    public static final NetworkHandler NETWORK = NetworkHandler.of(MOD_ID);

    @Override
    public void onInitialize() {
        this.registerMessages();
        this.initTestConfigs();
    }

    private void registerMessages() {
        NETWORK.register(C2SAskPermissionsMessage.class, C2SAskPermissionsMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(S2CGrantPermissionsMessage.class, S2CGrantPermissionsMessage::new, MessageDirection.TO_CLIENT);
        NETWORK.register(C2SSendConfigMessage.class, C2SSendConfigMessage::new, MessageDirection.TO_SERVER);
        NETWORK.register(S2CUpdateConfigMessage.class, S2CUpdateConfigMessage::new, MessageDirection.TO_CLIENT);
    }

    private void initTestConfigs() {
        if (ModLoaderEnvironment.isDevelopmentEnvironment()) {
            FileUtils.getOrCreateDirectory(ModLoaderEnvironment.getConfigDir().resolve(MOD_ID), String.format("%s config directory", MOD_ID));
            ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.CLIENT, TestConfig.CLIENT_SPEC, String.format("%s%s%s-%s.toml", MOD_ID, File.separator, MOD_ID, ModConfig.Type.CLIENT.extension()));
            ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, TestConfig.COMMON_SPEC, String.format("%s-%s.toml", MOD_ID, ModConfig.Type.COMMON.extension()));
            ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.SERVER, TestConfig.SERVER_SPEC, String.format("%s-%s.toml", MOD_ID, ModConfig.Type.SERVER.extension()));
        }
    }
}
