package com.shang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzhixiong on 2018/4/16.
 */
@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/sendSimpleMail",method = RequestMethod.POST)
    public ResultMap sendSimpleMail(@RequestParam(value = "to") String to, @RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content){

//        restTemplate.getForEntity()

        HttpHeaders headers = new HttpHeaders();
//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
//  也支持中文
        params.add("to",to);
        params.add("subject", subject);
        params.add("content",content);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//  执行HTTP请求
        ResponseEntity<ResultMap> response = restTemplate.exchange("http://eureka-client/community-mail/sendSimpleMail", HttpMethod.POST, requestEntity, ResultMap.class);

        return response.getBody();
    }


}
