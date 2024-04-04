package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class M32_BetterGenericSupportWithDeepStubs {

    class Line {
    }
    class Lines extends ArrayList<Line> {
    }

    @Test
    void action032() {
        Lines lines = mock(Lines.class, RETURNS_DEEP_STUBS);
        // Now Mockito understand this is not an Object but a Line
        Line line = lines.iterator().next();
    }
}
