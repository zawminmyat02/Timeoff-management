package yu.cs.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import yu.cs.spring.model.master.service.AccountService;

@Controller
public class ChangePasswordController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @AuthenticationPrincipal User user, Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "change-password";
        }

        accountService.changePassword(user.getUsername(), newPassword);
        return "redirect:/home";
    }
}
