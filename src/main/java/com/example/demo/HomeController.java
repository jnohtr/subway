package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    FoodRepository foodRepository;

    @RequestMapping("/")
    public String listFoods(Model model) {
        model.addAttribute("foods", foodRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String foodForm(Model model) {
        model.addAttribute("food", new Food());
        return "foodform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Food food, BindingResult result) {
        if (result.hasErrors()) {
            return "foodform";
        }
        foodRepository.save(food);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showFood(@PathVariable("id") long id, Model model) {
        model.addAttribute("food", foodRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateFood(@PathVariable("id") long id, Model model) {
        model.addAttribute("food", foodRepository.findById(id));
        return "foodform";
    }

    @RequestMapping("/delete/{id}")
    public String delFood(@PathVariable("id") long id) {
        foodRepository.deleteById(id);
        return "redirect:/";
    }

}
