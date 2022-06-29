package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.dto.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
            // file name format: {date}_{fileOriginalName}.{file type}
            sb.append(date.getTime());
            sb.append("_");
            sb.append(fileName);
            images.add("/images/" + sb.toString());
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
