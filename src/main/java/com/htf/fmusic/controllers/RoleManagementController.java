package com.htf.fmusic.controllers;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.htf.fmusic.models.Role;
import com.htf.fmusic.services.RoleService;

/**
 * @author HTFeeds
 */
@Controller
@RequestMapping("/admin/role")
public class RoleManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleManagementController.class);

    private final RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    RoleManagementController(RoleService roleService) {
        LOGGER.info("Inside constructor of RoleManagementController.");
        this.roleService = roleService;
    }

    //-------------------Retrieve All Roles-----------------------------------------------------
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "admin/role/list";
    }

    //-------------------Retrieve Single Role---------------------------------------------------
    @RequestMapping(value = "/details-{id}", method = RequestMethod.GET)
    public String details(@PathVariable Integer id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return "admin/role/details";
    }

    //-------------------Create a Role----------------------------------------------------------
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String newRole(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "admin/role/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveRole(@Valid @ModelAttribute("role") Role role, BindingResult result, @RequestParam String save, Model model,
            RedirectAttributes redirectAttributes) {
        if (!roleService.isTypeUnique(role.getType())) {
            FieldError typeError = new FieldError("role", "type",
                    messageSource.getMessage("UniqueType.role.type", new String[] { role.getType() }, Locale.getDefault()));
            result.addError(typeError);
        }
        if (result.hasErrors()) {
            return "admin/role/create";
        }

        Role r = roleService.create(role);

        if (save.equals("Create and Edit")) {
            redirectAttributes.addFlashAttribute("success", "The new role has been created successfully.");
            return "redirect:edit-" + r.getId();
        }

        return "redirect:list";
    }

    //------------------- Update a Role --------------------------------------------------------
    @RequestMapping(value = "/edit-{id}", method = RequestMethod.GET)
    public String editRole(@PathVariable Integer id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return "admin/role/edit";
    }

    @RequestMapping(value = "/edit-{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute Role role, BindingResult result, @PathVariable Integer id, @RequestParam String save, Model model,
            RedirectAttributes redirectAttributes) {
        if (!roleService.isTypeUnique(role.getType())) {
            FieldError typeError = new FieldError("role", "type",
                    messageSource.getMessage("UniqueType.role.type", new String[] { role.getType() }, Locale.getDefault()));
            result.addError(typeError);
        }
        if (result.hasErrors()) {
            return "admin/role/create";
        }

        roleService.update(role);

        if (save.equals("Save and Continue")) {
            redirectAttributes.addFlashAttribute("success", "This role has been changed successfully.");
            return "redirect:edit-" + role.getId();
        }

        return "redirect:list";
    }

    //------------------- Delete a Role --------------------------------------------------------
    @RequestMapping(value = "/delete-{id}", method = RequestMethod.GET)
    public String deleteRole(@PathVariable Integer id, Model model) {
        roleService.delete(id);
        return "redirect:list";
    }
}
