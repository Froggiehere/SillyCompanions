package com.grompartos.sillycompanions;

import com.grompartos.sillycompanions.entity.server.CompanionRegisterer;
import com.grompartos.sillycompanions.item.MyModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;

import net.neoforged.bus.api.SubscribeEvent;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(SillyCompanions.MODID)

public class SillyCompanions {
    public static final String MODID = "sillycompanions";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SillyCompanions(IEventBus modEventBus, ModContainer modContainer) {
        MyModItems.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::addCreative);
        CompanionRegisterer.ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(this::createDefaultAttributes);
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
            event.accept(MyModItems.SUMMONING_BOOK);
        }
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
    public void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(
                // Your entity type.
                CompanionRegisterer.COMPANION.get(),
                // An AttributeSupplier. This is typically created by calling LivingEntity#createLivingAttributes,
                // setting your values on it, and calling #build. You can also create the AttributeSupplier from scratch
                // if you want, see the source of LivingEntity#createLivingAttributes for an example.
                LivingEntity.createLivingAttributes()
                        // Add an attribute with a non-default value.
                        .add(Attributes.MAX_HEALTH, 50d)
                        .add(Attributes.MOVEMENT_SPEED, 0.15d)
                        .add(Attributes.FOLLOW_RANGE,32d)
                        // Build the AttributeSupplier.
                        .build()
        );
    }
    }



