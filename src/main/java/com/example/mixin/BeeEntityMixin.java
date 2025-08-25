package com.example.mixin;

import com.example.IEntityDataSaver;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {

    // This is the method that sends a chat message when you feed a bee
    @Inject(method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"))
    private void onBreed(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isIn(net.minecraft.registry.tag.ItemTags.FLOWERS)) {
            player.sendMessage(Text.of("A bee is ready to breed!"), false);
        }
    }

    // This is the method that assigns gender when a baby is born
    @Inject(method = "createChild", at = @At("RETURN"))
    private void assignGenderOnBirth(net.minecraft.server.world.ServerWorld world, net.minecraft.entity.passive.PassiveEntity entity, CallbackInfoReturnable<BeeEntity> cir) {
        BeeEntity child = cir.getReturnValue();

        if (child != null) {
            IEntityDataSaver dataSaver = (IEntityDataSaver) child;
            NbtCompound nbt = dataSaver.getPersistentData();
            int gender = world.getRandom().nextInt(2); // 0 for male, 1 for female
            nbt.putInt("gender", gender);
            com.example.ExampleMod.LOGGER.info("A baby bee was born with gender: " + (gender == 0 ? "Male" : "Female"));
        }
    }
}