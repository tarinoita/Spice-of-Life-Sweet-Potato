package com.tarinoita.solsweetpotato.item;

import com.tarinoita.solsweetpotato.SOLSweetPotato;
import com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainerItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public final class SOLSweetPotatoItems {

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SOLSweetPotato.MOD_ID);

	public static void registerSweetPotatoItems() {
		ITEMS.register("food_book", () -> new FoodBookItem());
		ITEMS.register("lunchbag", () -> new FoodContainerItem("lunchbag"));
		ITEMS.register("lunchbox", () -> new FoodContainerItem("lunchbox"));
		ITEMS.register("golden_lunchbox", () -> new FoodContainerItem("golden_lunchbox"));
		ITEMS.register("diamond_lunchbox", () -> new FoodContainerItem("diamond_lunchbox"));
		ITEMS.register("netherite_lunchbox", () -> new FoodContainerItem("netherite_lunchbox"));

		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
