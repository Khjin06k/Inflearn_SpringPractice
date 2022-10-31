package Infrean.Practice1.Hello.World.controller;

import Infrean.Practice1.Hello.World.Service.MemberService;
import Infrean.Practice1.Hello.World.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //스프링 컨테이너 라는 통해 MemberController 객체를 생성해 스프링에 넣어 관리하도록 하는 애너테이션
public class MemberController { //Controller는 Service를 통해서 컨트롤러가 기능을 가져와 실행이 가능하기에 Service를 가져와 사용해야 한다.

    private final MemberService memberService;
    //new를 사용해 생성해도 가능하지만, 스프링에서 관리하게 될 경우 다 스프링에 등록하고 스프링 컨테이너로부터 받아서 사용하도록 변경이 필요함
    // 또한, 하나만 등록해 공유해서 사용하면 되기 때문에 new를 이용해 여러개를 만들어 사용할 필요가 없음.

    @Autowired
    public MemberController(MemberService memberService) { //MemberService를 찾아서 가져와 사용이 가능해야 하기 때문에 MemberService도 스프링 컨테이너에 등록이 되어있어야 함
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName()); //form에서 가져와 멤버 이름에 지정

        memberService.join(member); //지정된 이름을 가지고 Service에 있는 join을 통해 member을 등록함

        return "/redirect:/"; //등록 이후 다시 홈 화면으로 이동
    }

    @GetMapping("/members")
    public String List(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); //model을 모두 List에 담아서 넘김

        return "members/memberList";
    }
}
