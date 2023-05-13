package CreateUser1.demo.controller;

import CreateUser1.demo.utils.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {

    public static final String UPLOAD_DIR = "C:/Users/ADMIN/Downloads/demo/src/main/resources/static/images";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadUtil.saveFile(UPLOAD_DIR, fileName, file);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file!");
        }
        return "upload";
    }
}
