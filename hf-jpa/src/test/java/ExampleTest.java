import com.hf.jpa.JpaApplication;
import com.hf.jpa.entity.UserEntity;
import com.hf.jpa.repo.UserExampleRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

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
public class ExampleTest {

    @Autowired
    private UserExampleRepository userRepository;

    @Test
    public void test1() {
        UserEntity userEntity = new UserEntity();
        // 设置哪个值就动态构建查询条件
        userEntity.setName("王五");
        userEntity.setRemark("测试");

        // 通过匹配对条件行为进行设置
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.endsWith())
                .withIgnoreCase("name");

        Example<UserEntity> example = Example.of(userEntity, exampleMatcher);
        List<UserEntity> userRepositoryAll = userRepository.findAll(example);
        System.out.println(userRepositoryAll);
    }
}
