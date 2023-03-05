import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.UserMethodNameRepository;
import com.hf.jpa.repo.UserRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 16:27
 */
@Slf4j
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
public class MethodNameTest {

    @Autowired
    private UserMethodNameRepository userRepository;

    @Test
    public void test1() {
        System.out.println(userRepository.existsByName("王五"));
    }

    @Test
    public void test2() {
        int countByName = userRepository.countByName("王五");
        System.out.println(countByName);
    }

    @Test
    public void test3() {
        List<UserEntity> byName = userRepository.findByName("王五");
        System.out.println(byName);
    }

    @Test
    public void test4() {
        List<UserEntity> byIdBetween = userRepository.findByIdBetween(1L, 4L);
        System.out.println(byIdBetween);
    }

    @Test
    public void test5() {
        List<UserEntity> byIdGreaterThanAndIdLessThan = userRepository.findByIdGreaterThanAndIdLessThan(1L, 4L);
        System.out.println(byIdGreaterThanAndIdLessThan);
    }

    @Test
    public void test6() {
        List<UserEntity> byIdIn = userRepository.findByIdIn(Arrays.asList(1L, 4L));
        System.out.println(byIdIn);
    }

    @Test
    public void test7() {
        LocalDateTime startTime = LocalDateTime.of(2023, 3, 5, 18, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 3, 5, 22, 0, 0);
        List<UserEntity> byBirthdayAfterAndBirthdayBefore =
                userRepository.findByBirthdayAfterAndBirthdayBefore(startTime, endTime);
        System.out.println(byBirthdayAfterAndBirthdayBefore);
    }
}
