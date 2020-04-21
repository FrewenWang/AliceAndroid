// IOnNewBookArrivedListener.aidl
package com.frewen.android.demo.samples.ipc.remote.aidl;

// Declare any non-default types here with import statements
import com.frewen.android.demo.samples.ipc.remote.aidl.RemoteTicket;

interface IOnNewTicketArrivedListener {
    void onNewTicketArrived(in RemoteTicket newTicket);
}
