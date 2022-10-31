package Infrean.Practice1.Hello.World.repository;

import Infrean.Practice1.Hello.World.domain.Member;
import java.util.*;

public interface MemberRepository {
    Member save(Member member); //회원이 저장소에 저장
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll(); //모든 회원 리스트 반환
}