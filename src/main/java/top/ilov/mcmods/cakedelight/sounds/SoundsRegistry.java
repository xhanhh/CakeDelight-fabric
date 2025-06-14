package top.ilov.mcmods.cakedelight.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static top.ilov.mcmods.cakedelight.CakeDelightMod.MOD_ID;

public class SoundsRegistry {

    public static final SoundEvent eat_ekac = registerSound("eating_ekac");
    public static final SoundEvent eat_cake = registerSound("eating_cake");

    public static SoundEvent registerSound(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<SoundEvent> key = RegistryKey.of(RegistryKeys.SOUND_EVENT, id);
        SoundEvent soundEvent = SoundEvent.of(id);
        return Registry.register(Registries.SOUND_EVENT, key, soundEvent);
    }

    public static void registerSounds() {}

}
