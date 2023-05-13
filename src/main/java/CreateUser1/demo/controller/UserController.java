package CreateUser1.demo.controller;

import CreateUser1.demo.entity.User;
import CreateUser1.demo.service.UserService;
import CreateUser1.demo.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    public static final String UPLOAD_DIR = "C:/Users/ADMIN/Downloads/demo/src/main/resources/static/images";

    @Autowired
    public UserService userService;
    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("listUsers", userService.getAllListUser());
        return "index";
    }
    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model){
        User user= new User();
        model.addAttribute("user",user);
        return "new_user";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "image", required = false) MultipartFile file, Model model) {
       if(file != null && !file.isEmpty()) {
           try {
               String fileName = StringUtils.cleanPath(file.getOriginalFilename());
               user.setAvatar(file.getOriginalFilename());
               FileUploadUtil.saveFile(UPLOAD_DIR, fileName, file);
               model.addAttribute("message", "File uploaded successfully!");
           } catch (IOException e) {
               e.printStackTrace();
               model.addAttribute("message", "Failed to upload file!");
           }
       } else {
           user.setAvatar(null);
       }
        userService.saveUser(user);
        return "redirect:/";
    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
        User user = userService.getUserById((int) id);
        model.addAttribute("user", user);
        return "update_user";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable (value = "id") long id) {
        this.userService.deleteUser(id);
        return "redirect:/";
    }
    @GetMapping("/users/search")
    public String searchUserByName(@RequestParam("name") String name,Model model){
        Optional<User> user=userService.searchUserByName(name);
        if(user!=null){
            model.addAttribute("user",user);
            return "index";
        } else {
            model.addAttribute("error", "User not found");
            return "index";
        }
    }

}