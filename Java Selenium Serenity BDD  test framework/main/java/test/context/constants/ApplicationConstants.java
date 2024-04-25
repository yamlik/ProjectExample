package com..test.context.constants;

public final class ApplicationConstants {

    // Generic messages on Toaster box
    public static final String MESSAGE_WHEN_SAVED = "Saved suTSNTssfully.";

    //     Utilities Constants
    public static final String SYSTEM_CONFIGURATION_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\data\\UI\\systemConfigurationFile\\productUnderTest_Data_.DAT";
    public static final String DATA_IMPORT_SUTSNTSS_MESSAGE = "SuTSNTssfully Imported.";
    public static final String FILE_IMPORT_SUTSNTSS_ALERT_MESSAGE = "Import Completed. For some productUnderTest Data, changes take effect upon next logon.";

    //    API Constants
    public static final String ATSNTSS_TOKEN_PLAIN_REQUEST_BODY_6_3 = "password=URmCfWeVPdK0GR%2B8I2UeoQ%3D%3D&username=ADMINISTRATOR&grant_type=password&database=Default&captcha_text=";
    public static final String ATSNTSS_TOKEN_NONADMIN_PLAIN_REQUEST_BODY_6_2 = "password=SMCMAXUteKCif82toW8H5g%3D%3D&username=NONADMIN&grant_type=password&database=Default&captcha_text=";


    public static final String ATSNTSS_TOKEN_PLAIN_REQUEST_BODY_6_2 = "password=5uanFP2jYBdkaWpA1oPfXw%3D%3D&username=administrator&grant_type=password&database=Default&captcha_text=";
  //  public static final String ATSNTSS_TOKEN_PLAIN_REQUEST_BODY_6_2 = "password=1hb%2B3oalPkexpCnFqrBXfw%3D%3D&username=administrator&grant_type=password&database=Default&captcha_text=";


    //   Application Constants
    public static String FraudScoreATI0001="40";
    public static String FraudScoreM000001="30";
    public static String FraudScoreRL00001="40";
    public static String FraudScoreAB00004="130";
    public static String FraudScoreA000003="80";
    public static String FraudScoreAL00001="10";
    public static String FraudScoreAB00003="130";
    public static String FraudScoreML0001="29";
    public static String FraudScoreAP00009="130";
    public static String FraudScoreAS00005="130";
    public static String FraudScoreFuzy001="30";
    public static String FraudScoreAS00006="130";
    public static String FraudScoreAF00018="130";
    public static String FraudScoreAF00019="130";
    public static String FraudScoreA000017="130";
    public static String FraudScoreA000033="120";
    public static String FraudScoreL000001="60";
    public static String FraudScoreDI00002="10";
    public static String FraudScoreU000004="60";
    public static String FraudScorePar0001="30";
    public static String FraudScorePar0002="30";
    public static String FraudScorePar0003="30";
    public static String FraudScoreHierarchy_is_Turned_ON="30";
    public static String FraudScoreHierarchy_is_Turned_OFF="160";
    public static String FraudScoreI000003="40";
    public static String FraudScoreIF00001="20";
    public static String FraudScoreIF00002="20";
    public static String FraudScoreIF00003="20";
    public static String FraudScoreIF00004="20";
    public static String FraudAlert="Suspect";
    public static String RuleTriggerCatMsg="ATI0001";
    public static String RuleTriggerCatMsgAL00001="AL00001";
    public static String RuleTriggerCatMsgDI00002="DI00002";
    public static String RuleTriggerCatMsgU000004="U000004";
    public static String FraudAlertSuspectLowerRange="29";
    public static String FraudAlertSuspectUpperRange="130";
    public static String FraudAlertHFPLowerRange="129";
    public static String FraudAlertHFPUpperRange="1000";
    public static String BlueColor="#0c5460";
    public static final String toggleOnColour="#3fcfd5";
    public static final String toggleOffColour="#cccccc";
    public static String GreenColor="#155724";
    public static String LikeRuleCondition="NES%";
    public static String valueOnlyAgeCondition="25";
    public static String variableMatchCondition="GETDATE()-14";
    public static String valueOnlyIncomeCondition="50000";
    public  static  String NotesFirstMessage="Workflow executed";
    public  static  String NotesSecondMessage="Application fraud checked - Clean (ID00001)";

    public  static  String NotesApplicantDeletedMessage="Applicant deleted";
    public  static  String NotesUserDeletedMessage="User deleted";

    public  static  String NotesThirdMessage="Record added";
    public static String RedColor="#b30437";
    public static String BatchNotesMessage="Batch Notes added";
    public  static  String RulesDescriptionATI0001="Company Name Like NES";
    public  static  String RulesDescriptionID00001="Id Number 1 on Watchlist";
    public  static  String RulesDescriptionAP00009="Company Name on Watchlist";
    public  static  String RulesDescriptionFuzy001= "AutomationRule1";
    public  static  String RulesDescriptionDI00002= "Age of Applicant < 25 years and Annual Income > $50,000";
    public  static  String RulesDescriptionIF00005= "Security Category";
    public static String savingsAmountField="Map Application User Field 2 Data to Savings Amount Field in Input";
    public static String accountNumber="Map Application User Field 3 Data to Account Number Field in Input";
    public static String nature_Of_Fraud="Map Applicant User Field 7 Data to Nature of Fraud Field in Input";
    public static String incomeField="Map Applicant User Field 10 Data to Income Field in Input";
    //   Action Constants
    public static final String KNOWN_FRAUD_ACTION="Known Fraud";
    public static final String SUSPICIOUS_ACTION="Suspicious";
    public static final String UNDER_INVESTIGATION_ACTION="Under Investigation";
    public static final String FALSE_POSITIVE_ACTION="False Positive";
    // Rule Constants
    public  static final String  errorFuzzyRule="An error was detected with one of the listed Rule Definitions.";
    public  static  final String confirmationFuzzyRule="Using Fuzzy match may take a considerable amount of time. Do you want to continue?";
    public  static  final String addRuleSet="The rule has been added. Do you want to add the rule to a rule set?";
    public  static  final String addRule="Do you want to add the rule?";
}
