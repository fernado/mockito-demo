package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockMakers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class M53_SpecifyingMockMaker4IndividualMocks {
    // using annotation
    @Mock(mockMaker = MockMakers.SUBCLASS)
    AbsPerson mock;

    @Test
    void action053() {
        // using MockSettings.withSettings()
        Person mockAbsPerson = Mockito.mock(Person.class, withSettings().mockMaker(MockMakers.SUBCLASS));
        // when(mockAbsPerson.characteristics()).thenReturn("handsome");

        System.out.println(mockAbsPerson.characteristics());
        doReturn("white").when(mockAbsPerson).hair();

        when(mockAbsPerson.hair()).thenReturn("yellow");
        System.out.println(mockAbsPerson.hair());
    }

}
