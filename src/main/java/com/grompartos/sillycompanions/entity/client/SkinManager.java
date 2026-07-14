package com.grompartos.sillycompanions.entity.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SkinManager {
    // 1. Replaced Identifier with ResourceLocation
    private static final Map<String, Identifier> SKIN_CACHE = new HashMap<>();

    public static Identifier getOrCreateSkin(String username, int type) {
        if (username == null || username.isEmpty() || type == 0) {
            // Default Steve skin
            return Identifier.withDefaultNamespace("textures/entity/player/wide/steve.png");
        }

        String cacheKey = type + "_" + username.toLowerCase();
        if (SKIN_CACHE.containsKey(cacheKey)) {
            return SKIN_CACHE.get(cacheKey);
        }

        // Generate a clean ResourceLocation for Minecraft's texture manager
        Identifier dynamicLoc = Identifier.fromNamespaceAndPath(
                "sillycompanions", "skins/" + type + "_" + username.toLowerCase()
        );
        SKIN_CACHE.put(cacheKey, dynamicLoc);

        // Fetch asynchronously to prevent game thread freezing
        CompletableFuture.runAsync(() -> {
            try {
                String skinUrl = null;

                if (type == 1) {
                    // Microsoft/Mojang Skin retrieval
                    String uuid = getMojangUUID(username);
                    if (uuid != null) {
                        skinUrl = getMojangSkinUrl(uuid);
                    }
                } else if (type == 2) {
                    // Ely.by Skin retrieval
                    skinUrl = "http://skinsystem.ely.by/skins/" + username + ".png";
                }

                if (skinUrl != null) {
                    URL url = URI.create(skinUrl).toURL();
                    try (InputStream stream = url.openStream()) {
                        // 2. Read the downloaded image directly into a NativeImage
                        NativeImage nativeImage = NativeImage.read(stream);

                        // Registration on Minecraft's texture engine must happen on the main thread
                        Minecraft.getInstance().execute(() -> {
                            TextureManager manager = Minecraft.getInstance().getTextureManager();

                            // Create a DynamicTexture using the native image pixels
                            DynamicTexture dynamicTexture = new DynamicTexture(() -> "skin_texture", nativeImage);

                            manager.register(dynamicLoc, dynamicTexture);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return dynamicLoc;
    }

    private static String getMojangUUID(String username) throws Exception {
        URL url = URI.create("https://api.mojang.com/users/profiles/minecraft/" + username).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() == 200) {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
            return json.get("id").getAsString();
        }
        return null;
    }

    private static String getMojangSkinUrl(String uuid) throws Exception {
        URL url = URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() == 200) {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
            JsonObject properties = json.getAsJsonArray("properties").get(0).getAsJsonObject();
            String valueBase64 = properties.get("value").getAsString();
            String decoded = new String(java.util.Base64.getDecoder().decode(valueBase64));
            JsonObject textureJson = JsonParser.parseString(decoded).getAsJsonObject();
            return textureJson.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
        }
        return null;
    }
}