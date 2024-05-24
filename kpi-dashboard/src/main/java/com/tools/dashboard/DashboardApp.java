package com.tools.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DashboardApp {
    public static void main(String[] args) {
		SpringApplication.run(DashboardApp.class, args);
        System.out.println("KPI Dashboard up and running");
    }
}
