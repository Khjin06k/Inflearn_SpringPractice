package Infrean.Practice1.Hello.World.Service;

import Infrean.Practice1.Hello.World.domain.Member;
import Infrean.Practice1.Hello.World.repository.MemberRepository;
import Infrean.Practice1.Hello.World.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService { //비즈니스에 가까운 용어를 사용함

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
   //@Autowired
    public MemberService(MemberRepository memberRepository){ // Repository를 가져와 사용이 가능해야 하기 때문에 스프링 빈 등록이 되어 있어야 함.
        this.memberRepository = memberRepository; //MemberRepository는 MemoryMemberRepository가 구현체이기에 이 Repository에 추가함
    }
    //객체가 다른 것을 해결하기 위해서 객체를 가져오도록 함.


    /**
     * 회원 가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원이 불가능함
        //Optional<Member> result = memberRepository.findByName(member.getName()); //findByName을 통해서 이름을 가져옴
        // result.ifPresent(m -> {
        //            throw new IllegalStateException("이미 존재하는 회원입니다."); //result에서 member.getName과 동일한 것이 존재하면 예외를 던짐
        //        });
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다."); //result에서 member.getName과 동일한 것이 존재하면 예외를 던짐
        });
        //result가 결국 memberRepository.findByname~이기 때문에 바로 연결도 가능함.

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한명 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
