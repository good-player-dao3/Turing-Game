package turing.game.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.DyeColor;
import turing.game.Nodes.Blocks.Cable.Cable;
import turing.game.Nodes.Blocks.Cable.Custom.Cable_block.Cable_block;

@Environment(EnvType.CLIENT)
public class TGTuringGameClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		BlockRenderLayerMap.INSTANCE.putBlock(Cable.CABLE,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.GAT,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.NOT_GAT,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.AND_GAT,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.OR_GAT,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.XOR_GAT,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.CABLE_POWER_TO_REDSTONE,RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Cable.REDSTONE_TO_CABLE_POWER,RenderType.cutout());

		//Cable
		ColorProviderRegistry.BLOCK.register(
				(state,world,pos,tintIndex) -> {
					int color = state.getOptionalValue(Cable_block.COLOR).orElse(0);
					if (color == 0)
						return 0;
					else
					{
						return DyeColor.byId(color-1).getTextureDiffuseColor();
					}
				},
				Cable.CABLE
		);
	}
}