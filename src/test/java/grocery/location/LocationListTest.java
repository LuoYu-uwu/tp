package grocery.location;

import exceptions.DuplicateException;
import exceptions.GitException;
import exceptions.emptyinput.EmptyInputException;
import exceptions.nosuch.NoSuchObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationListTest {
    @Test
    public void addLocation_findLocation_success() {
        try {
            LocationList.addLocation("freezer");
            LocationList.findLocation("freezer");
        } catch (GitException e) {
            fail("findLocation should be successful.");
        }
    }

    @Test
    public void addLocation_emptyInput_exceptionThrown() {
        assertThrows(EmptyInputException.class, () -> LocationList.addLocation(""));
    }

    @Test
    public void addLocation_duplicate_exceptionThrown() {
        assertThrows(DuplicateException.class, () -> LocationList.addLocation("FREEZER"));
    }

    @Test
    public void findLocation_noSuchLocation_exceptionThrown() {
        assertThrows(NoSuchObjectException.class, () -> LocationList.findLocation("cabinet"));
    }

    @Test
    public void removeLocation_success() {
        try {
            LocationList.addLocation("freezer");
            LocationList.removeLocation("freezer");
        } catch (GitException e) {
            fail("removeLocation should be successful.");
        }
    }

    @Test
    public void removeLocation_noSuchLocation_exceptionThrown() {
        assertThrows(NoSuchObjectException.class, () -> LocationList.removeLocation("cubby"));
    }
}
