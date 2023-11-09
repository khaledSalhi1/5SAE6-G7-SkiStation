package tn.esprit.spring.iche;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class CourseServicesTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Test
    public void testRetrieveAllCourses() {
        // Arrange
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1L, 1, TypeCourse.COLLECTIVE_CHILDREN, Support.SKI, 100.0f, 2, null));
        courses.add(new Course(2L, 2, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 150.0f, 3, null));

        Mockito.when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseServices.retrieveAllCourses();

        // Assert
        assertEquals(courses, result);
    }

    @Test
    public void testAddCourse() {
        // Arrange
        Course newCourse = new Course(3L, 3, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 200.0f, 4, null);

        Mockito.when(courseRepository.save(newCourse)).thenReturn(newCourse);

        // Act
        Course result = courseServices.addCourse(newCourse);

        // Assert
        assertEquals(newCourse, result);
    }

    @Test
    public void testRetrieveCourse() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course(courseId, 1, TypeCourse.COLLECTIVE_CHILDREN, Support.SKI, 100.0f, 2, null);

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        Course result = courseServices.retrieveCourse(courseId);

        // Assert
        assertEquals(course, result);
    }
}
