package com.arash.altafi.caffebazar.util.communication;


import com.arash.altafi.caffebazar.util.IabResult;

public interface BillingSupportCommunication {
    void onBillingSupportResult(int response);
    void remoteExceptionHappened(IabResult result);
}
