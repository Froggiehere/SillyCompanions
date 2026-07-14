package com.grompartos.sillycompanions;

import com.grompartos.sillycompanions.entity.custom.MyModEntities;
import com.grompartos.sillycompanions.entity.custom.Companion;
import com.grompartos.sillycompanions.item.MyModItems;
import net.neoforged.fml.common.EventBusSubscriber;
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
        MyModEntities.ENTITY_TYPES.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::addCreative);
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

    @EventBusSubscriber(modid = "sillycompanions")
    public class ModEvents {

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            // Associate the attributes builder from your Companion class with your Entity Type definition
            event.put(MyModEntities.MY_ENTITY.get(), Companion.createAttributes().build());
        }
    }
}
