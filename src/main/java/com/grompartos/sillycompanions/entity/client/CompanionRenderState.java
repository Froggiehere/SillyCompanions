package com.grompartos.sillycompanions.entity.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class CompanionRenderState extends LivingEntityRenderState {
    // Add these two fields so CompanionRenderer can read and write to them!
    public int skinType = 0;
    public String skinName = "";
}