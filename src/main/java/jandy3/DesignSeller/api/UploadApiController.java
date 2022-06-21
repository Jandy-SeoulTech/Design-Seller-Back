package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.dto.ImageResponse;
import jandy3.DesignSeller.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = {"파일 api"})
@RestController
public class UploadApiController {



    @PostMapping(value = "/uploads")
    public ImageResponse upload(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestParam("uploadfile") MultipartFile[] uploadfile
    ) {
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
            sb.append("." + ext);
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
