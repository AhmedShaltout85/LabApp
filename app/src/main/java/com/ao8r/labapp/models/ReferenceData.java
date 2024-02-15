package com.ao8r.labapp.models;

import com.ao8r.labapp.customiz.CustomTimeAndDate;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;

public class ReferenceData {

    public static final String urlIP = "http://41.33.226.211:5010/awlabs";
    // user sample Data
    public static String sampleQrCode;
    public static String labCodeLt;
    public static String sampleCodeLt;
    public static String xLatitude;
    public static String yLongitude;
    public static String notes;
    //    Ref. Params
//    public static final String LAB_CODE_TEM = "labcode";
//    public static String LAB_CODE_VAL = "";
    public static String labCode ;
    public static String sampleCode;
    public static String sampleName;
    public static String labName;
    public static String sampleX;
    public static String sampleY;
    //Add new Broken Point sample
    public static int maxSampleCode;
    public static String sectorName;
    public static String batchName;
    public static String sampleKind;
    public static String sampleDescription;
    public static String sampleBrokenX;
    public static String sampleBrokenY;
    public static String notesBroken;

    // On site Tests


    public static String testCode;
    public static String testNameArabic;
    public static String testNameEnglish;
    public static double testMaxValue;
    public static double testMinValue;
    public static String testUnit;
    public static String specificCode;
    public static String testSampleKind;
    public static String testPeriod;
    public static String pointersKind;
    public static String testResultTime;
    public static String testValue;
    public static String onTestXLoc;
    public static String onTestYLoc;
    public static String onSiteTestSampleLabCode;
    public static String onSiteTestSampleCode;

    // labs_daily_tests

    public static String TOAST_RANGE_MIN_MAX = "فضلا أدخل قيمة مابين ";
    public static int maxGeneralSerial;
    public static String onSiteTestSampleLabName;
    public static String onSiteTestLabName;
    public static String onSiteTestSectorName;
    public static String onSiteTestBatchName;
    public static String onSiteTestSampleName;
    public static String onSiteTestSampleKind;
    public static String onSiteTestSampleDescription;
    public static String onSiteTestGroupName = "onSiteTest";
    public static String onSiteTestChemistUserName = "كيميائى";
    public static double onSiteTestValueOneYearAgo;
    public static String onSiteTestHour;
    public static String onSiteTestHourCode = String.valueOf(CustomTimeAndDate.convertCurrentTimeToString());
    public static String testValueOutOfRange;
    public static String outOfRangeText;

//    //Global LAB DATA
//    public static String LAB_NAME = onSiteTestSampleLabName;
//    public static String SECTOR_NAME;
    // Spinner vars
public static String SELECTED_ITEM_SPINNER;

    //TRACKING INFO
    public static String TRACK_LAB_CODE;
    public static String TRACK_BREAK_ID;
    public static String TRACK_BREAK_DATE;
    // Compared X and Y
    public static String originX;
    public static String originY;
    public static String originSampleName;
    public static String originBatchName;
    public static double disBetweenTowPoints;
    // user ifo
    public static long currentUserId;
    public static String userFirstName;
    public static String userLastName;
    public static String userPassword;
    public static String loginUser;
    public static int userControl;
    //    write/read file

    public static final String LOGIN_FILE_NAME = "labsBaseIP.txt";
    public static final String LAB_CODE_FILE_NAME = "lab_code.txt";
    public static String URL_CONNECT_LINK;
    public static String BASE_IP = "baseIP";
    public static String LAB_CODE_CACHE = "labCodeCache";


}
