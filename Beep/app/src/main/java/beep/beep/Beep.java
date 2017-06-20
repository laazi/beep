package beep.beep;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Beep extends AppCompatActivity {

    Chronometer chrn;
    Button teraz;
    Boolean premava;
    TextView kokso;
    long potem;
    ToneGenerator toneG;
    long diePauze;
    long siZmacknul;
    TextView macky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beep);

        premava = false;
        diePauze = 10000; //eine minute und halb
        teraz = (Button) findViewById(R.id.teraz);
        chrn = (Chronometer) findViewById(R.id.chrn);
        kokso = (TextView) findViewById(R.id.kokso);
        toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        siZmacknul = 0;
        macky = (TextView) findViewById(R.id.macky);
        teraz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siZmacknul++;
                if (!premava) {
                    chrn.setBase(SystemClock.elapsedRealtime());
                    chrn.start();
                    premava = true;
                }
                potem = SystemClock.elapsedRealtime() + 90000;
                long dif = potem - chrn.getBase();
                String tenText = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(dif),
                        TimeUnit.MILLISECONDS.toMinutes(dif) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(dif)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(dif) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dif)));
                kokso.setText(tenText);
                macky.setText(String.valueOf(siZmacknul));
            }
        });

        chrn.setOnChronometerTickListener(
                new Chronometer.OnChronometerTickListener(){
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        long vRytmuDoby = SystemClock.elapsedRealtime();
                        if(potem<=vRytmuDoby&&!(potem+diePauze<=vRytmuDoby)) {
                            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                            toneG.stopTone();
                        }

                    }}
        );

    }

}
