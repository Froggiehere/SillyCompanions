package com.grompartos.sillycompanions.entity.custom;

import com.grompartos.sillycompanions.SillyCompanions;

import com.grompartos.sillycompanions.entity.commands.CompanionSkinCommand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MyModEntities {

    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(SillyCompanions.MODID);

    public static final Supplier<EntityType<Companion>> MY_ENTITY = ENTITY_TYPES.registerEntityType(
            "my_entity", Companion::new, MobCategory.MISC,
            builder -> builder.sized(0.6f, 1.4f).eyeHeight(1.3f));





    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    @EventBusSubscriber(modid = "sillycompanions")
    public class GameEvents {
        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            CompanionSkinCommand.register(event.getDispatcher());
        }
    }
}
