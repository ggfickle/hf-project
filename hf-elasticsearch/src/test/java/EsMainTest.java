import com.hf.common.utils.JacksonUtils;
import com.hf.es.ElasticSearchMain;
import com.hf.es.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/25 19:47
 */
@SpringBootTest(classes = ElasticSearchMain.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class EsMainTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        log.info("Test Before");
    }

    @Test
    public void save() {
        Book book = new Book()
                .setId(1)
                .setTitle("西西弗书店")
                .setAuthor("未知")
                .setPrice(12.9)
                .setCreateTime(new Date())
                .setUpdateTime(new Date());

        try {
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .put("/book")
                    .content(JacksonUtils.writeValueAsString(book))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON));
            resultActions.andReturn().getResponse().setCharacterEncoding("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findById() throws UnsupportedEncodingException {
        ResultActions resultActions = null;
        try {
            resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .get("/book/1")
                    .param("id", "1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert resultActions != null;
        System.out.println("resultActions.andReturn().getResponse().getContentAsString() = " + resultActions.andReturn().getResponse().getContentAsString());

    }
}
