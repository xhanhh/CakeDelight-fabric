package top.ilov.mcmods.cakedelight.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class SoundsRegistry {

    public static final SoundEvent eat_ekac = registerSound("eating_ekac");

    public static SoundEvent registerSound(String string) {

        return Registry.register(Registry.SOUND_EVENT, new Identifier(MOD_ID, string), new SoundEvent(Identifier.of(MOD_ID, string)));

    }

    public static void registerSounds() {}

}
