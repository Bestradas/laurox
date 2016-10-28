package com.laurox.lauroxonline.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {
 
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-lauroxApp-alert", message);
        headers.add("X-lauroxApp-params", param);
        return headers;
    }
    
    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("lauroxApp." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("lauroxApp." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("lauroxApp." + entityName + ".deleted", param);
    }
    
    public static HttpHeaders createEntityApproveAlert(String entityName, String param) {
        return createAlert("lauroxApp.customerorder.approve", param);
    }
    
    public static HttpHeaders createEntityRejectAlert(String entityName, String param) {
        return createAlert("lauroxApp.customerorder.rejected", param);
    }

}