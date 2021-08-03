package com.d3si.loak_inapp.Module;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.d3si.loak_inapp.Constructor.ConstTransaksi;
import com.d3si.loak_inapp.UI.Agen.HomeActivityAgen;
import com.d3si.loak_inapp.UI.Member.HomeActivityMember;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrderService extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference request;
    String idUser;


    public ListenOrderService()
    {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        request = db.getReference("Transaksi");
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        request.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot snapshot,String previousChildName) {

    }

    @Override
    public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        ConstTransaksi constTransaksi = snapshot.getValue(ConstTransaksi.class);
        if(constTransaksi.getID_MEMBER().equalsIgnoreCase(idUser))
        {
            if(constTransaksi.getID_STATUS_TRANSAKSI().equalsIgnoreCase("ST2"))
            {
                NotificationCaller notificationCaller = new NotificationCaller(ListenOrderService.this);
                notificationCaller.notifPickUpLoak();
            } else if(constTransaksi.getID_STATUS_TRANSAKSI().equalsIgnoreCase("ST4"))
            {
                NotificationCaller notificationCaller = new NotificationCaller(ListenOrderService.this);
                notificationCaller.notifOrderSelesai();
            } else  if(constTransaksi.getID_STATUS_TRANSAKSI().equalsIgnoreCase("ST1"))
            {
                NotificationCaller notificationCaller = new NotificationCaller(ListenOrderService.this);
                notificationCaller.notfOrderBatal(constTransaksi.getNAMA_BARANG_LOAK());
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

    }

    @Override
    public void onCancelled(DatabaseError error) {

    }

    @Override
    public void onDestroy() {
        idUser ="";
        super.onDestroy();
    }
}