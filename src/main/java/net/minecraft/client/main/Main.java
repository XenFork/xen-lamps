//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.minecraft.client.main;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.User;
import net.minecraft.client.User.Type;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.telemetry.TelemetryProperty;
import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
import net.minecraft.core.UUIDUtil;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class Main {
    static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
    }

    @SuppressWarnings("DataFlowIssue")
    @DontObfuscate
    public static void main(String[] $$0) {
        Stopwatch $$1 = Stopwatch.createStarted(Ticker.systemTicker());
        Stopwatch $$2 = Stopwatch.createStarted(Ticker.systemTicker());
        GameLoadTimesEvent.INSTANCE.beginStep(TelemetryProperty.LOAD_TIME_TOTAL_TIME_MS, $$1);
        GameLoadTimesEvent.INSTANCE.beginStep(TelemetryProperty.LOAD_TIME_PRE_WINDOW_MS, $$2);
        SharedConstants.tryDetectVersion();
        SharedConstants.enableDataFixerOptimizations();
        OptionParser $$3 = new OptionParser();
        $$3.allowsUnrecognizedOptions();
        $$3.accepts("demo");
        $$3.accepts("disableMultiplayer");
        $$3.accepts("disableChat");
        $$3.accepts("fullscreen");
        $$3.accepts("checkGlErrors");
        OptionSpec<Void> $$4 = $$3.accepts("jfrProfile");
        OptionSpec<String> $$5 = $$3.accepts("quickPlayPath").withRequiredArg();
        OptionSpec<String> $$6 = $$3.accepts("quickPlaySingleplayer").withRequiredArg();
        OptionSpec<String> $$7 = $$3.accepts("quickPlayMultiplayer").withRequiredArg();
        OptionSpec<String> $$8 = $$3.accepts("quickPlayRealms").withRequiredArg();
        OptionSpec<File> $$9 = $$3.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        OptionSpec<File> $$10 = $$3.accepts("assetsDir").withRequiredArg().ofType(File.class);
        OptionSpec<File> $$11 = $$3.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        OptionSpec<String> $$12 = $$3.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> $$13 = $$3.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        OptionSpec<String> $$14 = $$3.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> $$15 = $$3.accepts("proxyPass").withRequiredArg();
        OptionSpec<String> $$16 = $$3.accepts("username").withRequiredArg().defaultsTo("Player" + Util.getMillis() % 1000L);
        OptionSpec<String> $$17 = $$3.accepts("uuid").withRequiredArg();
        OptionSpec<String> $$18 = $$3.accepts("xuid").withOptionalArg().defaultsTo("");
        OptionSpec<String> $$19 = $$3.accepts("clientId").withOptionalArg().defaultsTo("");
        OptionSpec<String> $$20 = $$3.accepts("accessToken").withRequiredArg().required();
        OptionSpec<String> $$21 = $$3.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> $$22 = $$3.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        OptionSpec<Integer> $$23 = $$3.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        OptionSpec<Integer> $$24 = $$3.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
        OptionSpec<Integer> $$25 = $$3.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
        OptionSpec<String> $$26 = $$3.accepts("userProperties").withRequiredArg().defaultsTo("{}");
        OptionSpec<String> $$27 = $$3.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
        OptionSpec<String> $$28 = $$3.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> $$29 = $$3.accepts("userType").withRequiredArg().defaultsTo(Type.LEGACY.getName());
        OptionSpec<String> $$30 = $$3.accepts("versionType").withRequiredArg().defaultsTo("release");
        OptionSpec<String> $$31 = $$3.nonOptions();
        OptionSet $$32 = $$3.parse($$0);
        List<String> $$33 = $$32.valuesOf($$31);
        if (!$$33.isEmpty()) {
            System.out.println("Completely ignored arguments: " + $$33);
        }

        String $$34 = (String)parseArgument($$32, $$12);
        Proxy $$35 = Proxy.NO_PROXY;
        if ($$34 != null) {
            try {
                $$35 = new Proxy(java.net.Proxy.Type.SOCKS, new InetSocketAddress($$34, parseArgument($$32, $$13)));
            } catch (Exception ignored) {
            }
        }

        String $$36 = (String)parseArgument($$32, $$14);
        String $$37 = (String)parseArgument($$32, $$15);
        if (!$$35.equals(Proxy.NO_PROXY) && stringHasValue($$36) && stringHasValue($$37)) {
            Authenticator.setDefault(new Main$1($$36, $$37));
        }

        int $$38 = parseArgument($$32, $$22);
        int $$39 = parseArgument($$32, $$23);
        OptionalInt $$40 = ofNullable(parseArgument($$32, $$24));
        OptionalInt $$41 = ofNullable(parseArgument($$32, $$25));
        boolean $$42 = $$32.has("fullscreen");
        boolean $$43 = $$32.has("demo");
        boolean $$44 = $$32.has("disableMultiplayer");
        boolean $$45 = $$32.has("disableChat");
        String $$46 = (String)parseArgument($$32, $$21);
        Gson $$47 = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create();
        PropertyMap $$48 = (PropertyMap)GsonHelper.fromJson($$47, parseArgument($$32, $$26), PropertyMap.class);
        PropertyMap $$49 = (PropertyMap)GsonHelper.fromJson($$47, (String)parseArgument($$32, $$27), PropertyMap.class);
        String $$50 = (String)parseArgument($$32, $$30);
        File $$51 = (File)parseArgument($$32, $$9);
        File $$52 = $$32.has($$10) ? (File)parseArgument($$32, $$10) : new File($$51, "assets/");
        File $$53 = $$32.has($$11) ? (File)parseArgument($$32, $$11) : new File($$51, "resourcepacks/");
        String $$54 = $$32.has($$17) ? (String)$$17.value($$32) : UUIDUtil.createOfflinePlayerUUID((String)$$16.value($$32)).toString();
        String $$55 = $$32.has($$28) ? (String)$$28.value($$32) : null;
        String $$56 = (String)$$32.valueOf($$18);
        String $$57 = (String)$$32.valueOf($$19);
        String $$58 = (String)parseArgument($$32, $$5);
        String $$59 = (String)parseArgument($$32, $$6);
        String $$60 = (String)parseArgument($$32, $$7);
        String $$61 = (String)parseArgument($$32, $$8);
        if ($$32.has($$4)) {
            JvmProfiler.INSTANCE.start(Environment.CLIENT);
        }

        CrashReport.preload();
        Bootstrap.bootStrap();
        GameLoadTimesEvent.INSTANCE.setBootstrapTime(Bootstrap.bootstrapDuration.get());
        Bootstrap.validate();
        Util.startTimerHackThread();
        String $$62 = (String)$$29.value($$32);
        User.Type $$63 = Type.byName($$62);
        if ($$63 == null) {
            LOGGER.warn("Unrecognized user type: {}", $$62);
        }

        User $$64 = new User((String)$$16.value($$32), $$54, (String)$$20.value($$32), emptyStringToEmptyOptional($$56), emptyStringToEmptyOptional($$57), $$63);
        GameConfig $$65 = new GameConfig(new GameConfig.UserData($$64, $$48, $$49, $$35), new DisplayData($$38, $$39, $$40, $$41, $$42), new GameConfig.FolderData($$51, $$53, $$52, $$55), new GameConfig.GameData($$43, $$46, $$50, $$44, $$45), new GameConfig.QuickPlayData($$58, $$59, $$60, $$61));
        Thread $$66 = new Main$2("Client Shutdown Thread");
        $$66.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        Runtime.getRuntime().addShutdownHook($$66);

        Minecraft $$72;
        try {
            Thread.currentThread().setName("Render thread");
            RenderSystem.initRenderThread();
            RenderSystem.beginInitialization();
            $$72 = new Minecraft($$65);
            RenderSystem.finishInitialization();
        } catch (SilentInitException var81) {
            LOGGER.warn("Failed to create window: ", var81);
            return;
        } catch (Throwable var82) {
            CrashReport $$70 = CrashReport.forThrowable(var82, "Initializing game");
            CrashReportCategory $$71 = $$70.addCategory("Initialization");
            NativeModuleLister.addCrashSection($$71);
            Minecraft.fillReport(null, null, $$65.game.launchVersion, (Options)null, $$70);
            Minecraft.crash($$70);
            return;
        }

        Main$3 $$74;
        if ($$72.renderOnThread()) {
            $$74 = new Main$3("Game thread", $$72);
            $$74.start();
            while ($$72.isRunning());
//            while(true) {
//                if ($$72.isRunning()) {
//                    continue;
//                }
//            }
        } else {
            $$74 = null;

            try {
                RenderSystem.initGameThread(false);
                $$72.run();
            } catch (Throwable var80) {
                LOGGER.error("Unhandled game exception", var80);
            }
        }

        BufferUploader.reset();

        try {
            $$72.stop();
        } finally {
            $$72.destroy();
        }

    }

    private static Optional<String> emptyStringToEmptyOptional(String $$0) {
        return $$0.isEmpty() ? Optional.empty() : Optional.of($$0);
    }

    private static OptionalInt ofNullable(@Nullable Integer $$0) {
        return $$0 != null ? OptionalInt.of($$0) : OptionalInt.empty();
    }

    @Nullable
    private static <T> T parseArgument(OptionSet $$0, OptionSpec<T> $$1) {
        try {
            return $$0.valueOf($$1);
        } catch (Throwable var5) {
            if ($$1 instanceof ArgumentAcceptingOptionSpec<T> $$3) {
                List<T> $$4 = $$3.defaultValues();
                if (!$$4.isEmpty()) {
                    return $$4.get(0);
                }
            }
            throw var5;
        }
    }

    private static boolean stringHasValue(@Nullable String $$0) {
        return $$0 != null && !$$0.isEmpty();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }
}
