package pr.iceworld.fernando;

import java.util.UUID;

public class OuterClass {
    public abstract class InnerClass {
        public String getSerialNo() {
            return UUID.randomUUID().toString();
        }
    }
}
