package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class M17_ResettingMocks {

    @Test
    void action017() {
        List mock = mock(List.class);
        when(mock.size()).thenReturn(10);
        mock.add(1);

        // org.mockito.Mockito#reset()
        reset(mock);
        // 模拟对象忘记了任何交互和存根化
        // 0
        System.out.println(mock.size());
    }
}
