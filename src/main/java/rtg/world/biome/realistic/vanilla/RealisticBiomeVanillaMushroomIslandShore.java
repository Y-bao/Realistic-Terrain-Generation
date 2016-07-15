package rtg.world.biome.realistic.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import rtg.api.biome.BiomeConfig;
import rtg.world.biome.deco.DecoBaseBiomeDecorations;
import rtg.world.gen.surface.vanilla.SurfaceVanillaMushroomIslandShore;
import rtg.world.gen.terrain.vanilla.TerrainVanillaMushroomIslandShore;

public class RealisticBiomeVanillaMushroomIslandShore extends RealisticBiomeVanillaBase
{	
	public static Block topBlock = Biomes.mushroomIslandShore.topBlock;
	public static Block fillerBlock = Biomes.mushroomIslandShore.fillerBlock;
	
	public RealisticBiomeVanillaMushroomIslandShore(BiomeConfig config)
	{
		super(config, 
			BiomeGenBase.mushroomIslandShore,
			BiomeGenBase.river,
			new TerrainVanillaMushroomIslandShore(),
			new SurfaceVanillaMushroomIslandShore(config, topBlock, fillerBlock, 67, topBlock, 0f)
		);
        this.noLakes=true;
		
		DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
		this.addDeco(decoBaseBiomeDecorations);
	}	
}
