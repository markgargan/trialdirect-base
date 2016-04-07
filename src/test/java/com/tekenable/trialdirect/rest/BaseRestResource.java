package com.tekenable.trialdirect.rest;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author smoczyna
 */
public class BaseRestResource {
    public static final String REST_TEST_DESC = "HTTP response should be equal 20x";
    public final static String BASE_URL = "http://localhost:8080/api/";
    
    private WebTarget target;
    private final Client client;
    private int responseCode;

    public BaseRestResource() {
        this.client = ClientBuilder.newClient();
        this.client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
    }
    
    public int getResponseCode() {
        return this.responseCode;
    }

    /**
     * this methods reads all items present, regardless their membership
     * @param restPath
     * @return 
     */
    public String getAllItems(String restPath) {
        return this.makeRestGetCall(restPath);
    }
    
    /**
     * this method reads single items identified by given ID
     * @param restPath
     * @param id
     * @return 
     */
    public String getSingleItemById(String restPath, int id) {
        return this.makeRestGetCall(restPath.concat("/").concat(String.valueOf(id)));
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
        return this.makeRestCall("POST", restPath, Entity.json(payload));
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
        //return this.makeRestCall("PATCH", restPath+"/"+id, Entity.json(payload));
        return this.makeRestCall("PUT", restPath+"/"+id, Entity.json(payload));
    }
    
    public String createQuestionnaireEntry(int taId, int questionId) {
        String payload = "{\"question\": \"http://localhost:8080/api/questions/#QID#{?projection}\",\n" +
                         " \"therapeuticArea\": \"http://localhost:8080/api/therapeuticareas/#TAID#\"\n" +
                         "}";
        payload = payload.replace("#QID#", String.valueOf(questionId));
        payload = payload.replace("#TAID#", String.valueOf(taId));
        return this.makeRestCall("POST", "questionnaireentries", Entity.json(payload));
    }
    
    public void assingEntryAnswer(int entryId, int answerId) {
        //String payload = "{\"_links\" : {\"answer\" : \"#ANSWER#\"}}".replace("#ANSWER#", "http://localhost:8080/api/answers/"+answerId);
        //this.makeRestPatchCall("questionnaireentries/" + entryId + "/answers", payload);
        String payload = "http://localhost:8080/api/answers/"+answerId;
        this.makeRestPatchTextCall("questionnaireentries/" + entryId + "/answers", payload);
    }
    
    protected String makeRestCall(String method, String servicePath,  Entity payload) {
        String result = null;       
        target = client.target(BASE_URL).path("/".concat(servicePath));        
        Builder builder = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        Response response = builder.method(method, payload, Response.class);
        this.responseCode = response.getStatus();
        if (responseCode >= 200 && responseCode < 300)
            System.out.println("Request completed successfully");            
        else    
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());       
        
        MultivaluedMap<String, Object> meta = response.getMetadata();
        for (String entry : meta.keySet()) {
            System.out.println(entry + ":" + meta.get(entry));
        }
        if (response.hasEntity()) {
            result = response.readEntity(String.class);  
        }
        return result;
    }
    
    protected String makeRestGetCall(String servicePath) {
        String result = null;       
        target = client.target(BASE_URL).path("/".concat(servicePath));        
        Builder builder = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        Response response = builder.get();  
        this.responseCode = response.getStatus();
        if (this.responseCode==200)
            System.out.println("Request completed successfully");            
        else    
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());       
        
        MultivaluedMap<String, Object> meta = response.getMetadata();
        for (String entry : meta.keySet()) {
            System.out.println(entry + ":" + meta.get(entry));
        }
        if (response.hasEntity()) {
            result = response.readEntity(String.class);  
        }
        return result;
    }

    /**
     * this method works only in Java8 due to REST client bug in Java7
     * @param servicePath
     * @param payload
     * @return 
     */
    protected String makeRestPatchTextCall(String servicePath, String payload) {
        String result = null;
        target = client.target(BASE_URL).path("/".concat(servicePath));        
        Builder builder = target.request("text/uri-list");
        Response response = builder.method("PATCH", Entity.text(payload), Response.class);  
        this.responseCode = response.getStatus();
        if (responseCode >= 200 && responseCode < 300)
            System.out.println("Request completed successfully");
        else    
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());       
        
        MultivaluedMap<String, Object> meta = response.getMetadata();
        for (String entry : meta.keySet()) {
            System.out.println(entry + ":" + meta.get(entry));
        }
        if (response.hasEntity()) {
            result = response.readEntity(String.class);  
        }
        return result;
    }
    
}
