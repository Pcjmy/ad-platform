package com.adplatform.adsponsor;

import com.adplatform.common.dump.DConstant;
import com.adplatform.adsponsor.dump.DumpDataService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 全量数据导出应用（独立于主服务，放在 test 目录下）。
 * 启动后连接数据库，将索引相关的表数据导出到本地文件，
 * 供 ad-search 加载全量索引使用。
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.adplatform.adsponsor.mapper")
public class Application implements CommandLineRunner {

    @Autowired
    private DumpDataService dumpDataService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("start dump data to {}", DConstant.DATA_ROOT_DIR);
        dumpDataService.dumpAll(DConstant.DATA_ROOT_DIR);
        log.info("dump data finished");
    }
}
