//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.minecraft.client.main;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class Main$1 extends Authenticator {
    private final String val$proxyUser;
    private final String val$proxyPass;

    Main$1(String var1, String var2) {
        this.val$proxyUser = var1;
        this.val$proxyPass = var2;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.val$proxyUser, this.val$proxyPass.toCharArray());
    }
}
