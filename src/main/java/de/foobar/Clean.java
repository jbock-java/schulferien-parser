package de.foobar;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Clean {

    private static final Pattern DATA_TIP_COMPLETE = Pattern.compile("(.*)(data-tip-text=\".*\")(.*)");
    private static final Pattern DATA_TIP_LINE1 = Pattern.compile("(.*)(data-tip-text=\".*$)");
    private static final Pattern DATA_TIP_LINE2 = Pattern.compile("^([a-zA-Z].*)\"(.*)");

    static String readClean(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String x1 = removeDataTipText(line);
            String x2 = removeDataTipText2(x1);
            String x3 = removeBad(x2);
            String y = x3.replace("&nbsp;", "");
            sb.append(y);
            sb.append("\n");
        }
        return sb.toString();
    }


    private static String removeBad(String line) {
        Matcher matcher = DATA_TIP_LINE2.matcher(line);
        if (!matcher.matches()) {
            return line;
        }
        return matcher.group(2);
    }

    private static String removeDataTipText(String line) {
        Matcher matcher = DATA_TIP_COMPLETE.matcher(line);
        if (!matcher.matches()) {
            return line;
        }
        return matcher.group(1) + matcher.group(3);
    }

    private static String removeDataTipText2(String line) {
        Matcher matcher = DATA_TIP_LINE1.matcher(line);
        if (!matcher.matches()) {
            return line;
        }
        return matcher.group(1);
    }
}
