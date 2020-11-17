package cat.yoink.xanax.main.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil
{
    public static void saveFile(File file, List<String> content) throws IOException
    {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        for (String s : content)
        {
            out.write(s);
            out.write("\r\n");
        }
        out.close();
    }

    public static List<String> loadFile(File file) throws IOException
    {
        List<String> content = new ArrayList<>();

        FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        DataInputStream in = new DataInputStream(stream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;

        while ((line = br.readLine()) != null) content.add(line);

        br.close();

        return content;
    }
}
