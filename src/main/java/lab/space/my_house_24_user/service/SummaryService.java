package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.model.summary.SummaryResponse;

public interface SummaryService {
    SummaryResponse summaryByApartment(Long id);
}
