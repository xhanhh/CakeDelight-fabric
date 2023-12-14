package top.ilov.mcmods.cakedelight;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;

public final class CakeConfig {

    public CakeConfig() {}

    private boolean enable_the_sound_of_eating_ekac = true;

    public boolean isEnableEkacSound() {
        return enable_the_sound_of_eating_ekac;
    }

    public void setEnableEkacSound(boolean nEnableEkacSound) {

        enable_the_sound_of_eating_ekac = nEnableEkacSound;

    }

    static File config = new File(FabricLoader.getInstance().getConfigDir().toFile(), "cakedelight-common.json");

    public static CakeConfig loadConfig() {

        CakeConfig cakeConfig = new CakeConfig();

        if (!config.exists()) {
            write(cakeConfig);
        }

        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(config.toPath());
            Gson gson = new Gson();
            cakeConfig = gson.fromJson(reader, CakeConfig.class);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cakeConfig;

    }

    public static void write(CakeConfig cakeConfig) {

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(config);
            Gson gson = new Gson();
            fileWriter.write(gson.toJson(cakeConfig));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
