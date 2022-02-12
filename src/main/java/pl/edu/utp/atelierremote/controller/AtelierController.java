package pl.edu.utp.atelierremote.controller;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.edu.utp.atelierremote.model.*;
import pl.edu.utp.atelierremote.model.dto.*;
import pl.edu.utp.atelierremote.model.repository.*;
import pl.edu.utp.atelierremote.service.ReportService;
import pl.edu.utp.atelierremote.util.JwtUtil;
import pl.edu.utp.atelierremote.util.TimeUtil;

import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AtelierController {

    DishRepository dishRepository;
    CustomerRepository customerRepository;
    CustomerTableRepository customerTableRepository;
    DishOrderRepository dishOrderRepository;
    SubOrderRepository subOrderRepository;
    ReportService reportService;
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    TableRepository tableRepository;
    ReservationRepository reservationRepository;
    WorkDayRepository workDayRepository;

    @Autowired
    public AtelierController(DishRepository dishRepository, CustomerRepository customerRepository, CustomerTableRepository customerTableRepository,
                             DishOrderRepository dishOrderRepository, SubOrderRepository subOrderRepository,
                             ReportService reportService, JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                             TableRepository tableRepository, ReservationRepository reservationRepository,
                             WorkDayRepository workDayRepository) {
        this.dishRepository = dishRepository;
        this.customerRepository = customerRepository;
        this.customerTableRepository = customerTableRepository;
        this.dishOrderRepository = dishOrderRepository;
        this.subOrderRepository = subOrderRepository;
        this.reportService = reportService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
        this.workDayRepository = workDayRepository;

    }

    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getDishes() {
        return new ResponseEntity<>(dishRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return new ResponseEntity<>(customerRepository.findAll().stream().map(CustomerDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/customersTables")
    public ResponseEntity<List<CustomerTableDTO>> getCustomersTables()
    {
        return new ResponseEntity<>(customerTableRepository.findAll().stream().map(CustomerTableDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<DishOrderDTO>> getOrders() {
        return new ResponseEntity<>(dishOrderRepository.findAll().stream().map(DishOrderDTO::new).sorted(Comparator.comparing(DishOrderDTO::getIssuedAt)).collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/tables")
    public ResponseEntity<List<DinnerTableDTO>> getTables(){
        return new ResponseEntity<>(tableRepository.findAll().stream().map(DinnerTableDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getReservation(){
        return new ResponseEntity<>(reservationRepository.findAll().stream().map(ReservationDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/freeReservationDates/{fromDate}")
    public ResponseEntity<FreeReservationsDTO> getFreeReservationsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate)
    {
        return new ResponseEntity<>((findFreeReservationsByDate(fromDate)), HttpStatus.OK);
    }

    @PostMapping("/dishes")
    public ResponseEntity<Dish> makeDish(@RequestBody Dish dish)
    {
        Dish newDish = new Dish(dish.getName(), dish.getDescription(), dish.getPrice());
        return new ResponseEntity<>(dishRepository.save(newDish), HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<PostReservationDTO> makeReservation(@RequestBody PostReservationDTO reservation){
        LocalTime startHour = reservation.getActualHour();
        reservation.setActualDate(reservation.getActualDate());
        reservation.setActualHour(startHour);
        Optional<CustomerTable> customer = customerTableRepository.findByPhoneNumber(reservation.getPhoneNumber());
        CustomerTable newCustomer = null;
        if (!customer.isPresent()) {
            newCustomer = customerTableRepository.save(new CustomerTable(reservation.getSurname(), reservation.getPhoneNumber()));
        }
        DinnerTable dinnerTable = tableRepository.getById(reservation.getId());
        Reservation newReservation = new Reservation(reservation.getActualDate(), startHour, LocalTime.of(startHour.getHour(),
                startHour.getMinute()).plusMinutes(120), customer.orElse(newCustomer), dinnerTable);
        return new ResponseEntity<>(new PostReservationDTO(reservationRepository.save(newReservation)), HttpStatus.OK);
    }
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> makeOrder(@RequestBody OrderDTO order) {
        order.setIssuedAt(LocalDateTime.now());
        Optional<Customer> customer = customerRepository.findByPhoneNumber(order.getCustomer().getPhoneNumber());
        Customer newCustomer = null;
        if (!customer.isPresent()) {
            newCustomer = customerRepository.save(new Customer(order.getCustomer().getPhoneNumber(), order.getCustomer().getAddress()));
        }
        DishOrder newOrder = new DishOrder(customer.orElse(newCustomer));
        newOrder.setSubOrders(order.getSubOrders().stream().map(subOrder ->
                new SubOrder(newOrder, subOrder.getDishType(), subOrder.getQuantity())
        ).collect(Collectors.toList()));
        return new ResponseEntity<>(new OrderDTO(dishOrderRepository.save(newOrder)), HttpStatus.OK);
    }
    @GetMapping("/report")
    public java.util.Map<Integer, Float> getReport() {
        return reportService.makeProfitReport();
    }

    @PostMapping("/login")
    public Map generateToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return Collections.singletonMap("token",jwtUtil.generateToken(authRequest.getUserName()));
    }

    @DeleteMapping("/dishes/{id}")
    @Transactional
    public ResponseEntity<String> deleteDish(@PathVariable String id)
    {
        List<SubOrder> subOrders = subOrderRepository.findAllByDishTypeId(Long.parseLong(id));
        for(int i=0; i<subOrders.size(); i++)
        {
            subOrders.get(i).setOrder(null);
            subOrders.get(i).setDishType(null);
        }
        dishRepository.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/workDays")
    public List<WorkDayDTO> getWorkDays(){
        return workDayRepository.findAll().stream().map(WorkDayDTO::new).collect(Collectors.toList());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Transactional
    public FreeReservationsDTO findFreeReservationsByDate(LocalDate date)
    {
        Optional<WorkDayDTO> workDay = getWorkDay(getWorkDays(), date.getDayOfWeek());

        if(workDay.isEmpty()) {
            return new FreeReservationsDTO(date);
        }

        Collection<Reservation> reservations = reservationRepository.findAllByDateOrderByStartHour(date);
        Range<LocalTime> workHours = Range.closed(workDay.get().getStartTime(), workDay.get().getEndTime());
        RangeSet<LocalTime> freeHours = TreeRangeSet.create();
        freeHours.add(workHours);
        reservations.forEach(reservation -> freeHours.remove(Range.closed(reservation.getStartHour(), reservation.getEndHour())));

        FreeReservationsDTO freeReservations = new FreeReservationsDTO(date);
        freeHours.asRanges().forEach(range -> freeReservations.getReservations().addAll(TimeUtil.parseRangeToIntervals(range)));

        return freeReservations;
    }

    private Optional<WorkDayDTO> getWorkDay(List<WorkDayDTO> workDays, DayOfWeek weekDay) {
        return workDays
                .stream()
                .filter(workDay -> workDay.getWeekDay() == weekDay)
                .findFirst();
    }


    @EventListener(ApplicationReadyEvent.class)
    public void populateDataBase() {
        Dish bigos = new Dish("Bigos", "Kapusta, kiełbasa, pieczarki", 20);
        Dish pork = new Dish("Kotlet schabowy z frytkami", "200g panierowanego mięsa, 300g ziemniaków, surówka", 16);

        Customer client1 = new Customer("123456789", "ul.Testowa 1");
        Customer client2 = new Customer("222555222", "ul.Krótka 2");

        CustomerTable clientTable1 = new CustomerTable("Toczek", "123456789");
        CustomerTable clientTable2 = new CustomerTable("Karbowski", "987654321");

        WorkDay monday = new WorkDay(MONDAY, LocalTime.of(10, 0), LocalTime.of(20,0));
        WorkDay tuesday = new WorkDay(TUESDAY, LocalTime.of(10, 0), LocalTime.of(20,0));
        WorkDay wednesday = new WorkDay(WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(20,0));
        WorkDay thursday = new WorkDay(THURSDAY, LocalTime.of(10, 0), LocalTime.of(20,0));
        WorkDay friday = new WorkDay(FRIDAY, LocalTime.of(10, 0), LocalTime.of(21,0));
        WorkDay saturday = new WorkDay(SATURDAY, LocalTime.of(10, 0), LocalTime.of(21,0));
        WorkDay sunday = new WorkDay(SUNDAY, LocalTime.of(10, 0), LocalTime.of(20,0));


        DinnerTable dinnerTable1 = new DinnerTable(3);
        DinnerTable dinnerTable2 = new DinnerTable(5);

        Reservation reservation1 = new Reservation(LocalDate.of(2021, 6, 9), LocalTime.of(14, 0), LocalTime.of(16, 0), clientTable1 , dinnerTable1);
        Reservation reservation2 = new Reservation(LocalDate.of(2021, 6, 9), LocalTime.of(16, 0), LocalTime.of(18, 0), clientTable2, dinnerTable2);

        DishOrder order = new DishOrder(client1);
        SubOrder s1 = new SubOrder(order, bigos, 4);
        SubOrder s2 = new SubOrder(order, pork, 1);
        DishOrder order2 = new DishOrder(client2);
        SubOrder s3 = new SubOrder(order2, bigos, 5);
        SubOrder s4 = new SubOrder(order2, pork, 2);


        dishRepository.saveAll(Arrays.asList(bigos, pork));
        customerRepository.saveAll(Arrays.asList(client1, client2));
        customerTableRepository.saveAll(Arrays.asList(clientTable1, clientTable2));
        dishOrderRepository.saveAll(Arrays.asList(order, order2));
        subOrderRepository.saveAll(Arrays.asList(s1, s2, s3, s4));
        tableRepository.saveAll(Arrays.asList(dinnerTable1, dinnerTable2));
        reservationRepository.saveAll(Arrays.asList(reservation1,reservation2));

        workDayRepository.saveAll(Arrays.asList(monday, tuesday, wednesday, thursday, friday, saturday, sunday));
    }
}
