package com.grompartos.sillycompanions;

import com.grompartos.sillycompanions.entity.client.CompanionRenderer;
import com.grompartos.sillycompanions.entity.custom.MyModEntities;
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
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MyModEntities.MY_ENTITY.get(), CompanionRenderer::new);
    }
}
