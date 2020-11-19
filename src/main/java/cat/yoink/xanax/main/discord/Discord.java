package cat.yoink.xanax.main.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public enum Discord
{
    INSTANCE;

    private final DiscordRichPresence presence;
    private final DiscordRPC rpc;
    private final String id;
    private boolean connected;

    Discord()
    {
        presence = new DiscordRichPresence();
        rpc = DiscordRPC.INSTANCE;
        connected = false;
        id = "778887499362861067";
    }

    public void start()
    {
        if (connected) return;
        presence.startTimestamp = System.currentTimeMillis() / 1000L;

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        rpc.Discord_Initialize(id, handlers, true, "");
        rpc.Discord_UpdatePresence(presence);

        presence.details = "strong hack";
//        presence.state = state;
        presence.largeImageKey = "yum";
        rpc.Discord_UpdatePresence(presence);

        connected = true;
        new Thread(this::startThread).start();
    }

    public void stop()
    {
        if (!connected) return;
        connected = false;
        rpc.Discord_Shutdown();
    }

    private void startThread()
    {
        while (connected && !Thread.currentThread().isInterrupted())
        {
            try { Thread.sleep(3000); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
