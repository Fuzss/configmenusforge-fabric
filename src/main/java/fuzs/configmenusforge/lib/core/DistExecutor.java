package fuzs.configmenusforge.lib.core;

import net.fabricmc.api.EnvType;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class DistExecutor {

    @Nullable
    public static <T> T callWhenOn(EnvType envType, Supplier<Callable<T>> toRun) {

        if (envType == ModLoaderEnvironment.getEnvironmentType()) {

            try {

                return toRun.get().call();
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public static void runWhenOn(EnvType envType, Supplier<Runnable> toRun) {

        if (envType == ModLoaderEnvironment.getEnvironmentType()) {

            toRun.get().run();
        }
    }

    public static <T> T runForDist(Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {

        return switch (ModLoaderEnvironment.getEnvironmentType()) {

            case CLIENT -> clientTarget.get().get();
            case SERVER -> serverTarget.get().get();
        };
    }

}
