import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.Message;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.UserRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/7 22:33
 */
@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class OneToManyTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test1() {
        UserEntity repository = userRepository.findByName("李四");
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message().setInfo("你好"));
        messageList.add(new Message().setInfo("再见"));
        repository.setMessageList(messageList);
        userRepository.save(repository);
    }

    /**
     * 一对多默认懒加载
     * 需要加上@Transactional(readOnly = true)
     * 否则报:could not initialize proxy - no Session
     */
    @Test
    @Transactional(readOnly = true)
    public void findByID() {
        Optional<UserEntity> userRepositoryById = userRepository.findById(3L);
        System.out.println("-------------");
        System.out.println(userRepositoryById);
    }
}
