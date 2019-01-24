package fr.yrich.black_jack2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//envoyer à alexandrerocchi38430@gmail.com

public class gameActivity extends AppCompatActivity {
    private ImageButton piocheBtn;
    private Button couche;
    private TextView miseBourse;
    private SeekBar mise ;
    private TextView mainText;
    private gameActivity activity;



    private ArrayList<String> names;
    private ArrayList<Integer> bourses;
    private LogicGame gameInstance;
    private LogicGame.Joueur currentPlayer;
    private LogicGame.Pioche.Carte pickcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.couche =  findViewById(R.id.coucheBtn);
        this.piocheBtn =  findViewById(R.id.pioche);
        this.miseBourse = findViewById(R.id.text_bourse);
        this.mise =  findViewById(R.id.mis_bar);
        Intent Initgame = getIntent();
        mainText = findViewById(R.id.textMain);
        names = new ArrayList<>();
        bourses = new ArrayList<>();
        names = Initgame.getStringArrayListExtra("names");
        bourses = Initgame.getIntegerArrayListExtra("bourses");
        gameInstance = new LogicGame();
        gameInstance.init(names,bourses);


        currentPlayer = gameInstance.getCurrentPlayer();
        mainText.setText(String.format("joueur : " + currentPlayer.getName() + "  Score :" + currentPlayer.getScore()));
        miseBourse.setText(currentPlayer.getBourse().toString());
        mise.setProgress(currentPlayer.getBourse());
        piocheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.setMise(mise.getProgress());
                gameInstance.setPot(currentPlayer.getMise());
                pickcard = gameInstance.piocheMain.getCardinDeck();
                check_as(pickcard);
                currentPlayer.getMain().add(pickcard);
                Toast.makeText(getApplicationContext(),String.format("Vous avez tiré un "+pickcard.getRate()+" Score :"+currentPlayer.getScore()),Toast.LENGTH_SHORT).show();
                gameInstance.checkScore(currentPlayer);
                if (gameInstance.getJoueurs().size()==0){
                    fin_de_jeu();
                }
                else{
                    gameInstance.increaseIndex();
                    gameInstance.setNbJoueur(gameInstance.getJoueurs().size());
                    currentPlayer = gameInstance.getCurrentPlayer();
                    mainText.setText(String.format("joueur : " + currentPlayer.getName() + "  Score :" + currentPlayer.getScore()));
                    miseBourse.setText(currentPlayer.getBourse().toString());
                    mise.setProgress(currentPlayer.getBourse());
                }

            }
        });
        couche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInstance.getJoueurs().remove(gameInstance.getIndexCurrentPlayer());
                gameInstance.setNbJoueur(gameInstance.getJoueurs().size());
                gameInstance.getStopJoueurs().add(currentPlayer);
                if (gameInstance.getJoueurs().size()==0){
                    fin_de_jeu();
                }
                else{

                    gameInstance.increaseIndex();
                    currentPlayer = gameInstance.getCurrentPlayer();
                    mainText.setText(String.format("joueur : " + currentPlayer.getName() + "  Score :" + currentPlayer.getScore()));
                    miseBourse.setText(currentPlayer.getBourse().toString());
                    mise.setProgress(currentPlayer.getBourse());
                }

            }
        });





    }



    private void fin_de_jeu() {
        final CustomPopup custompopup= new CustomPopup(this);
        custompopup.settitle("Le grand Vainqueur est :"+gameInstance.getGagnant());
        custompopup.setsubtitle("Continuer");
        custompopup.build();
        custompopup.getYesBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameAct = new Intent(getApplicationContext(),gameActivity.class);
                gameAct.putStringArrayListExtra("names",names);

                for (int i=0;i<gameInstance.nbJoueur;i++){
                    Integer bou = gameInstance.getStopJoueurs().get(i).getBourse();
                    bourses.set(i,bou);
                }
                gameAct.putIntegerArrayListExtra("bourses",bourses);
                startActivity(gameAct);
                finish();
            }
        });
        custompopup.getNoBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainAct = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(MainAct);
                finish();
            }
        });
    }

    private void check_as(final LogicGame.Pioche.Carte card){
        if(card.getRate()==-1){
            final CustomPopup popupAs = new CustomPopup(this);
            popupAs.settitle("Tu as tiré un As !!");
            popupAs.setsubtitle("11 ou pas ?");
            popupAs.getYesBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.setRatePopup(11);
                    popupAs.dismiss();
                    Toast.makeText(getApplicationContext(),"Tu as chosis 11 !!",Toast.LENGTH_SHORT).show();
                }
            });
            popupAs.getNoBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.setRatePopup(1);
                    popupAs.dismiss();
                    Toast.makeText(getApplicationContext(),"Tu as chosis 1 !!",Toast.LENGTH_SHORT).show();
                }
            });
            popupAs.build();
        }
    }
}
