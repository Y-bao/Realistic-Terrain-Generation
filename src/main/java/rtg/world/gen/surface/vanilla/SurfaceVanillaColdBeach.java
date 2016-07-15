package rtg.world.gen.surface.vanilla;

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
import rtg.world.gen.surface.SurfaceBase;

public class SurfaceVanillaColdBeach extends SurfaceBase
{
    
    private IBlockState cliffBlock1;
    private IBlockState cliffBlock2;
    private byte sandMetadata;
    private int cliffType;
    
    public SurfaceVanillaColdBeach(BiomeConfig config, IBlockState top, IBlockState filler, IBlockState cliff1, IBlockState cliff2, byte metadata, int cliff)
    {
    
        super(config, top, filler);
        
        cliffBlock1 = cliff1;
        cliffBlock2 = cliff2;
        sandMetadata = metadata;
        cliffType = cliff;
    }
    
    @Override
	public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, Biome[] base)
	{
    
        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.3f ? true : false;
        boolean dirt = false;
        
        for (int k = 255; k > -1; k--)
        {
            IBlockState b = primer.getBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y));
            if(b == Blocks.AIR.getDefaultState())
            {
                depth = -1;
            }
            else if(b == Blocks.STONE.getDefaultState())
            {
                depth++;
                
                if (cliff)
                {
                    if (cliffType == 1)
                    {
                        if (depth < 6)
                        {
                            primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), cliffBlock1);
                        }
                    }
                    else
                    {
                        if (depth > -1 && depth < 2)
                        {
                            primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), rand.nextInt(3) == 0 ? cliffBlock2 : cliffBlock1);
                        }
                        else if (depth < 10)
                        {
                            primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), cliffBlock1);
                        }
                    }
                }
                else if (depth < 6)
                {
                    if (depth == 0 && k > 61)
                    {
                        if (simplex.noise2(i / 12f, j / 12f) > -0.3f + ((k - 61f) / 15f))
                        {
                            dirt = true;
                            primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), topBlock);
                        }
                        else
                        {
                            if (k<69) {
                                primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), Blocks.SAND.getStateFromMeta(sandMetadata));
                            } // else probably steep shore so leave stone

                        }
                    }
                    else if (depth < 4)
                    {
                        if (dirt)
                        {
                            primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), fillerBlock);
                        }
                        else
                        {
                            if (k<69) {
                                primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), Blocks.SAND.getStateFromMeta(sandMetadata));
                            } 
                        }
                    }
                    else if (!dirt)
                    {
                        primer.setBlockState(MathUtils.globalToLocal(x), k, MathUtils.globalToLocal(y), Blocks.SANDSTONE.getDefaultState());
                    }
                }
            }
        }
    }
}
