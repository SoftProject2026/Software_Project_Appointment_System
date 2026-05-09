package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.TimeSlot;

public class TimeSlotTest {

    @Test
    void testCreateTimeSlot() {

        LocalDateTime time =
                LocalDateTime.now().plusDays(1);

        TimeSlot slot = new TimeSlot(time);

        assertTrue(slot.isAvailable());
        assertEquals(time, slot.getStartTime());
    }

    @Test
    void testPastTimeSlot() {

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(
                LocalDateTime.now().minusDays(1)
            )
        );
    }

    @Test
    void testSetAvailable() {

        TimeSlot slot = new TimeSlot(
                LocalDateTime.now().plusHours(1)
        );

        slot.setAvailable(false);

        assertFalse(slot.isAvailable());
    }
    
    @Test
    void testToString() {

        LocalDateTime time =
                LocalDateTime.of(2026, 5, 10, 14, 30);

        TimeSlot slot = new TimeSlot(time);

        assertEquals(
            "2026-05-10 14:30",
            slot.toString()
        );
    }
}