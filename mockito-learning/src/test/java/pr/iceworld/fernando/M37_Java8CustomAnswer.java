package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.stubbing.Answer2;
import org.mockito.stubbing.VoidAnswer2;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.answer;
import static org.mockito.AdditionalAnswers.answerVoid;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class M37_Java8CustomAnswer {

    class PersonService {
        public String getSerialNo() {
            return UUID.randomUUID().toString();
        }
        public int guessAge(String firstName, String lastName, String gender) {
            return 0;
        }
    }

    @Test
    void action037_001() {
        PersonService personService = mock(PersonService.class);

        // org.mockito.Mockito#doAnswer()
        // answer by returning Iny every time
        doAnswer(invocation -> "Iny").when(personService).getSerialNo();
        assertEquals("Iny", personService.getSerialNo());

        // answer by using one of the parameters - converting into the right
        // type as your go - in this case, returning the length of the second string parameter
        // as the answer. This gets long-winded quickly, with casting of parameters.
        doAnswer(invocation -> ((String)invocation.getArgument(0)).length())
                .when(personService).guessAge(anyString(), anyString(), anyString());
        assertEquals(8, personService.guessAge("Fernando", "Shui", "Male"));
    }


    public interface Callback {
        void receive(String item);
    }

    public interface ExampleInterface {
        // Example interface to be mocked has a function like:
        void execute(String operand, Callback callback);
        // returning a value is possible with the answer() function
        // and the non-void version of the functional interfaces
        // so if the mock interface had a method like
        boolean isSameString(String input1, String input2);
    }

    // Java 8 - style 3 - where mocking function to is a static member of test class
    private static void dummyCallbackImpl(String operation, Callback callback) {
        callback.receive("dummy");
    }

    @Test
    void action037_002() {
        ExampleInterface mock = mock(ExampleInterface.class);

        // Java 8 - style 1
        doAnswer(AdditionalAnswers.<String, Callback>answerVoid((operand, callback) -> callback.receive("dummy")))
                .when(mock).execute(anyString(), any(Callback.class));

        // Java 8 - style 2 - assuming static import of AdditionalAnswers
        doAnswer(answerVoid((String operand, Callback callback) -> callback.receive("dummy")))
                .when(mock).execute(anyString(), any(Callback.class));

        doAnswer(answerVoid(M37_Java8CustomAnswer::dummyCallbackImpl))
                .when(mock).execute(anyString(), any(Callback.class));

        // org.mockito.stubbing.VoidAnswer2
        // Java 7
        doAnswer(answerVoid(new VoidAnswer2<String, Callback>() {
            public void answer(String operation, Callback callback) {
                callback.receive("dummy");
            }})).when(mock).execute(anyString(), any(Callback.class));

        // this could be mocked
        // Java 8
        doAnswer(AdditionalAnswers.<Boolean,String,String>answer((input1, input2) -> input1.equals(input2)))
                .when(mock).execute(anyString(), any(Callback.class));

        // Java 7
        doAnswer(answer(new Answer2<String, String, String>() {
            public String answer(String input1, String input2) {
                return input1 + input2;
            }})).when(mock).execute(anyString(), any(Callback.class));
    }


}
