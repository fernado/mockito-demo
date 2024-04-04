package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class M16_RealPartialMocks {


    @Test
    void action016() {
        class Person {
            private String name;
            public Person(String name) {
                this.name = name;
            }
            public String getName() {
                return "name -> " + this.name;
            }

            public String getFullName() {
                throw new RuntimeException("Not implement");
            }
        }
        // 使用 spy() 方法可以创建部分模拟对象：
        List list = spy(new LinkedList());

        // 允许有选择地在模拟对象上启用部分模拟能力：
        Person mock = mock(Person.class);
        // 确保真实实现是“安全”的。
        when(mock.getName()).thenCallRealMethod();
        assertEquals("name -> null", mock.getName());
        when(mock.getFullName()).thenCallRealMethod();
        try {
            System.out.println(mock.getFullName());
            fail("Runtime Exception");
        } catch (RuntimeException re) {
            System.out.println("RuntimeException => " + re);
        }
    }
}
