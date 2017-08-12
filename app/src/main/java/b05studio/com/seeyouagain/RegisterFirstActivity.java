package b05studio.com.seeyouagain;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import android.app.DatePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFirstActivity extends AppCompatActivity {

    @BindView(R.id.registerNameEditText)
    EditText registerNameEditText;

    @BindView(R.id.registerGenderEditText)
    EditText registerGenderEditText;

    @BindView(R.id.registerAgeEditText)
    EditText registerAgeEditText;

    @BindView(R.id.registerDateEditText)
    EditText registerDateEditText;

    @OnClick(R.id.registerBackButton)
    public void onBackButtonClick(View view) {
        finish();
    }

    @OnClick(R.id.registerNextButton)
    public void onNextClick(View view) {
        String name = registerNameEditText.getText().toString();
        String gender = registerGenderEditText.getText().toString();
        String age = registerAgeEditText.getText().toString();
        String dateString = registerDateEditText.getText().toString();

        if (name.length() > 0 && gender.length() > 0 && age.length() > 0 && dateString.length() > 0) {
            RegisterActivity.info.setName(name);
            RegisterActivity.info.setGender(gender);
            RegisterActivity.info.setAge(Integer.parseInt(age));
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);
            try {
                cal.setTime(sdf.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            RegisterActivity.info.setTimeOfMissing(cal.getTimeInMillis());
            Intent intent = new Intent(this,RegisterSecondActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "아직 입력하지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.registerDateEditText)
    public void openDatePickerDiaglog(View view) {
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(RegisterFirstActivity.this, dateSetListener, year, month, day).show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = String.format("%d년 %d월 %d일", year, monthOfYear + 1, dayOfMonth);
            registerDateEditText.setText(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        ButterKnife.bind(this);
    }
}
