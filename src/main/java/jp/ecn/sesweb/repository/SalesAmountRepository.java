package jp.ecn.sesweb.repository;

import jp.ecn.sesweb.domain.SalesAmount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesAmountRepository extends JpaRepository<SalesAmount, Long> {}
