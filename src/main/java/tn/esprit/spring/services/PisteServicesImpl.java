package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
@AllArgsConstructor
@Service
public class PisteServicesImpl implements  IPisteServices{

    private IPisteRepository pisteRepository;
    private static final Logger logger = LogManager.getLogger(PisteServicesImpl.class);


    @Override
    public List<Piste> retrieveAllPistes() {
        logger.info("Retrieving all Pistes");
        List<Piste> allPistes = pisteRepository.findAll();
        logger.debug("Number of Pistes retrieved: {}", allPistes.size());
        return allPistes;
    }

    @Override
    public Piste addPiste(Piste piste) {
        logger.info("Adding a new Piste: {}", piste.getNamePiste());
        try {
            Piste savedPiste = pisteRepository.save(piste);
            logger.info("Piste added successfully: {}", savedPiste.getNamePiste());
            return savedPiste;
        } catch (Exception e) {
            logger.error("Error while adding Piste: {}", e.getMessage());
            throw e;
        }
    }
    @Override
    public void removePiste(Long numPiste) {
        logger.info("Removing Piste with ID: {}", numPiste);
        pisteRepository.deleteById(numPiste);
        logger.info("Piste with ID {} removed", numPiste);
    }

    @Override
    public Piste retrievePiste(Long numPiste) {
        logger.info("Retrieving Piste with ID: {}", numPiste);
        Piste piste = pisteRepository.findById(numPiste).orElse(null);
        if (piste != null) {
            logger.info("Piste with ID {} retrieved: {}", numPiste, piste.getNamePiste());
        } else {
            logger.warn("Piste with ID {} not found", numPiste);
        }
        return piste;
    }
}
