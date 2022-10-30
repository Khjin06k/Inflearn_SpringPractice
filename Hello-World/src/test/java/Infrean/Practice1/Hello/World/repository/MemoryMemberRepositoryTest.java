package Infrean.Practice1.Hello.World.repository;

import Infrean.Practice1.Hello.World.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemoryMemberRepositoryTest { //테스트 케이스는 굳이 public이 아니어도 됨
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //메서드가 실행 끝날때마다 동작을 하도록 하는 콜백 메서드라고 생각
    public void afterEach(){
        repository.clearStore(); //테스트가 실행 끝날때마다 메모리를 모두 지움
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); //Optional이기 때문에 .get()을 이용하여 꺼낼 수 있음
        // System.out.println("result = " + (result == member)); //단순하게 나타낼 수 있음
        //Assertions.assertEquals(member, result); //result와 member가 동일한지를 확인함 (기대하는 값을 먼저 적음) 맞을 경우 출력되는 것은 없지만 만약 다르다면 실패하게 됨
        Assertions.assertThat(member).isEqualTo(result); //바로 위 코드보다는 조금 직관적임 (import를 하면 assertThat으로 바로 사용 가능
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
        //기대값이 2일때 에러가 남. 모든 테스트는 메서드별로 따로 동작해야 하는데 findAll이 먼저 실행됨
        //그렇기 때문에 spring1과 spring2가 이미 저장이 되어버림. >> findByName을 할 때 다른 객체가 나와버리는 것.
        //따라서 테스트 하나가 끝날때마다 클리어를 해줘야 함.
    }


}
