package fi.tuni.prog3.sisu.entity.sisu;

import com.google.gson.JsonObject;

public class SisuModule {
    LocalizedString name;
    String type;
    String code;
    JsonObject rule;

    public LocalizedString getName() {
        return name;
    }

    public void setName(LocalizedString name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JsonObject getRule() {
        return rule;
    }

    public void setRule(JsonObject rule) {
        this.rule = rule;
    }



    @Override
    public String toString() {
        return this.getName().getFi();
    }
}
