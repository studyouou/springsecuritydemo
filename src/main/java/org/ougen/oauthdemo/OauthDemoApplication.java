package org.ougen.oauthdemo;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.ougen.oauthdemo.domain.MySms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@Controller
public class OauthDemoApplication {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public static void main(String[] args) {
        SpringApplication.run(OauthDemoApplication.class, args);
    }

    @RequestMapping("/oauth/redirect")
    @ResponseBody
    public String redirct(@RequestParam("code")String code) throws IOException {
        System.out.println(code);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://github.com/login/oauth/access_token?client_id=606cb86eadc1f9e9a15c" +
                "&client_secret=aeffc42c8c2fba09228f77ed6a2039463de5b9ba&code="+code+"");
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        StringBuilder stringBuilder = new StringBuilder();
        int len=-1;
        byte[] buf = new byte[1024];
        while ((len = content.read(buf))>0) {
            stringBuilder.append(new String(buf,0,len));
        }
        HttpGet get = new HttpGet("https://api.github.com/user");
        get.setHeader("accept","application/json");
        get.setHeader("Authorization","token "+stringBuilder.toString().substring(stringBuilder.toString().indexOf("=")+1,stringBuilder.toString().indexOf("&")));
        CloseableHttpResponse execute = httpClient.execute(get);
        InputStream content1 = execute.getEntity().getContent();
        StringBuilder stringBuilder1 = new StringBuilder();
        while ((len = content1.read(buf))>0) {
            stringBuilder1.append(new String(buf,0,len));
        }
        content.close();
        content1.close();
        return stringBuilder1.toString();
    }

    @RequestMapping("/tologin")
    public String toLogin() {
        return "/welcome.html";
    }

    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/code/sms")
    @ResponseBody
    public void sms(HttpServletRequest request, HttpServletResponse response, String mobile) {
        MySms sms = createMySms();
        sessionStrategy.setAttribute(new ServletWebRequest(request),"SESSION_KEY_SMS_CODE"+mobile,sms);
        System.out.println("您的登录验证码是:"+sms.getCode()+"  有效时间为:"+sms.getExpireTime());
    }

    private MySms createMySms() {
        String code = RandomStringUtils.randomNumeric(6);
        return new MySms(code,60);
    }

}
