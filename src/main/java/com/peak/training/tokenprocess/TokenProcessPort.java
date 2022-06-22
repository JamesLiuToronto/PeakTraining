package com.peak.training.tokenprocess;

public interface TokenProcessPort {

    public void process(String token);
    public String getActivateAccountToken(int userId);
    public String setFamilyMember(int askedForUserId, int requestedByUserId);
    public String getEncrptedMessage(String message) ;

}
