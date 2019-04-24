package com.brainacad;

import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.apache.http.HttpResponse;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.brainacad.JsonUtils.*;


public class RestTest {

    private static final String URL = "https://reqres.in";

    @Test//GET метод
    public void checkGetResponseStatusCode() throws IOException {
        String endpoint = "/api/users";

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
    }

    @Test//GET метод
    public void checkGetResponseBodyNotNull() throws IOException {
        String endpoint = "/api/users";

        //Выполняем REST GET запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    @Test//POST метод
    public void checkPostResponseStatusCode() throws IOException {
        String endpoint = "/api/users";

        //создаём тело запроса
        String requestBody = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL + endpoint, requestBody);

        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 201", 201, statusCode);
    }

    @Test//POST метод
    public void checkPostResponseBodyNotNull() throws IOException {
        String endpoint = "/api/users";

        //создаём тело запроса
        String requestBody = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.post(URL + endpoint, requestBody);

        //Конвертируем входящий поток тела ответа в строку
        String body = HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
        Assert.assertNotEquals("Body shouldn't be null", null, body);
    }

    //TODO: напишите по тесткейсу на каждый вариант запроса на сайте https://reqres.in
    //TODO: в тескейсах проверьте Result Code и несколько параметров из JSON ответа (если он есть)

    @Test//GET mehtod jsonDAta from LIST USERS
    public void checkString() throws IOException {
        String endpoint = "/api/users";
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");
        String body = HttpClientHelper.getBodyFromResponse(response);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String data = stringFromJSONByPath(body, "$.data[1].first_name");
        System.out.println("First name = " + data);
        Assert.assertEquals("First name should be Charles", "Charles", data);
        int id = intFromJSONByPath(body, "$.data[1].id");
        System.out.println("id = " + id);
        Assert.assertEquals("ID should be 5", 5, id);
    }

    @Test//PUT method
    public void checkPutResponseData() throws IOException {
        String endpoint = "/api/users/2";

        //создаём тело запроса
        String requestBody = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.put(URL + endpoint, requestBody);
        //получаем статус код из ответа
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String body = HttpClientHelper.getBodyFromResponse(response);
        System.out.println(body);
    }

    @Test //PATCH method
    public void checkPatchResponseData() throws IOException {
        String endpoint = "/api/users/2";

        //создаём тело запроса
        String requestBody = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";

        //Выполняем REST POST запрос с нашими параметрами
        // и сохраняем результат в переменную response.
        HttpResponse response = HttpClientHelper.patch(URL + endpoint, requestBody);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);
        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String body = HttpClientHelper.getBodyFromResponse(response);
        // System.out.println(body);
        String data = stringFromJSONByPath(body, "$.updatedAt");
        // DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");--можно так
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZoneUTC();
        DateTime dt = formatter.parseDateTime(data);
        System.out.println(dt);
        // Assert.assertTrue("Error messsege",dt.plusHours(3).plusMinutes(-1).isBeforeNow()); --тогда так
        Assert.assertTrue("Error messsege", dt.plusMinutes(-10).isBeforeNow());
    }

    @Test// Get method (List data)
    public void checkListDAta() throws IOException {
        String endpoint = "/api/users";
        HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");
        String body = HttpClientHelper.getBodyFromResponse(response);
        List actuallist = listFromJSONByPath(body, "$.data[*].first_name");
        List expectedlist = Arrays.asList("Eve", "Charles", "Tracey");
        Assert.assertEquals("Error msg", expectedlist, actuallist);
    }

    @Test //DEL method
    public void Delete() throws IOException {
        String endpoint = "/api/users/2";
        HttpResponse response = HttpClientHelper.delete(URL + endpoint);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals("Response status code should be 204", 204, statusCode);
    }

    @Test // Check Jsonschema for Get Users request
    public void checkJsonSchema() throws Exception {
//         String endpoint = "/api/users";
//         HttpResponse response = HttpClientHelper.get(URL + endpoint, "page=2");
//         String body = HttpClientHelper.getBodyFromResponse(response);
//        int statusCode = response.getStatusLine().getStatusCode();
//        System.out.println("Response Code : " + statusCode);
//        Assert.assertEquals("Response status code should be 200", 200, statusCode);
        String mappedbody = new String(Files.readAllBytes(Paths.get("schemaJson1/bodyMapped.json")));
        ProcessingReport result = MyJsonValidator.validateJson(mappedbody, "schemaJson1/schema.json");
//         ProcessingReport result = MyJsonValidator.validateJson(body, "schemaJson1/schema.json");
        Assert.assertTrue(result.toString(), result.isSuccess());

    }
}

