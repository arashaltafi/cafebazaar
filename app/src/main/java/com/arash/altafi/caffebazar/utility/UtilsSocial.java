package com.arash.altafi.caffebazar.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class UtilsSocial {


    public static   boolean  isInstallPackgName(Context context,String packagename)
    {

        PackageManager manager = context.getPackageManager();

        try {
            manager.getPackageInfo(packagename,PackageManager.GET_ACTIVITIES);
            return true;

        }
       catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
            return false;
        }
    }

    public static void instagram(Context context,String result )
    {

        String instagramPackageName ="com.instagram.android";
        boolean isAppInsatll = isInstallPackgName(context,instagramPackageName);
        if (isAppInsatll)
        {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else
        {
            Toast.makeText(context, "این برنامه را نصب ندارید", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
    public static void telegram(Context context,String result )
    {

        String telegramPackageName ="org.telegram.messenger";
        boolean isAppInsatll = isInstallPackgName(context,telegramPackageName);
        if (isAppInsatll)
        {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else
        {
            Toast.makeText(context, "این برنامه را نصب ندارید", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
    public static void whatsApp(Context context,String result )
    {

        String whatsappPackageName ="com.whatsapp";
        boolean isAppInsatll = isInstallPackgName(context,whatsappPackageName);
        if (isAppInsatll)
        {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else
        {
            Toast.makeText(context, "این برنامه را نصب ندارید", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
    public static void youtube(Context context,String result )
    {

        String whatsappPackageName ="com.google.android.youtube";
        boolean isAppInsatll = isInstallPackgName(context,whatsappPackageName);
        if (isAppInsatll)
        {
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/channel/"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else
        {
            Toast.makeText(context, "این برنامه را نصب ندارید", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/channel/"+result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
