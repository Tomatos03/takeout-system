package com.takeout.controller.amdin;

import com.takeout.service.ChartService;
import com.takeout.util.Result;
import com.takeout.vo.OrderReportVO;
import com.takeout.vo.SalesTop10ReportVO;
import com.takeout.vo.TurnoverVO;
import com.takeout.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@RequestMapping("/admin/report")
@RestController
public class ChartController {
    @Autowired
    private ChartService chartService;

    @GetMapping("/turnoverStatistics")
    public Result<TurnoverVO> turnoverChart(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        TurnoverVO turnoverVO = chartService.turnoverChart(begin, end);
        return Result.success(turnoverVO);
    }

    @GetMapping("/userStatistics")
    public Result<UserReportVO> userReportChart(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        UserReportVO userReportVO = chartService.userReportChart(begin, end);
        return Result.success(userReportVO);
    }

    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderReportChart(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        OrderReportVO orderReportVO = chartService.orderReportChart(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 销量排名统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        return Result.success(chartService.getSalesTop10(begin,end));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        chartService.exportDataToExcel(response);
    }
}