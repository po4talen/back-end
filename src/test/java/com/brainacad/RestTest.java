package com.brainacad;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.brainacad.JsonUtils.intFromJSONByPath;
import static com.brainacad.JsonUtils.stringFromJSONByPath;


public class RestTest{

    private static final String URL="https://reqres.in";

    @Test//GET метод
    public void checkGetResponseStatusCode() throws IOException {
        String endpoint="/api/users";

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод
    public void checkGetResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    @Test//POST метод
    public void checkPostResponseStatusCode() throws IOException {
        String endpoint="/api/users";

        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 201", 201, statusCode);
    }

    @Test//POST метод
    public void checkPostResponseBodyNotNull() throws IOException {
        String endpoint="/api/users";

        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL+endpoint,requestBody);

        //Конвертируем входящий поток тела ответа в строку
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    //TODO: напишите по тесткейсу на каждый вариант запроса на сайте https://reqres.in
    //TODO: в тескейсах проверьте Result Code и несколько параметров из JSON ответа (если он есть)

    @Test//GET mehtod jsonDAta from LIST USERS
     public void checkString () throws IOException {
        String endpoint="/api/users";
        HttpResponse response = HttpClientHelper.get(URL+endpoint,"page=2");
        String body=HttpClientHelper.getBodyFromResponse(response);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String data=stringFromJSONByPath(body,"$.data[1].first_name" );
        System.out.println("First name = "+data);
        Assert.assertEquals("First name should be Charles","Charles",data);
        int id = intFromJSONByPath(body,"$.data[1].id");
        System.out.println("id = "+id);
        Assert.assertEquals("ID should be 5",5,id);
    }

    @Test//PUT method
    public void checkPutResponseData()throws IOException{
        String endpoint="/api/users/2";

        //создаём тело запроса
        String requestBody="{\"name\": \"morpheus\",\"job\": \"zion resident\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.put(URL+endpoint,requestBody);
        //получаем статус код из ответа
       int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String body=HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
    }
    @Test //PATCH method
  public void checkPatchResponseData() throws IOException{
      String endpoint="/api/users/2";

      //создаём тело запроса
      String requestBody="{\"name\": \"morpheus\",\"job\": \"zion resident\"}";

      //Выполняем REST POST запрос с нашими параметрами
      // и сохраняем результат в переменную response.
      HttpResponse response = HttpClientHelper.patch(URL+endpoint,requestBody);
      int statusCode = response.getStatusLine().getStatusCode();
      System.out.println("Response Code : " + statusCode);
      Assert.assertEquals("Response status code should be 200", 200, statusCode);
      String body=HttpClientHelper.getBodyFromResponse(response);
      System.out.println(body);
      String data=stringFromJSONByPath(body,"$.updatedAt");
        System.out.println(data);
    }
}
