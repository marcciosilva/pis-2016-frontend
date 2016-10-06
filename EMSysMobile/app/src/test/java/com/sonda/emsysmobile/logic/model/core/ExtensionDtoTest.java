package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marccio on 10/5/16.
 */
public class ExtensionDtoTest {


    private int mIdentifier;
    private String mInformant;
    private String mPhone;
    private Date mTimeStamp;
    private Date mCreatedDate;
    private boolean mInProcess;
    private String mOrigin;
    private String mSectorCode;
    private String mStreet;
    private String mCorner;
    private String mNumber;
    private String mDepartment;
    private List<ExtensionDto> mExtensions;
    // Category.
    private CategoryDto mCategory;
    private int mCategoryIdentifier;
    private String mCategoryCode;
    private String mCategoryKey;
    private CategoryPriority mCategoryPriority;
    private boolean mCategoryIsActive;
    // Extension.
    private ExtensionDto mExtensionDto;
    private int mExtensionIdentifier;
    private String mExtensionDescription;
    private ExtensionState mExtensionState;
    private Date mExtensionTimeStamp;
    private CategoryDto mExtensionCategory;
    private ZoneDto mExtensionZone;
    private transient EventDto mExtensionEvent;
    // Extension > ZoneDto.
    private String mExtensionZoneName;
    private int mExtensionZoneIdentifier;
    private String mExtensionZoneExecUnitName;

    @Before
    public void setUp() throws Exception {
        mIdentifier = 1;
        mInformant = "mInformant";
        mPhone = "mPhone";
        mTimeStamp = new Date(2016, 10, 5);
        mCreatedDate = new Date(2016, 10, 5);
        mInProcess = true;
        mOrigin = "mOrigin";
        mSectorCode = "mSectorCode";
        mStreet = "mStreet";
        mCorner = "mCorner";
        mNumber = "mNumber";
        mDepartment = "mDepartment";
        mExtensions = new ArrayList<>();
        // Category.
        mCategoryIdentifier = 1;
        mCategoryCode = "categoryCode";
        mCategoryKey = "categoryKey";
        mCategoryPriority = CategoryPriority.HIGH;
        mCategoryIsActive = true;
        mCategory = new CategoryDto(mCategoryIdentifier, mCategoryCode, mCategoryKey,
                mCategoryPriority, mCategoryIsActive);
        // Extension.
        mExtensionIdentifier = 1;
        mExtensionDescription = "description";
        mExtensionState = ExtensionState.CLOSED;
        mExtensionTimeStamp = new Date(2016, 10, 5);
        mExtensionCategory = mCategory;
        mExtensionZoneName = "zone1";
        mExtensionZoneIdentifier = 1;
        mExtensionZoneExecUnitName = "ue1";
        mExtensionZone = new ZoneDto(mExtensionZoneName, mExtensionZoneIdentifier,
                mExtensionZoneExecUnitName);
        // EventDto.
        mExtensionEvent = new EventDto(mIdentifier, mInformant, mPhone, mTimeStamp, mCreatedDate,
                mInProcess, mOrigin, mSectorCode, mStreet, mCorner, mNumber, mDepartment,
                mExtensions, mCategory);
        mExtensionDto = new ExtensionDto(mExtensionIdentifier, mExtensionDescription,
                mExtensionState, mExtensionTimeStamp, mExtensionCategory, mExtensionZone,
                mExtensionEvent);
        mExtensions.add(mExtensionDto);
    }

    @Test
    public void getIdentifier_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getIdentifier() == mExtensionIdentifier);
    }

    @Test
    public void setIdentifier_DifferentAttribute_ReturnsTrue() throws Exception {
        int testIdentifier = 2;
        mExtensionDto.setIdentifier(testIdentifier);
        assertTrue(mExtensionDto.getIdentifier() == testIdentifier);
    }

    @Test
    public void getDescription_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getDescription().equals(mExtensionDescription));
    }

    @Test
    public void setDescription_DifferentAttribute_ReturnsTrue() throws Exception {
        String testDescription = "alt_description";
        mExtensionDto.setDescription(testDescription);
        assertTrue(mExtensionDto.getDescription().equals(testDescription));
    }

    @Test
    public void getExtensionState_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getExtensionState().equals(mExtensionState));
    }

    @Test
    public void setExtensionState_DifferentAttribute_ReturnsTrue() throws Exception {
        ExtensionState testExtensionState = ExtensionState.DISPATCHED;
        mExtensionDto.setExtensionState(testExtensionState);
        assertTrue(mExtensionDto.getExtensionState().equals(testExtensionState));
    }

    @Test
    public void getTimeStamp_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getTimeStamp().equals(mExtensionTimeStamp));
    }

    @Test
    public void setTimeStamp_DifferentAttribute_ReturnsTrue() throws Exception {
        Date testDate = new Date(1992, 10, 12);
        mExtensionDto.setTimeStamp(testDate);
        assertTrue(mExtensionDto.getTimeStamp().equals(testDate));
    }

    @Test
    public void getCategory_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getCategory().equals(mExtensionCategory));
    }

    @Test
    public void setCategory_DifferentAttribute_ReturnsTrue() throws Exception {
        int testCategoryIdentifier = 1;
        String testCategoryCode = "categoryCode";
        String testCategoryKey = "categoryKey";
        CategoryPriority testCategoryPriority = CategoryPriority.HIGH;
        boolean testCategoryIsActive = true;
        CategoryDto testCategory = new CategoryDto(testCategoryIdentifier, testCategoryCode, testCategoryKey,
                testCategoryPriority, testCategoryIsActive);
        mExtensionDto.setCategory(testCategory);
        assertTrue(mExtensionDto.getCategory().equals(testCategory));
    }

    @Test
    public void getZone_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getZone().equals(mExtensionZone));
    }

    @Test
    public void setZone_DifferentAttribute_ReturnsTrue() throws Exception {
        ZoneDto testExtensionZone;
        String testExtensionZoneName;
        int testExtensionZoneIdentifier;
        String testExtensionZoneExecUnitName;
        testExtensionZoneName = "zone1";
        testExtensionZoneIdentifier = 1;
        testExtensionZoneExecUnitName = "ue1";
        testExtensionZone = new ZoneDto(testExtensionZoneName, testExtensionZoneIdentifier,
                testExtensionZoneExecUnitName);
        mExtensionDto.setZone(testExtensionZone);
        assertTrue(mExtensionDto.getZone().equals(testExtensionZone));
    }

    @Test
    public void getEvent_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionDto.getEvent().equals(mExtensionEvent));
    }

    @Test
    public void setEvent_DifferentAttribute_ReturnsTrue() throws Exception {
        EventDto testEventDto;
        int testIdentifier;
        String testInformant;
        String testPhone;
        Date testTimeStamp;
        Date testCreatedDate;
        boolean testInProcess;
        String testOrigin;
        String testSectorCode;
        String testStreet;
        String testCorner;
        String testNumber;
        String testDepartment;
        List<ExtensionDto> testExtensions;
        // Category.
        CategoryDto testCategory;
        int testCategoryIdentifier;
        String testCategoryCode;
        String testCategoryKey;
        CategoryPriority testCategoryPriority;
        boolean testCategoryIsActive;
        // Extension.
        ExtensionDto testExtension;
        int testExtensionIdentifier;
        String testExtensionDescription;
        ExtensionState testExtensionState;
        Date testExtensionTimeStamp;
        CategoryDto testExtensionCategory;
        ZoneDto testExtensionZone;
        EventDto testExtensionEvent;
        // Extension > ZoneDto.
        String testExtensionZoneName;
        int testExtensionZoneIdentifier;
        String testExtensionZoneExecUnitName;
        testIdentifier = 2;
        testInformant = "mInformant";
        testPhone = "mPhone";
        testTimeStamp = new Date(2016, 10, 5);
        testCreatedDate = new Date(2016, 10, 5);
        testInProcess = true;
        testOrigin = "mOrigin";
        testSectorCode = "mSectorCode";
        testStreet = "mStreet";
        testCorner = "mCorner";
        testNumber = "mNumber";
        testDepartment = "mDepartment";
        testExtensions = new ArrayList<>();
        // Category.
        testCategoryIdentifier = 1;
        testCategoryCode = "categoryCode";
        testCategoryKey = "categoryKey";
        testCategoryPriority = CategoryPriority.HIGH;
        testCategoryIsActive = true;
        testCategory = new CategoryDto(testCategoryIdentifier, testCategoryCode, testCategoryKey,
                testCategoryPriority, testCategoryIsActive);
        // Extension.
        testExtensionIdentifier = 1;
        testExtensionDescription = "description";
        testExtensionState = ExtensionState.CLOSED;
        testExtensionTimeStamp = new Date(2016, 10, 5);
        testExtensionCategory = testCategory;
        testExtensionZoneName = "zone1";
        testExtensionZoneIdentifier = 1;
        testExtensionZoneExecUnitName = "ue1";
        testExtensionZone = new ZoneDto(testExtensionZoneName, testExtensionZoneIdentifier,
                testExtensionZoneExecUnitName);
        // EventDto.
        testExtensionEvent = new EventDto(testIdentifier, testInformant, testPhone, testTimeStamp, testCreatedDate,
                testInProcess, testOrigin, testSectorCode, testStreet, testCorner, testNumber, testDepartment,
                testExtensions, testCategory);
        testExtension = new ExtensionDto(testExtensionIdentifier, testExtensionDescription,
                testExtensionState, testExtensionTimeStamp, testExtensionCategory, testExtensionZone,
                testExtensionEvent);
        testExtensions.add(testExtension);
        testEventDto = new EventDto(testIdentifier, testInformant, testPhone, testTimeStamp, testCreatedDate,
                testInProcess, testOrigin, testSectorCode, testStreet, testCorner, testNumber, testDepartment,
                testExtensions, testCategory);
        mExtensionDto.setEvent(testEventDto);
        assertTrue(mExtensionDto.getEvent().equals(testEventDto));
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mExtensionDto.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        RoleDto rol = new RoleDto(null, null);
        assertFalse(mExtensionDto.equals(rol));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
        // Category.
        CategoryDto testCategory;
        int testCategoryIdentifier;
        String testCategoryCode;
        String testCategoryKey;
        CategoryPriority testCategoryPriority;
        boolean testCategoryIsActive;
        // Extension.
        ExtensionDto testExtensionDto;
        int testExtensionIdentifier;
        String testExtensionDescription;
        ExtensionState testExtensionState;
        Date testExtensionTitesteStatestp;
        CategoryDto testExtensionCategory;
        ZoneDto testExtensionZone;
        EventDto testExtensionEvent;
        // Extension > ZoneDto.
        String testExtensionZoneNateste;
        int testExtensionZoneIdentifier;
        String testExtensionZoneExecUnitNateste;
        // Category.
        testCategoryIdentifier = 1;
        testCategoryCode = "categoryCode";
        testCategoryKey = "categoryKey";
        testCategoryPriority = CategoryPriority.HIGH;
        testCategoryIsActive = true;
        testCategory = new CategoryDto(testCategoryIdentifier, testCategoryCode, testCategoryKey,
                testCategoryPriority, testCategoryIsActive);
        testExtensionIdentifier = 1;
        testExtensionDescription = "description";
        testExtensionState = ExtensionState.CLOSED;
        testExtensionTitesteStatestp = new Date(2016, 10, 5);
        testExtensionCategory = testCategory;
        testExtensionZoneNateste = "zone1";
        testExtensionZoneIdentifier = 1;
        testExtensionZoneExecUnitNateste = "ue1";
        testExtensionZone = new ZoneDto(testExtensionZoneNateste, testExtensionZoneIdentifier,
                testExtensionZoneExecUnitNateste);
        // EventDto.
        testExtensionEvent = null;
        testExtensionDto = new ExtensionDto(testExtensionIdentifier, testExtensionDescription,
                testExtensionState, testExtensionTitesteStatestp, testExtensionCategory, testExtensionZone,
                testExtensionEvent);
        assertTrue(mExtensionDto.equals(testExtensionDto));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
        // Category.
        CategoryDto testCategory;
        int testCategoryIdentifier;
        String testCategoryCode;
        String testCategoryKey;
        CategoryPriority testCategoryPriority;
        boolean testCategoryIsActive;
        // Extension.
        ExtensionDto testExtensionDto;
        int testExtensionIdentifier;
        String testExtensionDescription;
        ExtensionState testExtensionState;
        Date testExtensionTitesteStatestp;
        CategoryDto testExtensionCategory;
        ZoneDto testExtensionZone;
        EventDto testExtensionEvent;
        // Extension > ZoneDto.
        String testExtensionZoneNateste;
        int testExtensionZoneIdentifier;
        String testExtensionZoneExecUnitNateste;
        // Category.
        testCategoryIdentifier = 1;
        testCategoryCode = "categoryCode";
        testCategoryKey = "categoryKey";
        testCategoryPriority = CategoryPriority.HIGH;
        testCategoryIsActive = true;
        testCategory = new CategoryDto(testCategoryIdentifier, testCategoryCode, testCategoryKey,
                testCategoryPriority, testCategoryIsActive);
        testExtensionIdentifier = 2;
        testExtensionDescription = "description";
        testExtensionState = ExtensionState.CLOSED;
        testExtensionTitesteStatestp = new Date(2016, 10, 5);
        testExtensionCategory = testCategory;
        testExtensionZoneNateste = "zone1";
        testExtensionZoneIdentifier = 1;
        testExtensionZoneExecUnitNateste = "ue1";
        testExtensionZone = new ZoneDto(testExtensionZoneNateste, testExtensionZoneIdentifier,
                testExtensionZoneExecUnitNateste);
        // EventDto.
        testExtensionEvent = null;
        testExtensionDto = new ExtensionDto(testExtensionIdentifier, testExtensionDescription,
                testExtensionState, testExtensionTitesteStatestp, testExtensionCategory, testExtensionZone,
                testExtensionEvent);
        assertFalse(mExtensionDto.equals(testExtensionDto));
    }

}