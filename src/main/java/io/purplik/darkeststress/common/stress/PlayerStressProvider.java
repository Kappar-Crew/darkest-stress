package io.purplik.darkeststress.common.stress;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStressProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerStress> PLAYER_STRESS = CapabilityManager.get(new CapabilityToken<PlayerStress>() { });

    private PlayerStress stress = null;
    private final LazyOptional<PlayerStress> optional = LazyOptional.of(this::createPlayerStress);

    private PlayerStress createPlayerStress() {
        if(this.stress == null) {
            this.stress = new PlayerStress();
        }

        return this.stress;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_STRESS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerStress().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerStress().loadNBTData(nbt);
    }
}
