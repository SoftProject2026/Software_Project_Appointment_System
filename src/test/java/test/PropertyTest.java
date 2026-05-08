package test;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.enums.PropertyType;

class PropertyTest {
    
    private Property property;
    private TimeSlot mockTimeSlot;
    
    @BeforeEach
    void setUp() {
        property = new Property("prop-123", "comp-456", PropertyType.APARTMENT, 
                                500.0, 120.5, 3, "123 Main Street");
        mockTimeSlot = mock(TimeSlot.class);
    }
    
    @Test
    void testConstructor_ShouldCreatePropertyCorrectly() {
        assertEquals("prop-123", property.getId());
        assertEquals("comp-456", property.getCompanyId());
        assertEquals(PropertyType.APARTMENT, property.getType());
        //assertEquals(500.0, property.getPrice());
        //assertEquals(120.5, property.getArea());
        //assertEquals(3, property.getRoomsNumber());
        //assertEquals("123 Main Street", property.getAddress());
    }
    
    @Test
    void testAddTimeSlot_ShouldAddSlotToList() {
        property.addTimeSlot(mockTimeSlot);
        assertEquals(1, property.getTimeSlots().size());
    }
    
    @Test
    void testAddTimeSlot_WithNull_ShouldNotAdd() {
        property.addTimeSlot(null);
        assertEquals(0, property.getTimeSlots().size());
    }
    
    @Test
    void testAddMultipleTimeSlots_ShouldAddAll() {
        TimeSlot slot2 = mock(TimeSlot.class);
        
        property.addTimeSlot(mockTimeSlot);
        property.addTimeSlot(slot2);
        
        assertEquals(2, property.getTimeSlots().size());
    }
    
    @Test
    void testGetAvailableSlots_ShouldReturnOnlyAvailableSlots() {
        TimeSlot availableSlot = mock(TimeSlot.class);
        TimeSlot unavailableSlot = mock(TimeSlot.class);
        
        when(availableSlot.isAvailable()).thenReturn(true);
        when(unavailableSlot.isAvailable()).thenReturn(false);
        
        property.addTimeSlot(availableSlot);
        property.addTimeSlot(unavailableSlot);
        
        List<TimeSlot> availableSlots = property.getAvailableSlots();
        
        assertEquals(1, availableSlots.size());
        assertEquals(availableSlot, availableSlots.get(0));
    }
    
    // 6. اختبار عدم وجود مواعيد متاحة
    @Test
    void testGetAvailableSlots_WhenNoAvailableSlots_ShouldReturnEmpty() {
        when(mockTimeSlot.isAvailable()).thenReturn(false);
        property.addTimeSlot(mockTimeSlot);
        
        List<TimeSlot> availableSlots = property.getAvailableSlots();
        
        assertTrue(availableSlots.isEmpty());
    }
    
    @Test
    void testSetId_ShouldUpdateId() {
        property.setId("new-prop-id");
        assertEquals("new-prop-id", property.getId());
    }
    
    @Test
    void testSetCompanyId_ShouldUpdateCompanyId() {
        property.setCompanyId("new-company-id");
        assertEquals("new-company-id", property.getCompanyId());
    }
    
    @Test
    void testSetType_ShouldUpdateType() {
        property.setType(PropertyType.VILLA);
        assertEquals(PropertyType.VILLA, property.getType());
    }
    
    @Test
    void testToString_ShouldReturnFormattedString() {
        String result = property.toString();
        
        assertTrue(result.contains("APARTMENT"));
        assertTrue(result.contains("500.0"));
        assertTrue(result.contains("120.5"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("123 Main Street"));
    }
}