package com.slash.youth.domain.bean;

/**
 * Created by acer on 2017/3/15.
 */

public class PersonRelation {
    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        private int addMeFriendCount;
        private int friendCount;
        private int myAddFriendCount;
        private int myFansCount;
        private int myFollowCount;

        public int getAddMeFriendCount() {
            return addMeFriendCount;
        }

        public void setAddMeFriendCount(int addMeFriendCount) {
            this.addMeFriendCount = addMeFriendCount;
        }

        public int getFriendCount() {
            return friendCount;
        }

        public void setFriendCount(int friendCount) {
            this.friendCount = friendCount;
        }

        public int getMyAddFriendCount() {
            return myAddFriendCount;
        }

        public void setMyAddFriendCount(int myAddFriendCount) {
            this.myAddFriendCount = myAddFriendCount;
        }

        public int getMyFansCount() {
            return myFansCount;
        }

        public void setMyFansCount(int myFansCount) {
            this.myFansCount = myFansCount;
        }

        public int getMyFollowCount() {
            return myFollowCount;
        }

        public void setMyFollowCount(int myFollowCount) {
            this.myFollowCount = myFollowCount;
        }
    }
}
