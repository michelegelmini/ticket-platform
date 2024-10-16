package org.lessons.java.ticket_platform.controller;

import java.util.List;

import org.lessons.java.ticket_platform.model.Category;

import org.lessons.java.ticket_platform.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService cService;

	// Index
	@GetMapping
	public String index(Model model, @RequestParam(name = "name", required = false) String name) {

		model.addAttribute("categoryName", name);

		List<Category> categoryList;

		if (name != null && !name.isEmpty()) {
			model.addAttribute("ticketTitle", name);
			categoryList = cService.findByName(name);

		} else {
			// prendo i dati da consegnare a tickets
			categoryList = cService.findAllSortedById();
		}

		// li inserisco nel modello
		model.addAttribute("categories", categoryList);
		
		
		

		return "/categories/index";
	}


	// Create
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("category", new Category());
		return "/categories/create";
	}

	// Store
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Category formCategory, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("ingredients", cService.findAllSortedById());
			return "/tickets/create";
		}
		cService.create(formCategory);
		attributes.addFlashAttribute("successMessage", formCategory.getName() + " has been created!");

		return "redirect:/categories";
	}

	// edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("category", cService.findById(id));

		// restituisco la view con il model inserito
		return "categories/edit";
	}

	// update
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("category") Category updatedFormCategory, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {

		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAllSortedById());
			return "/tickets/edit";
		}
		
		cService.update(updatedFormCategory);

		attributes.addFlashAttribute("successMessage", "Category with id " + updatedFormCategory.getId() + ": "
				+ updatedFormCategory.getName() + ", has been UPDATED!");

		return "redirect:/categories";
	}

	// delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {

		Category categoryToDelete = cService.findById(id);
		if(categoryToDelete.getTickets().size() > 0) {
			attributes.addFlashAttribute("deletedMessage",
					"Can't delete a category with tickets associated!");

			return "redirect:/categories";
		}
		cService.delete(id);

		attributes.addFlashAttribute("deletedMessage",
				"Category with id " + id + ": " + categoryToDelete.getName() + ", has been DELETED!");

		return "redirect:/categories";
	}

}
