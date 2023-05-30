/**
 * Much of the following code was adapted from Cyclic's storage bag code.
 * Copyright for portions of the code are held by Samson Basset (Lothrazar)
 * as part of Cyclic, under the MIT license.
 */
package com.tarinoita.solsweetpotato.item.foodcontainer;

import com.tarinoita.solsweetpotato.SOLSweetPotato;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.CapabilityItemHandler;
import static com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainer.GUI_SLOT_SIZE_PX;
import static com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainer.GUI_VERTICAL_BUFFER_PX;

public class FoodContainerScreen extends AbstractContainerScreen<FoodContainer> {


    public FoodContainerScreen(FoodContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.imageHeight = FoodContainerCalculator.getContainerInventoryScreenHeight(FoodContainerCalculator.getRequiredRowCount(container.containerItem.getNSlots()));
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int x, int y) {
        int containerSlots = this.menu.containerItem.getNSlots();
        this.drawBackground(matrices, new ResourceLocation(SOLSweetPotato.MOD_ID, FoodContainerCalculator.getContainerInventoryGUITexture(FoodContainerCalculator.getRequiredRowCount(containerSlots))));
        this.menu.capableContainerItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            int slotsPerRow = FoodContainerCalculator.getSlotsPerRow(containerSlots);
            int xStart = (2*8 + FoodContainerCalculator.MAX_SLOTS_PER_ROW*GUI_SLOT_SIZE_PX - slotsPerRow * GUI_SLOT_SIZE_PX) / 2;
            int yStart = GUI_VERTICAL_BUFFER_PX;
            for (int i = 0; i < containerSlots; i++) {
                int row = i / slotsPerRow;
                int col = i % slotsPerRow;
                int xPos = xStart - 1 + col * GUI_SLOT_SIZE_PX;
                int yPos = yStart - 1 + row * GUI_SLOT_SIZE_PX;

                this.drawSlot(matrices, xPos, yPos);
          }
        });
    }

    protected void drawBackground(PoseStack ms, ResourceLocation gui) {
        this.minecraft.getTextureManager().bindForSetup(gui);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, gui);
        int textureFileHeight = 256;
        if (this.getYSize() > textureFileHeight) {
            textureFileHeight = 512;
        }
        blit(ms, this.getGuiLeft(), this.getGuiTop(), 0, 0, 0, this.getXSize(), this.getYSize(), 256, textureFileHeight);
    }

    protected void drawSlot(PoseStack ms, int x, int y, ResourceLocation texture, int size) {
        this.minecraft.getTextureManager().bindForSetup(texture);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
        blit(ms, this.getGuiLeft() + x, this.getGuiTop() + y, 0, 0, size, size, size, size);
    }

    protected void drawSlot(PoseStack ms, int x, int y) {
        drawSlot(ms, x, y, new ResourceLocation(SOLSweetPotato.MOD_ID, "textures/gui/slot.png"), 18);
    }
}
