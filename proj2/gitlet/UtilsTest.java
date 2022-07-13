package gitlet;

import org.junit.Test;
import static gitlet.Utils.*;
import static org.junit.Assert.*;

import java.io.File;


public class UtilsTest {
    @Test
    public void testRelativePath() {
        File a = new File("path1/../path1/../path2");
        File b = new File("../proj2");
        File c = new File("../proj2/path1");
        assertEquals("path2", relativize(a).toString());
        assertEquals("", relativize(b).toString());
        assertEquals("path1", relativize(c).toString());
    }

    @Test
    public void testBlob() {
        File f = new File("Makefile");
        System.out.println(f);
        File fBlob = getBlob(f);

        File g = new File("../proj2/Makefile");
        System.out.println(g);
        File gBlob = getBlob(g);

        System.out.println(fBlob);
        System.out.println(gBlob);

        assertEquals(fBlob, gBlob);
    }
}
