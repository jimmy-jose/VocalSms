package app.jimmy.vocalsms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Jimmy
 * Created on 4/3/19.
 */
public class MainActivityPresenter {
    private View view;
    private Context context;
    private int[] posArray = {0,1,1,1,1,1,1};


    public MainActivityPresenter(View view) {
        this.view = view;
        context = (Context) view;
    }

    public void permissionDenied() {
        view.showToast("Permission Denied!");
    }

    public void start() {
        if(view.checkSMSPermission()){
            view.requestPermission();
        }else {
            getAllSms(context);
        }
    }

    public void getAllSms(Context context) {

        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            ArrayList<SMSDataModel> arrayList = new ArrayList<>();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));

                    Date dateFormat= new Date(Long.valueOf(smsDate));
                    Calendar calendar24hrAfter = Calendar.getInstance();
                    calendar24hrAfter.add(Calendar.HOUR,-24);

                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            if(dateFormat.after(calendar24hrAfter.getTime())){
                                SMSDataModel smsDataModel = new SMSDataModel(j,number,body,smsDate);
                                arrayList.add(smsDataModel);
                            }
                            break;
                        default:
                            break;
                    }


                    c.moveToNext();
                }
            }

            c.close();

            filterData(arrayList);
            view.updateData(posArray,arrayList);


        } else {
            view.showToast("No message to show!");
        }
    }

    private void filterData(ArrayList<SMSDataModel> arrayList) {
        sortSMS(arrayList);
        Calendar calendar1hrBefore = Calendar.getInstance();
        Calendar calendar2hrBefore = Calendar.getInstance();
        Calendar calendar3hrBefore = Calendar.getInstance();
        Calendar calendar6hrBefore = Calendar.getInstance();
        Calendar calendar12hrBefore = Calendar.getInstance();
        calendar1hrBefore.add(Calendar.HOUR,-1);
        calendar2hrBefore.add(Calendar.HOUR,-2);
        calendar3hrBefore.add(Calendar.HOUR,-3);
        calendar6hrBefore.add(Calendar.HOUR,-6);
        calendar12hrBefore.add(Calendar.HOUR,-12);
        for(SMSDataModel s :arrayList) {
            Date dateFormat= new Date(Long.valueOf(s.getDate()));
            if(dateFormat.after(calendar1hrBefore.getTime()))
                posArray[1]++;
        }
        posArray[2]+=posArray[1];
        for(SMSDataModel s :arrayList) {
            Date dateFormat= new Date(Long.valueOf(s.getDate()));
            if(dateFormat.after(calendar1hrBefore.getTime()))
                posArray[2]++;
        }
        posArray[3]+=posArray[2];

        for(SMSDataModel s :arrayList) {
            Date dateFormat= new Date(Long.valueOf(s.getDate()));
            if(dateFormat.after(calendar1hrBefore.getTime()))
                posArray[3]++;
        }
        posArray[4]+=posArray[3];
        for(SMSDataModel s :arrayList) {
            Date dateFormat= new Date(Long.valueOf(s.getDate()));
            if(dateFormat.after(calendar1hrBefore.getTime()))
                posArray[4]++;
        }
        posArray[5]+=posArray[4];
        for(SMSDataModel s :arrayList) {
            Date dateFormat= new Date(Long.valueOf(s.getDate()));
            if(dateFormat.after(calendar1hrBefore.getTime()))
                posArray[5]++;
        }
        posArray[5]++;
        posArray[6]+=posArray[5];
        for(SMSDataModel s :arrayList) {
            Date dateFormat= new Date(Long.valueOf(s.getDate()));
            if(dateFormat.after(calendar1hrBefore.getTime()))
                posArray[6]++;
        }

        view.setPosArray(posArray);
    }

    private void sortSMS(ArrayList<SMSDataModel> arrayList) {
        Collections.sort(arrayList, new Comparator<SMSDataModel>() {
            public int compare(SMSDataModel o1, SMSDataModel o2) {
                Date d1 = new Date(Long.valueOf(o1.getDate()));
                Date d2 = new Date(Long.valueOf(o2.getDate()));
                return d2.compareTo(d1);
            }
        });
    }

    public void smsPermissionGranted() {
        getAllSms(context);
    }

    public interface View{

        boolean checkSMSPermission();

        void requestPermission();

        void showToast(String message);

        void updateData(int[] posArray,ArrayList<SMSDataModel> arrayList);

        void setPosArray(int[] posArray);
    }
}
