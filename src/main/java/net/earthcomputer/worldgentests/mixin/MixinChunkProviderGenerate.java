package net.earthcomputer.worldgentests.mixin;

import net.earthcomputer.worldgentests.WorldGenTests;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Random;

@Mixin(ChunkProviderGenerate.class)
public abstract class MixinChunkProviderGenerate {

    @Shadow private double[] field_903_t;
    @Shadow private World worldObj;
    @Shadow private Random rand;
    @Shadow public NoiseGeneratorOctaves field_920_c;
    @Shadow private double[] field_4178_w;
    @Shadow private MobSpawnerBase[] field_4179_v;

    @Shadow public abstract void func_4062_a(int var1, int var2, byte[] var3, MobSpawnerBase[] var4);

    @Unique private int index;
    //@Unique private Random rand2 = new Random();

    /**
     * lazy
     * @author earth
     */
    @Overwrite
    public Chunk func_533_b(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        byte[] var3 = new byte[32768];
        Chunk var4 = new Chunk(this.worldObj, var3, x, z);
        if (this.field_4179_v == null || this.field_4179_v.length < 256)
            this.field_4179_v = new MobSpawnerBase[256];
        Arrays.fill(this.field_4179_v, MobSpawnerBase.field_4254_c);
        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                for (int dy = 0; dy < 70; dy++) {
                    setBlock(var3, dx, dy, dz, Block.stone.blockID);
                }
            }
        }
        this.func_4062_a(x, z, var3, this.field_4179_v);

        int tree1X = Math.floorMod(15 - 5 - 8, 16);
        int tree1Z = Math.floorMod(10 - 8 - 8, 16);
        int tree2X = Math.floorMod(15 - 3 - 8, 16);
        int tree2Z = Math.floorMod(10 + 3 - 8, 16);
        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                if (((Math.abs(dx - tree1X) > 1) || (Math.abs(dz - tree1Z) > 1))
                        && ((Math.abs(dx - tree2X) > 1) || (Math.abs(dz - tree2Z) > 1))) {
                    setBlock(var3, dx, 69, dz, Block.cobblestone.blockID);
                }
            }
        }

        if (x == 0 && z == 0) {
            setBlock(var3, 0, 69, 0, Block.sand.blockID); // spawn point
        }

        var4.func_1024_c();
        return var4;
    }

    private static void setBlock(byte[] blocks, int x, int y, int z, int block) {
        blocks[x << 11 | z << 7 | y] = (byte) block;
    }


    /**
     * lazy
     * @author earth
     */
    @Overwrite
    public void populate(IChunkProvider var1, int var2, int var3) {
        BlockSand.fallInstantly = true;
        int var4 = var2 * 16;
        int var5 = var3 * 16;
        MobSpawnerBase var6 = MobSpawnerBase.field_4254_c;
        this.rand.setSeed(this.worldObj.randomSeed ^ 0x5deece66dL);
        /*
        MobSpawnerBase var6 = this.worldObj.func_4075_a().func_4073_a(var4 + 16, var5 + 16);
        this.rand.setSeed(this.worldObj.randomSeed);
        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)var2 * var7 + (long)var3 * var9 ^ this.worldObj.randomSeed);
         */
        WorldGenTests.dungeonSuccessful = false;
        double var11 = 0.25D;

        for(int var13 = 0; var13 < 8; ++var13) {
            int var14 = var4 + this.rand.nextInt(16) + 8;
            int var15 = this.rand.nextInt(128);
            int var16 = var5 + this.rand.nextInt(16) + 8;
            if ((new WorldGenDungeons()).generate(this.worldObj, this.rand, var14, var15, var16))
                WorldGenTests.dungeonSuccessful = true;
        }

        for(int var25 = 0; var25 < 10; ++var25) {
            int var34 = var4 + this.rand.nextInt(16);
            int var43 = this.rand.nextInt(128);
            int var52 = var5 + this.rand.nextInt(16);
            (new WorldGenClay(32)).generate(this.worldObj, this.rand, var34, var43, var52);
        }

        for(int var26 = 0; var26 < 20; ++var26) {
            int var35 = var4 + this.rand.nextInt(16);
            int var44 = this.rand.nextInt(128);
            int var53 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.dirt.blockID, 32)).generate(this.worldObj, this.rand, var35, var44, var53);
        }

        for(int var27 = 0; var27 < 10; ++var27) {
            int var36 = var4 + this.rand.nextInt(16);
            int var45 = this.rand.nextInt(128);
            int var54 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.gravel.blockID, 32)).generate(this.worldObj, this.rand, var36, var45, var54);
        }

        for(int var28 = 0; var28 < 20; ++var28) {
            int var37 = var4 + this.rand.nextInt(16);
            int var46 = this.rand.nextInt(128);
            int var55 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreCoal.blockID, 16)).generate(this.worldObj, this.rand, var37, var46, var55);
        }

        for(int var29 = 0; var29 < 20; ++var29) {
            int var38 = var4 + this.rand.nextInt(16);
            int var47 = this.rand.nextInt(64);
            int var56 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreIron.blockID, 8)).generate(this.worldObj, this.rand, var38, var47, var56);
        }

        for(int var30 = 0; var30 < 2; ++var30) {
            int var39 = var4 + this.rand.nextInt(16);
            int var48 = this.rand.nextInt(32);
            int var57 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreGold.blockID, 8)).generate(this.worldObj, this.rand, var39, var48, var57);
        }

        for(int var31 = 0; var31 < 8; ++var31) {
            int var40 = var4 + this.rand.nextInt(16);
            int var49 = this.rand.nextInt(16);
            int var58 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(this.worldObj, this.rand, var40, var49, var58);
        }

        for(int var32 = 0; var32 < 1; ++var32) {
            int var41 = var4 + this.rand.nextInt(16);
            int var50 = this.rand.nextInt(16);
            int var59 = var5 + this.rand.nextInt(16);
            (new WorldGenMinable(Block.oreDiamond.blockID, 7)).generate(this.worldObj, this.rand, var41, var50, var59);
        }

        var11 = 0.5D;
        int var33 = (int)((this.field_920_c.func_806_a((double)var4 * var11, (double)var5 * var11) / 8.0D + this.rand.nextDouble() * 4.0D + 4.0D) / 3.0D);
        int var42 = 0;
        if (this.rand.nextInt(10) == 0) {
            ++var42;
        }

        if (var6 == MobSpawnerBase.field_4253_d) {
            var42 += var33 + 5;
        }

        if (var6 == MobSpawnerBase.field_4256_a) {
            var42 += var33 + 5;
        }

        if (var6 == MobSpawnerBase.field_4254_c) {
            var42 += var33 + 2;
        }

        if (var6 == MobSpawnerBase.field_4250_g) {
            var42 += var33 + 5;
        }

        if (var6 == MobSpawnerBase.field_4249_h) {
            var42 -= 20;
        }

        if (var6 == MobSpawnerBase.field_4246_k) {
            var42 -= 20;
        }

        if (var6 == MobSpawnerBase.field_4248_i) {
            var42 -= 20;
        }

        Object var51 = new WorldGenTrees();
        if (this.rand.nextInt(10) == 0) {
            var51 = new WorldGenBigTree();
        }

        if (var6 == MobSpawnerBase.field_4256_a && this.rand.nextInt(3) == 0) {
            var51 = new WorldGenBigTree();
        }

        var42 = 9;

        for(int var60 = 0; var60 < var42; ++var60) {
            int var17 = var4 + this.rand.nextInt(16) + 8;
            int var18 = var5 + this.rand.nextInt(16) + 8;
            ((WorldGenerator)var51).func_517_a(1.0D, 1.0D, 1.0D);
            ((WorldGenerator)var51).generate(this.worldObj, this.rand, var17, this.worldObj.getHeightValue(var17, var18), var18);
        }

        for(int var61 = 0; var61 < 2; ++var61) {
            int var68 = var4 + this.rand.nextInt(16) + 8;
            int var78 = this.rand.nextInt(128);
            int var19 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantYellow.blockID)).generate(this.worldObj, this.rand, var68, var78, var19);
        }

        if (this.rand.nextInt(2) == 0) {
            int var62 = var4 + this.rand.nextInt(16) + 8;
            int var69 = this.rand.nextInt(128);
            int var79 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantRed.blockID)).generate(this.worldObj, this.rand, var62, var69, var79);
        }

        if (this.rand.nextInt(4) == 0) {
            int var63 = var4 + this.rand.nextInt(16) + 8;
            int var70 = this.rand.nextInt(128);
            int var80 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.rand, var63, var70, var80);
        }

        if (this.rand.nextInt(8) == 0) {
            int var64 = var4 + this.rand.nextInt(16) + 8;
            int var71 = this.rand.nextInt(128);
            int var81 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.rand, var64, var71, var81);
        }

        for(int var65 = 0; var65 < 10; ++var65) {
            int var72 = var4 + this.rand.nextInt(16) + 8;
            int var82 = this.rand.nextInt(128);
            int var88 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenReed()).generate(this.worldObj, this.rand, var72, var82, var88);
        }

        if (this.rand.nextInt(32) == 0) {
            int var66 = var4 + this.rand.nextInt(16) + 8;
            int var73 = this.rand.nextInt(128);
            int var83 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(this.worldObj, this.rand, var66, var73, var83);
        }

        int var67 = 0;
        if (var6 == MobSpawnerBase.field_4249_h) {
            var67 += 10;
        }

        for(int var74 = 0; var74 < var67; ++var74) {
            int var84 = var4 + this.rand.nextInt(16) + 8;
            int var89 = this.rand.nextInt(128);
            int var20 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenCactus()).generate(this.worldObj, this.rand, var84, var89, var20);
        }

        for(int var75 = 0; var75 < 50; ++var75) {
            int var85 = var4 + this.rand.nextInt(16) + 8;
            int var90 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int var93 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenLiquids(Block.waterStill.blockID)).generate(this.worldObj, this.rand, var85, var90, var93);
        }

        for(int var76 = 0; var76 < 20; ++var76) {
            int var86 = var4 + this.rand.nextInt(16) + 8;
            int var91 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            int var94 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenLiquids(Block.lavaStill.blockID)).generate(this.worldObj, this.rand, var86, var91, var94);
        }

        this.field_4178_w = this.worldObj.func_4075_a().func_4071_a(this.field_4178_w, var4 + 8, var5 + 8, 16, 16);

        for(int var77 = var4 + 8; var77 < var4 + 8 + 16; ++var77) {
            for(int var87 = var5 + 8; var87 < var5 + 8 + 16; ++var87) {
                int var92 = var77 - (var4 + 8);
                int var95 = var87 - (var5 + 8);
                int var21 = this.worldObj.func_4083_e(var77, var87);
                double var22 = this.field_4178_w[var92 * 16 + var95] - (double)(var21 - 64) / 64.0D * 0.3D;
                if (var22 < 0.5D && var21 > 0 && var21 < 128 && this.worldObj.getBlockId(var77, var21, var87) == 0 && this.worldObj.getBlockMaterial(var77, var21 - 1, var87).func_880_c() && this.worldObj.getBlockMaterial(var77, var21 - 1, var87) != Material.field_1320_r) {
                    this.worldObj.setBlockWithNotify(var77, var21, var87, Block.snow.blockID);
                }
            }
        }

        if (WorldGenTests.dungeonSuccessful)
            WorldGenTests.chunksWithDungeons++;
        WorldGenTests.totalChunks++;

        BlockSand.fallInstantly = false;
    }

}
