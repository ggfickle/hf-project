package com.hf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hf.mybatis.utils.JacksonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/12 14:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class JacksonTest {

    @Test
    public void testList() throws JsonProcessingException {
        String jsonStr = "{\n" +
                "    \"status\":0,\n" +
                "    \"message\":\"\",\n" +
                "    \"data\":{\n" +
                "        \"search_data\":[\n" +
                "            {\n" +
                "                \"rating\":0,\n" +
                "                \"name\":\"奈良市\",\n" +
                "                \"url\":\"/scenic/3/10052/\",\n" +
                "                \"wish_to_go_count\":328,\n" +
                "                \"name_orig\":\"奈良市\",\n" +
                "                \"visited_count\":1958,\n" +
                "                \"comments_count\":0,\n" +
                "                \"location\":{\n" +
                "                    \"lat\":34.685087,\n" +
                "                    \"lng\":135.805\n" +
                "                },\n" +
                "                \"has_experience\":false,\n" +
                "                \"rating_users\":0,\n" +
                "                \"name_zh\":\"奈良市\",\n" +
                "                \"name_en\":\"Nara\",\n" +
                "                \"type\":3,\n" +
                "                \"id\":10052,\n" +
                "                \"has_route_maps\":false,\n" +
                "                \"icon\":\"http://media.breadtrip.com/images/icons/2/city.png\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        ObjectMapper objectMapper = JacksonUtils.getObjectMapperInstance();
        JsonNode rootTree = objectMapper.readTree(jsonStr);
        System.out.println(rootTree.get("status").asInt());
        JsonNode dataNode = rootTree.get("data");
        JsonNode search_data_List = dataNode.get("search_data");
        if (search_data_List.isArray()) {
            for (JsonNode jsonNode : search_data_List) {
                System.out.println("jsonNode.get(\"rating\").asInt() = " + jsonNode.get("rating").asInt());
                System.out.println("jsonNode.get(\"name\").asText() = " + jsonNode.get("name").asText());
                System.out.println("jsonNode.get(\"url\").asText() = " + jsonNode.get("url").asText());
                JsonNode location = jsonNode.get("location");
                System.out.println("location.get(\"lat\").asText() = " + location.get("lat").asText());
                System.out.println("location.get(\"lng\").asText() = " + location.get("lng").asText());
            }
        }

        Map<String, Object> stringObjectMap = objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(stringObjectMap);
    }
}
