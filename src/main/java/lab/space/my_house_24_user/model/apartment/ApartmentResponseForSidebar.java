package lab.space.my_house_24_user.model.apartment;

import lombok.Builder;

@Builder
public record ApartmentResponseForSidebar(
        Long id,
        String fullName
) {
}
