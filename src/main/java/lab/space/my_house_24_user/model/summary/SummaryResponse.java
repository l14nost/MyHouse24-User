package lab.space.my_house_24_user.model.summary;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
@Builder
public record SummaryResponse(
        String title,
        String bankBook,
        BigDecimal balance,
        BigDecimal averageCost,
        CostChartResponse costChartMonthResponse,
        CostChartResponse costChartYearResponse,
        List<BigDecimal> costByMonth
) {
}
