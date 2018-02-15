import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Before;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JpaTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }



    @Test
    public void query() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "谢谢");
        map.add("age", "1");
        map.add("size", "15");
        map.add("page", "3");
        map.add("sort", "age,desc");
        mockMvc.perform(MockMvcRequestBuilders.get("/user").params(map)
                  .contentType(MediaType.APPLICATION_JSON_UTF8))
                  .andDo(MockMvcResultHandlers.print())
                  .andExpect(MockMvcResultMatchers.status().isOk())
                  .andReturn().getResponse().getContentAsString();
        ;
    }
}