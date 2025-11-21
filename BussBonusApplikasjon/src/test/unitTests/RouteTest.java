import core.domain.Route;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouteTest {

    @Test
    public void routeShouldStoreCorrectValues() {
        // Arrange
        int id = 10;
        String start = "Sarpsborg";
        String end = "Fredrikstad";
        LocalDateTime time = LocalDateTime.of(2025, 2, 20, 10, 30);
        boolean delayed = true;

        // Act
        Route route = new Route(id, start, end, time, delayed);

        // Assert
        assertEquals(id, route.getRouteId());
        assertEquals(start, route.getStartBusStop());
        assertEquals(end, route.getEndBusStop());
        assertEquals(time, route.getTime());
        assertTrue(route.isDelayed());
    }
}
