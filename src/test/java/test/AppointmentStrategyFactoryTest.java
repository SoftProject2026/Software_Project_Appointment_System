package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.appointmentsystem.service.AppointmentStrategyFactory;
import com.appointmentsystem.service.FollowUpStrategy;
import com.appointmentsystem.service.GroupStrategy;
import com.appointmentsystem.service.InPersonStrategy;
import com.appointmentsystem.service.UrgentStrategy;
import com.appointmentsystem.service.VirtualStrategy;
import com.appointmentsystem.service.AppointmentStrategy;
import com.appointmentsystem.domain.models.enums.AppointmentType;

public class AppointmentStrategyFactoryTest {

    @Test
    void testUrgentStrategy() {

        AppointmentStrategy strategy =
                AppointmentStrategyFactory.getStrategy(AppointmentType.URGENT);

        assertTrue(strategy instanceof UrgentStrategy);
    }

    @Test
    void testFollowUpStrategy() {

        AppointmentStrategy strategy =
                AppointmentStrategyFactory.getStrategy(AppointmentType.FOLLOW_UP);

        assertTrue(strategy instanceof FollowUpStrategy);
    }

    @Test
    void testVirtualStrategy() {

        AppointmentStrategy strategy =
                AppointmentStrategyFactory.getStrategy(AppointmentType.VIRTUAL);

        assertTrue(strategy instanceof VirtualStrategy);
    }

    @Test
    void testInPersonStrategy() {

        AppointmentStrategy strategy =
                AppointmentStrategyFactory.getStrategy(AppointmentType.IN_PERSON);

        assertTrue(strategy instanceof InPersonStrategy);
    }

    @Test
    void testGroupStrategy() {

        AppointmentStrategy strategy =
                AppointmentStrategyFactory.getStrategy(AppointmentType.GROUP);

        assertTrue(strategy instanceof GroupStrategy);
    }
    @Test
    void testInvalidTypeThrowsException() {
        assertThrows(NullPointerException.class, () ->
            AppointmentStrategyFactory.getStrategy(null)
        );
    }
    
    @Test
    void testAllTypesNotNull() {
        for (AppointmentType type : AppointmentType.values()) {
            AppointmentStrategy strategy = AppointmentStrategyFactory.getStrategy(type);
            assertNotNull(strategy);
        }
    }
   
}