package io.github.thebusybiscuit.slimefun4.implementation.items.tools;

import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The {@link ExplosiveShovel} works similar to the {@link ExplosivePickaxe}.
 * However it can only break blocks that a shovel can break.
 *
 * @author Linox
 * @see ExplosivePickaxe
 */
public class ExplosiveShovel extends ExplosiveTool {

    public ExplosiveShovel(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected boolean canBreak(Player p, Block b) {
        if (isUnbreakable(b)) {
            return false;
        } else if (SlimefunTag.EXPLOSIVE_SHOVEL_BLOCKS.isTagged(b.getType())) {
            return false;
        } else {
            return SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.BREAK_BLOCK);
        }
    }
}