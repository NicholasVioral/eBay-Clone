package edu.sru.cpsc.webshopping.repository.group;

import edu.sru.cpsc.webshopping.domain.group.Group;
import edu.sru.cpsc.webshopping.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembersContaining(User user);

    List<Group> findByName(String name);

    // Modified JPQL query to eagerly fetch a group with its members
    // This query is optimized to prevent duplicate group entities due to JOIN FETCH
    @Query("SELECT DISTINCT g FROM Group g LEFT JOIN FETCH g.members WHERE g.id = :groupId")
    Optional<Group> findByIdWithMembersEagerly(@Param("groupId") Long groupId);
}
