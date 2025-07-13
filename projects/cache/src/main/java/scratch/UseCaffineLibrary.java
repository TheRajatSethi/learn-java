package scratch;

import com.github.benmanes.caffeine.cache.Caffeine;

public class UseCaffineLibrary {
    public static void main(String[] args) {

        var c = Caffeine.newBuilder().build();
        c.put();
        System.out.println(c);

    }
}
