package com.takeout.context;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
public class EmployeeContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentEmpId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentEmpId() {
        return threadLocal.get();
    }

    public static void removeCurrentEmpId() {
        threadLocal.remove();
    }
}
