package net.earthcomputer.worldgentests.mixin;

import net.earthcomputer.worldgentests.WorldGenTests;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.src.NoiseGeneratorPerlin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mixin(NoiseGeneratorOctaves.class)
public class MixinNoiseGeneratorOctaves {

    @Shadow private int field_1191_b;

    @Shadow private NoiseGeneratorPerlin[] field_1192_a;

    /**
     * lazy
     * @author earth
     */
    @Overwrite
    public double[] func_807_a(double[] var1, double var2, double var4, double var6, int var8, int var9, int var10, double var11, double var13, double var15) {
        if (var1 == null) {
            var1 = new double[var8 * var9 * var10];
        } else {
            for(int var17 = 0; var17 < var1.length; ++var17) {
                var1[var17] = 0.0D;
            }
        }

        double var20 = 1.0D;

        for(int var19 = 0; var19 < this.field_1191_b; ++var19) {
            this.field_1192_a[var19].func_805_a(var1, var2, var4, var6, var8, var9, var10, var11 * var20, var13 * var20, var15 * var20, var20);
            var20 /= 2.0D;
        }

        return var1;
    }

}
