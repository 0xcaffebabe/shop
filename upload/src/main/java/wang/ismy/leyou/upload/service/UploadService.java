package wang.ismy.leyou.upload.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.BusinessException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.io.FilenameUtils.getExtension;

/**
 * @author MY
 * @date 2019/9/18 22:05
 */
@Service
@Slf4j
public class UploadService {

    private static final List<String> ALLOW_TYPE = List.of("image/jpeg","image/png","image/gif","image/bmp");

    @Value("${image.adress}")
    private String imageAdress;

    private FastFileStorageClient storageClient;

    public UploadService(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    public String uploadImage(MultipartFile file) {

        try {
            // 校验文件类型
            if (!ALLOW_TYPE.contains(file.getContentType())){
                throw new BusinessException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            // 校验文件内容
            BufferedImage img = ImageIO.read(file.getInputStream());

            if (img == null){
                throw new BusinessException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            // 保存文件到DFS
            StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),getExtension(file.getOriginalFilename()), null);

            // 返回路径
            return imageAdress + storePath.getFullPath();
        } catch (IOException e) {
            log.error("文件上传失败",e);
            throw new BusinessException(ExceptionEnum.UPLOAD_FAIL);
        }

    }
}
