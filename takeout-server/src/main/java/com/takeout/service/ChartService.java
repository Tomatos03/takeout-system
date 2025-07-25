package com.takeout.service;

import com.takeout.vo.OrderReportVO;
import com.takeout.vo.SalesTop10ReportVO;
import com.takeout.vo.TurnoverVO;
import com.takeout.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
public interface ChartService {
    TurnoverVO turnoverChart(LocalDate begin, LocalDate end);

    UserReportVO userReportChart(LocalDate begin, LocalDate end);

    OrderReportVO orderReportChart(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    void exportDataToExcel(HttpServletResponse response);
}
