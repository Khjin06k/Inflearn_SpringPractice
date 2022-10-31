package Infrean.Practice1.Hello.World.repository;

import Infrean.Practice1.Hello.World.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //sequence는 0,1,2 key 값을 생성해 주는 것을 말함

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //sequence를 먼저 증가시킨 후 사용
        //Id를 세팅해주고
        store.put(member.getId(),member);
        //Map에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //결과값이 없는 경우를 위해 Optional로 감싸줌
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) //member에서 getName이 파라미터인 name과 동일한지 확인
                .findAny(); //찾으면 반환함 >> 끝까지 돌렸을 때 없다면 Optional로 감싸져 있기 때문에 Null로 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear(); //store을 모두 비움 >> MemoryMemberRepository에 있는 afterEach에서 사용하기 위함
    }
}
