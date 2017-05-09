package com.project.rishabhsingh.dWarden.DonorSearchingSystem;

public class DonorData {

    private String donorName,donorBranch,donorYear,donorContact,donorEmail;

    public DonorData() {

    }

    public DonorData(String donorName, String donorBranch, String donorYear, String donorContact, String donorEmail) {
        this.donorName = donorName;
        this.donorBranch = donorBranch;
        this.donorYear = donorYear;
        this.donorContact = donorContact;
        this.donorEmail = donorEmail;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorBranch() {
        return donorBranch;
    }

    public void setDonorBranch(String donorBranch) {
        this.donorBranch = donorBranch;
    }

    public String getDonorYear() {
        return donorYear;
    }

    public void setDonorYear(String donorYear) {
        this.donorYear = donorYear;
    }

    public String getDonorContact() {
        return donorContact;
    }

    public void setDonorContact(String donorContact) {
        this.donorContact = donorContact;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }
}
