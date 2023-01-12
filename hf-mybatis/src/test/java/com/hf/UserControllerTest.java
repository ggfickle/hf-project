package com.hf;

import com.hf.mybatis.DO.User;
import com.hf.mybatis.controller.UserController;
import com.hf.mybatis.service.UserService;
import com.hf.mybatis.utils.JacksonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/12 14:20
 */
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureRestDocs
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void testGetUser() throws Exception {
        String userJsonString = "{\"id\":1,\"name\":\"xiehongfei\",\"username\":\"xiehongfei\"," +
                "\"password\":\"$2a$10$W/bwQGTP7tiDRsuCZDBSTuuEV2RaNz6RuSkCMuziPwooAhXWZpIfu\",\"status\":\"离职\",\"createTime\":\"2022-09-04 16:26:10\",\"updateTime\":\"2023-01-11 23:39:35\"}";
        User user = JacksonUtils.readValueFromString(userJsonString, User.class);
        BDDMockito.given(userService.getUserById(1))
                .willReturn(user);

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        System.out.println(response.getContentAsString());
    }
}
