package com.longkubi.qlns;

import com.longkubi.qlns.config.FileStorageProperties;
import com.longkubi.qlns.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
//@EnableCaching
public class QlnsApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    public static void main(String[] args) {
        SpringApplication.run(QlnsApplication.class, args);

       // User user = new User();
    }

}
