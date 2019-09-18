package wang.ismy.leyou.upload.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;

import java.io.File;
import java.io.IOException;

/**
 * @author MY
 * @date 2019/9/18 22:05
 */
@Service
@Slf4j
public class UploadService {


    public String uploadImage(MultipartFile file) {
        // 保存文件到本地
        File dest = new File("D:/upload",file.getOriginalFilename());

        try {
            file.transferTo(dest);

            // 返回路径
            return
        } catch (IOException e) {
            log.error("文件上传失败",e);
            throw new BusinessException(ExceptionEnum.UPLOAD_FAIL);
        }

    }
}
