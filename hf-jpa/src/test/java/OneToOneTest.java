import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.AccountEntity;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.UserRepository;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class OneToOneTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {

        UserEntity userEntity = new UserEntity();
        userEntity.setName("谢谢");
        userEntity.setBirthday(LocalDateTime.now());
        userEntity.setRemark("备注");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("xiexie");
        accountEntity.setPassword("xiexiePS");
        userEntity.setAccountEntity(accountEntity);
        userRepository.save(userEntity);
    }

    @Test
    public void test1() {
        System.out.println(userRepository.findById(5L));
    }

    @Test
    public void testUpdate() {
        final UserEntity userEntity = userRepository.findByName("谢谢");
        userEntity.setName("你好");
        userEntity.setRemark("新备注");
        userEntity.setBirthday(LocalDateTime.now());
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setUsername("了我");
        accountEntity.setPassword("111");
        userEntity.setAccountEntity(accountEntity);
        userRepository.save(userEntity);
    }

    @Test
    public void testDelete() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(6L);
        userRepository.delete(userEntity);
    }
}
