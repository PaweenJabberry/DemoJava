package com.example.demo.controller;

import com.example.demo.model.UserData;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class DemoController {

    @Autowired
    private UserRepository userRepository;

    // Home Page http://localhost:9090/
    @RequestMapping("/")
    public String index() {
        return "web/index";
    }

    // Home Page http://localhost:9090/
    @RequestMapping("/home")
    public String home() {
        return "web/home";
    }

    // Adding Form Page http://localhost:9090/addingForm
    @RequestMapping("/addingForm")
    public String add() {
        return "web/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("fname") String firstName, @RequestParam("lname") String lastName, @RequestParam("age") int age) {
        userRepository.save(new UserData(email, password, firstName, lastName, age));
        return "redirect:/show";
    }

    // Show users http://localhost:9090/show
    @RequestMapping("/show")
    public String show(Model model) {
        try {
            List<UserData> current = (List<UserData>) userRepository.findAll();
            model.addAttribute("data", current);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "web/show";
    }

    /* Receive User's Id for Delete */
    @RequestMapping(value = "/delete", method=RequestMethod.POST)
    public String deleteItems(@RequestParam("deleteId") long deleteId) {
        System.out.println("Delete Id is "+deleteId);
        userRepository.deleteById(deleteId);
        return "redirect:/show";
    }

    /* Edit target User */
    @RequestMapping(value = "/edit/{id}", method=RequestMethod.GET)
    public ModelAndView display(@PathVariable("id") long id) {

        System.out.println("Edit Id is "+id);
        Optional targetUser = userRepository.findById(id);
        ModelAndView mv = new ModelAndView();

        mv.setViewName("web/edit");
        mv.addObject("user", targetUser.get());

        return mv;
    }

    /* Update data */
    @RequestMapping(value = "/update", method=RequestMethod.POST)
    public String update(@RequestParam("userId") String userId, @RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword, @RequestParam("userFirstName") String userFirstName, @RequestParam("userLastName") String userLastName, @RequestParam("userAge") int userAge) {
        Optional targetUser = userRepository.findById(Long.parseLong(userId));
        UserData currentUser = (UserData) targetUser.get();
        currentUser.setEmail(userEmail);
        currentUser.setPassword(userPassword);
        currentUser.setFirstName(userFirstName);
        currentUser.setLastName(userLastName);
        currentUser.setAge(userAge);
        userRepository.save(currentUser);

        return "redirect:/show";
    }

}
