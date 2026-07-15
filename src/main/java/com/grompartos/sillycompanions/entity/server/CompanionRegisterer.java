package com.grompartos.sillycompanions.entity.server;

import com.grompartos.sillycompanions.SillyCompanions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CompanionRegisterer {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(SillyCompanions.MODID);

    public static final Supplier<EntityType<Companion>> COMPANION = ENTITY_TYPES.registerEntityType(
            "companion",Companion::new, MobCategory.CREATURE,
            builder -> builder.sized(0.6f,1.8f).eyeHeight(1.5f));
}
