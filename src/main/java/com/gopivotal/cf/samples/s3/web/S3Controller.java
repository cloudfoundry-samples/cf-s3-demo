package com.gopivotal.cf.samples.s3.web;

import com.gopivotal.cf.samples.s3.data.S3FileRepository;
import com.gopivotal.cf.samples.s3.repository.S3;
import com.gopivotal.cf.samples.s3.repository.S3File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Controller
public class S3Controller {

    Log log = LogFactory.getLog(S3Controller.class);

    @Autowired
    S3FileRepository repository;

    @Autowired
    S3 s3;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", "Hello Boot!");

        Iterable<S3File> images = repository.findAll();
        model.addAttribute("images", images);

        return "index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable String id) {

        S3File s3File = repository.findOne(id);

        s3.delete(s3File);
        log.info(s3File.getActualFileName() + " deleted from S3 bucket.");
        repository.delete(s3File);
        log.info(s3File.getId() + " deleted from MySQL.");

        return "redirect:/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        String id = UUID.randomUUID().toString();
        File uploadedFile = new File(file.getOriginalFilename());

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file.", e);
        }

        S3File s3File = s3.createS3FileObject(id, file.getOriginalFilename(), uploadedFile);

        try {
            URL url = s3.put(s3File);
            s3File.setUrl(url);
            log.info(s3File.getName() + " put to S3.");
            repository.save(s3File);
            log.info(s3File.getName() + " record saved to MySQL.");
        } catch (MalformedURLException e){
            throw new RuntimeException("Failed saving file to backend.", e);
        }

        uploadedFile.delete();
        log.info(s3File.getFile().getAbsolutePath() + " temporary file is deleted.");

        return "redirect:/";
    }
}
