package com.gsered.gse.topdonors;

public class TopDonorModal {
    String srno,name,amount;

    public TopDonorModal(String srno, String name, String amount) {
        this.srno = srno;
        this.name = name;
        this.amount = amount;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
