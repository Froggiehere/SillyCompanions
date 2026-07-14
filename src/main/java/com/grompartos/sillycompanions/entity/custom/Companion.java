package com.grompartos.sillycompanions.entity.custom;

import com.grompartos.sillycompanions.entity.goals.PlayerFollowGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Companion extends PathfinderMob {

    public Companion(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }
    // Sync state: 0 = Steve (default), 1 = Microsoft (Mojang), 2 = Ely.by
    private static final EntityDataAccessor<Integer> SKIN_TYPE =
            SynchedEntityData.defineId(Companion.class, EntityDataSerializers.INT);

    // Sync state: The name of the player whose skin we want to fetch
    private static final EntityDataAccessor<String> SKIN_NAME =
            SynchedEntityData.defineId(Companion.class, EntityDataSerializers.STRING);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SKIN_TYPE, 0);
        builder.define(SKIN_NAME, "");
    }

    public int getSkinType() {
        return this.entityData.get(SKIN_TYPE);
    }

    public void setSkinType(int type) {
        this.entityData.set(SKIN_TYPE, type);
    }

    public String getSkinName() {
        return this.entityData.get(SKIN_NAME);
    }

    public void setSkinName(String name) {
        this.entityData.set(SKIN_NAME, name);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1,new PlayerFollowGoal(this,2F,4));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4,new WaterAvoidingRandomStrollGoal(this,1.0));
    }
    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,30.0D)
                .add(Attributes.MOVEMENT_SPEED,0.3D)
                .add(Attributes.FOLLOW_RANGE,32.0D);
    }
}


