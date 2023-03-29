package fi.tuni.prog3.sisu.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * SisuModule related utility functions
 */
public class ModuleUtil {

    /**
     * Recursive method to parse through SisuModule rules and get the related group and course IDs
     * @param object Rule JsonObject (SisuModule.getRule)
     * @return Module and course IDs as separate lists
     */
    public static LinkedHashMap<String, List<String>> getChildModulesFromRules(JsonObject object) {
        List<String> moduleIds = new ArrayList<>();
        List<String> courseIds = new ArrayList<>();

        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        map.put("modules", moduleIds);
        map.put("courses", courseIds);

        if(object == null) {
            return new LinkedHashMap<>();
        }

        String type = object.get("type").getAsString();

        switch (type) {
            case "CompositeRule":
                if(object.has("rules")) {
                    JsonArray arr = object.getAsJsonArray("rules");
                    for(JsonElement elem : arr) {
                        var map2 = getChildModulesFromRules(elem.getAsJsonObject());
                        combineMaps(map, map2);
                    }
                } else {
                    System.out.println("No rules");
                }
                break;
            case "ModuleRule":
                moduleIds.add(object.get("moduleGroupId").getAsString());
                break;
            case "CreditsRule":
                var map2 = getChildModulesFromRules(object.get("rule").getAsJsonObject());
                combineMaps(map, map2);
                break;
            case "CourseUnitRule":
                courseIds.add(object.get("courseUnitGroupId").getAsString());
                break;
        }

        return map;
    }

    private static void combineMaps(LinkedHashMap<String, List<String>> a, LinkedHashMap<String, List<String>> other) {
        a.get("modules").addAll(other.get("modules"));
        a.get("courses").addAll(other.get("courses"));
    }
}
