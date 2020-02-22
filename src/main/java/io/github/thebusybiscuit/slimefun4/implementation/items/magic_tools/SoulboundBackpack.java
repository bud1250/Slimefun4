package io.github.thebusybiscuit.slimefun4.implementation.items.magic_tools;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunBackpack;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class SoulboundBackpack extends SlimefunBackpack implements Soulbound {

	public SoulboundBackpack(int size, Category category, SlimefunItemStack item, ItemStack[] recipe) {
		super(size, category, item, RecipeType.MAGIC_WORKBENCH, recipe);
	}

}