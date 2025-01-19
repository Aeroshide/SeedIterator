package com.aeroshide.seediterator;

import me.voidxwalker.autoreset.Atum;
import me.voidxwalker.autoreset.api.seedprovider.SeedProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import java.util.Optional;

public class Seediterator implements ClientModInitializer {

    private static final SeedProvider seedProvider = new AeroshideSeedProvider();
    @Override
    public void onInitializeClient() {
        Optional<String> seedOptional = seedProvider.getSeed();
        seedOptional.ifPresent(seed -> {
            System.out.println("Using seed: " + seed);
        });
        Atum.setSeedProvider(seedProvider);
    }
}
