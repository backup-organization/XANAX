package cat.yoink.xanax.core;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public enum Loader
{
    INSTANCE;

    public void load(boolean isBeta)
    {
        try
        {
            Field field = LaunchClassLoader.class.getDeclaredField("resourceCache");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, byte[]> cache = (Map<String, byte[]>) field.get(Launch.classLoader);

            URL url = new URL(isBeta ? "https://yoink.site/XANAX/client-beta.jar" : "https://yoink.site/XANAX/client.jar");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream inputStream = httpURLConnection.getInputStream();

            ZipInputStream zipInputStream = new ZipInputStream(inputStream);

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null)
            {
                String name = zipEntry.getName();

                if (!name.endsWith(".class")) continue;

                name = name.substring(0, name.length() - 6);
                name = name.replace('/', '.');

                ByteArrayOutputStream streamBuilder = new ByteArrayOutputStream();
                int bytesRead;
                byte[] tempBuffer = new byte[16384];
                while ((bytesRead = zipInputStream.read(tempBuffer)) != -1)
                    streamBuilder.write(tempBuffer, 0, bytesRead);

                cache.put(name, streamBuilder.toByteArray());
            }
        }
        catch (Exception ignored) { }
    }

    public void update(String version)
    {
        if (shouldUpdate(Integer.parseInt(version)))
        {
            JOptionPane.showMessageDialog(new Frame("XANAX"), "Loader is not up to date", "XANAX", JOptionPane.ERROR_MESSAGE);

            FMLCommonHandler.instance().exitJava(0, false);
        }
    }

    public void loadMixin()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.xanax.json");
    }

    public boolean shouldUpdate(int version)
    {
        return version < getVersion();
    }

    public int getVersion()
    {
        try
        {
            URL url = new URL("https://yoink.site/XANAX/version.php");
            Scanner scanner = new Scanner(url.openStream(), "UTF-8");

            return Integer.parseInt(scanner.useDelimiter("\\A").next().replace("\n", ""));
        }
        catch (IOException e)
        {
            return 999;
        }
    }
}
