package com.travel.hotelsapp.controller;

import com.travel.hotelsapp.filter.HotelFilter;
import com.travel.hotelsapp.model.*;
import com.travel.hotelsapp.repository.BookingRepository;
import com.travel.hotelsapp.repository.FeedbackRepository;
import com.travel.hotelsapp.repository.UserRepository;
import com.travel.hotelsapp.service.HotelService;
import com.travel.hotelsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainPageController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    BookingRepository bookingRepository;

    @GetMapping({"/", "/search"})
    public String allHotels(Model model, @ModelAttribute("filter") HotelFilter filter, @RequestParam("page") Optional<Integer> page){
        Optional<List<String>> countriesParam = Optional.ofNullable(filter.getCountries());
        Optional<List<String>> nutritionsParam = Optional.ofNullable(filter.getNutritions());
        Optional<List<Integer>> starsParam = Optional.ofNullable(filter.getStars());
        int currentPage = page.orElse(1);
        List<Hotel> hotels;
        if(countriesParam.isEmpty() || nutritionsParam.isEmpty() || starsParam.isEmpty()){
            filter=null;
        }
        if(filter==null){
            hotels = hotelService.findAllHotels();
        } else{
            hotels = hotelService.findAllHotelsByCriteria(filter);
        }
        model.addAttribute("hotels", hotels);
        Page<Hotel> hotelPage = hotelService.findPaginated(PageRequest.of(currentPage - 1, 10), filter);
        model.addAttribute("hotelPage", hotelPage);

        int totalPages = hotelPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        List<Nutrition> nutrition = List.of(Nutrition.values());

        model.addAttribute("allNutrition", nutrition);

        List<Integer> stars = List.of(1,2,3,4,5);
        model.addAttribute("stars", stars);

        List<String> countries = hotelService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Integer> costs = new ArrayList<>();
        for(Hotel hotel : hotelService.findAllHotels()){
            for (Cost cost : hotel.getCosts()){
                costs.add(cost.getCost());
            }
        }

        HotelFilter hotelFilter = new HotelFilter();
        hotelFilter.setMaxCost(Collections.max(costs));
        hotelFilter.setMinCost(Collections.min(costs));
        model.addAttribute("filter", hotelFilter);
          return "main";
    }

    @GetMapping("/{id}")
    public String getHotel(Model model, @PathVariable("id") int id){
        Hotel hotel = hotelService.findHotelById(id);
        model.addAttribute("hotel", hotel);
        return "hotel";
    }

    @GetMapping("/feedback/{id}")
    public String getFeedbackPage(Model model, @PathVariable("id") int id, Principal principal){
        Hotel hotel = hotelService.findHotelById(id);
        model.addAttribute("hotel", hotel);
        Feedback feedback = new Feedback();
        User user = userRepository.findUserByEmail(principal.getName());
        feedback.setUser(user);
        feedback.setHotel(hotel);
        model.addAttribute("feedback", feedback);
        return "feedback";
    }

    @PostMapping(value = "/feedback/{id}")
    public String addFeedback(@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult bindingResult,
                            Model model, @PathVariable("id") int id, Principal principal)  {
        if (bindingResult.hasErrors()) {
            Hotel hotel = hotelService.findHotelById(id);
            User user = userRepository.findUserByEmail(principal.getName());
            feedback.setUser(user);
            feedback.setHotel(hotel);
            model.addAttribute("hotel", hotel);
            model.addAttribute("feedback", feedback);
            return "feedback";
        }
        Hotel hotel = hotelService.findHotelById(id);
        User user = userRepository.findUserByEmail(principal.getName());
        feedback.setUser(user);
        feedback.setHotel(hotel);
        feedbackRepository.save(feedback);
        return "redirect:/{id}";
    }

    @GetMapping("/booking/{id}")
    public String getBookingPage(Model model, @PathVariable("id") int id, Principal principal){
        Hotel hotel = hotelService.findHotelById(id);
        model.addAttribute("hotel", hotel);
        User user = userRepository.findUserByEmail(principal.getName());
        List<Cost> costs = hotel.getCosts();
        model.addAttribute("costs", costs);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setHotel(hotel);
        model.addAttribute("booking", booking);
        return "booking";
    }

    @PostMapping(value = "/booking/{id}")
    public String addBooking(@Valid @ModelAttribute("booking") Booking booking, BindingResult bindingResult,
                            Model model, @PathVariable("id") int id, Principal principal)  {
        if (bindingResult.hasErrors()) {
            Hotel hotel = hotelService.findHotelById(id);
            User user = userRepository.findUserByEmail(principal.getName());
            booking.setUser(user);
            booking.setHotel(hotel);
            List<Cost> costs = hotel.getCosts();
            model.addAttribute("costs", costs);
            model.addAttribute("hotel", hotel);
            model.addAttribute("booking", booking);
            return "booking";
        }
        Hotel hotel = hotelService.findHotelById(id);
        User user = userRepository.findUserByEmail(principal.getName());
        booking.setUser(user);
        booking.setHotel(hotel);
        int finalCost = booking.getCost().getCost()*booking.getDayAmount();
        booking.setFinalCost(finalCost);
        bookingRepository.save(booking);
        String message = "Здравствуйте, " + user.getName() + "! Вы совершили бронь отеля "
                + hotel.getName() + " на " + booking.getDayAmount() + " с " + booking.getStartDate() + ". Итоговая стоимость - "
                + booking.getFinalCost() + "$ за номер типа " + booking.getCost().getRoom().getType() + ". ";
        System.out.println(message);
        return "redirect:/{id}";
    }

}
