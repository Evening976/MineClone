package MineClone.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Utils {
    public static String loadResource(String fileName) throws Exception {
        String result;
        try(InputStream in = Utils.class.getResourceAsStream(fileName)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name());
            result = scanner.useDelimiter("\\A").next();
        }
        catch (Exception e) {
            throw new Exception("Could not load resource: " + fileName);
        }
        return result;
    }
}
