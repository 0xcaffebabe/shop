package wang.ismy.leyou.upload.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.leyou.upload.service.UploadService;

/**
 * @author MY
 * @date 2019/9/18 22:03
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    private UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url = uploadService.uploadImage(file);

        return ResponseEntity.ok(url);
    }
}
