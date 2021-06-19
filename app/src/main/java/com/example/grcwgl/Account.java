package com.example.grcwgl;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private String accountCategory="";


    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    private int accountId;
    public String getAccountCategory() {
        return accountCategory;
    }

    public void setAccountCategory(String accountCategory) {
        this.accountCategory = accountCategory;
    }


    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    public String getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney) {
        this.accountMoney = accountMoney;
    }

    public static Creator<Account> getCREATOR() {
        return CREATOR;
    }

    private String accountMoney;
    private String accountDate="";
    private String accountRemark="";

    public Account() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountCategory);
        dest.writeInt(this.accountId);
        dest.writeString(this.accountMoney);
        dest.writeString(this.accountDate);
        dest.writeString(this.accountRemark);
    }

    protected Account(Parcel in) {
        this.accountCategory = in.readString();
        this.accountId = in.readInt();
        this.accountMoney = in.readString();
        this.accountDate = in.readString();
        this.accountRemark = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
