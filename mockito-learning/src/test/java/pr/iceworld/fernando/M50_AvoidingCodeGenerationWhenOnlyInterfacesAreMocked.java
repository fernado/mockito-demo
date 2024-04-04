package pr.iceworld.fernando;

import org.mockito.Mockito;

public class M50_AvoidingCodeGenerationWhenOnlyInterfacesAreMocked {


    public static void main(String[] args) {
        // Create a mock of the interface using Proxy-based mock maker
        MyInterface mock = Mockito.mock(MyInterface.class);

        // Define behavior for the mocked method
        Mockito.when(mock.doSomething()).thenReturn("Mocked value");

        // Call the method on the mock
        System.out.println(mock.doSomething()); // Output: Mocked value
    }


    interface MyInterface {
        String doSomething();
    }
}
