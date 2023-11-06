package tn.esprit.spring;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PisteServicesImplTest {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Test
    public void testRetrieveAllPistes() {
        // Create sample data or mock repository behavior
        Piste piste1 = new Piste();
        piste1.setNumPiste(1L);
        piste1.setNamePiste("Piste 1");
        piste1.setColor(Color.GREEN);
        piste1.setLength(100);
        piste1.setSlope(20);

        Piste piste2 = new Piste();
        piste2.setNumPiste(2L);
        piste2.setNamePiste("Piste 2");
        piste2.setColor(Color.BLUE);
        piste2.setLength(200);
        piste2.setSlope(15);

        when(pisteRepository.findAll()).thenReturn(Arrays.asList(piste1, piste2));
        
        // Call the service method
        List<Piste> pistes = pisteServices.retrieveAllPistes();

        // Verify the method was called
        verify(pisteRepository).findAll();

        // Add assertions based on your specific test data
        assertEquals(2, pistes.size());
        assertEquals("Piste 1", pistes.get(0).getNamePiste());
        assertEquals("Piste 2", pistes.get(1).getNamePiste());
    }

    @Test
    public void testAddPiste() {
        // Create a sample Piste object
        Piste piste = new Piste();
        piste.setNumPiste(3L);
        piste.setNamePiste("Test Piste");
        piste.setColor(Color.RED);
        piste.setLength(150);
        piste.setSlope(18);

        // Mock repository behavior
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        // Call the service method
        Piste addedPiste = pisteServices.addPiste(piste);

        // Verify the method was called
        verify(pisteRepository).save(any(Piste.class));

        // Add assertions based on your specific test data
        assertEquals("Test Piste", addedPiste.getNamePiste());
    }

    @Test
    public void testRemovePiste() {
        // Mock repository behavior
        pisteServices.removePiste(4L);

        // Verify the method was called
        verify(pisteRepository).deleteById(4L);
    }

    @Test
    public void testRetrievePiste() {
        // Create sample data or mock repository behavior
        Piste piste = new Piste();
        piste.setNumPiste(5L);
        piste.setNamePiste("Test Piste");
        piste.setColor(Color.RED);
        piste.setLength(150);
        piste.setSlope(18);

        when(pisteRepository.findById(5L)).thenReturn(Optional.of(piste));

        // Call the service method
        Piste retrievedPiste = pisteServices.retrievePiste(5L);

        // Verify the method was called
        verify(pisteRepository).findById(5L);

        // Add assertions based on your specific test data
        assertEquals("Test Piste", retrievedPiste.getNamePiste());
    }
}

