package jp.ecn.sesweb.repository;

import jp.ecn.sesweb.domain.Contracts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contracts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractsRepository extends JpaRepository<Contracts, Long> {}
