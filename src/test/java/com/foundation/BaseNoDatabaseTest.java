package com.foundation;

import com.foundation.repository.RefreshTokenRepository;
import com.foundation.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"})
@AutoConfigureMockMvc
public abstract class BaseNoDatabaseTest {
    // Repositories must be mocked as @SpringBootTest's properties
    // Data Source and JPA are disabled to not use database
    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;
    @MockBean
    protected UserRepository userRepository;
}