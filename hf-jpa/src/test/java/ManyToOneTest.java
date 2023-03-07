import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.Message;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.MessageRepo;
import com.hf.jpa.repo.UserRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/7 22:48
 */
@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class ManyToOneTest {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("李志");
        userEntity.setBirthday(LocalDateTime.now());
        List<Message> messageList = new ArrayList<>();
        Message message1 =
                new Message().setInfo("dasd").setUserEntity(userEntity);
        Message message2 =
                new Message().setInfo("fdsaf").setUserEntity(userEntity);
        messageList.add(message1);
        messageList.add(message2);
        messageRepo.saveAll(messageList);
    }

    @Test
    public void delete() {
        UserEntity userEntity = userRepository.findByName("李志");
        userRepository.delete(userEntity);
    }
}
