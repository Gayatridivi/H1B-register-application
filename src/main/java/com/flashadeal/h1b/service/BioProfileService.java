package com.flashadeal.h1b.service;

import com.flashadeal.h1b.domain.BioProfile;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BioProfile}.
 */
public interface BioProfileService {
    /**
     * Save a bioProfile.
     *
     * @param bioProfile the entity to save.
     * @return the persisted entity.
     */
    BioProfile save(BioProfile bioProfile);

    /**
     * Partially updates a bioProfile.
     *
     * @param bioProfile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BioProfile> partialUpdate(BioProfile bioProfile);

    /**
     * Get all the bioProfiles.
     *
     * @return the list of entities.
     */
    List<BioProfile> findAll();

    /**
     * Get the "id" bioProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BioProfile> findOne(Long id);

    /**
     * Delete the "id" bioProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
