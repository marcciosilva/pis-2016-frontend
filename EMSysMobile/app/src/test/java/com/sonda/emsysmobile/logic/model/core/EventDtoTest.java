package com.sonda.emsysmobile.logic.model.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 10/5/16.
 */

public class EventDtoTest {

    private EventDto mExtensionEvent;
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
    private double mLatitude;
    private double mLongitude;
    private List<ExtensionDto> mExtensions;
    // Category.
    private CategoryDto mCategory;
    private int mCategoryIdentifier;
    private String mCategoryCode;
    private String mCategoryKey;
    private CategoryPriority mCategoryPriority;
    private boolean mCategoryIsActive;
    // Extension.
    private ExtensionDto mExtension;
    private int mExtensionIdentifier;
    private String mExtensionDescription;
    private ExtensionState mExtensionState;
    private Date mExtensionTimeStamp;
    private CategoryDto mExtensionCategory;
    private ZoneDto mExtensionZone;
    private transient EventDto bla;
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
        mLatitude = -34.905743;
        mLongitude = -56.198887;
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
                mExtensions, mCategory, mLatitude, mLongitude);
        mExtension = new ExtensionDto(mExtensionIdentifier, mExtensionDescription,
                mExtensionState, mExtensionTimeStamp, mExtensionCategory, mExtensionZone, null,
                mExtensionEvent);
        mExtensions.add(mExtension);
    }

    @Test
    public void getIdentifier_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getIdentifier() == mIdentifier);
    }

    @Test
    public void setIdentifier_DifferentIdentifier_ReturnsTrue() throws Exception {
        int testIdentifier = 2;
        mExtensionEvent.setIdentifier(testIdentifier);
        assertTrue(mExtensionEvent.getIdentifier() == testIdentifier);
    }

    @Test
    public void getInformant_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getInformant().equals(mInformant));
    }

    @Test
    public void setInformant_DifferentInformant_ReturnsTrue() throws Exception {
        String testInformant = "alt_informant";
        mExtensionEvent.setInformant(testInformant);
        assertTrue(mExtensionEvent.getInformant().equals(testInformant));
    }

    @Test
    public void getPhone_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getPhone().equals(mPhone));
    }

    @Test
    public void setPhone_DifferentPhone_ReturnsTrue() throws Exception {
        String testPhone = "alt_phone";
        mExtensionEvent.setPhone(testPhone);
        assertTrue(mExtensionEvent.getPhone().equals(testPhone));
    }

    @Test
    public void getTimeStamp_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getTimeStamp().equals(mTimeStamp));
    }

    @Test
    public void setTimeStamp_DifferentTimeStamp_ReturnsTrue() throws Exception {
        Date testDate = new Date(1992, 12, 1);
        mExtensionEvent.setTimeStamp(testDate);
        assertTrue(mExtensionEvent.getTimeStamp().equals(testDate));
    }

    @Test
    public void getCreatedDate_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getCreatedDate().equals(mCreatedDate));
    }

    @Test
    public void setCreatedDate_DifferentCreatedDate_ReturnsTrue() throws Exception {
        Date testDate = new Date(1992, 12, 1);
        mExtensionEvent.setCreatedDate(testDate);
        assertTrue(mExtensionEvent.getCreatedDate().equals(testDate));
    }

    @Test
    public void isInProcess_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.isInProcess() == mInProcess);
    }

    @Test
    public void setInProcess_DifferentInProcess_ReturnsTrue() throws Exception {
        mExtensionEvent.setInProcess(!mInProcess);
        assertTrue(mExtensionEvent.isInProcess() == !mInProcess);
    }

    @Test
    public void getOrigin_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getOrigin().equals(mOrigin));
    }

    @Test
    public void setOrigin_DifferentOrigin_ReturnsTrue() throws Exception {
        String testOrigin = "alt_origin";
        mExtensionEvent.setOrigin(testOrigin);
        assertTrue(mExtensionEvent.getOrigin().equals(testOrigin));
    }

    @Test
    public void getSectorCode_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getSectorCode().equals(mSectorCode));
    }

    @Test
    public void setSectorCode_DifferentSectorCode_ReturnsTrue() throws Exception {
        String testSectorCode = "alt_sectorCode";
        mExtensionEvent.setSectorCode(testSectorCode);
        assertTrue(mExtensionEvent.getSectorCode().equals(testSectorCode));
    }

    @Test
    public void getStreet_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getStreet().equals(mStreet));
    }

    @Test
    public void setStreet_DifferentStreet_ReturnsTrue() throws Exception {
        String testStreet = "alt_street";
        mExtensionEvent.setStreet(testStreet);
        assertTrue(mExtensionEvent.getStreet().equals(testStreet));
    }

    @Test
    public void getCorner_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getCorner().equals(mCorner));
    }

    @Test
    public void setCorner_DifferentCorner_ReturnsTrue() throws Exception {
        String testCorner = "alt_corner";
        mExtensionEvent.setCorner(testCorner);
        assertTrue(mExtensionEvent.getCorner().equals(testCorner));
    }

    @Test
    public void getNumber_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getNumber().equals(mNumber));
    }

    @Test
    public void setNumber_DifferentNumber_ReturnsTrue() throws Exception {
        String testNumber = "alt_number";
        mExtensionEvent.setNumber(testNumber);
        assertTrue(mExtensionEvent.getNumber().equals(testNumber));
    }

    @Test
    public void getDepartment_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getDepartment().equals(mDepartment));
    }

    @Test
    public void setDepartment_DifferentDepartment_ReturnsTrue() throws Exception {
        String testDepartment = "alt_department";
        mExtensionEvent.setDepartment(testDepartment);
        assertTrue(mExtensionEvent.getDepartment().equals(testDepartment));
    }

    @Test
    public void getExtensions_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getExtensions().equals(mExtensions));
    }

    @Test
    public void setExtensions_DifferentExtensions_ReturnsTrue() throws Exception {
        List<ExtensionDto> testExtensions = new ArrayList<>();
        mExtensionEvent.setExtensions(testExtensions);
        assertTrue(mExtensionEvent.getExtensions().equals(testExtensions));
    }

    @Test
    public void getCategory_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getCategory().equals(mCategory));
    }

    @Test
    public void setCategory_DifferentCategory_ReturnsTrue() throws Exception {
        CategoryDto testCategory = new CategoryDto(1, "alt_code", "alt_key", CategoryPriority.HIGH, false);
        mExtensionEvent.setCategory(testCategory);
        assertTrue(mExtensionEvent.getCategory().equals(testCategory));
    }

    @Test
    public void getLatitude_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getLatitude() == mLatitude);
    }

    @Test
    public void setLatitude_DifferentAttribute_ReturnsTrue() throws Exception {
        double testLatitude = -3.5;
        mExtensionEvent.setLatitude(testLatitude);
        assertTrue(mExtensionEvent.getLatitude() == testLatitude);
    }

    @Test
    public void getLongitude_CompareWithSameAttribute_ReturnsTrue() throws Exception {
        assertTrue(mExtensionEvent.getLongitude() == mLongitude);
    }

    @Test
    public void setLongitude_DifferentAttribute_ReturnsTrue() throws Exception {
        double testLongitude = -3.5;
        mExtensionEvent.setLongitude(testLongitude);
        assertTrue(mExtensionEvent.getLongitude() == testLongitude);
    }

    @Test
    public void equals_NullComparison_ReturnsFalse() {
        assertFalse(mExtensionEvent.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        ResourceDto resource = new ResourceDto(null, 0);
        assertFalse(mExtensionEvent.equals(resource));
    }

    @Test
    public void equals_CompareWithSameFields_ReturnsTrue() {
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
        double testLatitude;
        double testLongitude;
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
        testIdentifier = 1;
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
        testLatitude = -34.905743;
        testLongitude = -56.198887;
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
                testExtensions, testCategory, testLatitude, testLongitude);
        testExtension = new ExtensionDto(testExtensionIdentifier, testExtensionDescription,
                testExtensionState, testExtensionTimeStamp, testExtensionCategory, testExtensionZone, null,
                testExtensionEvent);
        testExtensions.add(testExtension);
        assertTrue(mExtensionEvent.equals(testExtensionEvent));
    }

    @Test
    public void equals_CompareWithDifferentFields_ReturnsFalse() {
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
        double testLatitude;
        double testLongitude;
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
        testLatitude = -34.905743;
        testLongitude = -56.198887;
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
                testExtensions, testCategory, testLatitude, testLongitude);
        testExtension = new ExtensionDto(testExtensionIdentifier, testExtensionDescription,
                testExtensionState, testExtensionTimeStamp, testExtensionCategory, testExtensionZone, null,
                testExtensionEvent);
        testExtensions.add(testExtension);
        assertFalse(mExtensionEvent.equals(testExtensionEvent));
    }

}
