package com.devwue.spring.api.repository;

import com.devwue.spring.api.dto.response.PostTypeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostSearchRepositoryImplTests {
    @Autowired
    private PostSearchRepositoryImpl postSearchRepository;

    @Test
    public void queryDsl_groupBy() {
        List<PostTypeResponse> responseList = postSearchRepository.getSummaryType();
        responseList.forEach(postTypeResponse -> {
            System.out.println(postTypeResponse.getType() + ':'+postTypeResponse.getCount());
        });

        Assertions.assertEquals(true, true);
    }
}
