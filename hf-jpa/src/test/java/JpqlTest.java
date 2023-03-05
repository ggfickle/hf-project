import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.UserRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 16:27
 */
@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class JpqlTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testQueryByName() {
        UserEntity userEntity = userRepository.findByName("张三");
        System.out.println(userEntity);
    }

    @Test
    public void testUpdateById() {
        int user = userRepository.updateUser("王五", 2L);
        System.out.println(user);
    }

    @Test
    public void testInsert() {
        int result = userRepository.insertUserBySelect(2L);
        System.out.println(result);
    }

    @Test
    public void testBySql() {
        UserEntity userEntity = userRepository.findByNameBySql("李四");
        System.out.println(userEntity);
    }
}
