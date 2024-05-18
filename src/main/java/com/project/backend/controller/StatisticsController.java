package com.project.backend.controller;

import com.project.backend.dto.StatisticsDto;
import com.project.backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(path="/statistics")
public class StatisticsController {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public StatisticsController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping("/getTopTenByCategoryWithinDays")
    public List<StatisticsDto> getTopTenByCategoryWithinDays(@RequestParam("category") String category, @RequestParam("days") Integer days) {
        Calendar calendar = Calendar.getInstance(); // now
        calendar.add(Calendar.DATE, -days); // get the earliest date
        Date earliestDate = calendar.getTime();

        // CASE 1: category is not provided
        if (category.length() == 0)
        {
            // get max 10 results using pagination from highest order quantity to lowest
            return orderItemRepository.findAllStatisticsGroupByProductOrderByQtyDescAfterDate(earliestDate, PageRequest.of(0, 10));
        }
        // CASE 2: category is selected
        else
        {
            // get max 10 results under provided category
            return orderItemRepository.findAllStatisticsGroupByProductByCategoryOrderByQtyDescAfterDate(category, earliestDate, PageRequest.of(0, 10));
        }
    }
}
