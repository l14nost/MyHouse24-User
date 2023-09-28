package lab.space.my_house_24_user.model.masters_appl;

import lombok.Builder;

@Builder
public record MastersApplicationRequest(
        int pageIndex
) {
}
