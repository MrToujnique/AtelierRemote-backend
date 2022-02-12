package pl.edu.utp.atelierremote.service;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.utp.atelierremote.controller.AtelierController;
import pl.edu.utp.atelierremote.model.DishOrder;
import pl.edu.utp.atelierremote.model.Reservation;
import pl.edu.utp.atelierremote.model.dto.FreeReservationsDTO;
import pl.edu.utp.atelierremote.model.dto.WorkDayDTO;
import pl.edu.utp.atelierremote.model.repository.DishOrderRepository;
import pl.edu.utp.atelierremote.model.repository.ReservationRepository;
import pl.edu.utp.atelierremote.model.repository.WorkDayRepository;
import pl.edu.utp.atelierremote.util.TimeUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    DishOrderRepository orderRepository;

    @Autowired
    public ReportService(DishOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private Float countPriceOfDishesInList(List<DishOrder> value) {
        return value.stream()
                .flatMap(order -> order.getSubOrders().stream()
                        .map(subOrder -> subOrder.getQuantity() * subOrder.getDishType().getPrice()))
                .collect(Collectors.toList())
                .stream().reduce(0f, Float::sum);
    }

    public Map<Integer, Float> makeProfitReport() {
        return  orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(order -> order.getIssuedAt().getHour()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> countPriceOfDishesInList(e.getValue())));
    }
}
