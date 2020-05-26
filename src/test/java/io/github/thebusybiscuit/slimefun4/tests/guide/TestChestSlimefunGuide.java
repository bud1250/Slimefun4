package io.github.thebusybiscuit.slimefun4.tests.guide;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.guide.ChestSlimefunGuide;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;

public class TestChestSlimefunGuide {

    @BeforeAll
    public static void load() {
        MockBukkit.mock();
        MockBukkit.load(SlimefunPlugin.class);
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }

    @Test
    public void testBasicGetters() {
        ChestSlimefunGuide guide = new ChestSlimefunGuide(false);

        Assertions.assertEquals(SlimefunGuideLayout.CHEST, guide.getLayout());
        Assertions.assertTrue(guide.isSurvivalMode());
    }

}
