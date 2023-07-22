//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.minecraft.client.main;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

class Main$3 extends Thread {
    private final Minecraft val$minecraft;

    Main$3(String $$0, Minecraft var2) {
        super($$0);
        this.val$minecraft = var2;
    }

    public void run() {
        try {
            RenderSystem.initGameThread(true);
            this.val$minecraft.run();
        } catch (Throwable var2) {
            Main.LOGGER.error("Exception in client thread", var2);
        }

    }
}
