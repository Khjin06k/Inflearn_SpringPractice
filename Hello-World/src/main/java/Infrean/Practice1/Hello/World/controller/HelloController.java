package Infrean.Practice1.Hello.World.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("hell-mvc") //hell-mvx URL에 매칭이 됨
    public String helloMvc(@RequestParam(name = "name", required = true) String name, Model model){ //required 값이 기본 true이기 때문에 생략이 가능함.
        model.addAttribute("name", name);
        return "hello-template";
        //위의 설명 참고하기
        //hello와 달리 @RequestParam으로 받아 name을 받음. 이는 'localhost:8080/hello-mvc?name=입력값'으로 주었을 때 hello 입력값 이 출력됨.
        // hello + ${name} 이라고 hello-template.html 파일에 코드를 짜 놓았기에 위에처럼 name 부분의 변경이 가능함
    }
}
