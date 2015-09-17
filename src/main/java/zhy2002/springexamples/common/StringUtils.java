package zhy2002.springexamples.common;

/**
 * Created by JOHNZ on 17/09/2015.
 */
public class StringUtils {

    public static String repeat(String str, int times, String separator) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            if (i != 0)
                stringBuilder.append(separator);
            stringBuilder.append(str);
        }
        return stringBuilder.toString();

    }
}
