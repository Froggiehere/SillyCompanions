package com.grompartos.sillycompanions.entity.client;

import com.grompartos.sillycompanions.entity.custom.MyModEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = "sillycompanions", value = Dist.CLIENT)
public class ClientEvents {

    public static final ModelLayerLocation COMPANION_LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("sillycompanions", "companion"), "main"
    );

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(COMPANION_LAYER, CompanionModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MyModEntities.MY_ENTITY.get(), CompanionRenderer::new);
    }
}