package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.mock.SerializableMode.ACROSS_CLASSLOADERS;

public class M31_MockitoMocksCanBeSerializedOrDeserializedAcrossClassloaders {

    public class Book implements Serializable {
        public String getTitle() {
            return "The Book of Doors";
        }
    }

    static final String acrossClsBookTitle = "across classloader get title";
    static final String sameClsBookTitle = "same classloader get title";

    @Test
    void action031_001() throws IOException, ClassNotFoundException {
        // use regular serialization
        Book mockedBookSameClassLoader = mock(Book.class, withSettings().serializable());
        when(mockedBookSameClassLoader.getTitle()).thenReturn(sameClsBookTitle);
        // Serialization and deserialization within the same classloader
        byte[] serializedSameClassLoader = serialize(mockedBookSameClassLoader);
        Book deserializedSameClassLoader = deserialize(serializedSameClassLoader);
        assertEquals(sameClsBookTitle, deserializedSameClassLoader.getTitle());
    }

    // can separate run action031_002 and action031_003
    @Test
    void action031_002() throws Exception {
        // use serialization across classloaders
        Book mockedBookAcrossClassLoaders = mock(Book.class, withSettings().serializable(ACROSS_CLASSLOADERS));
        // Serializing the mocked book
        when(mockedBookAcrossClassLoaders.getTitle()).thenReturn(acrossClsBookTitle);
        serializeAcrossCl(mockedBookAcrossClassLoaders);
    }

    @Test
    void action031_003() throws Exception {
         // Serializing the mocked book
        Book deSerialBook = deserializeAcrossCl();
        assertEquals(acrossClsBookTitle, deSerialBook.getTitle());
    }

    private static void serializeAcrossCl(Object object) throws Exception {
        try (FileOutputStream fileOut = new FileOutputStream("serializedBook.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
            System.out.println("Object serialized successfully.");
        }
    }

    private static Book deserializeAcrossCl() throws Exception {
        try (FileInputStream fileIn = new FileInputStream("serializedBook.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Book book = (Book) in.readObject();
            System.out.println("Object deserialized successfully.");
            return book;
        }
    }

    private static byte[] serialize(Book book) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(book);
            System.out.println("Object serialized successfully.");
            return bos.toByteArray();
        }
    }

    private static Book deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            Book book = (Book) in.readObject();
            System.out.println("Object deserialized successfully.");
            return book;
        }
    }
}
