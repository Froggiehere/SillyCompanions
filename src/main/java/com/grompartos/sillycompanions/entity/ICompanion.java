package com.grompartos.sillycompanions.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;

public interface ICompanion extends GeoAnimatable {
    void registerControllers(AnimatableManager.ControllerRegistrar controllers);

    AnimatableInstanceCache getAnimatableInstanceCache();
}
