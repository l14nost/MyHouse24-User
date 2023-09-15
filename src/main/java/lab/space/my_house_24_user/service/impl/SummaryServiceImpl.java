package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.entity.Bill;
import lab.space.my_house_24_user.entity.ServiceBill;
import lab.space.my_house_24_user.model.summary.CostChartResponse;
import lab.space.my_house_24_user.model.summary.SummaryResponse;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.SummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryServiceImpl implements SummaryService {
    private final ApartmentService apartmentService;
    @Override
    public SummaryResponse summaryByApartment(Long id) {
        log.info("Try to get summary by apartment: "+id);
        String bankBook = "-";
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal averageCost = BigDecimal.ZERO;
        Apartment apartment = apartmentService.findById(id);
        List<BigDecimal> costByMonth = new ArrayList<>();
        CostChartResponse costChartResponse = CostChartResponse.builder().legend(Set.of()).count(List.of()).build();
        CostChartResponse costChartResponseYear = CostChartResponse.builder().legend(Set.of()).count(List.of()).build();
        if (apartment.getBankBook()!=null) {
            bankBook = apartment.getBankBook().getNumber();
            balance = apartment.getBankBook().getTotalPrice();
            averageCost = calculateAverage(new ArrayList<>(apartment.getBankBook().getBill()));
            costByMonth = calculateCostByMonth(apartment.getBankBook().getBill());
            costChartResponse = calculateCostChartForPreviousMonth(apartment.getBankBook().getBill());
            costChartResponseYear = calculateCostChartForYear(apartment.getBankBook().getBill());
        }
        return SummaryResponse.builder()
                .title("RC\""+apartment.getHouse().getName()+"\", №"+apartment.getNumber())
                .balance(balance)
                .bankBook(bankBook)
                .averageCost(averageCost)
                .costByMonth(costByMonth)
                .costChartMonthResponse(costChartResponse)
                .costChartYearResponse(costChartResponseYear)
                .build();
    }

    private BigDecimal calculateAverage(List<Bill> bills){
        log.info("Try to calculate average cost");
        BigDecimal averageCost = BigDecimal.ZERO;
        List<BigDecimal> costByMonth = new ArrayList<>();
        for (int i = 0; i<bills.size();i++){
            BigDecimal cost = bills.get(i).getTotalPrice();
            for (int j = 0; j<bills.size();j++){
                if (bills.get(i).getPeriodOf().getMonthValue()==bills.get(j).getPeriodOf().getMonthValue() && bills.get(i).getPeriodOf().getYear()==bills.get(j).getPeriodOf().getYear() && i!=j){
                    cost = cost.add(bills.get(j).getTotalPrice());
                    bills.remove(j);
                }
            }
            costByMonth.add(cost);
        }
        for (int i = 0; i<costByMonth.size();i++){
            averageCost = averageCost.add(costByMonth.get(i));
        }
        if (!costByMonth.isEmpty()) {
            return averageCost.divide(new BigDecimal(costByMonth.size()));
        }
        else return BigDecimal.ZERO;
    }

    private List<BigDecimal> calculateCostByMonth(List<Bill> bills){
        log.info("Try to calculate cost by month for 3 chart");
        List<BigDecimal> costByMonth = new ArrayList<>();
        for (int i = 1; i<13;i++){
            BigDecimal cost = BigDecimal.ZERO;
            for (int j = 0; j<bills.size();j++){
                if (bills.get(j).getPeriodOf().getMonthValue()==i&& bills.get(j).getPeriodOf().getYear()== LocalDateTime.now().getYear()){
                    cost = cost.add(bills.get(j).getTotalPrice());
                }
            }
            costByMonth.add(cost);
        }
        return costByMonth;
    }

    private CostChartResponse calculateCostChartForPreviousMonth(List<Bill> billList){
        log.info("Try to calculate cost by previous month for 1 chart");
        LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1);
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        List<ServiceBill> serviceBillList = new ArrayList<>();
        for (Bill bill:billList){
            if (bill.getPeriodOf().getYear() == year && bill.getPeriodOf().getMonthValue() == month){
                serviceBillList.addAll(bill.getServiceBillList());
            }
        }
        Set<String> legendSet = new HashSet<>();
        List<BigDecimal> count = new ArrayList<>();
        for (ServiceBill serviceBill: serviceBillList){
            legendSet.add(serviceBill.getService().getName());
        }
        for (String name: legendSet){
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (ServiceBill serviceBill: serviceBillList){
                if (serviceBill.getService().getName().equals(name)){
                    totalPrice = totalPrice.add(serviceBill.getTotalPrice());
                }
            }
            count.add(totalPrice);
        }
        return CostChartResponse.builder()
                .count(count)
                .legend(legendSet)
                .build();
    }


    private CostChartResponse calculateCostChartForYear(List<Bill> billList){
        log.info("Try to calculate cost by year for 2 chart");
        int year = LocalDateTime.now().getYear();
        List<ServiceBill> serviceBillList = new ArrayList<>();
        for (Bill bill:billList){
            if (bill.getPeriodOf().getYear() == year){
                serviceBillList.addAll(bill.getServiceBillList());
            }
        }
        Set<String> legendSet = new HashSet<>();
        List<BigDecimal> count = new ArrayList<>();
        for (ServiceBill serviceBill: serviceBillList){
            legendSet.add(serviceBill.getService().getName());
        }
        for (String name: legendSet){
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (ServiceBill serviceBill: serviceBillList){
                if (serviceBill.getService().getName().equals(name)){
                    totalPrice = totalPrice.add(serviceBill.getTotalPrice());
                }
            }
            count.add(totalPrice);
        }
        return CostChartResponse.builder()
                .count(count)
                .legend(legendSet)
                .build();
    }
}
