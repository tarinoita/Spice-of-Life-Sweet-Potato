/**
 * Much of the following code was adapted from Cyclic's storage bag code.
 * Copyright for portions of the code are held by Samson Basset (Lothrazar)
 * as part of Cyclic, under the MIT license.
 */
package com.tarinoita.solsweetpotato.item.foodcontainer;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FoodContainerCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    private int slots;

    private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(() -> {
        return new ItemStackHandler(slots) {
            @Override
            public void deserializeNBT(CompoundTag nbt)
            {
                /*
                    Override normal deserializing to prevent setting the stack size back to a prior config value.
                    If the item previously had 10 item slots full, and the config is changed to only allow 7,
                    the last 3 items will be deleted.
                */
                ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
                for (int i = 0; i < tagList.size(); i++)
                {
                    CompoundTag itemTags = tagList.getCompound(i);
                    int slot = itemTags.getInt("Slot");

                    if (slot >= 0 && slot < stacks.size())
                    {
                        stacks.set(slot, ItemStack.of(itemTags));
                    }
                }
                onLoad();
            }


            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return !(stack.getItem() instanceof FoodContainerItem) && super.isItemValid(slot, stack);
            }
        };
    });

    public FoodContainerCapabilityProvider(ItemStack stack, int slots) {
        this.slots = slots;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return inventory.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        if (inventory.isPresent()) {
            return inventory.resolve().get().serializeNBT();
        }
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventory.ifPresent(h -> h.deserializeNBT(nbt));
    }
}