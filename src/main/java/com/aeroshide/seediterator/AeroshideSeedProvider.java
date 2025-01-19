package com.aeroshide.seediterator;

import me.voidxwalker.autoreset.api.seedprovider.SeedProvider;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AeroshideSeedProvider implements SeedProvider {
    private List<String> seeds;
    private int currentSeedIndex = 0;

    public AeroshideSeedProvider() {
        try {
            if (!Files.exists(Paths.get("seeds.txt"))) {
                System.out.println("seeds.txt not found. Fetching from online database...");
                fetchAndCreateSeedFile();
            }

            try (Stream<String> lines = Files.lines(Paths.get("seeds.txt"))) {
                seeds = lines.collect(Collectors.toList());
            }

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

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("seed.used", true))) {
                writer.write(seed);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Optional.of(seed);
        } else {
            System.out.println("All seeds have been used.");
            return Optional.empty();
        }
    }

    @Override
    public boolean shouldShowSeed() {
        return true;
    }

    private void fetchAndCreateSeedFile() {
        String url = "https://raw.githubusercontent.com/Aeroshide/SeedMinerBot/refs/heads/dev/seedbank/mapless.txt";
        try (Scanner scanner = new Scanner(new URL(url).openStream());
             BufferedWriter writer = new BufferedWriter(new FileWriter("seeds.txt"))) {

            while (scanner.hasNextLine()) {
                writer.write(scanner.nextLine());
                writer.newLine();
            }
            System.out.println("seeds.txt successfully created from the online database.");
        } catch (IOException e) {
            System.err.println("Error fetching seeds from the online database: " + e.getMessage());
        }
    }
}
