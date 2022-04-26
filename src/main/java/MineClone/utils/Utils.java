package MineClone.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static String loadResource(String fileName) throws Exception {
        String result;
        try(InputStream in = Utils.class.getResourceAsStream(fileName)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name());
            result = scanner.useDelimiter("\\A").next();
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return result;
    }

    public static final Random rnd = new Random();
}
