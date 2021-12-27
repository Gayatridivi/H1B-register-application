package com.flashadeal.h1b.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.flashadeal.h1b.IntegrationTest;
import com.flashadeal.h1b.domain.BioProfile;
import com.flashadeal.h1b.repository.BioProfileRepository;
import com.flashadeal.h1b.service.criteria.BioProfileCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BioProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BioProfileResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOB = "AAAAAAAAAA";
    private static final String UPDATED_DOB = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bio-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BioProfileRepository bioProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBioProfileMockMvc;

    private BioProfile bioProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BioProfile createEntity(EntityManager em) {
        BioProfile bioProfile = new BioProfile()
            .userName(DEFAULT_USER_NAME)
            .userId(DEFAULT_USER_ID)
            .memberId(DEFAULT_MEMBER_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dob(DEFAULT_DOB)
            .gender(DEFAULT_GENDER)
            .imageUrl(DEFAULT_IMAGE_URL)
            .title(DEFAULT_TITLE)
            .summary(DEFAULT_SUMMARY);
        return bioProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BioProfile createUpdatedEntity(EntityManager em) {
        BioProfile bioProfile = new BioProfile()
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .imageUrl(UPDATED_IMAGE_URL)
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY);
        return bioProfile;
    }

    @BeforeEach
    public void initTest() {
        bioProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createBioProfile() throws Exception {
        int databaseSizeBeforeCreate = bioProfileRepository.findAll().size();
        // Create the BioProfile
        restBioProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bioProfile)))
            .andExpect(status().isCreated());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeCreate + 1);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testBioProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBioProfile.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBioProfile.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBioProfile.getSummary()).isEqualTo(DEFAULT_SUMMARY);
    }

    @Test
    @Transactional
    void createBioProfileWithExistingId() throws Exception {
        // Create the BioProfile with an existing ID
        bioProfile.setId(1L);

        int databaseSizeBeforeCreate = bioProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBioProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bioProfile)))
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBioProfiles() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bioProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())));
    }

    @Test
    @Transactional
    void getBioProfile() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get the bioProfile
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, bioProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bioProfile.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.memberId").value(DEFAULT_MEMBER_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()));
    }

    @Test
    @Transactional
    void getBioProfilesByIdFiltering() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        Long id = bioProfile.getId();

        defaultBioProfileShouldBeFound("id.equals=" + id);
        defaultBioProfileShouldNotBeFound("id.notEquals=" + id);

        defaultBioProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBioProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultBioProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBioProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userName equals to DEFAULT_USER_NAME
        defaultBioProfileShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the bioProfileList where userName equals to UPDATED_USER_NAME
        defaultBioProfileShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userName not equals to DEFAULT_USER_NAME
        defaultBioProfileShouldNotBeFound("userName.notEquals=" + DEFAULT_USER_NAME);

        // Get all the bioProfileList where userName not equals to UPDATED_USER_NAME
        defaultBioProfileShouldBeFound("userName.notEquals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultBioProfileShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the bioProfileList where userName equals to UPDATED_USER_NAME
        defaultBioProfileShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userName is not null
        defaultBioProfileShouldBeFound("userName.specified=true");

        // Get all the bioProfileList where userName is null
        defaultBioProfileShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserNameContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userName contains DEFAULT_USER_NAME
        defaultBioProfileShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the bioProfileList where userName contains UPDATED_USER_NAME
        defaultBioProfileShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userName does not contain DEFAULT_USER_NAME
        defaultBioProfileShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the bioProfileList where userName does not contain UPDATED_USER_NAME
        defaultBioProfileShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userId equals to DEFAULT_USER_ID
        defaultBioProfileShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the bioProfileList where userId equals to UPDATED_USER_ID
        defaultBioProfileShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userId not equals to DEFAULT_USER_ID
        defaultBioProfileShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the bioProfileList where userId not equals to UPDATED_USER_ID
        defaultBioProfileShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultBioProfileShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the bioProfileList where userId equals to UPDATED_USER_ID
        defaultBioProfileShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userId is not null
        defaultBioProfileShouldBeFound("userId.specified=true");

        // Get all the bioProfileList where userId is null
        defaultBioProfileShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserIdContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userId contains DEFAULT_USER_ID
        defaultBioProfileShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the bioProfileList where userId contains UPDATED_USER_ID
        defaultBioProfileShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where userId does not contain DEFAULT_USER_ID
        defaultBioProfileShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the bioProfileList where userId does not contain UPDATED_USER_ID
        defaultBioProfileShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByMemberIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where memberId equals to DEFAULT_MEMBER_ID
        defaultBioProfileShouldBeFound("memberId.equals=" + DEFAULT_MEMBER_ID);

        // Get all the bioProfileList where memberId equals to UPDATED_MEMBER_ID
        defaultBioProfileShouldNotBeFound("memberId.equals=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByMemberIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where memberId not equals to DEFAULT_MEMBER_ID
        defaultBioProfileShouldNotBeFound("memberId.notEquals=" + DEFAULT_MEMBER_ID);

        // Get all the bioProfileList where memberId not equals to UPDATED_MEMBER_ID
        defaultBioProfileShouldBeFound("memberId.notEquals=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByMemberIdIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where memberId in DEFAULT_MEMBER_ID or UPDATED_MEMBER_ID
        defaultBioProfileShouldBeFound("memberId.in=" + DEFAULT_MEMBER_ID + "," + UPDATED_MEMBER_ID);

        // Get all the bioProfileList where memberId equals to UPDATED_MEMBER_ID
        defaultBioProfileShouldNotBeFound("memberId.in=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByMemberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where memberId is not null
        defaultBioProfileShouldBeFound("memberId.specified=true");

        // Get all the bioProfileList where memberId is null
        defaultBioProfileShouldNotBeFound("memberId.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByMemberIdContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where memberId contains DEFAULT_MEMBER_ID
        defaultBioProfileShouldBeFound("memberId.contains=" + DEFAULT_MEMBER_ID);

        // Get all the bioProfileList where memberId contains UPDATED_MEMBER_ID
        defaultBioProfileShouldNotBeFound("memberId.contains=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByMemberIdNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where memberId does not contain DEFAULT_MEMBER_ID
        defaultBioProfileShouldNotBeFound("memberId.doesNotContain=" + DEFAULT_MEMBER_ID);

        // Get all the bioProfileList where memberId does not contain UPDATED_MEMBER_ID
        defaultBioProfileShouldBeFound("memberId.doesNotContain=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllBioProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where firstName equals to DEFAULT_FIRST_NAME
        defaultBioProfileShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the bioProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultBioProfileShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where firstName not equals to DEFAULT_FIRST_NAME
        defaultBioProfileShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the bioProfileList where firstName not equals to UPDATED_FIRST_NAME
        defaultBioProfileShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultBioProfileShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the bioProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultBioProfileShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where firstName is not null
        defaultBioProfileShouldBeFound("firstName.specified=true");

        // Get all the bioProfileList where firstName is null
        defaultBioProfileShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where firstName contains DEFAULT_FIRST_NAME
        defaultBioProfileShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the bioProfileList where firstName contains UPDATED_FIRST_NAME
        defaultBioProfileShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where firstName does not contain DEFAULT_FIRST_NAME
        defaultBioProfileShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the bioProfileList where firstName does not contain UPDATED_FIRST_NAME
        defaultBioProfileShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where lastName equals to DEFAULT_LAST_NAME
        defaultBioProfileShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the bioProfileList where lastName equals to UPDATED_LAST_NAME
        defaultBioProfileShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where lastName not equals to DEFAULT_LAST_NAME
        defaultBioProfileShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the bioProfileList where lastName not equals to UPDATED_LAST_NAME
        defaultBioProfileShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultBioProfileShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the bioProfileList where lastName equals to UPDATED_LAST_NAME
        defaultBioProfileShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where lastName is not null
        defaultBioProfileShouldBeFound("lastName.specified=true");

        // Get all the bioProfileList where lastName is null
        defaultBioProfileShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where lastName contains DEFAULT_LAST_NAME
        defaultBioProfileShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the bioProfileList where lastName contains UPDATED_LAST_NAME
        defaultBioProfileShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where lastName does not contain DEFAULT_LAST_NAME
        defaultBioProfileShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the bioProfileList where lastName does not contain UPDATED_LAST_NAME
        defaultBioProfileShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllBioProfilesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where dob equals to DEFAULT_DOB
        defaultBioProfileShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the bioProfileList where dob equals to UPDATED_DOB
        defaultBioProfileShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllBioProfilesByDobIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where dob not equals to DEFAULT_DOB
        defaultBioProfileShouldNotBeFound("dob.notEquals=" + DEFAULT_DOB);

        // Get all the bioProfileList where dob not equals to UPDATED_DOB
        defaultBioProfileShouldBeFound("dob.notEquals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllBioProfilesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultBioProfileShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the bioProfileList where dob equals to UPDATED_DOB
        defaultBioProfileShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllBioProfilesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where dob is not null
        defaultBioProfileShouldBeFound("dob.specified=true");

        // Get all the bioProfileList where dob is null
        defaultBioProfileShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByDobContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where dob contains DEFAULT_DOB
        defaultBioProfileShouldBeFound("dob.contains=" + DEFAULT_DOB);

        // Get all the bioProfileList where dob contains UPDATED_DOB
        defaultBioProfileShouldNotBeFound("dob.contains=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllBioProfilesByDobNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where dob does not contain DEFAULT_DOB
        defaultBioProfileShouldNotBeFound("dob.doesNotContain=" + DEFAULT_DOB);

        // Get all the bioProfileList where dob does not contain UPDATED_DOB
        defaultBioProfileShouldBeFound("dob.doesNotContain=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllBioProfilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where gender equals to DEFAULT_GENDER
        defaultBioProfileShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the bioProfileList where gender equals to UPDATED_GENDER
        defaultBioProfileShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllBioProfilesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where gender not equals to DEFAULT_GENDER
        defaultBioProfileShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the bioProfileList where gender not equals to UPDATED_GENDER
        defaultBioProfileShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllBioProfilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultBioProfileShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the bioProfileList where gender equals to UPDATED_GENDER
        defaultBioProfileShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllBioProfilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where gender is not null
        defaultBioProfileShouldBeFound("gender.specified=true");

        // Get all the bioProfileList where gender is null
        defaultBioProfileShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByGenderContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where gender contains DEFAULT_GENDER
        defaultBioProfileShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the bioProfileList where gender contains UPDATED_GENDER
        defaultBioProfileShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllBioProfilesByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where gender does not contain DEFAULT_GENDER
        defaultBioProfileShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the bioProfileList where gender does not contain UPDATED_GENDER
        defaultBioProfileShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllBioProfilesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where title equals to DEFAULT_TITLE
        defaultBioProfileShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bioProfileList where title equals to UPDATED_TITLE
        defaultBioProfileShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBioProfilesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where title not equals to DEFAULT_TITLE
        defaultBioProfileShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the bioProfileList where title not equals to UPDATED_TITLE
        defaultBioProfileShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBioProfilesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBioProfileShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bioProfileList where title equals to UPDATED_TITLE
        defaultBioProfileShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBioProfilesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where title is not null
        defaultBioProfileShouldBeFound("title.specified=true");

        // Get all the bioProfileList where title is null
        defaultBioProfileShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBioProfilesByTitleContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where title contains DEFAULT_TITLE
        defaultBioProfileShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bioProfileList where title contains UPDATED_TITLE
        defaultBioProfileShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBioProfilesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList where title does not contain DEFAULT_TITLE
        defaultBioProfileShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bioProfileList where title does not contain UPDATED_TITLE
        defaultBioProfileShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBioProfileShouldBeFound(String filter) throws Exception {
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bioProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())));

        // Check, that the count call also returns 1
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBioProfileShouldNotBeFound(String filter) throws Exception {
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBioProfile() throws Exception {
        // Get the bioProfile
        restBioProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBioProfile() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();

        // Update the bioProfile
        BioProfile updatedBioProfile = bioProfileRepository.findById(bioProfile.getId()).get();
        // Disconnect from session so that the updates on updatedBioProfile are not directly saved in db
        em.detach(updatedBioProfile);
        updatedBioProfile
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .imageUrl(UPDATED_IMAGE_URL)
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY);

        restBioProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBioProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBioProfile))
            )
            .andExpect(status().isOk());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testBioProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBioProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBioProfile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBioProfile.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    void putNonExistingBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bioProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bioProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBioProfileWithPatch() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();

        // Update the bioProfile using partial update
        BioProfile partialUpdatedBioProfile = new BioProfile();
        partialUpdatedBioProfile.setId(bioProfile.getId());

        partialUpdatedBioProfile
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .dob(UPDATED_DOB)
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY);

        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBioProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBioProfile))
            )
            .andExpect(status().isOk());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testBioProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBioProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBioProfile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBioProfile.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    void fullUpdateBioProfileWithPatch() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();

        // Update the bioProfile using partial update
        BioProfile partialUpdatedBioProfile = new BioProfile();
        partialUpdatedBioProfile.setId(bioProfile.getId());

        partialUpdatedBioProfile
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberId(UPDATED_MEMBER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .imageUrl(UPDATED_IMAGE_URL)
            .title(UPDATED_TITLE)
            .summary(UPDATED_SUMMARY);

        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBioProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBioProfile))
            )
            .andExpect(status().isOk());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testBioProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBioProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBioProfile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBioProfile.getSummary()).isEqualTo(UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    void patchNonExistingBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bioProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBioProfile() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeDelete = bioProfileRepository.findAll().size();

        // Delete the bioProfile
        restBioProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, bioProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
