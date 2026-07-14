package com.grompartos.sillycompanions.entity.commands;

import com.grompartos.sillycompanions.entity.custom.Companion;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CompanionSkinCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("companion")
                .then(Commands.literal("skin")
                        .then(Commands.literal("steve")
                                .executes(context -> setCompanionSkin(context.getSource(), 0, "")))
                        .then(Commands.literal("microsoft")
                                .then(Commands.argument("username", StringArgumentType.string())
                                        .executes(context -> setCompanionSkin(context.getSource(), 1, StringArgumentType.getString(context, "username")))))
                        .then(Commands.literal("elyby")
                                .then(Commands.argument("username", StringArgumentType.string())
                                        .executes(context -> setCompanionSkin(context.getSource(), 2, StringArgumentType.getString(context, "username")))))
                )
        );
    }

    private static int setCompanionSkin(CommandSourceStack source, int type, String name) {
        if (source.getEntity() == null) return 0;

        // Locate closest companion within a 20-block radius
        AABB box = source.getEntity().getBoundingBox().inflate(20.0D);
        List<Companion> companions = source.getLevel().getEntitiesOfClass(Companion.class, box);

        if (companions.isEmpty()) {
            source.sendFailure(Component.literal("No Companion found nearby!"));
            return 0;
        }

        Companion companion = companions.get(0);
        companion.setSkinType(type);
        companion.setSkinName(name);

        source.sendSuccess(() -> Component.literal("Companion skin updated successfully!"), true);
        return 1;
    }
}