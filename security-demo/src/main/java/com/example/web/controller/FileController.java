package com.example.web.controller;

import com.example.dto.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author： ygl
 * @date： 2018/2/11-14:21
 * @Description：
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {


    private  String folder = "/Volumes/WORK/java/ideaPojects/springsecurity/security-demo/src/main/java/com/example/web/controller";


    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {

        log.info(file.getName());
        log.info(file.getOriginalFilename());
        log.info(file.getSize()+"");

        File localFile  = new File(folder, System.currentTimeMillis()+".txt");

        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());

    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception{

        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {
           response.setContentType("application/x-download");
           response.addHeader("Content-Disposition","attachment;filename=test.txt");

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        }
    }
}
