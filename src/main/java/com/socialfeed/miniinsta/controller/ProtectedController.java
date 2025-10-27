package com.socialfeed.miniinsta.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProtectedController {

    // ✅ Requires login
    @GetMapping("/profile")
    public String profile() {
        return "This is your profile data ✅";
    }

    // ✅ Requires ROLE_ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Welcome Admin 🚀";
    }
}
