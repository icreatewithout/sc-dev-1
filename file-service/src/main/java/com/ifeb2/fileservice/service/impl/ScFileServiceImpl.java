package com.ifeb2.fileservice.service.impl;

import com.ifeb2.fileservice.domian.ScFile;
import com.ifeb2.fileservice.repository.ScFileRepository;
import com.ifeb2.fileservice.service.ScFileService;
import com.ifeb2.scdevbase.service.BaseService;
import com.ifeb2.scdevminio.minio.MinioProperties;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScFileServiceImpl extends BaseService<ScFile, ScFileRepository> implements ScFileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionDefinition transactionDefinition;

    @Override
    public void load(Long id, HttpServletResponse response) {
        ScFile file = super.getById(id);
        if (null != file) {
            if (StringUtils.isNoneBlank(file.getContentType())) {
                response.setContentType(file.getContentType());
            }
            out(file, response);
        }
    }

    @Override
    public Boolean exists(String fileName) {
        ScFile scFile = super.getOne(ScFile.builder().fileName(fileName).build());
        return scFile == null;
    }

    @Override
    public List<Long> upload(Long uid, String bizName, MultipartFile[] files) {
        List<Long> arrayList = new ArrayList<>();
        for (MultipartFile file : files) {
            arrayList.add(up(uid, bizName, file));
        }
        return arrayList;
    }

    @Override
    public Long upload(Long uid, String bizName, InputStream inputStream, String fileName, String contentType) {
        TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);
        Long id = null;
        try {
            String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String path = getPath(uid, bizName);
            id = saveFile(uid, bizName, path, name, suffix, contentType);
            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(path + id + suffix).stream(inputStream, inputStream.available(), -1).build());
            platformTransactionManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(status);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public Long upload(Long uid, String bizName, MultipartFile file) {
        return up(uid, bizName, file);
    }

    @Override
    public void download(Long id, HttpServletRequest request, HttpServletResponse response) {
        ScFile file = super.getById(id);
        if (null != file) {
            try {
                response.setContentType("application/*");
                String agent = request.getHeader("USER-AGENT");
                if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
                    response.addHeader("Content-Disposition", "attachment;filename=" + new String((file.getFileName() + file.getExtName()).getBytes("GB2312"), StandardCharsets.ISO_8859_1));
                } else {
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode((file.getFileName() + file.getExtName()), "UTF-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            out(file, response);
        }
    }

    private Long up(Long uid, String bizName, MultipartFile multipartFile) {
        TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);
        Long id = null;
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")).toLowerCase();
            String fileName = multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
            String contentType = multipartFile.getContentType();
            String path = getPath(uid, bizName);
            id = saveFile(uid, bizName, path, fileName, suffix, contentType);
            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(path + id + suffix).stream(inputStream, inputStream.available(), -1).build());
            inputStream.close();
            platformTransactionManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();

            platformTransactionManager.rollback(status);
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return id;
    }

    private Long saveFile(Long uid, String bizName, String path, String fileName, String suffix, String contentType) {
        return super.save(ScFile.builder().userId(uid).bizName(bizName).fileName(fileName).fPath(path).contentType(contentType).extName(suffix).bucket(minioProperties.getBucket()).build());
    }

    private void out(ScFile file, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(file.getBucket()).object(file.getFPath() + file.getId() + file.getExtName()).build());
            out = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param bizName
     * @return
     */
    private String getPath(Long uid, String bizName) {
        String path = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";

        if (uid != null) {
            path = path + uid + "/";
        }

        if (StringUtils.isNotBlank(bizName)) {
            path = path + bizName + "/";
        }
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).stream(new ByteArrayInputStream(new byte[]{}), 0, -1).build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

}
