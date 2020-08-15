package org.amit.base;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import org.amit.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class Setups {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public Setups(){
      //  RestAssured.baseURI=System.getenv("baseUrl");
        RestAssured.baseURI="http://13.126.80.194:8080";

    }

    public String getSessionId(String body, String url, String contentType){

        Response response = given().header("Content-Type",contentType).body(body)
                            .when().log().all().post(url)
                            .then().extract().response();

        if(response.statusCode()==200){
            return response.jsonPath().getString("token");
        }
        else{
            logger.info("Unbale to Aunthenticate status code is {}",response.statusCode());
            return "Unauthonticated"+response.statusCode();

        }


    }
    public Response getRequest(String url, Map<String, Object> headers, Map<String,Object>pathParams,String userName, String password){
        Map<String,String> body = new HashMap<>();
        body.put("username",userName);
        body.put("password",password);
        String token = getSessionId(new Gson().toJson(body), Constants.AUTHENTICATE,"application/json");
        Response response;
        if(Objects.isNull(pathParams)){
            response=given().auth().oauth2(token).headers(headers)
                    .when().log().all().get(url)
                    .then().extract().response();

        }
        else {
            response = given().auth().oauth2(token).headers(headers).pathParams(pathParams)
                    .when().log().all().get(url)
                    .then().extract().response();
        }
        return response;
    }
}
