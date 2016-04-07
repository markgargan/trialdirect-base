package com.tekenable.trialdirect.rest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by smoczyna on 05/04/16.
 */
public class RestTestResourceTemplate {

    public static final String REST_TEST_DESC = "HTTP response should be equal 20x";
    public final static String BASE_URL = "http://localhost:8080/api/";

    private HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    /**
     * this methods reads all items present, regardless their membership
     * @param restPath
     * @return
     */
    public String getAllItems(String restPath) {
        return this.makeRestCall("GET", BASE_URL.concat(restPath), null);
    }

    /**
     * this method reads single items identified by given ID
     * @param restPath
     * @param id
     * @return
     */
    public String getSingleItemById(String restPath, int id) {
        return this.makeRestCall("GET", BASE_URL.concat(restPath).concat("/").concat(String.valueOf(id)), null);
    }

    /**
     * this method suites for artifacts having any text field
     * like Question, Answer, TherapeuticArea or Trial
     * it won't work for all other artifacts containing just ids and references
     * like QuestionnaireEntry or all selectors
     * @param restPath
     * @param itemName
     * @param itemText
     * @return
     */
    public String createTextItem(String restPath, String itemName, String itemText) {
        String payload = "{\"#NAME#\" : \"#VAL#\"}".replace("#NAME#", itemName).replace("#VAL#", itemText);
        return this.makeRestCall("POST", BASE_URL.concat(restPath), payload);
    }

    /**
     * same as above method, applicable only to text artifacts
     * @param restPath
     * @param id
     * @param itemName
     * @param itemText
     * @return
     */
    public String updateItemText(String restPath, int id, String itemName, String itemText) {
        String payload = "{\"#NAME#\" : \"#VAL#\"}".replace("#NAME#", itemName).replace("#VAL#", itemText);
        return this.makeRestCall("PUT", BASE_URL.concat(restPath)+"/"+id, payload);
    }

    public String createQuestionnaireEntry(int taId, int questionId) {
        String payload = "{\"question\": \"http://localhost:8080/api/questions/#QID#{?projection}\",\n" +
                " \"therapeuticArea\": \"http://localhost:8080/api/therapeuticareas/#TAID#\"\n" +
                "}";
        payload = payload.replace("#QID#", String.valueOf(questionId));
        payload = payload.replace("#TAID#", String.valueOf(taId));
        return this.makeRestCall("POST", BASE_URL.concat("questionnaireentries"), payload);
    }

    /*public void assingEntryAnswer(int entryId, int answerId) {
        String payload = "{\"_links\" : {\"answer\" : \"#ANSWER#\"}}".replace("#ANSWER#", "http://localhost:8080/api/answers/"+answerId);
        this.makeRestCall("PUT", BASE_URL.concat("questionnaireentries/") + entryId + "/answers", payload);
    }*/

    /**
     * partial updates do not work due to the bugs in Java7 virtual machine
     * and/or lack of support for PATCH rest method on the client side
     * Full support for this type of action is present in Java8.
     * This method is just a workaround saving all answers at once
     * but it is added here as a workaround for testing purposes
     */
    public void assingAnswersToEntry(int entryId, int[] answerIds) {
        String answer = "\"answer\" : \"http://localhost:8080/api/answers/#ANS#\"";
        String allAnswers = null;
        for (Integer i : answerIds) {
            if (allAnswers==null)
                allAnswers = answer.replace("#ANS#", String.valueOf(i));
            else
                allAnswers = allAnswers + "," + answer.replace("#ANS#", String.valueOf(i));
        }
        String payload = "{\"_links\" : {#ANSWERS#}}".replace("#ANSWERS#", allAnswers);
        this.makeRestCall("PUT", BASE_URL.concat("questionnaireentries/") + entryId + "/answers", payload);
    }

    private String makeRestCall(String method, String restPath, String jsonPayload) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity entity;
        if ("GET".equalsIgnoreCase(method))
            entity = new HttpEntity(headers);
        else
            entity = new HttpEntity(jsonPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(restPath, HttpMethod.valueOf(method), entity, String.class);
        this.status = response.getStatusCode();
        return response.toString();
    }

    /*protected String makeRestPatchCall(String servicePath, String jsonPayload) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList();
        mediaTypes.add(org.springframework.http.MediaType.APPLICATION_JSON);
        mediaTypes.add(org.springframework.http.MediaType.valueOf("application/hal+json"));
        mediaTypes.add(org.springframework.http.MediaType.valueOf("text/uri-list"));
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setObjectMapper(mapper);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(jsonPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL.concat(servicePath), HttpMethod.PATCH, entity, String.class);
        this.status = response.getStatusCode();
        return response.toString();
    }*/
}
