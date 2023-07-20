package jp.ecn.sesweb.repository;

import jp.ecn.sesweb.domain.CustomerManager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerManagerRepository extends JpaRepository<CustomerManager, Long> {}
