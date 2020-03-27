package io.github.synthrose.artofalchemy.render;

import java.util.Map.Entry;

import io.github.synthrose.artofalchemy.block.AoABlocks;
import io.github.synthrose.artofalchemy.essentia.Essentia;
import io.github.synthrose.artofalchemy.essentia.RegistryEssentia;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import io.github.synthrose.artofalchemy.fluid.AoAFluids;
import io.github.synthrose.artofalchemy.item.AoAItems;

@Environment(EnvType.CLIENT)
public class AoARenderers {

	@Environment(EnvType.CLIENT)
	public static void registerRenderers() {
		RendererFluid.setupFluidRendering(AoAFluids.ALKAHEST, AoAFluids.ALKAHEST_FLOWING,
    			new Identifier("minecraft", "water"), 0xAA0077);
    	RendererFluid.markTranslucent(AoAFluids.ALKAHEST, AoAFluids.ALKAHEST_FLOWING);
    	
    	RegistryEssentia.INSTANCE.forEach((Essentia essentia) -> {
    		Fluid still = AoAFluids.ESSENTIA_FLUIDS.get(essentia);
    		Fluid flowing = AoAFluids.ESSENTIA_FLUIDS_FLOWING.get(essentia);
    		RendererFluid.setupFluidRendering(still, flowing, new Identifier("minecraft", "water"), essentia.getColor());
    		RendererFluid.markTranslucent(still, flowing);
    	});
    	
    	ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
			0xAA0077, AoABlocks.DISSOLVER);
    	
    	
    	for (Entry<Essentia, Item> entry : AoAItems.ESSENTIA_VESSELS.entrySet()) {
    		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
        		if (tintIndex == 0) {
        			if (entry.getKey() == null) {
        				return 0xAA0077;
        			} else {
        				return entry.getKey().getColor();
        			}
        		} else {
        			return 0xFFFFFF;
        		}
        	}, entry.getValue());
    	}
    	
	}

}