package com.soumenprogramming.byte2byte.learn.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "forward:/dashboard/dashboard.html";
    }


}
