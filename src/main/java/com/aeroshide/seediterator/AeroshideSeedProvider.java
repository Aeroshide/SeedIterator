package com.aerohide.seediterator;

import me.voidxwalker.autoreset.api.seedprovider.SeedProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AeroshideSeedProvider implements SeedProvider {
    private List<String> seeds;
    private int currentSeedIndex = 0;

    public AeroshideSeedProvider() {
        try (Stream<String> lines = Files.lines(Paths.get("seeds.txt"))) {
            seeds = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<String> getSeed() {
        if (seeds == null || seeds.isEmpty()) {
            System.out.println("No seeds available in seeds.txt");
            return Optional.empty();
        }

        if (currentSeedIndex < seeds.size()) {
            String seed = seeds.get(currentSeedIndex);
            System.out.println("Iteration " + (currentSeedIndex + 1) + "/" + seeds.size() + ": " + seed);
            currentSeedIndex++;
            return Optional.of(seed);
        } else {
            System.out.println("All seeds have been used.");
            return Optional.empty();
        }
    }

    @Override
    public boolean shouldShowSeed() {
        return true; // Adjust based on your requirements
    }
}
