package edu.sru.cpsc.webshopping.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ApplicantTest.class})
public class ApplicantTest {

    @Test
    public void testGettersAndSetters() {
        Applicant applicant = new Applicant();
        applicant.setId(1L);
        applicant.setFirstName("John");
        applicant.setLastName("Doe");
        applicant.setPhoneNumber("123-456-7890");
        applicant.setEmail("john.doe@example.com");
        applicant.setApplicationDate("2022-01-01");
        applicant.setRoleAppliedfor("Software Engineer");
        applicant.setAnswer1("Answer 1");
        applicant.setAnswer2("Answer 2");
        applicant.setAnswer3("Answer 3");
        applicant.setAnswer4("Answer 4");
        applicant.setReviewed(true);

        Assertions.assertEquals(1L, applicant.getId());
        Assertions.assertEquals("John", applicant.getFirstName());
        Assertions.assertEquals("Doe", applicant.getLastName());
        Assertions.assertEquals("123-456-7890", applicant.getPhoneNumber());
        Assertions.assertEquals("john.doe@example.com", applicant.getEmail());
        Assertions.assertEquals("2022-01-01", applicant.getApplicationDate());
        Assertions.assertEquals("Software Engineer", applicant.getRoleAppliedfor());
        Assertions.assertEquals("Answer 1", applicant.getAnswer1());
        Assertions.assertEquals("Answer 2", applicant.getAnswer2());
        Assertions.assertEquals("Answer 3", applicant.getAnswer3());
        Assertions.assertEquals("Answer 4", applicant.getAnswer4());
        Assertions.assertTrue(applicant.getReviewed());
    }

    /* @Test
    public void testMaxSizeConstraints() {
        Applicant applicant = new Applicant();
        applicant.setFirstName("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setLastName("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setPhoneNumber("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setEmail("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setRoleAppliedfor("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setAnswer1("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setAnswer2("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setAnswer3("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");
        applicant.setAnswer4("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod.");

        Assertions.assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            ValidationUtil.validate(applicant);
        });
    } */

    @Test
    public void testEmailGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setEmail("john.doe@example.com");
        Assertions.assertEquals("john.doe@example.com", applicant.getEmail());
    }

    @Test
    public void testApplicationDateGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setApplicationDate("2022-01-01");
        Assertions.assertEquals("2022-01-01", applicant.getApplicationDate());
    }

    @Test
    public void testIdGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setId(1L);
        Assertions.assertEquals(1L, applicant.getId());
    }

    @Test
    public void testFirstNameGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setFirstName("John");
        Assertions.assertEquals("John", applicant.getFirstName());
    }

    @Test
    public void testLastNameGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setLastName("Doe");
        Assertions.assertEquals("Doe", applicant.getLastName());
    }

    @Test
    public void testPhoneNumberGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setPhoneNumber("123-456-7890");
        Assertions.assertEquals("123-456-7890", applicant.getPhoneNumber());
    }

    @Test
    public void testRoleAppliedforGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setRoleAppliedfor("Software Engineer");
        Assertions.assertEquals("Software Engineer", applicant.getRoleAppliedfor());
    }

    @Test
    public void testAnswer1GetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setAnswer1("Answer 1");
        Assertions.assertEquals("Answer 1", applicant.getAnswer1());
    }

    @Test
    public void testAnswer2GetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setAnswer2("Answer 2");
        Assertions.assertEquals("Answer 2", applicant.getAnswer2());
    }

    @Test
    public void testAnswer3GetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setAnswer3("Answer 3");
        Assertions.assertEquals("Answer 3", applicant.getAnswer3());
    }

    @Test
    public void testAnswer4GetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setAnswer4("Answer 4");
        Assertions.assertEquals("Answer 4", applicant.getAnswer4());
    }

    @Test
    public void testReviewedGetterAndSetter() {
        Applicant applicant = new Applicant();
        applicant.setReviewed(true);
        Assertions.assertTrue(applicant.getReviewed());
    }
}