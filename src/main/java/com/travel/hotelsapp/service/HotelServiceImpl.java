package com.travel.hotelsapp.service;

import com.travel.hotelsapp.filter.HotelFilter;
import com.travel.hotelsapp.model.Hotel;
import com.travel.hotelsapp.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> findAllHotelsByCriteria(HotelFilter hotelFilter) {
        List<String> nutritions = hotelFilter.getNutritions();
        List<Integer> stars = hotelFilter.getStars();
        List<String> countries = hotelFilter.getCountries();
        int minCost = hotelFilter.getMinCost();
        int maxCost = hotelFilter.getMaxCost();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = criteriaBuilder.createQuery(Hotel.class);
        Root<Hotel> hotelRoot = criteriaQuery.from(Hotel.class);
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> nutritionPredicates = new ArrayList<>();
        List<Predicate> starsPredicates = new ArrayList<>();
        List<Predicate> countriesPredicates = new ArrayList<>();
        if(nutritions.size()!=0){
            for(String type: nutritions){
                nutritionPredicates.add(criteriaBuilder.equal(hotelRoot.get("nutrition"), type));
            }
            Predicate nutritionFinalPredicate = criteriaBuilder.or(nutritionPredicates.toArray(new Predicate[nutritionPredicates.size()]));
            predicates.add(nutritionFinalPredicate);
        }
        if(stars.size()!=0){
            for(int star: stars){
                starsPredicates.add(criteriaBuilder.equal(hotelRoot.get("stars"), star));
            }
            Predicate starsFinalPredicate = criteriaBuilder.or(starsPredicates.toArray(new Predicate[starsPredicates.size()]));
            predicates.add(starsFinalPredicate);
        }
        if(countries.size()!=0){
            for(String country: countries){
                countriesPredicates.add(criteriaBuilder.equal(hotelRoot.get("country"), country));
            }
            Predicate countriesFinalPredicate = criteriaBuilder.or(countriesPredicates.toArray(new Predicate[countriesPredicates.size()]));
            predicates.add(countriesFinalPredicate);
        }
        if(maxCost!=0){
            Predicate maxCostPredicate = criteriaBuilder.lessThan(hotelRoot.get("minCost"), maxCost);
            predicates.add(maxCostPredicate);
        }
        if(minCost!=0){
            Predicate minCostPredicate = criteriaBuilder.greaterThanOrEqualTo(hotelRoot.get("maxCost"), minCost);
            predicates.add(minCostPredicate);
        }
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        criteriaQuery.where(finalPredicate);
        List<Hotel> hotelsFiltered = entityManager.createQuery(criteriaQuery).getResultList();
        return hotelsFiltered;
    }

    @Override
    public Page<Hotel> findPaginated(Pageable pageable, HotelFilter hotelFilter) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Hotel> list;
        List<Hotel> hotels;
        if(hotelFilter==null){
            hotels = this.findAllHotels();
        } else{
            hotels = this.findAllHotelsByCriteria(hotelFilter);
        }
        if (hotels.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, hotels.size());
            list = hotels.subList(startItem, toIndex);
        }
        Page<Hotel> hotelPage = new PageImpl<>(list, PageRequest.of(currentPage,pageSize), hotels.size());
        return hotelPage;
    }

    @Override
    public List<String> getAllCountries() {
        return hotelRepository.findAllCountries();
    }

    @Override
    public Hotel findHotelById(int id) {
        return hotelRepository.findHotelById(id);
    }

}
