package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.Apartment;
import hr.lcabraja.dynasty8.repository.ApartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Override
    public long getCount() {
        return apartmentRepository.count();
    }

    @Override
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    @Override
    public Apartment getApartmentById(Long apartmentId) {
        return apartmentRepository.getReferenceById(apartmentId);
    }

    @Override
    public boolean deleteApartment(Long apartmentId) {
        try {
            apartmentRepository.deleteById(apartmentId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Apartment updateApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    @Override
    public Apartment createApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }
}
