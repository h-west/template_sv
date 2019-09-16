package io.hsjang.template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.template.common.HMap;
import io.hsjang.template.common.ResponseCode;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/sample")
    public Flux<HMap> test(){
        return Flux.just(HMap.of("1","2"));
    }

    @GetMapping("/ex")
    public Flux<HMap> testEx(){
        ResponseCode.OK.ex();
        return Flux.just(HMap.of("1","2"));
    }
}