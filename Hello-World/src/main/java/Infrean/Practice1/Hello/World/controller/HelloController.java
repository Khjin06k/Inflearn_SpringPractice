package Infrean.Practice1.Hello.World.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    //(1)
    @GetMapping("hello") //hello URL에 매칭이 됨
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
        //resources에 있는 templates의 hello 이름과 동일한 것을 return
        //해당 파일을 찾아가서 렌더링 하라는 것
        //즉, 컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버가 화면을 찾아서 처리함
    }

    //(2)
    @GetMapping("hell-mvc") //hell-mvx URL에 매칭이 됨
    public String helloMvc(@RequestParam(name = "name", required = true) String name, Model model){ //required 값이 기본 true이기 때문에 생략이 가능함.
        model.addAttribute("name", name);
        return "hello-template";
        //위의 설명 참고하기
        //hello와 달리 @RequestParam으로 받아 name을 받음. 이는 'localhost:8080/hello-mvc?name=입력값'으로 주었을 때 hello 입력값 이 출력됨.
        // hello + ${name} 이라고 hello-template.html 파일에 코드를 짜 놓았기에 위에처럼 name 부분의 변경이 가능함
    }

    //(3)
    @GetMapping("hello-string")
    @ResponseBody //HTTP에서 바디 부분에 해당 데이터를 직접 넣어주겠다는 의미의 애너테이션
    //위에서 html을 통해서 출력했던것과는 달리 리턴값을 그대로 출력하며 소스코드 또한 러턴값으로만 존재함
    public String helloString(@RequestParam("name")String name){
        return "hello "+name;
    }

    //(4)
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){ //JSON 방식으로 출력됨 {"name" : "입력값"}으로 출력됨
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //문자가 아닌 객체를 넘김
        //객체일 경우 기본 디폴트가 JSON 방식으로 데이터를 만들어서 HTTP 응답에 반환하도록 되어있음
        //뷰리졸버 대신에 HttpMessageConverter가 동작하여 단순 문자면 StringConverter가 기본으로, 객체면 JsonConver가 기본으로 동작함
    }
    static class Hello{
        private String name; //객체가 private 이기에 밖에서 사용이 불가능하지만 아래 메서드는 public 이기 때문에 메서드를 통해 접근
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
