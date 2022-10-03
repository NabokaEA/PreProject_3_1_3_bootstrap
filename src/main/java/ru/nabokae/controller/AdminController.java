package ru.nabokae.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nabokae.entity.User;
import ru.nabokae.service.UserService;

import java.sql.Array;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String ListPage(Model model) {
        logger.info("Запрошен список пользьзователей");
        HashSet<GrantedAuthority> hashSet = new HashSet<GrantedAuthority>(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        String role = "";
        for (GrantedAuthority gauth : hashSet) {
            role = role + " " + gauth.getAuthority().substring(5);
        }
        model.addAttribute("usersAll", userService.findAllByOrderByIdAsc());
        model.addAttribute("authentication", role);

        return "BS_admin_page";
    }

    @GetMapping("/")
    public String ListPageHome(Model model) {
        logger.info("Запрошен корневой адрес");
        model.addAttribute("usersAll", userService.findAllByOrderByIdAsc());
        return "BS_admin_page";
    }
    @ResponseBody
    @GetMapping("/new")
    public String NewUserForm(Model model) {
        logger.info("Запрошена страница создания нового пользователя");
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping("/all")
    public String UpdateUser(User user) {
        logger.info("Запрошен обновленный список пользователей");
        userService.save(user);
        return "redirect:/admin/all";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public String EditUserForm(@PathVariable("id") Long id, Model model) {
        logger.info("Запрошена страница редактирования пользователя");
     model.addAttribute("user", String.valueOf(userService.findById(id)));
        return "User";
    }

    @GetMapping("/{id}/delete")
    public String EditUserForm(@PathVariable("id") Long id) {
        logger.info("Запрошена страница удаления пользователя c id={}", id);
        userService.delete(id);
        return "redirect:/admin/all";
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT,RequestMethod.GET})
    public String Update(User user) {
        logger.info("Запрошен новый апдейт метод");
        userService.save(user);
        return "redirect:/admin/all";
    }

}
