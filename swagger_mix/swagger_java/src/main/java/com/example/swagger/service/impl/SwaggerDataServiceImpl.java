package com.example.swagger.service.impl;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import com.example.swagger.utils.JsonRefRemover;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.entity.SwaggerData;
import com.example.swagger.mapper.SwaggerDataMapper;
import com.example.swagger.service.SwaggerDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
//import org.apache.commons.lang3.SerializationUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SwaggerDataServiceImpl extends ServiceImpl<SwaggerDataMapper, SwaggerData> implements SwaggerDataService {
    public static JSONObject root;
    private final SwaggerDataMapper swaggerDataMapper;

    public List<SwaggerData> analyze() {
        List<SwaggerData> swaggerDataList = new ArrayList<>();
        List<SwaggerData> tempList = new ArrayList<>();
        root = JSONObject.fromObject(getJson());
        analysisJson(root);
        String servers = null;
        if (root.has("servers")) {
            servers = root.getJSONArray("servers").getJSONObject(0).get("url").toString();
        }
        Iterator paths = null;
        if (root.has("paths")) {
            paths = root.getJSONObject("paths").keys();
        }
        if(paths!= null){
        while (paths.hasNext()) {
            SwaggerData swaggerData = new SwaggerData();
            //获取服务地址
            swaggerData.setServerUrl(servers);
            //获取API地址
            String apiUrl = paths.next().toString();
            swaggerData.setApiUrl(apiUrl);

            Iterator<String> keys = root.getJSONObject("paths").getJSONObject(apiUrl).keys();
            //通过url下的请求方式进行遍历
            while (keys.hasNext()){
                //获取请求方式
                String method = keys.next();
                swaggerData.setMethod(method);
                JSONObject tmp = root.getJSONObject("paths").getJSONObject(apiUrl).getJSONObject(method);
                //获取标签
                String tags = tmp.getJSONArray("tags").get(0).toString();
                swaggerData.setTag(tags);
                //获取描述
                String summary = tmp.get("summary").toString();
                swaggerData.setSummary(summary);
                //获取输入参数，解析get请求
                if (method.equals("get") && tmp.has("parameters")) {
                    // 需要将输入参数分割为参数和描述
                    String parametersTmp = tmp.get("parameters").toString();

                    // 将 JSON 字符串转换为 JSONArray 对象
                    JSONArray jsonArray = JSONArray.fromObject(parametersTmp);

                    // 提取第一个 JSON 对象的 name 字段，并生成新的 JSON 字符串 就是参数
                    JSONObject parameters = new JSONObject();

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        parameters.put(jsonObject.getString("name"), "");
                    }
                    String result1 = parameters.toString();
                    System.out.println(result1);
                    // 提取第二个 JSON 对象的 name 字段，并生成新的 JSON 字符串 就是描述

                    JSONArray parametersDec = new JSONArray();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject schema = jsonObject.getJSONObject("schema");
                        JSONObject json2 = new JSONObject();
                        json2.put("name", jsonObject.getString("name"));
                        json2.put("description", jsonObject.getString("description"));
                        json2.put("required", jsonObject.getBoolean("required"));
                        json2.put("type", schema.getString("type"));
                        parametersDec.add(json2);
                    }
                    swaggerData.setInputParam(JsonRefRemover.modifyInput(parameters.toString()));
                    swaggerData.setInputParamDec(JsonRefRemover.modifyInput(parametersDec.toString()));
                    if (tmp.getJSONObject("responses").toString().contains("*/*")) {
                        String responses_tmp = tmp.getJSONObject("responses").getJSONObject("200").getJSONObject("content").getJSONObject("*/*").getJSONObject("schema").toString();
                        String responses = StringEscapeUtils.unescapeJavaScript(responses_tmp);
                        swaggerData.setOutputParam(JsonRefRemover.modifyInput(responses));

                    } else if (tmp.getJSONObject("responses").toString().contains("application/json")) {
                        String responses = tmp.getJSONObject("responses").getJSONObject("200").getJSONObject("content").getJSONObject("application/json").getJSONObject("schema").getJSONObject("$ref").toString();
                        swaggerData.setOutputParam(JsonRefRemover.modifyInput(responses));
                    }



                }
                //解析postpost请求
                else if (method.equals("post") ) {
                    if (tmp.has("requestBody")){
                        String parameters = tmp.getJSONObject("requestBody").getJSONObject("content").getJSONObject("application/json").get("schema").toString();
                        swaggerData.setInputParam(JsonRefRemover.modifyInput(parameters));
                        if (tmp.getJSONObject("responses").toString().contains("*/*")) {
                            String responses_tmp = tmp.getJSONObject("responses").getJSONObject("200").getJSONObject("content").getJSONObject("*/*").getJSONObject("schema").toString();
                            String responses = StringEscapeUtils.unescapeJavaScript(responses_tmp);
                            swaggerData.setOutputParam(JsonRefRemover.modifyInput(responses));

                        } else if (tmp.getJSONObject("responses").toString().contains("application/json")) {
                            String responses = tmp.getJSONObject("responses").getJSONObject("200").getJSONObject("content").getJSONObject("application/json").getJSONObject("schema").getJSONObject("$ref").toString();
                            swaggerData.setOutputParam(JsonRefRemover.modifyInput(responses));
                        }}

                }
                tempList.add(swaggerData);
                // 获取templist的最后一条数据/最新add的数据
                SwaggerData lastData = tempList.get(tempList.size() - 1);

                // 进行深拷贝，并将拷贝后的数据添加到swaggerDataList中
                SwaggerData copiedData = deepCopy(lastData);
                swaggerDataList.add(copiedData);
            }
        }}
        //将数据存入数据库
//        this.saveBatch(swaggerDataList);
        //遍历ArrayList，临时插入id，解决前端下拉框同时下拉问题
        int counter = 1;
        // 遍历ArrayList中的每个元素
        for (SwaggerData item : swaggerDataList) {
            // 为每个元素的id字段分配递增的值
            item.setId((long) counter);
            counter++;
        }
        for (SwaggerData item : swaggerDataList) {
            System.out.println("ID: " + item.getId());
        }
        return swaggerDataList;
    }


    public static String getJson() {

        try {
            File file = new File("D:\\SpringBoot\\Swagger_remote\\swagger_java\\src\\main\\resources\\123.json");
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            int ch ;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void analysisJson(Object objJson) {

        //如果obj为json数组
        if (objJson instanceof JSONArray objArray) {
            for (Object o : objArray) {
                analysisJson(o);
            }
        }
        //如果为json对象
        else if (objJson instanceof JSONObject jsonObject) {

            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                Object object = jsonObject.get(key);
                //如果得到的是数组
                if (object instanceof JSONArray objArray) {
                    analysisJson(objArray);
                }
                //如果key中是一个json对象
                else if (object instanceof JSONObject) {
                    if (((JSONObject) object).has("$ref")) {
                        String[] refs = ((JSONObject) object).get("$ref").toString().split("/");
                        String ref = refs[refs.length - 1];

                        Object jsonObjectTmp = root.getJSONObject("components").getJSONObject("schemas").get(ref);
                        ((JSONObject) object).put("$ref", jsonObjectTmp);

                    }
                    analysisJson( object);

                }
                //如果key中是其他
            }

        }
    }// 通过调用SerializationUtils.clone()方法实现了SwaggerData对象的深拷贝
    public static SwaggerData deepCopy(SwaggerData original) {
        return SerializationUtils.clone(original);
    }
    public SwaggerDataServiceImpl(SwaggerDataMapper swaggerDataMapper) {
        this.swaggerDataMapper = swaggerDataMapper;
    }
    @Override
    public SwaggerData getInputOutputParamsById(Long id) {
        return swaggerDataMapper.findInputOutputParamsById(id);
    }
    @Override
    public SwaggerData saveData(SwaggerData swaggerData) {
        int rowsInserted = swaggerDataMapper.saveData(swaggerData);
        if (rowsInserted > 0) {
            // Insertion was successful
            return swaggerData;
        } else {
            // Handle insertion failure
            return null; // You can return an error response or throw an exception
        }
    }



}
