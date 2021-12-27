package com.flashadeal.h1b.repository;

import com.flashadeal.h1b.domain.BioProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BioProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BioProfileRepository extends JpaRepository<BioProfile, Long>, JpaSpecificationExecutor<BioProfile> {}
