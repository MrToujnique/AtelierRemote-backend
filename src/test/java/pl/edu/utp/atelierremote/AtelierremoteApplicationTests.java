package pl.edu.utp.atelierremote;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.utp.atelierremote.model.Customer;
import pl.edu.utp.atelierremote.model.Dish;
import pl.edu.utp.atelierremote.model.DishOrder;
import pl.edu.utp.atelierremote.model.SubOrder;
import pl.edu.utp.atelierremote.model.repository.DishOrderRepository;
import pl.edu.utp.atelierremote.service.ReportService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AtelierremoteApplicationTests {

    @Mock
    DishOrderRepository dishOrderRepository;

    @Test
    void test_makeProfitReport() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, Month.JUNE, 21, 20, 0, 0, 0);
        Dish bigos = new Dish("Bigos", "Kapusta, kiełbasa, pieczarki", 20);
        Dish pork = new Dish("Kotlet schabowy z frytkami", "200g panierowanego mięsa, 300g ziemniaków, surówka", 16);
        Customer client1 = new Customer("123456789", "ul.Długa 51");
        Customer client2 = new Customer("222555222", "ul.Krótka 21");
        DishOrder order = new DishOrder(client1);
        order.setIssuedAt(now);
        SubOrder s1 = new SubOrder(order, bigos, 2);
        SubOrder s2 = new SubOrder(order, pork, 4);
        order.setSubOrders(Arrays.asList(s1, s2));
        DishOrder order2 = new DishOrder(client2);
        order2.setIssuedAt(now);
        SubOrder s3 = new SubOrder(order2, bigos, 5);
        SubOrder s4 = new SubOrder(order2, pork, 2);
        order2.setSubOrders(Arrays.asList(s3, s4));

        when(dishOrderRepository.findAll()).thenReturn(Arrays.asList(order, order2));
        ReportService reportService = new ReportService(dishOrderRepository);
        //when
        Map<Integer, Float> profitReport = reportService.makeProfitReport();
        //then
        assertEquals(236.0f, profitReport.get(now.getHour()));
    }
}
