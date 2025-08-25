package com.example;

import net.minecraft.nbt.NbtCompound;

/**
 * An interface that allows us to attach custom data to an entity's NBT tag.
 * NBT (Named Binary Tag) is how Minecraft saves all its data.
 */
public interface IEntityDataSaver {
    NbtCompound getPersistentData();
}