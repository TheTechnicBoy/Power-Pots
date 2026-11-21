package de.thetechnicboy.powerpots.pot;

import net.neoforged.neoforge.energy.EnergyStorage;

public class PowerPotEnergyStorage extends EnergyStorage {
    public PowerPotEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy) {
        this.energy = Math.min(this.energy + energy, getMaxEnergyStored());
    }

    public void consumeEnergy(int energy) {
        this.energy = Math.max(this.energy - energy, 0);
    }
}
