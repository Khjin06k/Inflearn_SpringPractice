package Infrean.Practice1.Hello.World.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // "/"는 들어가자마자 아래것이 실행
    public String home() {
        return "home"; //@Controller로 스프링 컨테이너에 등록이 되어있. 스프링 컨테이너가 비어있지 않고 매핑되는 것이 있어서
        // static까지 가지 않아 현재 static 패키지에 있는 것이 실행되지 않고 home이 실행되고 종료되는 것
    }
}
