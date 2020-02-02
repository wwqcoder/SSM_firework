package cn.wwq.collector.web;

import cn.wwq.collector.util.InputMDC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {

    /**
     * [%d{yyyy-MM-dd'T'HH:mm:ss.SSSZZ}]
     * [%level{length=5}]
     * [%thread-%tid]
     * [%logger]
     * [%X{hostName}]
     * [%X{ip}]
     * [%X{applicationName}]
     * [%F,%L,%C,%M]
     * [%m] ## '%ex'%n
     * -----------------------------------------------
     * [2019-09-18T14:42:51.451+08:00]
     * [INFO]
     * [main-1]
     * [org.springframework.boot.web.embedded.tomcat.TomcatWebServer]
     * []
     * []
     * []
     * [TomcatWebServer.java,90,org.springframework.boot.web.embedded.tomcat.TomcatWebServer,initialize]
     * [Tomcat initialized with port(s): 8001 (http)] ## ''
     *
     * ["message",
     * "\[%{NOTSPACE:currentDateTime}\]
     *  \[%{NOTSPACE:level}\]
     *  \[%{NOTSPACE:thread-id}\]
     *  \[%{NOTSPACE:class}\]
     *  \[%{DATA:hostName}\]
     *  \[%{DATA:ip}\]
     *  \[%{DATA:applicationName}\]
     *  \[%{DATA:location}\]
     *  \[%{DATA:messageInfo}\]
     *  ## (\'\'|%{QUOTEDSTRING:throwable})"]
     * @return
     */
    @GetMapping("/index")
    public String index(){

        InputMDC.putMDC();
        log.info("info日志");
        log.warn("warn日志");
        log.error("error日志");

        return "idx";
    }

    @GetMapping(value = "/err")
    public String err() {
        InputMDC.putMDC();
        try {
            int a = 1/0;
        } catch (Exception e) {
            log.error("算术异常", e);
        }
        return "err";
    }

}
