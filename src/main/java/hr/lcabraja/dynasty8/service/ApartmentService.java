package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.Apartment;

import java.util.List;

public interface ApartmentService {

    long getCount();

    List<Apartment> getAllApartments();

    Apartment getApartmentById(Long apartmentId);

    boolean deleteApartment(Long apartmentId);

    Apartment updateApartment(Apartment apartment);

    Apartment createApartment(Apartment apartment);
}
