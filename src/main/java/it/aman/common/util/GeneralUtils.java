package it.aman.common.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Neither extending(not to be overriden) nor instantiating this class is
 * allowed
 * 
 * @author Amanuel
 *
 */
public final class GeneralUtils {

    private static final String ALLOWED_CHARS = "abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPRSTUVWXYZ0123456789";

    public static String getLockKey() {
        return getRandomToken(5);
    }
    
    public static String getRandomToken(final int width) {
        char[] result = new char[width];
        char[] charList = ALLOWED_CHARS.toCharArray();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < result.length; i++) {
            result[i] = charList[random.nextInt(charList.length)];

        }
        return new String(result);
    }

    private GeneralUtils() {
        throw new IllegalStateException("Utility class.");
    }
}
