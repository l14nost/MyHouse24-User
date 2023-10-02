package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.*;
import lab.space.my_house_24_user.model.summary.CostChartResponse;
import lab.space.my_house_24_user.model.summary.SummaryResponse;
import lab.space.my_house_24_user.service.ApartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SummaryServiceImplTest {
    @Mock
    private ApartmentService apartmentService;
    @InjectMocks
    private SummaryServiceImpl summaryService;
    @Test
    void summaryByApartment() {
        when(apartmentService.findById(1L)).thenReturn(Apartment.builder()
                        .house(House.builder().name("Name").build())
                        .number(1000)
                        .bankBook(BankBook.builder()
                                .number("000002")
                                .bill(List.of(
                                        Bill.builder().totalPrice(new BigDecimal(200)).periodOf(LocalDateTime.of(2023,12,12,3,21)).serviceBillList(List.of(ServiceBill.builder().service(Service.builder().name("name").build()).totalPrice(new BigDecimal(200)).build())).build(),
                                        Bill.builder().totalPrice(new BigDecimal(200)).periodOf(LocalDateTime.of(2023,12,12,3,21)).serviceBillList(List.of(ServiceBill.builder().service(Service.builder().name("name1").build()).totalPrice(new BigDecimal(200)).build())).build(),
                                        Bill.builder().totalPrice(new BigDecimal(200)).periodOf(LocalDateTime.of(2023,11,12,3,21)).serviceBillList(List.of(ServiceBill.builder().service(Service.builder().name("name").build()).totalPrice(new BigDecimal(200)).build())).build(),
                                        Bill.builder().totalPrice(new BigDecimal(400)).periodOf(LocalDateTime.of(2023,11,12,3,21)).serviceBillList(List.of(ServiceBill.builder().service(Service.builder().name("name1").build()).totalPrice(new BigDecimal(400)).build())).build(),
                                        Bill.builder().totalPrice(new BigDecimal(200)).periodOf(LocalDateTime.of(2023,9,12,3,21)).serviceBillList(List.of(
                                                ServiceBill.builder().service(Service.builder().name("name").build()).totalPrice(new BigDecimal(100)).build(),
                                                ServiceBill.builder().service(Service.builder().name("name1").build()).totalPrice(new BigDecimal(100)).build())
                                        ).build()
                                ))
                                .totalPrice(new BigDecimal(1000))
                                .build())
                .build());
        assertEquals(SummaryResponse.builder()
                .bankBook("000002")
                .averageCost(new BigDecimal(400))
                .balance(new BigDecimal(1000))
                .title("RC\""+"Name"+"\", №"+1000)
                .costByMonth(List.of(
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(0),
                        new BigDecimal(200),
                        new BigDecimal(0),
                        new BigDecimal(600),
                        new BigDecimal(400)

                ))
                .costChartMonthResponse(
                        CostChartResponse.builder()
                                .legend(Set.of("name1","name"))
                                .count(List.of(
                                        new BigDecimal(100),
                                        new BigDecimal(100)
                                ))
                                .build()
                )
                .costChartYearResponse(
                        CostChartResponse.builder()
                                .legend(Set.of("name1", "name"))
                                .count(List.of(
                                        new BigDecimal(500),
                                        new BigDecimal(700)
                                ))
                                .build()
                )
                .build(),
                summaryService.summaryByApartment(1L)
                );
    }

    @Test
    void summaryByApartment_BilsEmpty() {
        when(apartmentService.findById(1L)).thenReturn(Apartment.builder()
                .house(House.builder().name("Name").build())
                .number(1000)
                .bankBook(BankBook.builder()
                        .number("000002")
                        .bill(List.of(
                        ))
                        .totalPrice(new BigDecimal(1000))
                        .build())
                .build());
        assertEquals(SummaryResponse.builder()
                        .bankBook("000002")
                        .averageCost(new BigDecimal(0))
                        .balance(new BigDecimal(1000))
                        .title("RC\""+"Name"+"\", №"+1000)
                        .costByMonth(List.of(
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0),
                                new BigDecimal(0)

                        ))
                        .costChartMonthResponse(
                                CostChartResponse.builder()
                                        .legend(Set.of())
                                        .count(List.of(

                                        ))
                                        .build()
                        )
                        .costChartYearResponse(
                                CostChartResponse.builder()
                                        .legend(Set.of())
                                        .count(List.of(

                                        ))
                                        .build()
                        )
                        .build(),
                summaryService.summaryByApartment(1L)
        );
    }
}