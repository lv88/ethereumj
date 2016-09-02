package com.akcome.ethereum.starter;

import com.akcome.ethereum.starter.ethereum.EthereumBean;
import org.ethereum.config.CommonConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.Executors;

@Configuration
//@ComponentScan(basePackages = {"com.ethercamp"},excludeFilters = @ComponentScan.Filter(NoAutoscan.class))
@Import(CommonConfig.class)
//@AutoConfigureAfter(CommonConfig.class)
public class Config {

    @Bean
    EthereumBean ethereumBean() throws Exception {
        EthereumBean ethereumBean = new EthereumBean();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while(true){
//                        ethereumBean.start();
//                    }
//                } catch (Exception e) {
//                    logger.error("Error generating tx: ", e);
//                }
//            }
//        }).start();
        Executors.newSingleThreadExecutor().
                submit(ethereumBean::start);
//        ethereumBean.start();
        return ethereumBean;
    }
}
