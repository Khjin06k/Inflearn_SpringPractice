package Infrean.Practice1.Hello.World.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello") //hello URL에 매칭이 됨
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
        //resources에 있는 templates의 hello 이름과 동일한 것을 return
        //해당 파일을 찾아가서 렌더링 하라는 것
        //즉, 컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버가 화면을 찾아서 처리함
    }
}
