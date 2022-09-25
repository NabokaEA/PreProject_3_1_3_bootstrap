package ru.nabokae.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nabokae.entity.User;
import ru.nabokae.service.UserService;

import java.security.Principal;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public String ListPage(Model model) {
        logger.info("Запрошен список пользьзователей");
        //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("usersAll", userService.findAll());
       // model.addAttribute("Auth", authentication);
       // Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
       // model.addAttribute("roles", roles.stream().findFirst().toString());
        return "users";
    }

    @GetMapping("/Userhome")
    public String UserPage(Model model) {
        logger.info("Запрошена страница юзера");
        return "Userhome";
    }

    @GetMapping("/")
    public String ListPageHome(Model model) {
        logger.info("Запрошен корневой адрес");
        model.addAttribute("usersAll", userService.findAll());
        return "users";
    }

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

    @GetMapping("/{id}")
    public String EditUserForm(@PathVariable("id") Long id, Model model) {
        logger.info("Запрошена страница редактирования пользователя");
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @GetMapping("/{id}/delete")
    public String EditUserForm(@PathVariable("id") Long id) {
        logger.info("Запрошена страница удаления пользователя c id={}", id);
        userService.delete(id);
        return "redirect:/admin/all";
    }

}
