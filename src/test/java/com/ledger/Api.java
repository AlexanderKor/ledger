package com.ledger;

import org.testng.annotations.Test;

import java.util.Properties;

import static com.ledger.api.ApiRequest.doGetAndSetResponse;
import static com.ledger.api.ApiRequest.getApiResponse;
import static com.ledger.base.PropertyHandler.openPropertyFile;

public class Api {
    @Test
    public void busforTest3(){
        //https://busfor.com/buses/Kiev/Odesa?from_id=3863&to_id=4252&on=2020-08-21&passengers=3&details[][age]=2&details[][seats]=1&search=true
        //https://busfor.com/api/v1/searches/rMffBc2VZHgewpcgb8eoFocU?from_id=3863&to_id=4252&on=2020-08-21&passengers=3&details[][age]=2&details[][seats]=1&search=true&domainId=18
        int city1 = 3863; //kiev
        int city2 = 4252; //odessa
        int numberOfPassengers = 3;
        String date = "2020-08-21";
        String domainName = openPropertyFile(new Properties(), "config.properties").getProperty("busforUrl");
        //doGetAndSetResponse(domainName+ "/api/v1/searches/rMffBc2VZHgewpcgb8eoFocU?from_id=" + city1 + "&to_id=" + city2 + "&on=" + date + "&passengers=" + numberOfPassengers + "&details[][age]=2&details[][seats]=1&search=true&domainId=18");
        //doGetAndSetResponse("https://busfor.com/api/v1/searches/rMffBc2VZHgewpcgb8eoFocU?from_id=3863&to_id=4252&on=2020-08-21&passengers=3&details[][age]=2&details[][seats]=1&search=true&domainId=18");
        doGetAndSetResponse("https://busfor.com/api/v1/searches");
        getApiResponse().body().prettyPrint();

        //https://busfor.com/api/v1/preorders/9224253/seatmaps
        //https://busfor.com/api/v1/sms_auth/send_code
        System.out.println("qwe");
    }
}
