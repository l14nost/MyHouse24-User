package lab.space.my_house_24_user.model.summary;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class CostChartResponse {
        Set<String> legend;
        List<BigDecimal> count;
}
