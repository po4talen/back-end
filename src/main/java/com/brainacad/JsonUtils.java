package com.brainacad;

import com.jayway.jsonpath.JsonPath;

public class JsonUtils {
    //TODO: Используя библиотеку com.jayway.jsonpath (Maven) напишите метод извлечения строки из JSON по JSON Path:

  public static String stringFromJSONByPath(String json, String jsonPath){
  String data = JsonPath.read(json, jsonPath);
  return (data);
    }


    //TODO: Используя библиотеку com.jayway.jsonpath (Maven) напишите метод извлечения int из JSON по JSON Path:

    public static int intFromJSONByPath(String json, String jsonPath){
    int data = JsonPath.read(json, jsonPath);
    return (data);
    }


    //TODO: Используя библиотеку com.jayway.jsonpath (Maven) напишите метод извлечения double из JSON по JSON Path:

    public static double doubleFromJSONByPath(String json, String jsonPath){
      double data = JsonPath.read(json, jsonPath);
      return (data);
    }


    //TODO: Используя библиотеку com.jayway.jsonpath (Maven) напишите метод извлечения списка (List) из JSON по JSON Path:

//    public static List listFromJSONByPath(String json, String jsonPath){
//      List<String> data = new ArrayList<String>();
//      List<String> data2 = JsonPath.read(json, jsonPath);
//         return (data);
//    }


}
