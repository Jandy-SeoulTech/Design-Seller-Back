package jandy3.DesignSeller.controller;

import jandy3.DesignSeller.config.auth.PrincipalDetails;
import jandy3.DesignSeller.config.oauth.annotation.CurrentUser;
import jandy3.DesignSeller.dto.ImageResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UploadController {
    @PostMapping(value = "/uploads")
    public ImageResponse upload(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestParam("uploadfile") MultipartFile[] uploadfile
    ){
        List<String> images = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            Date date = new Date();
            StringBuilder sb = new StringBuilder();
            String fileName = file.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            // file name format: {date}_{provider}_{providerId}.{file type}
            sb.append(date.getTime());
            sb.append("_");
            sb.append(principalDetails.getUsername());
            sb.append("."+ext);
            images.add(sb.toString());
            File newFileName = new File(sb.toString());
            try {
                file.transferTo(newFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ImageResponse(images);
    }

}
