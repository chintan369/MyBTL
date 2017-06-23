package com.agraeta.user.btl.Distributor;

/**
 * Created by chaitalee on 9/17/2016.
 */
public class Bean_Ledger {

    int id;
    String ledger_id;
    String ledgerName;
    String date;
    String startingDate;
    String endingDate;
    String particulars;
    String vchType;
    String vchNo;
    String debit;
    String credit;
    String openingBalance;
    String closingBalance;
    String currentTotalCredit;
    String currentTotalDebit;

    public Bean_Ledger() {
    }

    public Bean_Ledger(int id, String ledger_id,String ledgerName, String date, String startingDate, String endingDate, String particulars, String vchType, String vchNo, String debit, String credit, String openingBalance, String closingBalance, String currentTotalCredit, String currentTotalDebit) {
        this.id = id;
        this.ledgerName=ledgerName;
        this.ledger_id = ledger_id;
        this.date = date;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.particulars = particulars;
        this.vchType = vchType;
        this.vchNo = vchNo;
        this.debit = debit;
        this.credit = credit;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.currentTotalCredit = currentTotalCredit;
        this.currentTotalDebit = currentTotalDebit;
    }

    public Bean_Ledger(String ledger_id,String ledgerName, String date, String startingDate, String endingDate, String particulars, String vchType, String vchNo, String debit, String credit, String openingBalance, String closingBalance, String currentTotalCredit, String currentTotalDebit) {
        this.ledger_id = ledger_id;
        this.ledgerName=ledgerName;
        this.date = date;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.particulars = particulars;
        this.vchType = vchType;
        this.vchNo = vchNo;
        this.debit = debit;
        this.credit = credit;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.currentTotalCredit = currentTotalCredit;
        this.currentTotalDebit = currentTotalDebit;
    }

    public Bean_Ledger(String ledgerName,String date, String startingDate, String endingDate, String particulars, String vchType, String vchNo, String debit, String credit, String openingBalance, String closingBalance, String currentTotalCredit, String currentTotalDebit) {
        this.ledgerName=ledgerName;
        this.date = date;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.particulars = particulars;
        this.vchType = vchType;
        this.vchNo = vchNo;
        this.debit = debit;
        this.credit = credit;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.currentTotalCredit = currentTotalCredit;
        this.currentTotalDebit = currentTotalDebit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLedger_id() {
        return ledger_id;
    }

    public void setLedger_id(String ledger_id) {
        this.ledger_id = ledger_id;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getVchType() {
        return vchType;
    }

    public void setVchType(String vchType) {
        this.vchType = vchType;
    }

    public String getVchNo() {
        return vchNo;
    }

    public void setVchNo(String vchNo) {
        this.vchNo = vchNo;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getCurrentTotalCredit() {
        return currentTotalCredit;
    }

    public void setCurrentTotalCredit(String currentTotalCredit) {
        this.currentTotalCredit = currentTotalCredit;
    }

    public String getCurrentTotalDebit() {
        return currentTotalDebit;
    }

    public void setCurrentTotalDebit(String currentTotalDebit) {
        this.currentTotalDebit = currentTotalDebit;
    }
}
