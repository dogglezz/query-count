package kr.dogglezz.querycounter.user;

import kr.dogglezz.querycounter.query.QueryCounter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QueryCounterTest {

    @Autowired
    private QueryCounter queryCounter;

    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        User user = userRepository.save(new User("hello"));
        userRepository.findById(user.getId());
        assertThat(queryCounter.getCount()).isEqualTo(2);
    }
}
