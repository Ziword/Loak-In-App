package com.d3si.loak_inapp.Module;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.d3si.loak_inapp.R;

public class NotificationCaller
{
    Context c;

    public NotificationCaller(Context c) {
        this.c = c;
    }

    public void notifOrderSelesai()
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "Loak-In Jual")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notifikasi Penjualan Barang Loak")
                .setContentText("Transaksi barang loak berhasil, Terimakasih telah menggunakan aplikasi Loak-In.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);

        notificationManager.notify(100,builder.build());
    }

    public void notifPickUpLoak()
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "Loak-In Jual")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notifikasi Penjualan Barang Loak")
                .setContentText("Agen telah menerima orderan barang loak anda dan segera melakukan pengambilan.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);

        notificationManager.notify(100,builder.build());
    }

    public void notifAmbilOrderan()
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "Loak-In Jual")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notifikasi Pembelian Barang Loak")
                .setContentText("Pesanan berhasil diambil, periksa menu pesanan untuk detail pengambilan.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);

        notificationManager.notify(100,builder.build());
    }

    public void notifUploadLoak()
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "Loak-In Jual")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notifikasi Iklan Barang Loak")
                .setContentText("Iklan barang loak berhasil diposting.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);

        notificationManager.notify(100,builder.build());
    }

    public void notifDeleteLoak(String nama_barang)
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "Loak-In Jual")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notifikasi Iklan Barang Loak")
                .setContentText("Pesanan barang loak "+nama_barang+" berhasil dibatalkan.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);

        notificationManager.notify(100,builder.build());
    }

    public void notfOrderBatal(String nama_barang)
    {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "Loak-In Jual")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notifikasi Iklan Barang Loak")
                .setContentText("Yahh agen batal mengambil pesanan "+nama_barang)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setDefaults(Notification.DEFAULT_ALL);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);

        notificationManager.notify(100,builder.build());
    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Loak-In Jual";
            String description = "Channel Notifikasi Posting Jual";
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Loak-In Jual", name, importance);
            channel.setDescription(description);
            android.app.NotificationManager notificationManager = c.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
