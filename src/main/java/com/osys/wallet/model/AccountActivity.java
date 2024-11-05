// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.model;

public class AccountActivity implements Comparable<AccountActivity> {
	
	private String activityType;
	private String activityAmount;
	private String activityTitle;
	private String activityDescription;
	private String activityLink;
	private int activityDate;
	
	public AccountActivity(String activityType, String activityAmount, String activityTitle, String activityDescription,
			String activityLink, int activityDate) {
		super();
		this.activityType = activityType;
		this.activityAmount = activityAmount;
		this.activityTitle = activityTitle;
		this.activityDescription = activityDescription;
		this.activityLink = activityLink;
		this.activityDate = activityDate;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityAmount() {
		return activityAmount;
	}

	public void setActivityAmount(String activityAmount) {
		this.activityAmount = activityAmount;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}

	public int getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(int activityDate) {
		this.activityDate = activityDate;
	}

	@Override
	public int compareTo(AccountActivity o) {
		// TODO Auto-generated method stub
		return 0;
	}


	

}



