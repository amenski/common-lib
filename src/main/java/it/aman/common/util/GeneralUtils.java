package it.aman.common.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import it.aman.common.exception.ERPException;
import it.aman.common.exception.ERPExceptionEnums;

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

    // extract service-name and url
    public static String[] parseServiceNameAndUrl(final String url) throws ERPException {
        if(StringUtils.isBlank(url)) {
            throw ERPExceptionEnums.INVALID_FIELD_VALUE_EXCEPTION.get().setErrorMessage("Can't parse url.");
        }
        // ommit the first '/'
        String[] split = url.substring(1).split("/");
        StringBuilder builder = new StringBuilder("");
        for (int i=1; i < split.length; i++) {
            builder.append("/").append(split[i]);
        }
        return new String[] {split[0], builder.toString()};
    }
    
    public static String stripPrefix(final String url) {
        try {
            return parseServiceNameAndUrl(url)[1];
        } catch(Exception e) {}
        return "";
    }
    private GeneralUtils() {
        throw new IllegalStateException("Utility class.");
    }
}
