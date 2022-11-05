package Infrean.Practice1.Hello.World.Service;

import Infrean.Practice1.Hello.World.domain.Member;
import Infrean.Practice1.Hello.World.repository.JdbcMemberRepository;
import Infrean.Practice1.Hello.World.repository.MemberRepository;
import Infrean.Practice1.Hello.World.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    //@Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean //직접 스프링 빈으로 동록함
    public MemberService memberService(){
        return new MemberService(memberRepository()); //Service가 Repository를 필요로 하기 때문에 생성시 스프링 컨테이너에 있는 Repository를 넣어줌
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(dataSource);
        //return new MemoryMemberRepository();
    }
}
