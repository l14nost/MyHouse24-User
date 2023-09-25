package lab.space.my_house_24_user.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.MastersApplication;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationRequest;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationSaveRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MastersApplicationService {
    MastersApplication getMastersApplicationById(Long id) throws EntityNotFoundException;

    MastersApplicationResponse getMastersApplicationResponse(Long id) throws EntityNotFoundException;

    Page<MastersApplicationResponse> getAllMastersApplicationByRequest(MastersApplicationRequest request);

    List<EnumResponse> getAllMastersApplicationStatus();

    List<EnumResponse> getAllTypeMaster();

    void saveMastersApplicationByRequest(MastersApplicationSaveRequest request);

    void saveMastersApplication(MastersApplication mastersApplication);

    void deleteMastersApplicationById(Long id);
}
