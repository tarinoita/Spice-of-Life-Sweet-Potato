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
import static com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainer.MAX_SLOTS_PER_ROW;

public class FoodContainerScreen extends AbstractContainerScreen<FoodContainer> {
    public FoodContainerScreen(FoodContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int x, int y) {
        this.menu.containerItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            int slotsPerRow = h.getSlots();
            if (h.getSlots() > MAX_SLOTS_PER_ROW) {
                slotsPerRow = MAX_SLOTS_PER_ROW;
            } 
            int rowsRequired = (int) Math.ceil((double) h.getSlots() / (double) slotsPerRow);
            String guiTextureToUse;
            if (rowsRequired >= 10) {
                guiTextureToUse = "textures/gui/extra_large_inventory.png";
            } else if (rowsRequired >= 7) {
                guiTextureToUse = "textures/gui/large_inventory.png";
            } else if (rowsRequired >= 4) {
                guiTextureToUse = "textures/gui/medium_inventory.png";
            } else {
                guiTextureToUse = "textures/gui/inventory.png";
            }
            this.drawBackground(matrices, new ResourceLocation(SOLSweetPotato.MOD_ID, guiTextureToUse));
            int xStart = (2*8 + MAX_SLOTS_PER_ROW*GUI_SLOT_SIZE_PX - slotsPerRow * GUI_SLOT_SIZE_PX) / rowsRequired;
            int yStart = GUI_VERTICAL_BUFFER_PX + GUI_SLOT_SIZE_PX;
            if (h.getSlots() > MAX_SLOTS_PER_ROW) {
                yStart = GUI_VERTICAL_BUFFER_PX + (84-36-23)/rowsRequired;
            }
            for (int i = 0; i < h.getSlots(); i++) {
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
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(ms, relX, relY, 0, 0, this.getXSize(), this.getYSize());
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
