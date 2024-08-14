// run this command to start testing -> ./gradlew test --tests itu.blg411.kanver.Location.controller.LocationControllerTest

package itu.blg411.kanver.Location.controller;

import itu.blg411.kanver.location.LocationService;
import itu.blg411.kanver.location.controller.LocationController;
import itu.blg411.kanver.location.model.Location;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    public LocationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLocations() {
        // Arrange
        List<Location> expectedLocations = Collections.singletonList(new Location());
        when(locationService.getAllLocations()).thenReturn(expectedLocations);

        // Act
        ResponseEntity<List<Location>> responseEntity = locationController.getAllLocations();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedLocations, responseEntity.getBody());
    }
}
