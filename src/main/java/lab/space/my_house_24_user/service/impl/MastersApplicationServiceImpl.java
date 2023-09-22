package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.MastersApplication;
import lab.space.my_house_24_user.enums.Master;
import lab.space.my_house_24_user.enums.MastersApplicationStatus;
import lab.space.my_house_24_user.mapper.EnumMapper;
import lab.space.my_house_24_user.mapper.MastersApplicationMapper;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationRequest;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationSaveRequest;
import lab.space.my_house_24_user.repository.MastersApplicationRepository;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.MastersApplicationService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.specification.MastersApplicationSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MastersApplicationServiceImpl implements MastersApplicationService {
    private final MastersApplicationRepository mastersApplicationRepository;
    private final MastersApplicationSpecification specification;
    private final UserService userService;
    private final ApartmentService apartmentService;

    @Override
    public MastersApplication getMastersApplicationById(Long id) throws EntityNotFoundException {
        log.info("Try to get MastersApplication");
        return mastersApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MastersApplication not found"));
    }

    @Override
    public MastersApplicationResponse getMastersApplicationResponse(Long id) throws EntityNotFoundException {
        log.info("Try to get MastersApplication");
        return MastersApplicationMapper.toMastersApplicationResponse(getMastersApplicationById(id));
    }

    @Override
    public Page<MastersApplicationResponse> getAllMastersApplicationByRequest(MastersApplicationRequest request) {
        log.info("Try to get MastersApplication");
        return mastersApplicationRepository
                .findAll(
                        specification.getMastersApplicationByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()),
                        PageRequest.of(request.pageIndex(), 10)
                ).map(MastersApplicationMapper::toMastersApplicationResponse);
    }

    @Override
    public List<EnumResponse> getAllMastersApplicationStatus() {
        log.info("Try to get MastersApplication");
        return Arrays.stream(MastersApplicationStatus.values())
                .map(status -> EnumMapper.toSimpleDto(
                                status.name(),
                                status.getMastersApplicationStatus(LocaleContextHolder.getLocale())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<EnumResponse> getAllTypeMaster() {
        log.info("Try to get all TypeMaster");
        return Arrays.stream(Master.values())
                .map(master -> EnumMapper.toSimpleDto(
                                master.name(),
                                master.getMaster(LocaleContextHolder.getLocale())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public void saveMastersApplicationByRequest(MastersApplicationSaveRequest request) throws EntityNotFoundException {
        log.info("Try to save MastersApplication by Save Request");
        saveMastersApplication(MastersApplicationMapper.toMastersApplication(
                request,
                userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()),
                apartmentService.findById(request.apartmentId()))
        );
        log.info("Success save MastersApplication by Save Request");
    }

    @Override
    public void saveMastersApplication(MastersApplication mastersApplication) {
        log.info("Try to save MastersApplication");
        mastersApplicationRepository.save(mastersApplication);
        log.info("Success save MastersApplication");
    }

    @Override
    public void deleteMastersApplicationById(Long id) throws EntityNotFoundException, IllegalArgumentException {
        log.info("Try to delete MastersApplication");
        MastersApplication mastersApplication = getMastersApplicationById(id);
        if (mastersApplication.getMastersApplicationStatus() != MastersApplicationStatus.NEW) {
            log.error("MastersApplication cant be deleted");
            throw new IllegalArgumentException("MastersApplication cant be deleted");
        }
        mastersApplicationRepository.delete(mastersApplication);
        log.info("Success delete MastersApplication by id " + id);

    }
}
