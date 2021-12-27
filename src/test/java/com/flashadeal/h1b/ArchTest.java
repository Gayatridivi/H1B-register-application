package com.flashadeal.h1b;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.flashadeal.h1b");

        noClasses()
            .that()
            .resideInAnyPackage("com.flashadeal.h1b.service..")
            .or()
            .resideInAnyPackage("com.flashadeal.h1b.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.flashadeal.h1b.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
