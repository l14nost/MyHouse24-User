package lab.space.my_house_24_user.controller;

import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ApartmentService apartmentService;

    @ModelAttribute("apartments")
    public List<ApartmentResponseForSidebar> getApartments(){
        return apartmentService.getAllApartmentResponse();
    }
}
