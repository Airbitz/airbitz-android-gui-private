/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.airbitz.api;

public class core implements coreConstants {
  public static SWIGTYPE_p_int new_intp() {
    long cPtr = coreJNI.new_intp();
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public static SWIGTYPE_p_int copy_intp(int value) {
    long cPtr = coreJNI.copy_intp(value);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public static void delete_intp(SWIGTYPE_p_int obj) {
    coreJNI.delete_intp(SWIGTYPE_p_int.getCPtr(obj));
  }

  public static void intp_assign(SWIGTYPE_p_int obj, int value) {
    coreJNI.intp_assign(SWIGTYPE_p_int.getCPtr(obj), value);
  }

  public static int intp_value(SWIGTYPE_p_int obj) {
    return coreJNI.intp_value(SWIGTYPE_p_int.getCPtr(obj));
  }

  public static SWIGTYPE_p_long new_longp() {
    long cPtr = coreJNI.new_longp();
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public static SWIGTYPE_p_long copy_longp(int value) {
    long cPtr = coreJNI.copy_longp(value);
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public static void delete_longp(SWIGTYPE_p_long obj) {
    coreJNI.delete_longp(SWIGTYPE_p_long.getCPtr(obj));
  }

  public static void longp_assign(SWIGTYPE_p_long obj, int value) {
    coreJNI.longp_assign(SWIGTYPE_p_long.getCPtr(obj), value);
  }

  public static int longp_value(SWIGTYPE_p_long obj) {
    return coreJNI.longp_value(SWIGTYPE_p_long.getCPtr(obj));
  }

  public static SWIGTYPE_p_int64_t new_int64_tp() {
    long cPtr = coreJNI.new_int64_tp();
    return (cPtr == 0) ? null : new SWIGTYPE_p_int64_t(cPtr, false);
  }

  public static SWIGTYPE_p_int64_t copy_int64_tp(SWIGTYPE_p_int64_t value) {
    long cPtr = coreJNI.copy_int64_tp(SWIGTYPE_p_int64_t.getCPtr(value));
    return (cPtr == 0) ? null : new SWIGTYPE_p_int64_t(cPtr, false);
  }

  public static void delete_int64_tp(SWIGTYPE_p_int64_t obj) {
    coreJNI.delete_int64_tp(SWIGTYPE_p_int64_t.getCPtr(obj));
  }

  public static void int64_tp_assign(SWIGTYPE_p_int64_t obj, SWIGTYPE_p_int64_t value) {
    coreJNI.int64_tp_assign(SWIGTYPE_p_int64_t.getCPtr(obj), SWIGTYPE_p_int64_t.getCPtr(value));
  }

  public static SWIGTYPE_p_int64_t int64_tp_value(SWIGTYPE_p_int64_t obj) {
    return new SWIGTYPE_p_int64_t(coreJNI.int64_tp_value(SWIGTYPE_p_int64_t.getCPtr(obj)), true);
  }

  public static SWIGTYPE_p_double new_doublep() {
    long cPtr = coreJNI.new_doublep();
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public static SWIGTYPE_p_double copy_doublep(double value) {
    long cPtr = coreJNI.copy_doublep(value);
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public static void delete_doublep(SWIGTYPE_p_double obj) {
    coreJNI.delete_doublep(SWIGTYPE_p_double.getCPtr(obj));
  }

  public static void doublep_assign(SWIGTYPE_p_double obj, double value) {
    coreJNI.doublep_assign(SWIGTYPE_p_double.getCPtr(obj), value);
  }

  public static double doublep_value(SWIGTYPE_p_double obj) {
    return coreJNI.doublep_value(SWIGTYPE_p_double.getCPtr(obj));
  }

  public static void setGbIsTestNet(boolean value) {
    coreJNI.gbIsTestNet_set(value);
  }

  public static boolean getGbIsTestNet() {
    return coreJNI.gbIsTestNet_get();
  }

  public static tABC_CC ABC_Initialize(String szRootDir, String szCaCertPath, String pSeedData, long seedLength, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_Initialize(szRootDir, szCaCertPath, pSeedData, seedLength, tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_Terminate() {
    coreJNI.ABC_Terminate();
  }

  public static tABC_CC ABC_Version(SWIGTYPE_p_p_char szVersion, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_Version(SWIGTYPE_p_p_char.getCPtr(szVersion), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_IsTestNet(SWIGTYPE_p_bool pResult, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_IsTestNet(SWIGTYPE_p_bool.getCPtr(pResult), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_ClearKeyCache(tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ClearKeyCache(tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_DataSyncAll(String szUserName, String szPassword, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void fAsyncBitCoinEventCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_DataSyncAll(szUserName, szPassword, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void.getCPtr(fAsyncBitCoinEventCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GeneralInfoUpdate(tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GeneralInfoUpdate(tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetCurrencies(SWIGTYPE_p_p_sABC_Currency paCurrencyArray, SWIGTYPE_p_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetCurrencies(SWIGTYPE_p_p_sABC_Currency.getCPtr(paCurrencyArray), SWIGTYPE_p_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetQuestionChoices(SWIGTYPE_p_p_sABC_QuestionChoices pOut, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetQuestionChoices(SWIGTYPE_p_p_sABC_QuestionChoices.getCPtr(pOut), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeQuestionChoices(tABC_QuestionChoices pQuestionChoices) {
    coreJNI.ABC_FreeQuestionChoices(tABC_QuestionChoices.getCPtr(pQuestionChoices), pQuestionChoices);
  }

  public static tABC_CC ABC_ParseBitcoinURI(String szURI, SWIGTYPE_p_p_sABC_BitcoinURIInfo ppInfo, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ParseBitcoinURI(szURI, SWIGTYPE_p_p_sABC_BitcoinURIInfo.getCPtr(ppInfo), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeURIInfo(tABC_BitcoinURIInfo pInfo) {
    coreJNI.ABC_FreeURIInfo(tABC_BitcoinURIInfo.getCPtr(pInfo), pInfo);
  }

  public static double ABC_SatoshiToBitcoin(SWIGTYPE_p_int64_t satoshi) {
    return coreJNI.ABC_SatoshiToBitcoin(SWIGTYPE_p_int64_t.getCPtr(satoshi));
  }

  public static SWIGTYPE_p_int64_t ABC_BitcoinToSatoshi(double bitcoin) {
    return new SWIGTYPE_p_int64_t(coreJNI.ABC_BitcoinToSatoshi(bitcoin), true);
  }

  public static tABC_CC ABC_ParseAmount(String szAmount, SWIGTYPE_p_uint64_t pAmountOut, long decimalPlaces) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ParseAmount(szAmount, SWIGTYPE_p_uint64_t.getCPtr(pAmountOut), decimalPlaces));
  }

  public static tABC_CC ABC_FormatAmount(SWIGTYPE_p_int64_t amount, SWIGTYPE_p_p_char pszAmountOut, long decimalPlaces, boolean bAddSign, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_FormatAmount(SWIGTYPE_p_int64_t.getCPtr(amount), SWIGTYPE_p_p_char.getCPtr(pszAmountOut), decimalPlaces, bAddSign, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CheckPassword(String szPassword, SWIGTYPE_p_double pSecondsToCrack, SWIGTYPE_p_p_p_sABC_PasswordRule paRules, SWIGTYPE_p_unsigned_int pCountRules, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CheckPassword(szPassword, SWIGTYPE_p_double.getCPtr(pSecondsToCrack), SWIGTYPE_p_p_p_sABC_PasswordRule.getCPtr(paRules), SWIGTYPE_p_unsigned_int.getCPtr(pCountRules), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreePasswordRuleArray(SWIGTYPE_p_p_sABC_PasswordRule aRules, long nCount) {
    coreJNI.ABC_FreePasswordRuleArray(SWIGTYPE_p_p_sABC_PasswordRule.getCPtr(aRules), nCount);
  }

  public static tABC_CC ABC_QrEncode(String szText, SWIGTYPE_p_p_unsigned_char paData, SWIGTYPE_p_unsigned_int pWidth, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_QrEncode(szText, SWIGTYPE_p_p_unsigned_char.getCPtr(paData), SWIGTYPE_p_unsigned_int.getCPtr(pWidth), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SignIn(String szUserName, String szPassword, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SignIn(szUserName, szPassword, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CreateAccount(String szUserName, String szPassword, String szPin, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CreateAccount(szUserName, szPassword, szPin, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetRecoveryQuestions(String szUserName, SWIGTYPE_p_p_char pszQuestions, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetRecoveryQuestions(szUserName, SWIGTYPE_p_p_char.getCPtr(pszQuestions), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CheckRecoveryAnswers(String szUserName, String szRecoveryAnswers, SWIGTYPE_p_bool pbValid, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CheckRecoveryAnswers(szUserName, szRecoveryAnswers, SWIGTYPE_p_bool.getCPtr(pbValid), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_PinLoginExists(String szUserName, SWIGTYPE_p_bool pbExists, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_PinLoginExists(szUserName, SWIGTYPE_p_bool.getCPtr(pbExists), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_PinLoginDelete(String szUserName, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_PinLoginDelete(szUserName, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_PinLogin(String szUserName, String szPin, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_PinLogin(szUserName, szPin, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_PinSetup(String szUserName, String szPassword, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_PinSetup(szUserName, szPassword, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_ListAccounts(SWIGTYPE_p_p_char pszUserNames, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ListAccounts(SWIGTYPE_p_p_char.getCPtr(pszUserNames), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_ChangePassword(String szUserName, String szPassword, String szNewPassword, String szDeprecated, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ChangePassword(szUserName, szPassword, szNewPassword, szDeprecated, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_ChangePasswordWithRecoveryAnswers(String szUserName, String szRecoveryAnswers, String szNewPassword, String szDeprecated, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ChangePasswordWithRecoveryAnswers(szUserName, szRecoveryAnswers, szNewPassword, szDeprecated, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SetAccountRecoveryQuestions(String szUserName, String szPassword, String szRecoveryQuestions, String szRecoveryAnswers, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SetAccountRecoveryQuestions(szUserName, szPassword, szRecoveryQuestions, szRecoveryAnswers, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_PasswordOk(String szUserName, String szPassword, SWIGTYPE_p_bool pOk, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_PasswordOk(szUserName, szPassword, SWIGTYPE_p_bool.getCPtr(pOk), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpKeyGet(String szUserName, SWIGTYPE_p_p_char pszKey, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpKeyGet(szUserName, SWIGTYPE_p_p_char.getCPtr(pszKey), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpKeySet(String szUserName, String szKey, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpKeySet(szUserName, szKey, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpKeyRemove(String szUserName, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpKeyRemove(szUserName, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpAuthGet(String szUserName, String szPassword, SWIGTYPE_p_bool pbEnabled, SWIGTYPE_p_long pTimeout, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpAuthGet(szUserName, szPassword, SWIGTYPE_p_bool.getCPtr(pbEnabled), SWIGTYPE_p_long.getCPtr(pTimeout), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpAuthSet(String szUserName, String szPassword, int timeout, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpAuthSet(szUserName, szPassword, timeout, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpAuthRemove(String szUserName, String szPassword, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpAuthRemove(szUserName, szPassword, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpResetGet(SWIGTYPE_p_p_char szUsernames, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpResetGet(SWIGTYPE_p_p_char.getCPtr(szUsernames), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpResetSet(String szUserName, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpResetSet(szUserName, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_OtpResetRemove(String szUserName, String szPassword, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_OtpResetRemove(szUserName, szPassword, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_LoadAccountSettings(String szUserName, String szPassword, SWIGTYPE_p_p_sABC_AccountSettings ppSettings, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_LoadAccountSettings(szUserName, szPassword, SWIGTYPE_p_p_sABC_AccountSettings.getCPtr(ppSettings), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_UpdateAccountSettings(String szUserName, String szPassword, tABC_AccountSettings pSettings, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_UpdateAccountSettings(szUserName, szPassword, tABC_AccountSettings.getCPtr(pSettings), pSettings, tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeAccountSettings(tABC_AccountSettings pSettings) {
    coreJNI.ABC_FreeAccountSettings(tABC_AccountSettings.getCPtr(pSettings), pSettings);
  }

  public static tABC_CC ABC_GetPIN(String szUserName, String szPassword, SWIGTYPE_p_p_char pszPin, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetPIN(szUserName, szPassword, SWIGTYPE_p_p_char.getCPtr(pszPin), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SetPIN(String szUserName, String szPassword, String szPin, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SetPIN(szUserName, szPassword, szPin, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetCategories(String szUserName, String szPassword, SWIGTYPE_p_p_p_char paszCategories, SWIGTYPE_p_unsigned_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetCategories(szUserName, szPassword, SWIGTYPE_p_p_p_char.getCPtr(paszCategories), SWIGTYPE_p_unsigned_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_AddCategory(String szUserName, String szPassword, String szCategory, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_AddCategory(szUserName, szPassword, szCategory, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_RemoveCategory(String szUserName, String szPassword, String szCategory, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_RemoveCategory(szUserName, szPassword, szCategory, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_DataSyncAccount(String szUserName, String szPassword, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void fAsyncBitCoinEventCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_DataSyncAccount(szUserName, szPassword, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void.getCPtr(fAsyncBitCoinEventCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_UploadLogs(String szUserName, String szPassword, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_UploadLogs(szUserName, szPassword, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_RequestExchangeRateUpdate(String szUserName, String szPassword, int currencyNum, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_RequestExchangeRateUpdate(szUserName, szPassword, currencyNum, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SatoshiToCurrency(String szUserName, String szPassword, SWIGTYPE_p_int64_t satoshi, SWIGTYPE_p_double pCurrency, int currencyNum, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SatoshiToCurrency(szUserName, szPassword, SWIGTYPE_p_int64_t.getCPtr(satoshi), SWIGTYPE_p_double.getCPtr(pCurrency), currencyNum, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CurrencyToSatoshi(String szUserName, String szPassword, double currency, int currencyNum, SWIGTYPE_p_int64_t pSatoshi, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CurrencyToSatoshi(szUserName, szPassword, currency, currencyNum, SWIGTYPE_p_int64_t.getCPtr(pSatoshi), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CreateWallet(String szUserName, String szPassword, String szWalletName, int currencyNum, long attributes, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void fRequestCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CreateWallet(szUserName, szPassword, szWalletName, currencyNum, attributes, SWIGTYPE_p_f_p_q_const__struct_sABC_RequestResults__void.getCPtr(fRequestCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetWalletUUIDs(String szUserName, String szPassword, SWIGTYPE_p_p_p_char paWalletUUID, SWIGTYPE_p_unsigned_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetWalletUUIDs(szUserName, szPassword, SWIGTYPE_p_p_p_char.getCPtr(paWalletUUID), SWIGTYPE_p_unsigned_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetWallets(String szUserName, String szPassword, SWIGTYPE_p_p_p_sABC_WalletInfo paWalletInfo, SWIGTYPE_p_unsigned_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetWallets(szUserName, szPassword, SWIGTYPE_p_p_p_sABC_WalletInfo.getCPtr(paWalletInfo), SWIGTYPE_p_unsigned_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeWalletInfoArray(SWIGTYPE_p_p_sABC_WalletInfo aWalletInfo, long nCount) {
    coreJNI.ABC_FreeWalletInfoArray(SWIGTYPE_p_p_sABC_WalletInfo.getCPtr(aWalletInfo), nCount);
  }

  public static tABC_CC ABC_SetWalletOrder(String szUserName, String szPassword, String szUUIDs, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SetWalletOrder(szUserName, szPassword, szUUIDs, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_RenameWallet(String szUserName, String szPassword, String szUUID, String szNewWalletName, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_RenameWallet(szUserName, szPassword, szUUID, szNewWalletName, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SetWalletArchived(String szUserName, String szPassword, String szUUID, long archived, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SetWalletArchived(szUserName, szPassword, szUUID, archived, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetWalletInfo(String szUserName, String szPassword, String szUUID, SWIGTYPE_p_p_sABC_WalletInfo ppWalletInfo, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetWalletInfo(szUserName, szPassword, szUUID, SWIGTYPE_p_p_sABC_WalletInfo.getCPtr(ppWalletInfo), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeWalletInfo(tABC_WalletInfo pWalletInfo) {
    coreJNI.ABC_FreeWalletInfo(tABC_WalletInfo.getCPtr(pWalletInfo), pWalletInfo);
  }

  public static tABC_CC ABC_ExportWalletSeed(String szUserName, String szPassword, String szUUID, SWIGTYPE_p_p_char pszWalletSeed, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ExportWalletSeed(szUserName, szPassword, szUUID, SWIGTYPE_p_p_char.getCPtr(pszWalletSeed), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CsvExport(String szUserName, String szPassword, String szUUID, SWIGTYPE_p_int64_t startTime, SWIGTYPE_p_int64_t endTime, SWIGTYPE_p_p_char szCsvData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CsvExport(szUserName, szPassword, szUUID, SWIGTYPE_p_int64_t.getCPtr(startTime), SWIGTYPE_p_int64_t.getCPtr(endTime), SWIGTYPE_p_p_char.getCPtr(szCsvData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_DataSyncWallet(String szUserName, String szPassword, String szWalletUUID, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void fAsyncBitCoinEventCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_DataSyncWallet(szUserName, szPassword, szWalletUUID, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void.getCPtr(fAsyncBitCoinEventCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CreateReceiveRequest(String szUserName, String szPassword, String szWalletUUID, tABC_TxDetails pDetails, SWIGTYPE_p_p_char pszRequestID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CreateReceiveRequest(szUserName, szPassword, szWalletUUID, tABC_TxDetails.getCPtr(pDetails), pDetails, SWIGTYPE_p_p_char.getCPtr(pszRequestID), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_ModifyReceiveRequest(String szUserName, String szPassword, String szWalletUUID, String szRequestID, tABC_TxDetails pDetails, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_ModifyReceiveRequest(szUserName, szPassword, szWalletUUID, szRequestID, tABC_TxDetails.getCPtr(pDetails), pDetails, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_FinalizeReceiveRequest(String szUserName, String szPassword, String szWalletUUID, String szRequestID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_FinalizeReceiveRequest(szUserName, szPassword, szWalletUUID, szRequestID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_CancelReceiveRequest(String szUserName, String szPassword, String szWalletUUID, String szRequestID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CancelReceiveRequest(szUserName, szPassword, szWalletUUID, szRequestID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GenerateRequestQRCode(String szUserName, String szPassword, String szWalletUUID, String szRequestID, SWIGTYPE_p_p_char pszURI, SWIGTYPE_p_p_unsigned_char paData, SWIGTYPE_p_unsigned_int pWidth, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GenerateRequestQRCode(szUserName, szPassword, szWalletUUID, szRequestID, SWIGTYPE_p_p_char.getCPtr(pszURI), SWIGTYPE_p_p_unsigned_char.getCPtr(paData), SWIGTYPE_p_unsigned_int.getCPtr(pWidth), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_InitiateSendRequest(String szUserName, String szPassword, String szWalletUUID, String szDestAddress, tABC_TxDetails pDetails, SWIGTYPE_p_p_char szTxId, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_InitiateSendRequest(szUserName, szPassword, szWalletUUID, szDestAddress, tABC_TxDetails.getCPtr(pDetails), pDetails, SWIGTYPE_p_p_char.getCPtr(szTxId), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_InitiateTransfer(String szUserName, String szPassword, tABC_TransferDetails pTransfer, tABC_TxDetails pDetails, SWIGTYPE_p_p_char szTxId, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_InitiateTransfer(szUserName, szPassword, tABC_TransferDetails.getCPtr(pTransfer), pTransfer, tABC_TxDetails.getCPtr(pDetails), pDetails, SWIGTYPE_p_p_char.getCPtr(szTxId), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetRequestAddress(String szUserName, String szPassword, String szWalletUUID, String szRequestID, SWIGTYPE_p_p_char pszAddress, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetRequestAddress(szUserName, szPassword, szWalletUUID, szRequestID, SWIGTYPE_p_p_char.getCPtr(pszAddress), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetPendingRequests(String szUserName, String szPassword, String szWalletUUID, SWIGTYPE_p_p_p_sABC_RequestInfo paRequests, SWIGTYPE_p_unsigned_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetPendingRequests(szUserName, szPassword, szWalletUUID, SWIGTYPE_p_p_p_sABC_RequestInfo.getCPtr(paRequests), SWIGTYPE_p_unsigned_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeRequests(SWIGTYPE_p_p_sABC_RequestInfo aRequests, long count) {
    coreJNI.ABC_FreeRequests(SWIGTYPE_p_p_sABC_RequestInfo.getCPtr(aRequests), count);
  }

  public static tABC_CC ABC_CalcSendFees(String szUserName, String szPassword, String szWalletUUID, String szDestAddress, boolean bTransfer, tABC_TxDetails pDetails, SWIGTYPE_p_int64_t pTotalFees, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_CalcSendFees(szUserName, szPassword, szWalletUUID, szDestAddress, bTransfer, tABC_TxDetails.getCPtr(pDetails), pDetails, SWIGTYPE_p_int64_t.getCPtr(pTotalFees), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_MaxSpendable(String szUsername, String szPassword, String szWalletUUID, String szDestAddress, boolean bTransfer, SWIGTYPE_p_uint64_t pMaxSatoshi, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_MaxSpendable(szUsername, szPassword, szWalletUUID, szDestAddress, bTransfer, SWIGTYPE_p_uint64_t.getCPtr(pMaxSatoshi), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SweepKey(String szUsername, String szPassword, String szWalletUUID, String szKey, SWIGTYPE_p_p_char pszAddress, SWIGTYPE_p_f_enum_eABC_CC_p_q_const__char_uint64_t__void fCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SweepKey(szUsername, szPassword, szWalletUUID, szKey, SWIGTYPE_p_p_char.getCPtr(pszAddress), SWIGTYPE_p_f_enum_eABC_CC_p_q_const__char_uint64_t__void.getCPtr(fCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetTransaction(String szUserName, String szPassword, String szWalletUUID, String szID, SWIGTYPE_p_p_sABC_TxInfo ppTransaction, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetTransaction(szUserName, szPassword, szWalletUUID, szID, SWIGTYPE_p_p_sABC_TxInfo.getCPtr(ppTransaction), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetTransactions(String szUserName, String szPassword, String szWalletUUID, SWIGTYPE_p_int64_t startTime, SWIGTYPE_p_int64_t endTime, SWIGTYPE_p_p_p_sABC_TxInfo paTransactions, SWIGTYPE_p_unsigned_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetTransactions(szUserName, szPassword, szWalletUUID, SWIGTYPE_p_int64_t.getCPtr(startTime), SWIGTYPE_p_int64_t.getCPtr(endTime), SWIGTYPE_p_p_p_sABC_TxInfo.getCPtr(paTransactions), SWIGTYPE_p_unsigned_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_SearchTransactions(String szUserName, String szPassword, String szWalletUUID, String szQuery, SWIGTYPE_p_p_p_sABC_TxInfo paTransactions, SWIGTYPE_p_unsigned_int pCount, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SearchTransactions(szUserName, szPassword, szWalletUUID, szQuery, SWIGTYPE_p_p_p_sABC_TxInfo.getCPtr(paTransactions), SWIGTYPE_p_unsigned_int.getCPtr(pCount), tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeTransaction(tABC_TxInfo pTransaction) {
    coreJNI.ABC_FreeTransaction(tABC_TxInfo.getCPtr(pTransaction), pTransaction);
  }

  public static void ABC_FreeTransactions(SWIGTYPE_p_p_sABC_TxInfo aTransactions, long count) {
    coreJNI.ABC_FreeTransactions(SWIGTYPE_p_p_sABC_TxInfo.getCPtr(aTransactions), count);
  }

  public static tABC_CC ABC_SetTransactionDetails(String szUserName, String szPassword, String szWalletUUID, String szID, tABC_TxDetails pDetails, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_SetTransactionDetails(szUserName, szPassword, szWalletUUID, szID, tABC_TxDetails.getCPtr(pDetails), pDetails, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_GetTransactionDetails(String szUserName, String szPassword, String szWalletUUID, String szID, SWIGTYPE_p_p_sABC_TxDetails ppDetails, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_GetTransactionDetails(szUserName, szPassword, szWalletUUID, szID, SWIGTYPE_p_p_sABC_TxDetails.getCPtr(ppDetails), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_DuplicateTxDetails(SWIGTYPE_p_p_sABC_TxDetails ppNewDetails, tABC_TxDetails pOldDetails, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_DuplicateTxDetails(SWIGTYPE_p_p_sABC_TxDetails.getCPtr(ppNewDetails), tABC_TxDetails.getCPtr(pOldDetails), pOldDetails, tABC_Error.getCPtr(pError), pError));
  }

  public static void ABC_FreeTxDetails(tABC_TxDetails pDetails) {
    coreJNI.ABC_FreeTxDetails(tABC_TxDetails.getCPtr(pDetails), pDetails);
  }

  public static tABC_CC ABC_WatcherStart(String szUserName, String szPassword, String szWalletUUID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatcherStart(szUserName, szPassword, szWalletUUID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_WatcherLoop(String szWalletUUID, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void fAsyncBitCoinEventCallback, SWIGTYPE_p_void pData, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatcherLoop(szWalletUUID, SWIGTYPE_p_f_p_q_const__struct_sABC_AsyncBitCoinInfo__void.getCPtr(fAsyncBitCoinEventCallback), SWIGTYPE_p_void.getCPtr(pData), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_WatcherConnect(String szWalletUUID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatcherConnect(szWalletUUID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_WatchAddresses(String szUsername, String szPassword, String szWalletUUID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatchAddresses(szUsername, szPassword, szWalletUUID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_PrioritizeAddress(String szUserName, String szPassword, String szWalletUUID, String szAddress, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_PrioritizeAddress(szUserName, szPassword, szWalletUUID, szAddress, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_WatcherDisconnect(String szWalletUUID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatcherDisconnect(szWalletUUID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_WatcherStop(String szWalletUUID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatcherStop(szWalletUUID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_WatcherDelete(String szWalletUUID, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_WatcherDelete(szWalletUUID, tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_TxHeight(String szWalletUUID, String szTxId, SWIGTYPE_p_unsigned_int height, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_TxHeight(szWalletUUID, szTxId, SWIGTYPE_p_unsigned_int.getCPtr(height), tABC_Error.getCPtr(pError), pError));
  }

  public static tABC_CC ABC_BlockHeight(String szWalletUUID, SWIGTYPE_p_unsigned_int height, tABC_Error pError) {
    return tABC_CC.swigToEnum(coreJNI.ABC_BlockHeight(szWalletUUID, SWIGTYPE_p_unsigned_int.getCPtr(height), tABC_Error.getCPtr(pError), pError));
  }

  public static SWIGTYPE_p_long p64_t_to_long_ptr(SWIGTYPE_p_int64_t x) {
    long cPtr = coreJNI.p64_t_to_long_ptr(SWIGTYPE_p_int64_t.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public static SWIGTYPE_p_double p64_t_to_double_ptr(SWIGTYPE_p_int64_t x) {
    long cPtr = coreJNI.p64_t_to_double_ptr(SWIGTYPE_p_int64_t.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public static SWIGTYPE_p_unsigned_int int_to_uint(SWIGTYPE_p_int x) {
    long cPtr = coreJNI.int_to_uint(SWIGTYPE_p_int.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_int(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_WalletInfo longp_to_ppWalletinfo(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppWalletinfo(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_WalletInfo(cPtr, false);
  }

  public static tABC_WalletInfo longp_to_pWalletinfo(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_pWalletinfo(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new tABC_WalletInfo(cPtr, false);
  }

  public static SWIGTYPE_p_p_p_sABC_WalletInfo longp_to_pppWalletInfo(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_pppWalletInfo(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_p_sABC_WalletInfo(cPtr, false);
  }

  public static SWIGTYPE_p_p_p_sABC_TxInfo longp_to_pppTxInfo(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_pppTxInfo(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_p_sABC_TxInfo(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_TxInfo longp_to_ppTxInfo(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppTxInfo(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_TxInfo(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_Currency longp_to_ppCurrency(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppCurrency(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_Currency(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_TxDetails longp_to_ppTxDetails(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppTxDetails(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_TxDetails(cPtr, false);
  }

  public static SWIGTYPE_p_p_p_sABC_PasswordRule longp_to_pppPasswordRule(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_pppPasswordRule(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_p_sABC_PasswordRule(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_AccountSettings longp_to_ppAccountSettings(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppAccountSettings(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_AccountSettings(cPtr, false);
  }

  public static SWIGTYPE_p_p_char longp_to_ppChar(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppChar(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_char(cPtr, false);
  }

  public static SWIGTYPE_p_p_p_char longp_to_pppChar(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_pppChar(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_p_char(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_QuestionChoices longp_to_ppQuestionChoices(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_ppQuestionChoices(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_QuestionChoices(cPtr, false);
  }

  public static SWIGTYPE_p_p_unsigned_char longp_to_unsigned_ppChar(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longp_to_unsigned_ppChar(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_unsigned_char(cPtr, false);
  }

  public static SWIGTYPE_p_long charpp_to_longp(SWIGTYPE_p_p_char x) {
    long cPtr = coreJNI.charpp_to_longp(SWIGTYPE_p_p_char.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_long(cPtr, false);
  }

  public static SWIGTYPE_p_p_sABC_BitcoinURIInfo longPtr_to_ppBitcoinURIInfo(SWIGTYPE_p_long x) {
    long cPtr = coreJNI.longPtr_to_ppBitcoinURIInfo(SWIGTYPE_p_long.getCPtr(x));
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_sABC_BitcoinURIInfo(cPtr, false);
  }

  public static SWIGTYPE_p_void requestResultsp_to_voidp(tABC_RequestResults x) {
    long cPtr = coreJNI.requestResultsp_to_voidp(tABC_RequestResults.getCPtr(x), x);
    return (cPtr == 0) ? null : new SWIGTYPE_p_void(cPtr, false);
  }

}
