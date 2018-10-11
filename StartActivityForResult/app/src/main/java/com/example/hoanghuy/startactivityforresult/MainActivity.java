package com.example.hoanghuy.startactivityforresult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    TextView tvDate, tvTime;
    EditText editCv, editNd;
    Button btnDate, btnTime, btnAdd;
    ArrayList<JobInWeek> arrJob = new ArrayList<JobInWeek>();
    ArrayAdapter<JobInWeek> adapter = null;
    ListView lvCv;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        getDateTime();
        addEvent();
    }

    // Ánh xạ các đên các đối tượng
    public void anhxa() {
        tvDate = findViewById(R.id.tvdate);
        tvTime = findViewById(R.id.tvtime);
        editCv = findViewById(R.id.editcongviec);
        editNd = findViewById(R.id.editnoidung);
        btnDate = findViewById(R.id.btndate);
        btnTime = findViewById(R.id.btntime);
        btnAdd = findViewById(R.id.btncongviec);
        lvCv = findViewById(R.id.lvcongviec);
        adapter = new ArrayAdapter<JobInWeek>
                (this,
                        android.R.layout.simple_list_item_1,
                        arrJob);
        // Gán adapter  với mới nhận ở trên vào ListView
        lvCv.setAdapter(adapter);
    }

    public void getDateTime() {
        //lấy ngày hiện tại của hệ thống
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        //hiển thị lên giao diện
        tvDate.setText(strDate);
        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        tvTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvTime.setTag(dft.format(cal.getTime()));

        editCv.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành

        dateFinish = cal.getTime();
        hourFinish = cal.getTime();
    }
    public void addEvent() {
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        lvCv.setOnItemClickListener(new MyListViewEvent());
        lvCv.setOnItemLongClickListener(new MyListViewEvent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btndate:
                showDatePickerDialog();
                break;
            case R.id.btntime:
                showTimePickerDialog();
                break;
            case R.id.btncongviec:
                AddJob();
                break;
        }
    }
    private class MyListViewEvent implements
            OnItemClickListener,
            OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            //Xóa vị trí thứ arg2
            arrJob.remove(arg2);
            adapter.notifyDataSetChanged();
            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            //Hiển thị nội dung công việc tại vị trí thứ arg2
            Toast.makeText(MainActivity.this,
                    arrJob.get(arg2).getDesciption(),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void showDatePickerDialog() {
        OnDateSetListener callback = new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Cập nhật lại textView Date
                tvDate.setText(
                        (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish = cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = tvDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                MainActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    public void showTimePickerDialog() {
        OnTimeSetListener callback = new OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s = hourOfDay + ":" + minute;
                int hourTam = hourOfDay;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                tvTime.setText
                        (hourTam + ":" + minute + (hourOfDay > 12 ? " PM" : " AM"));
                //lưu giờ thực vào tag
                tvTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish = cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = tvTime.getTag() + "";
        String strArr[] = s.split(":");
        int gio = Integer.parseInt(strArr[0]);
        int phut = Integer.parseInt(strArr[1]);
        TimePickerDialog time = new TimePickerDialog(
                MainActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }

    public void AddJob() {
        String title = editCv.getText() + "";
        String description = editNd.getText() + "";
        JobInWeek job = new JobInWeek(title, description, dateFinish, hourFinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        editCv.setText("");
        editNd.setText("");
        editCv.requestFocus();
    }

}