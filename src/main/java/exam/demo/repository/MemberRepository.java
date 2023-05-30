package exam.demo.repository;

import exam.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByUserName(String username);

    Optional<Member> findByMemberId(Long memberId);

}
