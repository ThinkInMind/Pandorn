package cn.d1m.pandora.entry.output.scv;

import java.util.ArrayList;

public class ConsumerFindResponse {
	
	private Error err;
	private String id ;//(string, optional),
	private String firstName ;//(string, optional),
	private String lastName ;//(string, optional), 
	private String emailAddress ;//(string, optional), 
	private String country ;//(string, optional): Market country , 
	private String mobilePhone ;//(string, optional),
	private String birthdayDay ;//(integer, optional), 
	private String birthdayMonth ;//(integer, optional),
	private String birthdayYear ;//(integer, optional), 
	private String preferredLanguage ;//(string, optional), 
	private String titleSalutation ;//(string, optional) = ['Mr', 'Mrs', 'Ms', 'Mr or Ms', 'Mr.', 'Mrs.', 'Ms.', 'Miss', 'Miss.'], 
	private String gender ;//(string, optional) = ['Male', 'Female', 'Unspecified'], 
	private String lastModifiedSource ;//(string, optional) = ['EStore', 'Club', 'Merged', 'MergedConsentPopup', 'AdvanceRetail'], 
	private String legalEntity ;//(string, optional), 
	private String legalEntityType ;//(string, optional) = ['Club', 'EStore'],
	private String governmentId ;//(string, optional): Government identification in the market country ,
	private String primaryStreet1 ;//(string, optional), 
	private String primaryStreet2 ;//(string, optional), 
	private String primaryStreet3 ;//(string, optional), 
	private String primaryCity ;//(string, optional),
	private String primaryStateOrProvince ;//(string, optional),
	private String primaryCountry ;//(string, optional),
	private String primaryPostalCode ;//(string, optional), 
	private String emailOptCurrentStatus ;//(boolean, optional), 
	private String emailDoubleOptInStatus ;//(string, optional) = ['NotSent', 'Awaited', 'Received'], 
	private String emailOptInDate ;//(string, optional), 
	private String emailOptOutDate ;//(string, optional), 
	private String mobileOptCurrentStatus ;//(boolean, optional), 
	private String mobileDoubleOptInStatus ;//(string, optional) = ['NotSent', 'Awaited', 'Received'], 
	private String mobileOptInDate ;//(string, optional), 
	private String mobileOptOutDate ;//(string, optional), 
	private String directMailOptCurrentStatus ;//(boolean, optional), 
	private String directMailDoubleOptInStatus ;//(string, optional) = ['NotSent', 'Awaited', 'Received'], 
	private String directMailOptInDate ;//(string, optional), 
	private String directMailOptOutDate ;//(string, optional), 
	private String telephoneOptCurrentStatus ;//(boolean, optional), 
	private String telephoneDoubleOptInStatus ;//(string, optional) = ['NotSent', 'Awaited', 'Received'], 
	private String telephoneOptInDate ;//(string, optional), 
	private String telephoneOptOutDate ;//(string, optional), 
	private String faxOptCurrentStatus ;//(boolean, optional), 
	private String faxDoubleOptInStatus ;//(string, optional) = ['NotSent', 'Awaited', 'Received'], 
	private String faxOptInDate ;//(string, optional), 
	private String faxOptOutDate ;//(string, optional), 
	private String vipNumber ;//(string, optional), 
	private String loyaltyPoints ;//(integer, optional), 
	private String loyaltyTier ;//(string, optional), 
	private String loyaltyPointsExpiryDate ;//(string, optional), 
	private String nextLoyaltyTier ;//(string, optional), 
	private String pointsToNextTier ;//(integer, optional), 
	private String previousLoyaltyTier ;//(string, optional), 
	private String lastBirthdayDiscountRedemptionDate ;//(string, optional), 
	private String sourceId ;//(string, optional)
	
	
	
	public Error getErr() {
		return err;
	}
	public void setErr(Error err) {
		this.err = err;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getBirthdayDay() {
		return birthdayDay;
	}
	public void setBirthdayDay(String birthdayDay) {
		this.birthdayDay = birthdayDay;
	}
	public String getBirthdayMonth() {
		return birthdayMonth;
	}
	public void setBirthdayMonth(String birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}
	public String getBirthdayYear() {
		return birthdayYear;
	}
	public void setBirthdayYear(String birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	public String getPreferredLanguage() {
		return preferredLanguage;
	}
	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}
	public String getTitleSalutation() {
		return titleSalutation;
	}
	public void setTitleSalutation(String titleSalutation) {
		this.titleSalutation = titleSalutation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLastModifiedSource() {
		return lastModifiedSource;
	}
	public void setLastModifiedSource(String lastModifiedSource) {
		this.lastModifiedSource = lastModifiedSource;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getLegalEntityType() {
		return legalEntityType;
	}
	public void setLegalEntityType(String legalEntityType) {
		this.legalEntityType = legalEntityType;
	}
	public String getGovernmentId() {
		return governmentId;
	}
	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}
	public String getPrimaryStreet1() {
		return primaryStreet1;
	}
	public void setPrimaryStreet1(String primaryStreet1) {
		this.primaryStreet1 = primaryStreet1;
	}
	public String getPrimaryStreet2() {
		return primaryStreet2;
	}
	public void setPrimaryStreet2(String primaryStreet2) {
		this.primaryStreet2 = primaryStreet2;
	}
	public String getPrimaryStreet3() {
		return primaryStreet3;
	}
	public void setPrimaryStreet3(String primaryStreet3) {
		this.primaryStreet3 = primaryStreet3;
	}
	public String getPrimaryCity() {
		return primaryCity;
	}
	public void setPrimaryCity(String primaryCity) {
		this.primaryCity = primaryCity;
	}
	public String getPrimaryStateOrProvince() {
		return primaryStateOrProvince;
	}
	public void setPrimaryStateOrProvince(String primaryStateOrProvince) {
		this.primaryStateOrProvince = primaryStateOrProvince;
	}
	public String getPrimaryCountry() {
		return primaryCountry;
	}
	public void setPrimaryCountry(String primaryCountry) {
		this.primaryCountry = primaryCountry;
	}
	public String getPrimaryPostalCode() {
		return primaryPostalCode;
	}
	public void setPrimaryPostalCode(String primaryPostalCode) {
		this.primaryPostalCode = primaryPostalCode;
	}
	public String getEmailOptCurrentStatus() {
		return emailOptCurrentStatus;
	}
	public void setEmailOptCurrentStatus(String emailOptCurrentStatus) {
		this.emailOptCurrentStatus = emailOptCurrentStatus;
	}
	public String getEmailDoubleOptInStatus() {
		return emailDoubleOptInStatus;
	}
	public void setEmailDoubleOptInStatus(String emailDoubleOptInStatus) {
		this.emailDoubleOptInStatus = emailDoubleOptInStatus;
	}
	public String getEmailOptInDate() {
		return emailOptInDate;
	}
	public void setEmailOptInDate(String emailOptInDate) {
		this.emailOptInDate = emailOptInDate;
	}
	public String getEmailOptOutDate() {
		return emailOptOutDate;
	}
	public void setEmailOptOutDate(String emailOptOutDate) {
		this.emailOptOutDate = emailOptOutDate;
	}
	public String getMobileOptCurrentStatus() {
		return mobileOptCurrentStatus;
	}
	public void setMobileOptCurrentStatus(String mobileOptCurrentStatus) {
		this.mobileOptCurrentStatus = mobileOptCurrentStatus;
	}
	public String getMobileDoubleOptInStatus() {
		return mobileDoubleOptInStatus;
	}
	public void setMobileDoubleOptInStatus(String mobileDoubleOptInStatus) {
		this.mobileDoubleOptInStatus = mobileDoubleOptInStatus;
	}
	public String getMobileOptInDate() {
		return mobileOptInDate;
	}
	public void setMobileOptInDate(String mobileOptInDate) {
		this.mobileOptInDate = mobileOptInDate;
	}
	public String getMobileOptOutDate() {
		return mobileOptOutDate;
	}
	public void setMobileOptOutDate(String mobileOptOutDate) {
		this.mobileOptOutDate = mobileOptOutDate;
	}
	public String getDirectMailOptCurrentStatus() {
		return directMailOptCurrentStatus;
	}
	public void setDirectMailOptCurrentStatus(String directMailOptCurrentStatus) {
		this.directMailOptCurrentStatus = directMailOptCurrentStatus;
	}
	public String getDirectMailDoubleOptInStatus() {
		return directMailDoubleOptInStatus;
	}
	public void setDirectMailDoubleOptInStatus(String directMailDoubleOptInStatus) {
		this.directMailDoubleOptInStatus = directMailDoubleOptInStatus;
	}
	public String getDirectMailOptInDate() {
		return directMailOptInDate;
	}
	public void setDirectMailOptInDate(String directMailOptInDate) {
		this.directMailOptInDate = directMailOptInDate;
	}
	public String getDirectMailOptOutDate() {
		return directMailOptOutDate;
	}
	public void setDirectMailOptOutDate(String directMailOptOutDate) {
		this.directMailOptOutDate = directMailOptOutDate;
	}
	public String getTelephoneOptCurrentStatus() {
		return telephoneOptCurrentStatus;
	}
	public void setTelephoneOptCurrentStatus(String telephoneOptCurrentStatus) {
		this.telephoneOptCurrentStatus = telephoneOptCurrentStatus;
	}
	public String getTelephoneDoubleOptInStatus() {
		return telephoneDoubleOptInStatus;
	}
	public void setTelephoneDoubleOptInStatus(String telephoneDoubleOptInStatus) {
		this.telephoneDoubleOptInStatus = telephoneDoubleOptInStatus;
	}
	public String getTelephoneOptInDate() {
		return telephoneOptInDate;
	}
	public void setTelephoneOptInDate(String telephoneOptInDate) {
		this.telephoneOptInDate = telephoneOptInDate;
	}
	public String getTelephoneOptOutDate() {
		return telephoneOptOutDate;
	}
	public void setTelephoneOptOutDate(String telephoneOptOutDate) {
		this.telephoneOptOutDate = telephoneOptOutDate;
	}
	public String getFaxOptCurrentStatus() {
		return faxOptCurrentStatus;
	}
	public void setFaxOptCurrentStatus(String faxOptCurrentStatus) {
		this.faxOptCurrentStatus = faxOptCurrentStatus;
	}
	public String getFaxDoubleOptInStatus() {
		return faxDoubleOptInStatus;
	}
	public void setFaxDoubleOptInStatus(String faxDoubleOptInStatus) {
		this.faxDoubleOptInStatus = faxDoubleOptInStatus;
	}
	public String getFaxOptInDate() {
		return faxOptInDate;
	}
	public void setFaxOptInDate(String faxOptInDate) {
		this.faxOptInDate = faxOptInDate;
	}
	public String getFaxOptOutDate() {
		return faxOptOutDate;
	}
	public void setFaxOptOutDate(String faxOptOutDate) {
		this.faxOptOutDate = faxOptOutDate;
	}
	public String getVipNumber() {
		return vipNumber;
	}
	public void setVipNumber(String vipNumber) {
		this.vipNumber = vipNumber;
	}
	public String getLoyaltyPoints() {
		return loyaltyPoints;
	}
	public void setLoyaltyPoints(String loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}
	public String getLoyaltyTier() {
		return loyaltyTier;
	}
	public void setLoyaltyTier(String loyaltyTier) {
		this.loyaltyTier = loyaltyTier;
	}
	public String getLoyaltyPointsExpiryDate() {
		return loyaltyPointsExpiryDate;
	}
	public void setLoyaltyPointsExpiryDate(String loyaltyPointsExpiryDate) {
		this.loyaltyPointsExpiryDate = loyaltyPointsExpiryDate;
	}
	public String getNextLoyaltyTier() {
		return nextLoyaltyTier;
	}
	public void setNextLoyaltyTier(String nextLoyaltyTier) {
		this.nextLoyaltyTier = nextLoyaltyTier;
	}
	public String getPointsToNextTier() {
		return pointsToNextTier;
	}
	public void setPointsToNextTier(String pointsToNextTier) {
		this.pointsToNextTier = pointsToNextTier;
	}
	public String getPreviousLoyaltyTier() {
		return previousLoyaltyTier;
	}
	public void setPreviousLoyaltyTier(String previousLoyaltyTier) {
		this.previousLoyaltyTier = previousLoyaltyTier;
	}
	public String getLastBirthdayDiscountRedemptionDate() {
		return lastBirthdayDiscountRedemptionDate;
	}
	public void setLastBirthdayDiscountRedemptionDate(String lastBirthdayDiscountRedemptionDate) {
		this.lastBirthdayDiscountRedemptionDate = lastBirthdayDiscountRedemptionDate;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
}

class ConflictResponse {
	private Conflict conflict;

	public Conflict getConflict() {
		return conflict;
	}

	public void setConflict(Conflict conflict) {
		this.conflict = conflict;
	}
	
}
class Conflict{
	private String code ;//(string): Error code, enumerated , 
	private String message;// (string): Error message , 
	private ArrayList<Conflict>  details ;//(Array[Conflict], optional): List of detailed errors
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<Conflict> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<Conflict> details) {
		this.details = details;
	}

}
