package com.guo.goldenpenguin.beans;

import java.io.Serializable;

/**
 * @Description:用户信息
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2017/8/212253
 */

public class UserInfoBean extends BaseJsonBeans {


    /**
     * Data : {"SessionKey":"122d00ff-eae9-47a8-963f-a5b2fbba1f30","MemberID":10,"Gender":0,"LoginName":null,"HeadImgUrl":"http://v1.qzone.cc/avatar/201409/24/19/58/5422b1ff86ed0232.jpg%21200x200.jpg","Name":null,"Mobile":"18739395502","LicenceNo":null,"QQNumber":null,"WXID":null,"BlogID":null,"Grade":0,"Account":0,"QRCodeUrl":"http://59.110.48.164:9002//MemberCode/21/bb738211a9244394a67f4f4109380c5cAPP_21.jpg","IDNumber":"0000212","Signature":null,"SkillsTag":null,"Birthday":null,"Hometown":null,"ConcernTimes":0,"FansTimes":0,"VideoTimes":0,"IMPassWord":"EAED67D6AEE34390"}
     */

    private DataBean Data;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * SessionKey : 122d00ff-eae9-47a8-963f-a5b2fbba1f30
         * MemberID : 10
         * Gender : 0
         * LoginName : null
         * HeadImgUrl : http://v1.qzone.cc/avatar/201409/24/19/58/5422b1ff86ed0232.jpg%21200x200.jpg
         * Name : null
         * Mobile : 18739395502
         * LicenceNo : null
         * QQNumber : null
         * WXID : null
         * BlogID : null
         * Grade : 0
         * Account : 0
         * QRCodeUrl : http://59.110.48.164:9002//MemberCode/21/bb738211a9244394a67f4f4109380c5cAPP_21.jpg
         * IDNumber : 0000212
         * Signature : null
         * SkillsTag : null
         * Birthday : null
         * Hometown : null
         * ConcernTimes : 0
         * FansTimes : 0
         * VideoTimes : 0
         * IMPassWord : EAED67D6AEE34390
         */

        private String SessionKey;
        private int MemberID;
        private int ConcernTimes;
        private int FansTimes;
        private int VideoTimes;
        private String IMPassWord;



        private int Gender;
        private String LoginName;
        private String HeadImgUrl;
        private String Name;
        private String Mobile;
        private String LicenceNo;
        private String QQNumber;
        private String WXID;
        private String BlogID;
        private int Grade;
        private int Account;
        private String QRCodeUrl;
        private String IDNumber;
        private String Signature;
        private String SkillsTag;
        private String Birthday;
        private String Hometown;


        public String getSessionKey() {
            return SessionKey;
        }

        public void setSessionKey(String SessionKey) {
            this.SessionKey = SessionKey;
        }

        public int getMemberID() {
            return MemberID;
        }

        public void setMemberID(int MemberID) {
            this.MemberID = MemberID;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public String getHeadImgUrl() {
            return HeadImgUrl;
        }

        public void setHeadImgUrl(String HeadImgUrl) {
            this.HeadImgUrl = HeadImgUrl;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getLicenceNo() {
            return LicenceNo;
        }

        public void setLicenceNo(String LicenceNo) {
            this.LicenceNo = LicenceNo;
        }

        public String getQQNumber() {
            return QQNumber;
        }

        public void setQQNumber(String QQNumber) {
            this.QQNumber = QQNumber;
        }

        public String getWXID() {
            return WXID;
        }

        public void setWXID(String WXID) {
            this.WXID = WXID;
        }

        public String getBlogID() {
            return BlogID;
        }

        public void setBlogID(String BlogID) {
            this.BlogID = BlogID;
        }

        public int getGrade() {
            return Grade;
        }

        public void setGrade(int Grade) {
            this.Grade = Grade;
        }

        public int getAccount() {
            return Account;
        }

        public void setAccount(int Account) {
            this.Account = Account;
        }

        public String getQRCodeUrl() {
            return QRCodeUrl;
        }

        public void setQRCodeUrl(String QRCodeUrl) {
            this.QRCodeUrl = QRCodeUrl;
        }

        public String getIDNumber() {
            return IDNumber;
        }

        public void setIDNumber(String IDNumber) {
            this.IDNumber = IDNumber;
        }

        public String getSignature() {
            return Signature;
        }

        public void setSignature(String Signature) {
            this.Signature = Signature;
        }

        public String getSkillsTag() {
            return SkillsTag;
        }

        public void setSkillsTag(String SkillsTag) {
            this.SkillsTag = SkillsTag;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getHometown() {
            return Hometown;
        }

        public void setHometown(String Hometown) {
            this.Hometown = Hometown;
        }

        public int getConcernTimes() {
            return ConcernTimes;
        }

        public void setConcernTimes(int ConcernTimes) {
            this.ConcernTimes = ConcernTimes;
        }

        public int getFansTimes() {
            return FansTimes;
        }

        public void setFansTimes(int FansTimes) {
            this.FansTimes = FansTimes;
        }

        public int getVideoTimes() {
            return VideoTimes;
        }

        public void setVideoTimes(int VideoTimes) {
            this.VideoTimes = VideoTimes;
        }

        public String getIMPassWord() {
            return IMPassWord;
        }

        public void setIMPassWord(String IMPassWord) {
            this.IMPassWord = IMPassWord;
        }
    }
}
