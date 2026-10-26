package com.miguelsouza.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.miguelsouza.pedidos.client")
public class ClientsConfig {
}
