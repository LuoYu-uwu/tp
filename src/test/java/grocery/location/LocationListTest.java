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
            LocationList.addLocation("back of freezer");
            LocationList.findLocation("back of freezer");
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
        assertThrows(DuplicateException.class, () -> LocationList.addLocation("BACK of freezer"));
    }

    @Test
    public void findLocation_noSuchLocation_exceptionThrown() {
        assertThrows(NoSuchObjectException.class, () -> LocationList.findLocation("Nuclear reactor"));
    }

    @Test
    public void removeLocation_success() {
        try {
            LocationList.addLocation("front of freezer");
            LocationList.removeLocation("front of freezer");
        } catch (GitException e) {
            fail("removeLocation should be successful.");
        }
    }

    @Test
    public void removeLocation_noSuchLocation_exceptionThrown() {
        assertThrows(NoSuchObjectException.class, () -> LocationList.removeLocation("cubby"));
    }
}
