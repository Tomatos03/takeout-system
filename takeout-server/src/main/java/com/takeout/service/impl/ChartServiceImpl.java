package com.takeout.service.impl;

import cn.hutool.core.util.StrUtil;
import com.takeout.constant.OrderConst;
import com.takeout.mapper.OrderMapper;
import com.takeout.mapper.UserMapper;
import com.takeout.service.ChartService;
import com.takeout.service.WorkspaceService;
import com.takeout.vo.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Tomatos
 * @date : 2025/7/24
 */
@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkspaceService workspaceService;

    @Override
    public TurnoverVO turnoverChart(LocalDate begin, LocalDate end) {
        List<LocalDate> timeInterval = getTimeInterval(begin, end);
        List<Double> turnoverList = getTurnoverInterval(timeInterval);
        return TurnoverVO.builder()
                         .dateList(StrUtil.join(".", timeInterval))
                         .turnoverList(StrUtil.join(".", turnoverList))
                         .build();
    }

    @Override
    public UserReportVO userReportChart(LocalDate begin, LocalDate end) {
        List<LocalDate> timeInterval = getTimeInterval(begin, end);

        List<Integer> totalUser = new ArrayList<>();
        List<Integer> newUer = new ArrayList<>();

        timeInterval.forEach(current -> {
            LocalDateTime beginTime = LocalDateTime.of(current, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(current, LocalTime.MAX);
            // 获取直到当天为止的用户总数
            totalUser.add(getUserCount(null, endTime));
            // 获取某个时间段内的用户总数
            newUer.add(getUserCount(beginTime, endTime));
        });

        return UserReportVO.builder()
                           .dateList(StrUtil.join(",", timeInterval))
                           .totalUserList(StrUtil.join(",", timeInterval))
                           .newUserList(StrUtil.join(",", timeInterval))
                           .build();
    }

    @Override
    public OrderReportVO orderReportChart(LocalDate begin, LocalDate end) {
        List<LocalDate> timeInterval = getTimeInterval(begin, end);
        // 每天订单总数集合
        ArrayList<Integer> orderCountList = new ArrayList<>();
        // 媒体有效订单数集合
        ArrayList<Integer> validOrderCountList = new ArrayList<>();

        timeInterval.forEach(current -> {
            // 查询每天总数
            Integer orderCount = getOrderCount(current, null);
            // 查询每天有效订单数
            Integer validOrderCount = getOrderCount(current, OrderConst.COMPLETED);

            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        });

        // 时间区间内的总订单数
        Integer totalOrderCount = orderCountList.stream()
                                                .reduce(0, Integer::sum);
        // 区间时间内有效订单数(订单状态为完成的订单)
        Integer validOrderCount = validOrderCountList.stream()
                                                     .reduce(0, Integer::sum);
        // 订单完成率 = 有效订单率 / 总订单率
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        return OrderReportVO.builder()
                            .dateList(StrUtil.join(",", timeInterval))
                            .orderCountList(StrUtil.join(",", orderCountList))
                            .validOrderCountList(StrUtil.join(",", validOrderCountList))
                            .totalOrderCount(totalOrderCount)
                            .validOrderCount(validOrderCount)
                            .orderCompletionRate(orderCompletionRate)
                            .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);

        List<String> nameList = goodsSalesDTOList.stream()
                                                 .map(GoodsSalesDTO::getName)
                                                 .collect(Collectors.toList());

        List<Integer> numberList = goodsSalesDTOList.stream()
                                                    .map(GoodsSalesDTO::getNumber)
                                                    .collect(Collectors.toList());

        String nameListStr = StrUtil.join(",", nameList);
        String numberListStr = StrUtil.join(",", numberList);
        return SalesTop10ReportVO.builder()
                                 .nameList(nameListStr)
                                 .numberList(numberListStr)
                                 .build();
    }

    @Override
    public void exportDataToExcel(HttpServletResponse response) {

        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);

        //查询概览运营数据，提供给Excel模板文件
        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        InputStream inputStream = this.getClass()
                                      .getClassLoader()
                                      .getResourceAsStream("template/template.xlsx");
        try {
            //基于提供好的模板文件创建一个新的Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
            //获得第4行
            XSSFRow row = sheet.getRow(3);
            //获取单元格
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());
            for (int i = 0; i < 30; i++) {
                LocalDate date = begin.plusDays(i);
                //准备明细数据
                businessData = workspaceService.getBusinessData(LocalDateTime.of(date,LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }
            //通过输出流将文件下载到客户端浏览器中
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //关闭资源
            out.flush();
            out.close();
            excel.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Double> getTurnoverInterval(List<LocalDate> timeInterval) {
        int size = timeInterval.size();
        ArrayList<Double> turnoverList = new ArrayList<>(size);
        turnoverList.ensureCapacity(size);

        timeInterval.forEach(currentLocalDate -> {
            Double turnover = queryTurnover(currentLocalDate);
            turnover = turnover == null ? 0.0 : turnover;

            turnoverList.add(turnover);
        });
        return turnoverList;
    }

    private List<LocalDate> getTimeInterval(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }

    private Integer getOrderCount(LocalDate current, Integer status) {
        LocalDateTime[] dayInterval = getDayInterval(current);

        Map<String, Object> map = new HashMap();
        map.put("status", status);
        map.put("begin", dayInterval[0]);
        map.put("end", dayInterval[1]);
        return orderMapper.queryOrderNumByMap(map);
    }

    /**
     * 返回指定日期的起止时间区间
     *
     * @return 长度为2的数组，arr[0]为开始时间，arr[1]为结束时间
     * @author : Tomatos
     * @date : 2025/7/24 15:13
     * @since : 1.0
     */
    private LocalDateTime[] getDayInterval(LocalDate currentDay) {
        LocalDateTime begin = LocalDateTime.of(currentDay, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(currentDay, LocalTime.MAX);
        return new LocalDateTime[]{begin, end};
    }

    /**
     * 统计某天的营业额
     *
     * @param currentDay
     * @return java.lang.Double
     * @author : Tomatos
     * @date : 2025/7/24 15:23
     * @since : 1.0
     */
    private Double queryTurnover(LocalDate currentDay) {
        LocalDateTime[] dayInterval = getDayInterval(currentDay);

        Map<String, Object> map = new HashMap<>();
        map.put("status", OrderConst.COMPLETED);
        map.put("begin", dayInterval[0]);
        map.put("end", dayInterval[1]);
        return orderMapper.queryTurnoverByMap(map);
    }

    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        Map<String, Object> map = new HashMap();
        map.put("begin", beginTime);
        map.put("end", endTime);
        return userMapper.countByMap(map);
    }
}
