package fr.yrich.black_jack2;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

public class CustomPopup extends Dialog {

    private String title;
    private String subtitle;
    private Button YesBtn,NoBtn;
    private TextView titleView,subtitleView;

    //Constructeur
    public CustomPopup(Activity activity)
    {
        super (activity,R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.pop_up);
        this.title = "Mon titre";
        this.subtitle = "Mon sous-titre";
        this.YesBtn=findViewById(R.id.yesBtn);
        this.NoBtn=findViewById(R.id.noBtn);
        this.titleView=findViewById(R.id.Tilte);
        this.subtitleView=findViewById(R.id.Subtitle);
    }

    public void settitle(String title){this.title = title;}

    public void setsubtitle(String subtitle){this.subtitle= subtitle;}

    public Button getYesBtn(){return YesBtn;}

    public Button getNoBtn(){return NoBtn;}

    public void build(){
        show();
        titleView.setText(title);
        subtitleView.setText(subtitle);
    }

}
