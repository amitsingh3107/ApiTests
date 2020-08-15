package org.amit.tests.testcases;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.amit.Dtos.ResponseDto;
import org.amit.base.Setups;
import org.amit.utils.Constants;
import org.amit.utils.FileUtils;
import org.amit.utils.ReadJson;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeachUsersTest {

    public Setups setups;
    @BeforeTest
    private void setup(){
        setups = new Setups();
    }
   @Test(dataProvider = "seachAllUser")
   public void seachAllUserTest(JsonObject data){
       Map<String,Object> headears = new HashMap<>();
       headears.put("Content-Type","application/json");
       Map<String,Object> body = new HashMap<>();
       String userName=data.get("userName")!= JsonNull.INSTANCE ? data.get("userName").getAsString():null;
       String password = data.get("password")!=JsonNull.INSTANCE ? data.get("password").getAsString():null;
       Response response=setups.getRequest(Constants.GET_ALL_USERS,headears,null,userName,password);
       if(response.statusCode()==200) {
           Assert.assertEquals(response.statusCode(), data.get("expectedStatusCode").getAsInt());
           List<ResponseDto> list = response.jsonPath().getList("", ResponseDto.class);
           Assert.assertEquals(list.size(), data.get("outPutSize").getAsInt());
       }
       else{
           Assert.assertEquals(response.statusCode(), data.get("expectedStatusCode").getAsInt());
       }

   }

    @Test(dataProvider = "seachUser")
    private void seachSpecificUserTest(JsonObject data){
      Map<String,Object> headears = new HashMap<>();
      //headears.put("Content-Type","application/json");
      Map<String,Object> body = new HashMap<>();
      Map<String,Object> pathParam = new HashMap<>();
      pathParam.put("phone",data.get("seachNo").getAsString());
      String userName=data.get("userName")!= JsonNull.INSTANCE ? data.get("userName").getAsString():null;
      String password = data.get("password")!=JsonNull.INSTANCE ? data.get("password").getAsString():null;
      Response response=setups.getRequest(Constants.GET_SPECIFIC_USER,headears,pathParam,userName,password);
      if(response.statusCode()==200) {
          Assert.assertEquals(response.statusCode(), data.get("expectedStatusCode").getAsInt());
          if(!response.getBody().asString().equals("")) {
              ResponseDto list = (ResponseDto) response.jsonPath().getObject("", ResponseDto.class);
              Assert.assertEquals(list.getPhone(),data.get("seachNo").getAsString());
          }
          else{
              Assert.assertEquals(response.getBody().asString(),"");
          }
          //.assertEquals(list.size(), data.get("outPutSize").getAsInt());

      }
      else{
          Assert.assertEquals(response.statusCode(),data.get("expectedStatusCode").getAsInt());
      }
    }

    @DataProvider(name="seachAllUser")
    private Object[][] getAllUsers(){
        return ReadJson.getData("searchAllUsers",FileUtils.getJsonFilePath()+File.separator+"seachAllUsers.json");
    }
    @DataProvider(name="seachUser")
    private Object[][] getSpecificUserData(){
        return ReadJson.getData("searchUserWithMobile",FileUtils.getJsonFilePath()+File.separator+"seachUserWithMobileNo.json");
    }
}
