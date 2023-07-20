package jp.ecn.sesweb.repository;

import java.util.List;
import jp.ecn.sesweb.domain.PartnerManager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PartnerManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerManagerRepository extends JpaRepository<PartnerManager, Long> {
    List<PartnerManager> findByNameContaining(String name);
    List<PartnerManager> findByCompanyNameContaining(String companyName);
}
