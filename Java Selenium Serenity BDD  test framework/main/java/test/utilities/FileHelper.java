package com..test.utilities;

import com..test.context.ScenarioContext;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileHelper {

    private static final String PATH = System.getProperty("user.dir") + "/src/main/resources/";

    public FileHelper() {}

    public static String getRequestBody(String fileName)  {
        try {
            return FileUtils.readFileToString(new File(PATH + "data/API/rest/"+fileName), Charset.defaultCharset());
        }
        catch (IOException e) {
           return null;
        }
    }

    public File getRecentFileFromDownloads()
    {
        java.nio.file.Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
        File folder = new File(downloadsDir.toString());
        File[] files = folder.listFiles();
        assert files != null;
        Arrays.sort(files, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));
        return files[0];
    }

    public static void findAndReplaceFileContent(String filename) throws IOException {
        Path path = Paths.get(filename);
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(path), charset);
        content = content.replaceAll("<organisation>", ScenarioContext.getTSNTance().getOrganisation());
        content = content.replaceAll("<country>", ScenarioContext.getTSNTance().getCountry());
        Files.write(path, content.getBytes(charset));
    }
}
