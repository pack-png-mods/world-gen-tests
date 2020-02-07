package net.earthcomputer.worldgentests.mixin;

import net.minecraft.src.ThreadDownloadResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.*;
import java.net.URL;

@Mixin(ThreadDownloadResources.class)
public class MixinThreadDownloadResources {

    @Shadow private boolean closing;

    /**
     * try catch
     * @author earth
     */
    @Overwrite
    private void downloadResource(URL var1, File var2, long var3) {
        try {
            byte[] var5 = new byte[4096];
            DataInputStream var6 = new DataInputStream(var1.openStream());
            DataOutputStream var7 = new DataOutputStream(new FileOutputStream(var2));
            int var8 = 0;

            while((var8 = var6.read(var5)) >= 0) {
                var7.write(var5, 0, var8);
                if (this.closing) {
                    return;
                }
            }

            var6.close();
            var7.close();
        } catch (IOException ignore) {

        }
    }

}
