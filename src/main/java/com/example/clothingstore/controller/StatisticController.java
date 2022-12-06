package com.example.clothingstore.controller;


import com.example.clothingstore.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/statistic")
public class StatisticController {
    @Autowired
    OrderServiceImpl orderService;
    @GetMapping("/year")
    public String getStatisticByYear(){
        String res = orderService.getAllOrderByYear();
        if (res == null)
        {
            return "0";
        }
        return res;
    }

    @GetMapping("/month")
    public String getStatisticByMonth(){
        String res = orderService.getAllOrderByMonth();
        if (res == null)
        {
            return "Turnover: 0";
        }
        return res;
    }

    @GetMapping("/day")
    public String getStatisticByDay(){
        String res = orderService.getAllOrderByDay();
        if (res == null)
        {
            return "0";
        }
        return res;
    }

    @GetMapping("/chart")
    public Map<String, String> getStatisticByDate(){
        Date date = new Date(); // current date
        Map<String, String> monthly = new HashMap<>(); // map return
        for (int i =0; i< 10; i++)
        {
            Date yesterday = new Date(date.getTime() - (1000*60*60*24*i));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yesterday);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) +1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (orderService.getAllOrderByDate(year, month, day) == null)
                monthly.put(day+"/"+month, "0");
            monthly.put(day+"/"+month, orderService.getAllOrderByDate(year, month, day));
        }
        return monthly;
    }

}
