import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.Role;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.UserRepository;

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
 * @date 2023/3/7 23:15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class ManyToMany {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void name() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("234");
        userEntity.setBirthday(LocalDateTime.now());
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role().setName("管理员"));
        roleList.add(new Role().setName("平民"));
        userEntity.setRoleList(roleList);
        userRepository.save(userEntity);
    }
}
