    package cc.maids.lms.services;

import cc.maids.lms.model.Patron;
import cc.maids.lms.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(String id) {
        return patronRepository.findById(id);
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(String id, Patron patronDetails) {
        return patronRepository.findById(id).map(patron -> {
            patron.setName(patronDetails.getName());
            patron.setContactInformation(patronDetails.getContactInformation());
            return patronRepository.save(patron);
        }).orElseGet(() -> {
            patronDetails.setId(id);
            return patronRepository.save(patronDetails);
        });
    }

    public void deletePatron(String id) {
        patronRepository.deleteById(id);
    }
}
