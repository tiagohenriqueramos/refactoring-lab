package br.com.refactoringlab;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.docker.compose.enabled=false",
        "spring.data.mongodb.uri=mongodb://localhost:27017/test",
        "spring.data.mongodb.database=test"
})
class RefactoringLabApplicationTests {

    @Test
    void contextLoads() {
    }

}
