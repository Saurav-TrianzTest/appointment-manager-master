package com.appointmentapp.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoApplicationTest {

    @Test
    void testMain_startsSpringApplication() {
        // Arrange
        String[] args = new String[]{};

        // Act & Assert
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            DemoApplication.main(args);
            springApplicationMock.verify(() -> SpringApplication.run(DemoApplication.class, args), times(1));
        }
    }

    @Test
    void testMain_withArguments_startsSpringApplication() {
        // Arrange
        String[] args = new String[]{"--server.port=8081", "--spring.profiles.active=dev"};

        // Act & Assert
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            DemoApplication.main(args);
            springApplicationMock.verify(() -> SpringApplication.run(DemoApplication.class, args), times(1));
        }
    }

    @Test
    void testMain_withEmptyArgs_startsSpringApplication() {
        // Arrange
        String[] args = new String[]{};

        // Act & Assert
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            DemoApplication.main(args);
            springApplicationMock.verify(() -> SpringApplication.run(DemoApplication.class, args), times(1));
        }
    }

    @Test
    void testDemoApplication_classExists() {
        // Act
        DemoApplication application = new DemoApplication();

        // Assert
        assertNotNull(application);
    }

    @Test
    void testDemoApplication_hasSpringBootApplicationAnnotation() {
        // Act
        boolean hasAnnotation = DemoApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class);

        // Assert
        assertTrue(hasAnnotation);
    }

    @Test
    void testMain_withNullArgs_handlesNull() {
        // Arrange
        String[] args = null;

        // Act & Assert
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            DemoApplication.main(args);
            springApplicationMock.verify(() -> SpringApplication.run(DemoApplication.class, args), times(1));
        }
    }

    @Test
    void testMain_withMultipleArguments_passesAllArguments() {
        // Arrange
        String[] args = new String[]{"arg1", "arg2", "arg3"};

        // Act & Assert
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            DemoApplication.main(args);
            springApplicationMock.verify(() -> SpringApplication.run(DemoApplication.class, args), times(1));
        }
    }
}
