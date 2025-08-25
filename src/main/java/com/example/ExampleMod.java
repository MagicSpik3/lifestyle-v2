package com.example;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world! My first mod is working!");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });
    }

    /**
     * Helper method to spawn a bee with a specific gender.
     * @param source The source of the command (the player).
     * @param gender The gender to assign (0 for male, 1 for female).
     */
    private void summonBeeWithGender(ServerCommandSource source, int gender) {
        LOGGER.info("--- Starting summonBeeWithGender ---");

        ServerWorld world = source.getWorld();
        LOGGER.info("1. Got world: " + (world != null));

        BeeEntity bee = new BeeEntity(EntityType.BEE, world);
        LOGGER.info("2. Created bee entity: " + (bee != null));

        IEntityDataSaver dataSaver = (IEntityDataSaver) bee;
        LOGGER.info("3. Cast to IEntityDataSaver successful.");

        NbtCompound nbt = dataSaver.getPersistentData();
        LOGGER.info("4. Got persistent data: " + (nbt != null));

        nbt.putInt("gender", gender);
        LOGGER.info("5. Set gender to " + gender);

        bee.setPosition(source.getPosition());
        LOGGER.info("6. Set bee position.");

        world.spawnEntity(bee);
        LOGGER.info("7. Spawned bee in world.");

        String genderName = (gender == 0) ? "male" : "female";
        source.sendFeedback(() -> Text.of("Summoned a " + genderName + " bee!"), true);
        LOGGER.info("--- Finished summonBeeWithGender ---");
    }

    private void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        LOGGER.info("Registering commands...");
        // Register the simple /hello command
        dispatcher.register(CommandManager.literal("hello")
            .executes(context -> {
                context.getSource().sendFeedback(() -> Text.of("Hello from my mod!"), false);
                return 1;
            }));

        // Register a simple command to summon a male bee
        dispatcher.register(CommandManager.literal("summonmalebee")
            .executes(context -> {
                LOGGER.info("Executing /summonmalebee command...");
                summonBeeWithGender(context.getSource(), 0); // 0 for male
                return 1;
            }));

        // Register a simple command to summon a female bee
        dispatcher.register(CommandManager.literal("summonfemalebee")
            .executes(context -> {
                LOGGER.info("Executing /summonfemalebee command...");
                summonBeeWithGender(context.getSource(), 1); // 1 for female
                return 1;
            }));
    }
}