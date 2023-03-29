package fi.tuni.prog3.sisu.entity.sisu;

import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SisuResponse<T> {

    public static TypeToken<SisuResponse<DegreeProgramme>> DEGREE_RESPONSE_TYPE = new TypeToken<>(){};

    int start;
    int limit;
    int total;
    List<T> searchResults;
    boolean truncated;
    Object notifications;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<T> searchResults) {
        this.searchResults = searchResults;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public Object getNotifications() {
        return notifications;
    }

    public void setNotifications(Object notifications) {
        this.notifications = notifications;
    }
}
