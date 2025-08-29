package com.omeshwar.project.airBNB.service;

import com.omeshwar.project.airBNB.entity.Hotel;
import com.omeshwar.project.airBNB.entity.HotelMinPrice;
import com.omeshwar.project.airBNB.entity.Inventory;
import com.omeshwar.project.airBNB.repository.HotelMinPriceRepository;
import com.omeshwar.project.airBNB.repository.HotelRepository;
import com.omeshwar.project.airBNB.repository.InventoryRepository;
import com.omeshwar.project.airBNB.strategy.PricingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PricingUpdateService {

    //scheduler to update inventory and hotel min price tables every hour
    private final HotelRepository hotelRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final InventoryRepository inventoryRepository;
    private final PricingService pricingService;

    @Scheduled(cron = "* */30 * * * *")
    public void updatePrices(){
        log.info("Update Hotel Inventory and Hotel Min Price");
        int pageNumber = 0;
        int pageSize = 100;

        while (true){
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(pageNumber,pageSize));
            if (hotelPage.isEmpty())
                break;
            hotelPage.getContent().forEach(this::updateHotelPrice);

            pageNumber++;
        }
    }

    private void updateHotelPrice(Hotel hotel) {
        log.info("Hotel update for {}",hotel.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endDate);
        updateInventoryPrice(inventoryList);
        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);
    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        log.info("Update Min Price");
        Map<LocalDate,BigDecimal> dailyMinPrice = inventoryList.stream()
                .collect(
                        Collectors.groupingBy(
                                Inventory::getDate,
                                Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                        )
                ).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));
        List<HotelMinPrice> hotelMinPriceList = new ArrayList<>();
        dailyMinPrice.forEach(
                (date,price)->{
                    HotelMinPrice hotelMinPrice = hotelMinPriceRepository.findByHotelAndDate(hotel,date)
                            .orElse(new HotelMinPrice(hotel,date));
                    hotelMinPrice.setMinPrice(price);
                    hotelMinPriceList.add(hotelMinPrice);
                }
        );
        hotelMinPriceRepository.saveAll(hotelMinPriceList);
    }

    private void updateInventoryPrice(List<Inventory> inventoryList){
        log.info("Update inventory");
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }


}
