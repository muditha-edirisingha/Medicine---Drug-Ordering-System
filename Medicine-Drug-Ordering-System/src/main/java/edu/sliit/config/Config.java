package edu.sliit.config;

import edu.sliit.dto.OrderItem;
import edu.sliit.entity.OrderItemEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class Config {
    @Bean
    public ModelMapper getMapper(){

        return new ModelMapper();
    }

}
