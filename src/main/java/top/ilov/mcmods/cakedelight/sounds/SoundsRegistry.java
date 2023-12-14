package top.ilov.mcmods.cakedelight.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class SoundsRegistry {

    public static final SoundEvent eat_ekac = registerSound("eating_ekac");

    public static SoundEvent registerSound(String string) {

        return Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, string), SoundEvent.of(new Identifier(MOD_ID, string)));

    }

    public static void registerSounds() {}

}
