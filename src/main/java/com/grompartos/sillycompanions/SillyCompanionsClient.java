package com.grompartos.sillycompanions;

import com.geckolib.renderer.GeoEntityRenderer;
import com.grompartos.sillycompanions.entity.server.Companion;
import com.grompartos.sillycompanions.entity.server.CompanionRegisterer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SillyCompanions.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SillyCompanions.MODID, value = Dist.CLIENT)
public class SillyCompanionsClient {
    public SillyCompanionsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        SillyCompanions.LOGGER.info("HELLO FROM CLIENT SETUP");
    }
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CompanionRegisterer.COMPANION.get(),
                context -> new GeoEntityRenderer<>(context, CompanionRegisterer.COMPANION.get()));
    }
}
