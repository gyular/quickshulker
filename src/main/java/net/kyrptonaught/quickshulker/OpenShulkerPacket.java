package net.kyrptonaught.quickshulker;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.quickshulker.api.Util;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class OpenShulkerPacket {
    private static final Identifier OPEN_SHULKER_PACKET = new Identifier(QuickShulkerMod.MOD_ID, "open_shulker_packet");

    static void registerReceivePacket() {
        ServerPlayNetworking.registerGlobalReceiver(OPEN_SHULKER_PACKET, (server, player, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            int type = packetByteBuf.readInt();
            int invSlot = packetByteBuf.readInt();
            server.execute(() -> {
                Util.openItem(player, invSlot, type);
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendOpenPacket(int invSlot, int type) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(type);
        buf.writeInt(invSlot);
        ClientPlayNetworking.send(OPEN_SHULKER_PACKET, new PacketByteBuf(buf));
    }
}