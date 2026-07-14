package com.grompartos.sillycompanions.entity.client;

import com.grompartos.sillycompanions.entity.custom.Companion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class CompanionRenderer extends MobRenderer<Companion, CompanionRenderState, CompanionModel> {

    public CompanionRenderer(EntityRendererProvider.Context context) {
        super(context, new CompanionModel(context.bakeLayer(ClientEvents.COMPANION_LAYER)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(CompanionRenderState state) {
        // Resolve skin type and user on the client side
        return SkinManager.getOrCreateSkin(state.skinName, state.skinType);
    }

    @Override
    public CompanionRenderState createRenderState() {
        return new CompanionRenderState();
    }

    @Override
    public void extractRenderState(Companion entity, CompanionRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        // Expose variables from entity to our render state
        state.skinType = entity.getSkinType();
        state.skinName = entity.getSkinName();
    }
}