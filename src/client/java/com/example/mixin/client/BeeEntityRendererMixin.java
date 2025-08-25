package com.example.mixin.client;

import com.example.IEntityDataSaver;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntityRenderer.class)
public class BeeEntityRendererMixin {
    private static final Identifier MALE_TEXTURE = Identifier.of("minecraft", "textures/entity/bee/bee_male.png");
    private static final Identifier FEMALE_TEXTURE = Identifier.of("minecraft", "textures/entity/bee/bee_female.png");
    // We'll use the angry bee texture as a visual indicator for bees without a gender
    private static final Identifier DEFAULT_TEXTURE = Identifier.of("minecraft", "textures/entity/bee/bee_angry.png");

    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/BeeEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    private void getTexture(BeeEntity beeEntity, CallbackInfoReturnable<Identifier> cir) {
        // This is a safe way to access our custom data
        if (beeEntity instanceof IEntityDataSaver dataSaver) {
            NbtCompound nbt = dataSaver.getPersistentData();

            if (nbt.contains("gender")) {
                int gender = nbt.getInt("gender");

                if (gender == 0) { // Male
                    cir.setReturnValue(MALE_TEXTURE);
                } else if (gender == 1) { // Female
                    cir.setReturnValue(FEMALE_TEXTURE);
                }
            } else {
                // If the bee has no gender, use the angry texture so we can see it.
                // This is great for debugging!
                cir.setReturnValue(DEFAULT_TEXTURE);
            }
        }
    }
    
}