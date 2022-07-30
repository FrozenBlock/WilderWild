package net.frozenblock.wilderwild.misc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.frozenblock.wilderwild.WilderWild;

import java.text.MessageFormat;

@Config(name = WilderWild.MOD_ID)
public class WilderWildConfig implements ConfigData {

    @ConfigEntry.Category("generation")
    @ConfigEntry.Gui.CollapsibleObject
    public MudLakeConfig mudLakeConfig = new MudLakeConfig(true, 25);

    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig albinoCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig amberChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig ashenCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig boneSpider = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig boldStripedRabbit = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig boulderingZombie = new EntityConfig(15);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig bronzedChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig cluckshroom = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig cookieCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig creamCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig dairyCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig fancyChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig fleckedSheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig freckledRabbit = new EntityConfig(4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig fuzzySheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig goldCrestedChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig harelequinRabbit = new EntityConfig(4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig hornedSheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig inkySheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig jollyLlama = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig jumboRabbit = new EntityConfig(4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig lobberZombie = new EntityConfig(15);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig longNosedSheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig midnightChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig moobloom = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig moolip = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig mottledPig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig muddyPig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig muddyFootRabbit = new EntityConfig(4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig palePig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig patchedSheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig piebaldPig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig pinkFootedPig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig pintoCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig rainbowSheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig rockySheep = new EntityConfig(12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig skeletonWolf = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig skewbaldChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig sootyPig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig spottedPig = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig stormyChicken = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig sunsetCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig tropicalSlime = new EntityConfig(10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig umbraCow = new EntityConfig(8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig vestedRabbit = new EntityConfig(4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig vilerWitch = new EntityConfig(1, 1, 1);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig woolyCow = new EntityConfig(8);

    @Override
    public void validatePostLoad() throws ValidationException {

    }

    private void printCorrectionMessage(String field, String old, String corrected) {
        System.out.println(MessageFormat.format("[Earth2Java] (Config) Corrected field {0}: was {1}, now {2}", field, old, corrected));
    }

    public static class EntityConfig {
        public boolean spawn;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int weight;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int groupMin;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int groupMax;

        EntityConfig(int weight, int groupMin, int groupMax) {
            this.spawn = true;
            this.weight = weight;
            this.groupMin = groupMin;
            this.groupMax = groupMax;
        }

        EntityConfig(int weight) {
            this(weight, 2, 4);
        }
    }

    public static class MudLakeConfig {
        public boolean canGenerate;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int mudLakeFrequency;

        MudLakeConfig(boolean canGenerate, int mudLakeFrequency) {
            this.canGenerate = canGenerate;
            this.mudLakeFrequency = mudLakeFrequency;
        }
    }
}
