package rtg.world.gen.surface;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import rtg.api.biome.BiomeConfig;
import rtg.util.CellNoise;
import rtg.util.CliffCalculator;
import rtg.util.MathUtils;
import rtg.util.OpenSimplexNoise;

public class SurfaceRedDesert extends SurfaceBase
{
	private IBlockState cliffBlock1;
	private IBlockState cliffBlock2;
	private IBlockState bottomBlock;
	
	public SurfaceRedDesert(BiomeConfig config)
	{
		super(config, Blocks.SAND.getStateFromMeta(1), Blocks.SAND.getStateFromMeta(1));
		
		bottomBlock = Blocks.SANDSTONE.getDefaultState(); 
		cliffBlock1 = Blocks.STAINED_HARDENED_CLAY.getDefaultState();
	}
	
    @Override
	public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, Biome[] base)
	{
    
		float c = CliffCalculator.calc(x, y, noise);
		boolean cliff = c > 1.4f ? true : false;

		for(int k = 255; k > -1; k--)
		{
			IBlockState b = primer.getBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y));
            if(b == Blocks.AIR.getDefaultState())
            {
            	depth = -1;
            }
            else if(b == Blocks.STONE.getDefaultState())
            {
            	depth++;

            	if(cliff)
            	{
            		if (depth < 6)
            		{                			
            			primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), cliffBlock1);
            		}
            	}
            	else if(depth < 6)
            	{
	        		if(depth == 0 && k > 61)
	        		{
	        			primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), topBlock);
	        		}
	        		else if(depth < 4)
	        		{
	        			primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), fillerBlock);
	        		}
	        		else
	        		{
	        			primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), bottomBlock);
	        		}
            	}
            }
		}
	}
}
