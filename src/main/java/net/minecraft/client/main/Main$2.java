//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.minecraft.client.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;

class Main$2 extends Thread {
    Main$2(String $$0) {
        super($$0);
    }

    public void run() {
        Minecraft $$0 = Minecraft.getInstance();
        IntegratedServer $$1 = $$0.getSingleplayerServer();
        if ($$1 != null) {
            $$1.halt(true);
        }

    }
}