package com.grompartos.sillycompanions.item;

import com.grompartos.sillycompanions.SillyCompanions;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MyModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SillyCompanions.MODID);
    public static final DeferredItem<Item> SUMMONING_BOOK = ITEMS.registerSimpleItem("summoning_book");
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
