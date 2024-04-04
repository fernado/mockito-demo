package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class M20_SerializableMocks {

    @Test
    void action020() {

        List normalMock = mock(List.class);
        // org.mockito.Mockito#withSettings()
        List serializableMock = mock(List.class, withSettings().serializable());

        normalMock.add("2");
        normalMock.add("7");

        serializableMock.add("1");
        serializableMock.add("5");

        try {
            serialize(normalMock, "normalMock.txt");
            fail("RuntimeException");
        }catch (RuntimeException re) {
            // RuntimeException java.io.NotSerializableException: org.mockito.codegen.List$MockitoMock$BVSjR2tH
            System.out.println("RuntimeException " + re.getMessage());
        }

        serialize(serializableMock, "serializableMock.txt");

        try {
            normalMock = (List) deSerialize("normalMock.txt");
            fail("RuntimeException");
        }catch (RuntimeException re) {
            // RuntimeException java.io.WriteAbortedException: writing aborted; java.io.NotSerializableException: org.mockito.codegen.List$MockitoMock$BVSjR2tH
            System.out.println("RuntimeException " + re.getMessage());
        }

        serializableMock = (List) deSerialize("serializableMock.txt");

        // as above serialize and deserialize havent impacted normalMock.
        // it runs successfully as normal logic
        verify(normalMock).add("2");
        verify(normalMock).add("7");

        // as above serialize and deserialize have impacted serializableMock.
        // it runs successfully becaz now it has been deserialized.
        verify(serializableMock).add("1");
        verify(serializableMock).add("5");

        List<Object> list = new ArrayList<>();
        // org.mockito.Mockito.CALLS_REAL_METHODS
        List<Object> spy = mock(ArrayList.class, withSettings()
                .spiedInstance(list)
                .defaultAnswer(CALLS_REAL_METHODS)
                .serializable());
    }

    private void serialize(Object obj, String filename) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                Files.newOutputStream(new File(filename).toPath()))) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object deSerialize(String filename) {
        try (ObjectInputStream objectOutputStream = new ObjectInputStream(
                Files.newInputStream(new File(filename).toPath()))) {
            return objectOutputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
