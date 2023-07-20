package jp.ecn.sesweb.repository;

import jp.ecn.sesweb.domain.ContractPriceUpdateHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContractPriceUpdateHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractPriceUpdateHistoryRepository extends JpaRepository<ContractPriceUpdateHistory, Long> {}
