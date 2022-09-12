package io.purplik.darkeststress.common.stress;

import net.minecraft.nbt.CompoundTag;

public class PlayerStress {

    private int stress;
    private String afflictionType = "none";
    private final int min_stress = 0;
    public final int max_stress = 100;

    public String[] positiveAfflictions = {"motivated", "fearless", "resistant"};
    public String[] negativeAfflictions = {"hungry", "tired", "insomniac"};

    public int getStress() {
        return stress;
    }

    public String getAfflictionType() {
        return afflictionType;
    }

    public void setAfflictionType(String afflictionType) {
        this.afflictionType = afflictionType;
    }

    public void setStress(int stressAmount) {
        this.stress = stressAmount;
    }

    public void addStress(int addStress) {
        this.stress = Math.min(stress + addStress, max_stress);
    }

    public void removeStress(int removeStress) {
        this.stress = Math.max(stress - removeStress, min_stress);
    }

    public void copyFrom(PlayerStress source) {
        this.stress = source.stress;
    }

    public void saveNBTData(CompoundTag nbtData) {
        nbtData.putInt("stress", stress);
        nbtData.putString("afflictionType", afflictionType);
    }

    public void loadNBTData(CompoundTag nbtData) {
        stress = nbtData.getInt("stress");
        afflictionType = nbtData.getString("afflictionType");
    }
}
