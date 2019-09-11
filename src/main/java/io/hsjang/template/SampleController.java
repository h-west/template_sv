package io.hsjang.template;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping
    public Mono<Map<String,Object>> test(){
        Map<String,Object> test = new HashMap<String,Object>();
        test.put("k", "v");
        return Mono.just(test);
    }
}