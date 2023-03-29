package fi.tuni.prog3.sisu.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fi.tuni.prog3.sisu.entity.sisu.DegreeProgramme;
import fi.tuni.prog3.sisu.entity.sisu.SisuCourse;
import fi.tuni.prog3.sisu.entity.sisu.SisuModule;
import fi.tuni.prog3.sisu.entity.sisu.SisuResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Client for handling requests to Sisu API
 */
public class SisuClient {
    private final HttpClient client = HttpClientBuilder.create().build();
    private final Gson gson = new GsonBuilder().create();

    private final String baseUrl = "https://sis-tuni.funidata.fi/kori/api";
    private final String uniId = "tuni-university-root-id";

    private final String degreeUrl = "/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000";
    private String moduleSearchUrl(List<String> groups) {
        String groupIds = groups.stream().map((value) -> "groupId=" + value).collect(Collectors.joining("&"));
        return String.format("%s/modules/v1/by-group-id?%s&universityId=%s", baseUrl, groupIds, uniId);
    }
    private String courseSearchUrl(List<String> groups) {
        String groupIds = groups.stream().map((value) -> "groupId=" + value).collect(Collectors.joining("&"));
        return String.format("%s/course-units/v1/by-group-id?%s&universityId=%s", baseUrl, groupIds, uniId);
    }


    public SisuClient() {
    }

    private <T> SisuResponse<T> getRequest(URL url, TypeToken<? extends SisuResponse<?>> token) throws IOException, URISyntaxException {
        HttpGet getMethod = new HttpGet(url.toURI());
        HttpResponse response = client.execute(getMethod);
        String responseBody = EntityUtils.toString(response.getEntity());

        return gson.fromJson(responseBody, token.getType());

    }

    private List<SisuModule> getRequestModule(URL url) throws IOException, URISyntaxException {
        HttpGet getMethod = new HttpGet(url.toURI());
        HttpResponse response = client.execute(getMethod);
        String responseBody = EntityUtils.toString(response.getEntity());

        return gson.fromJson(responseBody, new TypeToken<List<SisuModule>>(){}.getType());

    }

    private List<SisuCourse> getRequestCourse(URL url) throws IOException, URISyntaxException {
        HttpGet getMethod = new HttpGet(url.toURI());
        HttpResponse response = client.execute(getMethod);
        String responseBody = EntityUtils.toString(response.getEntity());

        return gson.fromJson(responseBody, new TypeToken<List<SisuCourse>>(){}.getType());

    }

    /**
     * Get degree programmes
     * @return Fetch all degree programmes in Tampere University
     */
    public SisuResponse<DegreeProgramme> getDegreeProgrammes() throws IOException, URISyntaxException {
        URL url = new URL(baseUrl + degreeUrl);
        return getRequest(url, SisuResponse.DEGREE_RESPONSE_TYPE);
    }

    /**
     * Fetch modules which have any of the given groupIds
     * @param groupIds group IDs to search by
     * @return Modules found by given groupIds
     */
    public List<SisuModule> getModules(List<String> groupIds) throws IOException, URISyntaxException {
        URL url = new URL(moduleSearchUrl(groupIds));
        System.out.println(url);
        return getRequestModule(url);
    }

    /**
     * Fetch courses which have any of the given groupIds
     * @param courseIds group IDs to search by
     * @return Courses found by given groupIds
     */
    public List<SisuCourse> getCourses(List<String> courseIds) throws IOException, URISyntaxException {
        URL url = new URL(courseSearchUrl(courseIds));
        System.out.println(url);
        return getRequestCourse(url);
    }
}
