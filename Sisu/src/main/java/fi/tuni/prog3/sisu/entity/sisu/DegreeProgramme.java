package fi.tuni.prog3.sisu.entity.sisu;

import com.google.gson.JsonObject;

import java.util.List;

public class DegreeProgramme {
    String id;
    String code;
    String lang;
    String groupId;
    String name;
    String nameMatch;
    String searchTagsMatch;
    List<String> curriculumPeriodIds;
    JsonObject credits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMatch() {
        return nameMatch;
    }

    public void setNameMatch(String nameMatch) {
        this.nameMatch = nameMatch;
    }

    public String getSearchTagsMatch() {
        return searchTagsMatch;
    }

    public void setSearchTagsMatch(String searchTagsMatch) {
        this.searchTagsMatch = searchTagsMatch;
    }

    public List<String> getCurriculumPeriodIds() {
        return curriculumPeriodIds;
    }

    public void setCurriculumPeriodIds(List<String> curriculumPeriodIds) {
        this.curriculumPeriodIds = curriculumPeriodIds;
    }

    public JsonObject getCredits() {
        return credits;
    }

    public void setCredits(JsonObject credits) {
        this.credits = credits;
    }
}
