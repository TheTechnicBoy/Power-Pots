package de.thetechnicboy.powerpots.datagen;

import de.thetechnicboy.powerpots.PowerPots;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageGenerator extends LanguageProvider {
    public ModLanguageGenerator(PackOutput output, String locale) {
        super(output, PowerPots.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + PowerPots.MODID, "Power Pots");

        this.addBlock(PowerPots.POWER_POT_MK1, "PowerPot MK1");
        this.addBlock(PowerPots.POWER_POT_MK2, "PowerPot MK2");
        this.addBlock(PowerPots.POWER_POT_MK3, "PowerPot MK3");
        this.addBlock(PowerPots.POWER_POT_MK4, "PowerPot MK4");
    }
}
