package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class M30_SpyingOrMockingAbstractClasses {

    @Test
    void action030() {
        List list = spy(List.class);
        // false
        System.out.println(list.contains("x"));
        when(list.contains("x")).thenReturn(Boolean.TRUE);
        // true
        System.out.println(list.contains("x"));

        Fruitable fruitable = spy(Fruitable.class);
        // 0.0
        System.out.println(fruitable.cost());
        when(fruitable.cost()).thenReturn(45.0d);
        // 45.0
        System.out.println(fruitable.cost());

        //convenience API, new overloaded spy() method:
        AbstractFruit spyFruit = spy(AbstractFruit.class);
        // 0.0
        System.out.println(spyFruit.cost());
        when(spyFruit.cost()).thenReturn(10.0d);
        // 10.0
        System.out.println(spyFruit.cost());

        //Mocking abstract methods, spying default methods of an interface (only available since 2.7.13)
        Function<String, String> function = spy(Function.class);
        // null
        System.out.println(function.apply("x"));
        when(function.apply("x")).thenReturn("y");
        // y
        System.out.println(function.apply("x"));

        //Robust API, via settings builder:
        Fruitable spyFruitable = mock(Fruitable.class, withSettings()
                .useConstructor().defaultAnswer(CALLS_REAL_METHODS));
        // 0.0
        System.out.println(spyFruitable.cost());
        when(spyFruitable.cost()).thenReturn(20.0d);
        // 20.0
        System.out.println(spyFruitable.cost());


        AbstractFruit spyAbstractFruit = mock(AbstractFruit.class, withSettings()
                .useConstructor().defaultAnswer(CALLS_REAL_METHODS));

        //Mocking an abstract class with constructor arguments (only available since 2.7.14)
        AbstractFruit spyAbstractFruitWithCp = mock(AbstractFruit.class, withSettings()
                .useConstructor("arg1", 123).defaultAnswer(CALLS_REAL_METHODS));

        //Mocking a non-static inner abstract class:
        OuterClass.InnerClass spyOI = mock(OuterClass.InnerClass.class, withSettings()
                .useConstructor().outerInstance(new OuterClass()).defaultAnswer(CALLS_REAL_METHODS));
        // 35bd42ff-b12f-4ef7-a6a9-f10b2d089a83
        System.out.println(spyOI.getSerialNo());
        when(spyOI.getSerialNo()).thenReturn("serialNo");
        // serialNo
        System.out.println(spyOI.getSerialNo());
    }
}
