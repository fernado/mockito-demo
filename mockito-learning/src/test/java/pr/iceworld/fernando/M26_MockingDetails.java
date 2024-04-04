package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;

import static org.mockito.Mockito.*;

public class M26_MockingDetails {
    class Person {
        private String name;
        private int age;
        private String gender;
        public Person(String name, int age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
        public String getGender() {
            return gender;
        }
    }
    @Test
    void action026() {
        Person person = mock(Person.class);
        when(person.getName()).thenReturn("Fernando");
        when(person.getAge()).thenReturn(24);
        when(person.getGender()).thenReturn("Male");
        // Fernando
        System.out.println(person.getName());
        // 24
        System.out.println(person.getAge());
        verify(person).getName();
        //To identify whether a particular object is a mock or a spy:
        // true
        System.out.println(mockingDetails(person).isMock());
        // false
        System.out.println(mockingDetails(person).isSpy());

        //Getting details like type to mock or default answer:
        MockingDetails details = mockingDetails(person);
        // class pr.iceworld.fernando.Mockito006$Person
        System.out.println(details.getMockCreationSettings().getTypeToMock());
        // RETURNS_DEFAULTS
        System.out.println(details.getMockCreationSettings().getDefaultAnswer());

        //Getting invocations and stubbings of the mock:
        details = mockingDetails(person);

        // [person.getName();, person.getAge();]
        System.out.println(details.getInvocations());
        // [person.getName(); stubbed with: [Returns: Fernando], person.getAge(); stubbed with: [Returns: 24], person.getGender(); stubbed with: [Returns: Male]]
        System.out.println("----------2");
        System.out.println(details.getStubbings());
        //Printing all interactions (including stubbing, unused stubs)
        // [Mockito] Interactions of: Mock for Person, hashCode: 1584918772
        //  1. person.getName();
        //   -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:35)
        //    - stubbed -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:31)
        //  2. person.getAge();
        //   -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:37)
        //    - stubbed -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:32)
        // [Mockito] Unused stubbings of: Mock for Person, hashCode: 1584918772
        //  1. person.getName();
        //   - stubbed -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:31)
        //  2. person.getAge();
        //   - stubbed -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:32)
        //  3. person.getGender();
        //   - stubbed -> at pr.iceworld.fernando.Mockito006.action026(Mockito006.java:33)
        System.out.println(details.printInvocations());
    }
}
