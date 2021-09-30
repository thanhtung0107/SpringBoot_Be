package com.laptrinhjavaweb;

import com.laptrinhjavaweb.entity.ServiceEntity;
import com.laptrinhjavaweb.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;



@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application implements CommandLineRunner {

    @Autowired
    private ServiceRepository serviceRepository;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(serviceRepository.count() == 0){
            ServiceEntity serviceEntity = new ServiceEntity();
            serviceEntity.setName("Tẩy ố");
            serviceEntity.setPrice(50000);
            serviceRepository.save(serviceEntity);

            ServiceEntity serviceEntity2 = new ServiceEntity();
            serviceEntity2.setName("Xịt nano");
            serviceEntity2.setPrice(60000);
            serviceRepository.save(serviceEntity2);

            ServiceEntity serviceEntity3 = new ServiceEntity();
            serviceEntity3.setName("Khâu/vá/filter giày");
            serviceEntity3.setPrice(70000);
            serviceRepository.save(serviceEntity3);

            ServiceEntity serviceEntity4 = new ServiceEntity();
            serviceEntity4.setName("Dán đế bảo vệ");
            serviceEntity4.setPrice(80000);
            serviceRepository.save(serviceEntity4);

            ServiceEntity serviceEntity5= new ServiceEntity();
            serviceEntity5.setName("Tẩy mốc");
            serviceEntity5.setPrice(90000);
            serviceRepository.save(serviceEntity5);

            ServiceEntity serviceEntity6 = new ServiceEntity();
            serviceEntity6.setName("Repaint thân giày");
            serviceEntity6.setPrice(100000);
            serviceRepository.save(serviceEntity6);

            ServiceEntity serviceEntity7 = new ServiceEntity();
            serviceEntity7.setName("Chăm sóc da");
            serviceEntity7.setPrice(110000);
            serviceRepository.save(serviceEntity7);

            ServiceEntity serviceEntity8 = new ServiceEntity();
            serviceEntity8.setName("Deep Clean");
            serviceEntity8.setPrice(120000);
            serviceRepository.save(serviceEntity8);

            ServiceEntity serviceEntity9 = new ServiceEntity();
            serviceEntity9.setName("Repaint Đế Boost");
            serviceEntity9.setPrice(50000);
            serviceRepository.save(serviceEntity9);

            System.out.println("Success importing data service type");
        }
    }
}
