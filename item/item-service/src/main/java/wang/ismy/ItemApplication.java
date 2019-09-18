package wang.ismy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author MY
 * @date 2019/9/17 17:19
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "wang.ismy.item.mapper")
public class ItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);

    }
}
