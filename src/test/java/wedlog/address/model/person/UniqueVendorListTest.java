package wedlog.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static wedlog.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wedlog.address.testutil.Assert.assertThrows;
import static wedlog.address.testutil.TypicalVendors.ANNE;
import static wedlog.address.testutil.TypicalVendors.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import wedlog.address.model.person.exceptions.DuplicateVendorException;
import wedlog.address.model.person.exceptions.VendorNotFoundException;
import wedlog.address.testutil.VendorBuilder;

public class UniqueVendorListTest {

    private final UniqueVendorList uniqueVendorList = new UniqueVendorList();

    @Test
    public void contains_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.contains(null));
    }

    @Test
    public void contains_vendorNotInList_returnsFalse() {
        assertFalse(uniqueVendorList.contains(ANNE));
    }

    @Test
    public void contains_vendorInList_returnsTrue() {
        uniqueVendorList.add(ANNE);
        assertTrue(uniqueVendorList.contains(ANNE));
    }

    @Test
    public void contains_vendorWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVendorList.add(ANNE);
        Vendor editedAnne = new VendorBuilder(ANNE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueVendorList.contains(editedAnne));
    }

    @Test
    public void add_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.add(null));
    }

    @Test
    public void add_duplicateVendor_throwsDuplicateVendorException() {
        uniqueVendorList.add(ANNE);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.add(ANNE));
    }

    @Test
    public void setVendor_nullTargetVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(null, ANNE));
    }

    @Test
    public void setVendor_nullEditedVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendor(ANNE, null));
    }

    @Test
    public void setVendor_targetVendorNotInList_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.setVendor(ANNE, ANNE));
    }

    @Test
    public void setVendor_editedVendorIsSameVendor_success() {
        uniqueVendorList.add(ANNE);
        uniqueVendorList.setVendor(ANNE, ANNE);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(ANNE);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasSameIdentity_success() {
        uniqueVendorList.add(ANNE);
        Vendor editedAlice = new VendorBuilder(ANNE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueVendorList.setVendor(ANNE, editedAlice);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(editedAlice);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasDifferentIdentity_success() {
        uniqueVendorList.add(ANNE);
        uniqueVendorList.setVendor(ANNE, BOB);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(BOB);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendor_editedVendorHasNonUniqueIdentity_throwsDuplicateVendorException() {
        uniqueVendorList.add(ANNE);
        uniqueVendorList.add(BOB);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendor(ANNE, BOB));
    }

    @Test
    public void remove_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.remove(null));
    }

    @Test
    public void remove_vendorDoesNotExist_throwsVendorNotFoundException() {
        assertThrows(VendorNotFoundException.class, () -> uniqueVendorList.remove(ANNE));
    }

    @Test
    public void remove_existingVendor_removesVendor() {
        uniqueVendorList.add(ANNE);
        uniqueVendorList.remove(ANNE);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_nullUniqueVendorList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((UniqueVendorList) null));
    }

    @Test
    public void setVendors_uniqueVendorList_replacesOwnListWithProvidedUniqueVendorList() {
        uniqueVendorList.add(ANNE);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(BOB);
        uniqueVendorList.setVendors(expectedUniqueVendorList);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVendorList.setVendors((List<Vendor>) null));
    }

    @Test
    public void setVendors_list_replacesOwnListWithProvidedList() {
        uniqueVendorList.add(ANNE);
        List<Vendor> vendorList = Collections.singletonList(BOB);
        uniqueVendorList.setVendors(vendorList);
        UniqueVendorList expectedUniqueVendorList = new UniqueVendorList();
        expectedUniqueVendorList.add(BOB);
        assertEquals(expectedUniqueVendorList, uniqueVendorList);
    }

    @Test
    public void setVendors_listWithDuplicateVendors_throwsDuplicateVendorException() {
        List<Vendor> listWithDuplicateVendors = Arrays.asList(ANNE, ANNE);
        assertThrows(DuplicateVendorException.class, () -> uniqueVendorList.setVendors(listWithDuplicateVendors));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueVendorList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueVendorList.asUnmodifiableObservableList().toString(), uniqueVendorList.toString());
    }
}
