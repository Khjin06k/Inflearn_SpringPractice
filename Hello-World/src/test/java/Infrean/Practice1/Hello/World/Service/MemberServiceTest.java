package Infrean.Practice1.Hello.World.Service;

import Infrean.Practice1.Hello.World.domain.Member;
import Infrean.Practice1.Hello.World.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    //MemberService와 MemberServiceTest에서 new로 생성자를 생성했기 때문에 다른 객체가 됨. 따라서 다른 리포지토리를 이용하는 것이 되어있기 때문에 변화 필요함.
    //MemberService로 이동

    @BeforeEach //실행하기 전에 리포지토리를 만들어 해당 리포지토리를 Service에다가 넣어줌 >> 같은 멤버리포지토리가 사용됨.
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach //메서드가 실행 끝날때마다 동작을 하도록 하는 콜백 메서드라고 생각
    public void afterEach(){
        memberRepository.clearStore(); //테스트가 실행 끝날때마다 메모리를 모두 지움
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("spring"); //이름이 다르면 상관없지만 이름이 동일하면 메모리에 계속 쌓이기 때문에 메모리를 비우는 과정이 필요함
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMemeber = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMemeber .getName());
    }
    @Test
    public void sameName(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
   /*     try{
            memberService.join(member2); // 동일한 이름이기에 여기서 예외가 터져야 함
            fail();
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    */
        //assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //예외가 발생해야 함.

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}