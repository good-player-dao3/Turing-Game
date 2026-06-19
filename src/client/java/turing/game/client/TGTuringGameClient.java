package turing.game.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import turing.game.Nodes.Blocks.Cable.Cable;

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
	}
}