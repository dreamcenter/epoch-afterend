package top.dreamcenter.epoch.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonDataRegex {
    /**
     * basic way to get Json Data by regex, not useful when too many cycle
     * @param jsonData
     * @param target
     * @return
     */
    public static String getResult(String jsonData,String target){
        Pattern pattern1 = Pattern.compile(target + "\":\"(.*?)\"");
        Pattern pattern2 = Pattern.compile(target + "\":\\{(.*?)\\}");
        Pattern pattern3 = Pattern.compile(target + "\":\\[(.*?)\\]");

        Matcher matcher = pattern1.matcher(jsonData);
        if (matcher.find()) return matcher.group(1);
        matcher = pattern2.matcher(jsonData);
        if (matcher.find()) return "{" + matcher.group(1) + "}";
        matcher = pattern3.matcher(jsonData);
        if (matcher.find()) return "[" + matcher.group(1) + "]";
        return "";
    }
}
