package dev.cammiescorner.icarus;

import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import dev.cammiescorner.icarus.client.IcarusClientConfig;

@ConfigInfo(
        title = "Icarus",
        description = "Adds a bunch of colourful and unique wings.",
        links = {
                @ConfigInfo.Link(
                        value = "https://modrinth.com/mod/Dw7M6XKW",
                        icon = "modrinth",
                        text = "Modrinth"
                ),
                @ConfigInfo.Link(
                        value = "https://curseforge.com/projects/428151",
                        icon = "curseforge",
                        text = "Curseforge"
                ),
                @ConfigInfo.Link(
                        value = "https://github.com/CammiesCorner/Icarus",
                        icon = "github",
                        text = "Github"
                ),
                @ConfigInfo.Link(
                        value = "https://cammiescorner.dev/discord",
                        icon = "gamepad-2",
                        text = "Discord"
                )
        }
)
@Config(value = Icarus.MODID, categories = IcarusClientConfig.class)
public final class IcarusConfig {

    @ConfigEntry(id = "can_loop_de_loop", translation = "config.icarus.can_loop_de_loop_server")
    public static boolean canLoopDeLoop = true;
    @ConfigEntry(id = "armor_slows", translation = "config.icarus.armor_slows")
    public static boolean armorSlows = true;
    @ConfigEntry(id = "can_slow_fall", translation = "config.icarus.can_slow_fall")
    public static boolean canSlowFall = true;
    @ConfigEntry(id = "max_slowed_multiplier", translation = "config.icarus.max_slowed_multiplier")
    public static float maxSlowedMultiplier = 3F;
    @ConfigEntry(id = "wings_speed", translation = "config.icarus.wings_speed")
    public static float wingsSpeed = 0.0125F;
    @ConfigEntry(id = "wings_durability", translation = "config.icarus.wings_durability")
    public static int wingsDurability = 0;
    @ConfigEntry(id = "exhaustion_amount", translation = "config.icarus.exhaustion_amount")
    public static float exhaustionAmount = 0.03F;
    @ConfigEntry(id = "required_food_amount", translation = "config.icarus.required_food_amount")
    public static float requiredFoodAmount = 6.001F;
    @ConfigEntry(id = "max_height_enabled", translation = "config.icarus.max_height_enabled")
    public static boolean maxHeightEnabled = true;
    @ConfigEntry(id = "drop_out_of_sky_when_tired", translation = "config.icarus.drop_out_of_sky_when_tired")
    public static boolean dropOutOfSkyWhenTired = false;
    @ConfigEntry(id = "use_stamina_for_flight", translation = "config.icarus.use_stamina_for_flight")
    public static boolean useStaminaForFlight = true;
    @ConfigEntry(id = "stamina_amount", translation = "config.icarus.stamina_amount")
    public static float staminaAmount = 1F;
    @ConfigEntry(id = "stamina_regen", translation = "config.icarus.stamina_regen")
    public static float staminaRegen = 2F;
    @ConfigEntry(id = "max_height_above_world", translation = "config.icarus.max_height_above_world")
    public static int maxHeightAboveWorld = 64;
    @ConfigEntry(id = "flying_target_radius", translation = "config.icarus.flying_target_radius")
    public static float flyingTargetRadius = 0.25F;

    public static IcarusClientConfig client;
}
