package app.jimmy.vocalsms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private final static int SMS_PERMISSION_CODE =10001;

    private SMSRCViewAdapter mSMSRCVAdapter;
    private ArrayList<SMSDataModel> feedsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.smsList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mSMSRCVAdapter = new SMSRCViewAdapter();
        mSMSRCVAdapter.updateList(feedsList);
        recyclerView.setAdapter(mSMSRCVAdapter);

        presenter = new MainActivityPresenter(this);

        presenter.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.smsPermissionGranted();
            } else {
                presenter.permissionDenied();
            }
        }
    }

    @Override
    public boolean checkSMSPermission() {
        return checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateData(int[] posArray, ArrayList<SMSDataModel> arrayList) {
        for (int i=0;i<posArray.length;i++) {
            switch (i) {
                case 0:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("0 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("0 hr ago"));
                    break;
                case 1:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("1 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("1 hr ago"));
                    break;
                case 2:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("2 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("2 hr ago"));
                    break;
                case 3:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("3 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("3 hr ago"));
                    break;
                case 4:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("6 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("6 hr ago"));
                    break;
                case 5:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("12 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("12 hr ago"));
                    break;
                case 6:
                    if(arrayList.size()>=posArray[i])
                        arrayList.add(posArray[i], new SMSDataModel("24 hr ago"));
                    else
                        arrayList.add(new SMSDataModel("24 hr ago"));
                    break;
            }
        }
        mSMSRCVAdapter.updateList(arrayList);
    }

    @Override
    public void setPosArray(int[] posArray) {
        mSMSRCVAdapter.setPosArray(posArray);
    }
}
