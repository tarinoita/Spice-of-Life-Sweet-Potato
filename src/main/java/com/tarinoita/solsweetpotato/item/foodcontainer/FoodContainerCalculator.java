package com.tarinoita.solsweetpotato.item.foodcontainer;

public final class FoodContainerCalculator {

    private FoodContainerCalculator() {
        // Utility class
    }

    public static final int MAX_SLOTS_PER_ROW = 9;
    // Any higher than this and the container will overflow into the player inventory
    public static final int MAX_SLOTS_ALLOWED = MAX_SLOTS_PER_ROW * 15;
    public static final int EXTRA_LARGE_ROW_THRESHOLD = 11;
    public static final int LARGE_ROW_THRESHOLD = 7;
    public static final int MEDIUM_ROW_THRESHOLD = 4;

    public static final int PLAYER_INVENTORY_HEIGHT_PX = 82;
    

    public static int getContainerInventoryScreenHeight(int nrows) {
        if (nrows >= EXTRA_LARGE_ROW_THRESHOLD) {
            return 382;
        }
        if (nrows >= LARGE_ROW_THRESHOLD) {
            return 302;
        }
        if (nrows >= MEDIUM_ROW_THRESHOLD) {
            return 230;
        }
        return 166;
    }

    public static int getSlotsPerRow(int nslots) {
        return Math.min(nslots, MAX_SLOTS_PER_ROW);
    }

    public static int getRequiredRowCount(int nslots) {
        return (int) Math.ceil((double) nslots / (double) getSlotsPerRow(nslots));
    }

    public static int getPlayerInventoryUpperPosition(int nrows) {
        return getContainerInventoryScreenHeight(nrows) - PLAYER_INVENTORY_HEIGHT_PX;
    }

    public static String getContainerInventoryGUITexture(int nrows) {
        
        if (nrows >= EXTRA_LARGE_ROW_THRESHOLD) {
            return "textures/gui/extra_large_inventory.png";
        }
        if (nrows >= LARGE_ROW_THRESHOLD) {
            return "textures/gui/large_inventory.png";
        }
        if (nrows >= MEDIUM_ROW_THRESHOLD) {
            return "textures/gui/medium_inventory.png";
        }
        return "textures/gui/inventory.png";
    }
    
}
