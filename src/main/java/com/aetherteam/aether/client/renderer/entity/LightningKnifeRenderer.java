package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Quaternionf;

public class LightningKnifeRenderer extends EntityRenderer<ThrownLightningKnife> {
	public LightningKnifeRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.0F;
	}

	/**
	 * Rotates and renders the Lightning Knife to look as if it has been thrown.
	 * @param lightningKnife The {@link ThrownLightningKnife} entity.
	 * @param entityYaw The {@link Float} for the entity's yaw rotation.
	 * @param partialTicks The {@link Float} for the game's partial ticks.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param buffer The rendering {@link MultiBufferSource}.
	 * @param packedLight The {@link Integer} for the packed lighting for rendering.
	 */
	@Override
	public void render(ThrownLightningKnife lightningKnife, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		Quaternionf quaternion = Axis.YP.rotationDegrees(lightningKnife.getYRot());
		quaternion.mul(Axis.XP.rotationDegrees((-(lightningKnife.xRotO + (lightningKnife.getXRot() - lightningKnife.xRotO) * partialTicks)) - 90.0F));
		quaternion.mul(Axis.ZP.rotationDegrees(-135.0F));
		poseStack.mulPose(quaternion);
		Minecraft.getInstance().getItemRenderer().renderStatic(lightningKnife.getItem(), ItemDisplayContext.GUI, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, lightningKnife.level(), lightningKnife.getId());
		poseStack.popPose();
		super.render(lightningKnife, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(ThrownLightningKnife lightningKnife) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
