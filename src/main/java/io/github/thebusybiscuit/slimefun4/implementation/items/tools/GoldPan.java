package io.github.thebusybiscuit.slimefun4.implementation.items.tools;

import io.github.thebusybiscuit.cscorelib2.collections.RandomizedSet;
import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityInteractHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.ElectricGoldPan;
import io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks.AutomatedPanningMachine;
import io.github.thebusybiscuit.slimefun4.implementation.settings.GoldPanDrop;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * A {@link GoldPan} is a {@link SlimefunItem} which allows you to obtain various
 * resources from Gravel.
 *
 * @author TheBusyBiscuit
 *
 * @see NetherGoldPan
 * @see AutomatedPanningMachine
 * @see ElectricGoldPan
 *
 */
public class GoldPan extends SimpleSlimefunItem<ItemUseHandler> implements RecipeDisplayItem {

    private final RandomizedSet<ItemStack> randomizer = new RandomizedSet<>();
    private final Set<GoldPanDrop> drops = new HashSet<>();

    @ParametersAreNonnullByDefault
    public GoldPan(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        drops.addAll(getGoldPanDrops());
        addItemSetting(drops.toArray(new GoldPanDrop[0]));
        addItemHandler(onEntityInteract());
    }

    /**
     * This method returns the target {@link Material} for this {@link GoldPan}.
     *
     * @return The {@link Material} this {@link GoldPan} can be used on
     */
    @Nonnull
    protected Material getTargetMaterial() {
        return Material.GRAVEL;
    }

    @Nonnull
    protected Set<GoldPanDrop> getGoldPanDrops() {
        Set<GoldPanDrop> settings = new HashSet<>();

        settings.add(new GoldPanDrop(this, "chance.FLINT", 40, new ItemStack(Material.FLINT)));
        settings.add(new GoldPanDrop(this, "chance.CLAY", 20, new ItemStack(Material.CLAY_BALL)));
        settings.add(new GoldPanDrop(this, "chance.SIFTED_ORE", 35, SlimefunItems.SIFTED_ORE));
        settings.add(new GoldPanDrop(this, "chance.IRON_NUGGET", 5, new ItemStack(Material.IRON_NUGGET)));

        return settings;
    }

    @Override
    public void postRegister() {
        super.postRegister();
        updateRandomizer();
    }

    /**
     * <strong>Do not call this method directly</strong>.
     * <p>
     * This method is for internal purposes only.
     * It will update and re-calculate all weights in our {@link RandomizedSet}.
     */
    public void updateRandomizer() {
        randomizer.clear();

        for (GoldPanDrop setting : drops) {
            randomizer.add(setting.getOutput(), setting.getValue());
        }

        if (randomizer.sumWeights() < 100) {
            randomizer.add(new ItemStack(Material.AIR), 100 - randomizer.sumWeights());
        }
    }

    /**
     * This returns a random output {@link ItemStack} that can be obtained via
     * this {@link GoldPan}.
     *
     * @return a random {@link ItemStack} obtained by this {@link GoldPan}
     */
    @Nonnull
    public ItemStack getRandomOutput() {
        return randomizer.getRandom();
    }

    @Override
    public String getLabelLocalPath() {
        return "guide.tooltips.recipes.gold-pan";
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            Optional<Block> block = e.getClickedBlock();

            if (block.isPresent()) {
                Block b = block.get();

                if (b.getType() == getTargetMaterial() && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), b.getLocation(), ProtectableAction.BREAK_BLOCK)) {
                    ItemStack output = getRandomOutput();

                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                    b.setType(Material.AIR);

                    if (output.getType() != Material.AIR) {
                        b.getWorld().dropItemNaturally(b.getLocation(), output.clone());
                    }
                }
            }

            e.cancel();
        };
    }

    /**
     * This method cancels {@link EntityInteractHandler} to prevent interacting {@link GoldPan}
     * with entities.
     *
     * @return the {@link EntityInteractHandler} of this {@link SlimefunItem}
     */
    public EntityInteractHandler onEntityInteract() {
        return (e, item, offHand) -> {
            if (!(e.getRightClicked() instanceof ItemFrame)) {
                e.setCancelled(true);
            }
        };
    }

    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> recipes = new LinkedList<>();

        for (GoldPanDrop drop : drops) {
            if (drop.getValue() > 0) {
                recipes.add(new ItemStack(getTargetMaterial()));
                recipes.add(drop.getOutput());
            }
        }

        return recipes;
    }

}