package Infrean.Practice1.Hello.World.controller;

import Infrean.Practice1.Hello.World.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //스프링 컨테이너 라는 통해 MemberController 객체를 생성해 스프링에 넣어 관리하도록 하는 애너테이션
public class MemberController { //Controller는 Service를 통해서 컨트롤러가 기능을 가져와 실행이 가능하기에 Service를 가져와 사용해야 한다.

    private final MemberService memberService;
    //new를 사용해 생성해도 가능하지만, 스프링에서 관리하게 될 경우 다 스프링에 등록하고 스프링 컨테이너로부터 받아서 사용하도록 변경이 필요함
    // 또한, 하나만 등록해 공유해서 사용하면 되기 때문에 new를 이용해 여러개를 만들어 사용할 필요가 없음.

    @Autowired
    public MemberController(MemberService memberService) { //MemberService를 찾아서 가져와 사용이 가능해야 하기 때문에 MemberService도 스프링 컨테이너에 등록이 되어있어야 함
        this.memberService = memberService;
    }
}
