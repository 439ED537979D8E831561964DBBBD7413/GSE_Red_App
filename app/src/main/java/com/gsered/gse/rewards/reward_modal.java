package com.gsered.gse.rewards;

public class reward_modal {

    String rewardamount,seen,id;

    public reward_modal() {
    }

    public reward_modal(String rewardamount, String seen, String id) {
        this.rewardamount = rewardamount;
        this.seen = seen;
        this.id = id;
    }

    public String getRewardamount() {
        return rewardamount;
    }

    public void setRewardamount(String rewardamount) {
        this.rewardamount = rewardamount;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
