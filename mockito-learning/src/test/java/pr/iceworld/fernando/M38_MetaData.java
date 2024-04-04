package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.MockSettings;
import org.mockito.Mockito;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class M38_MetaData {


    @Retention(RetentionPolicy.RUNTIME)
    @interface MyAnnotation {}

    @MyAnnotation
    class Foo {
        List<String> bar() { return null; }
    }

    @Test
    void action038() {
        MockSettings settings = Mockito.withSettings().defaultAnswer(Mockito.RETURNS_DEFAULTS);
        Class<?> mockType = Mockito.mock(Foo.class, settings).getClass();
        assertTrue(mockType.isAnnotationPresent(MyAnnotation.class));

        try {
            Method method = mockType.getDeclaredMethod("bar");
            assertTrue(method.getGenericReturnType() instanceof ParameterizedType);
            System.out.println("All assertions passed successfully.");
        } catch (AssertionError | NoSuchMethodException e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }

}
