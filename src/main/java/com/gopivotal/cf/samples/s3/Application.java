package com.gopivotal.cf.samples.s3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories(basePackageClasses = {MongoS3FileRepository.class})
public class Application {

    Log log = LogFactory.getLog(Application.class);

    @Autowired
    MongoS3FileRepository repository;

    @Autowired
    S3Properties s3Properties;

    @Autowired
    S3 s3;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello Boot!");

        List<S3File> images = repository.findAll();
        model.addAttribute("images", images);

        return "index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable String id) {

        S3File s3File = repository.findOne(id);

        repository.delete(s3File);
        log.info(s3File.getId() + " deleted from Mongo.");
        s3.delete(s3File);
        log.info(s3File.getActualFileName() + " deleted from S3 bucket.");

        return "redirect:/";
    }

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        String id = UUID.randomUUID().toString();
        File uploadedFile = new File(file.getOriginalFilename());

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file!", e);
        }

        S3File s3File = new S3File(id, s3Properties.getBucket(), file.getOriginalFilename(), uploadedFile);

        s3.put(s3File);
        log.info(s3File.getName() + " put to S3.");
        repository.save(s3File);
        log.info(s3File.getName() + " record saved to Mongo.");
        uploadedFile.delete();
        log.info(s3File.getFile().getAbsolutePath() + " is deleted.");

        return "redirect:/";
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }
}
