package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.entity.MastersApplication;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.MastersApplicationStatus;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationSaveRequest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;

public interface MastersApplicationMapper {
    static MastersApplicationResponse toMastersApplicationResponse(MastersApplication mastersApplication) {
        return MastersApplicationResponse.builder()
                .id(mastersApplication.getId())
                .status(
                        EnumMapper.toSimpleDto(
                                mastersApplication.getMastersApplicationStatus().name(),
                                mastersApplication.getMastersApplicationStatus().getMastersApplicationStatus(LocaleContextHolder.getLocale())
                        )
                )
                .masterType(
                        EnumMapper.toSimpleDto(
                                mastersApplication.getMaster().name(),
                                mastersApplication.getMaster().getMaster(LocaleContextHolder.getLocale())
                        )
                )
                .apartment(ApartmentMapper.entityToDtoForSidebar(mastersApplication.getApartment()))
                .date(mastersApplication.getDateTime().atZone(ZoneId.systemDefault()).toLocalDate())
                .time(mastersApplication.getDateTime().atZone(ZoneId.systemDefault()).toLocalTime())
                .description(mastersApplication.getDescription())
                .build();
    }

    static MastersApplication toMastersApplication(MastersApplicationSaveRequest request, User user, Apartment apartment) {
        return MastersApplication.builder()
                .description(request.description())
                .master(request.masterType())
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .dateTime(LocalDateTime.of(request.date(), request.time()))
                .staff(null)
                .user(user)
                .apartment(apartment)
                .build();
    }
}
