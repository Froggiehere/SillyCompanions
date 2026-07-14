package com.grompartos.sillycompanions.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import java.util.EnumSet;

public class PlayerFollowGoal extends Goal {
    private final Mob mob;
    private Player targetPlayer;
    private final double speedModifier;
    private final float stopDistanceSqr; // Takibi bırakıp etrafa bakacağı yakınlık (Karesi)

    public PlayerFollowGoal(Mob mob, double speedModifier, float stopDistance) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.stopDistanceSqr = stopDistance * stopDistance;
        // Hem hareket (MOVE) hem de bakış (LOOK) kontrolünü ele alıyoruz
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // 16 blok yarıçapındaki en yakın oyuncuyu bul
        this.targetPlayer = this.mob.level().getNearestPlayer(this.mob, 32.0D);

        // Oyuncu yoksa veya zaten çok yakınındaysa bu hedefi başlatma
        if (this.targetPlayer == null) {
            return false;
        }
        return this.mob.distanceToSqr(this.targetPlayer) > this.stopDistanceSqr;
    }

    @Override
    public boolean canContinueToUse() {
        // Oyuncu hayattaysa ve çok uzaklaşmadıysa (örn: 30 blok) hedefi sürdür
        return this.targetPlayer != null
                && this.targetPlayer.isAlive()
                && this.mob.distanceToSqr(this.targetPlayer) < 900.0D;
    }

    @Override
    public void stop() {
        this.targetPlayer = null;
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.targetPlayer == null) return;

        double distanceSqr = this.mob.distanceToSqr(this.targetPlayer);

        // Oyuncuya sürekli yüzünü dönmesini sağla
        this.mob.getLookControl().setLookAt(this.targetPlayer, 10.0F, (float)this.mob.getMaxHeadXRot());

        if (distanceSqr > this.stopDistanceSqr) {
            // Oyuncu belirlenen mesafeden uzaksa ona doğru yürü
            this.mob.getNavigation().moveTo(this.targetPlayer, this.speedModifier);
        } else {
            // Oyuncu yakındaysa yürümeyi durdur (Böylece diğer etrafta dolaşma hedefleri devreye girebilir)
            this.mob.getNavigation().stop();
        }
    }
}
