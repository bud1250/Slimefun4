package io.github.thebusybiscuit.slimefun4.implementation.items.seasonal;

import io.github.thebusybiscuit.cscorelib2.inventory.ItemUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.utils.FireworkUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This {@link SlimefunItem} represents an {@link EasterEgg}.
 * The {@link EasterEgg} can be activated upon Right-Click and will
 * spawn a random {@link ItemStack} from the {@link ItemStack} Array specified in the constructor.
 *
 * @author TheBusyBiscuit
 */
public class EasterEgg extends SimpleSlimefunItem<ItemUseHandler> {

    private final ItemStack[] gifts;

    public EasterEgg(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput, ItemStack... gifts) {
        super(category, item, recipeType, recipe, recipeOutput);

        this.gifts = gifts;
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();

            Player p = e.getPlayer();

            if (p.getGameMode() != GameMode.CREATIVE) {
                ItemUtils.consumeItem(e.getItem(), false);
            }

            FireworkUtils.launchRandom(p, 2);

            p.getWorld().dropItemNaturally(p.getLocation(), gifts[ThreadLocalRandom.current().nextInt(gifts.length)].clone());
        };
    }

}
