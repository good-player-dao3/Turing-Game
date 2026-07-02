package turing.game.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import turing.game.Rules.Rules;

@Mixin(PoweredRailBlock.class)
public class Better_powered_rail
{
	@Overwrite
	public int getAnalogOutputSignal(BlockState state, @NotNull Level level, BlockPos pos)
	{
		System.out.println("Better powered rail");
		return level.getGameRules().getBoolean(Rules.POWERED_RAIL_CAN_COMPARATOR)?0:15;
	}
	@Overwrite
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		System.out.println("hasAnalogOutputSignal true");
		return true;
	}
}