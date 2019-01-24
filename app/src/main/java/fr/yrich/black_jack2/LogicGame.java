package fr.yrich.black_jack2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LogicGame {
    public Pioche piocheMain;
    public int nbJoueur;
    public int indexCurrentPlayer = 0;
    public ArrayList<Joueur> joueurs;
    public ArrayList<Joueur> stop_joueurs;
    public int pot;

    public LogicGame(){
        piocheMain = new Pioche();
        joueurs = new ArrayList<>();
        stop_joueurs = new ArrayList<>();
    }

    public Pioche getPioche() { return piocheMain;
    }


    public Joueur getCurrentPlayer(){
        if (this.checkJoueur()){
            indexCurrentPlayer =0;
        }
        return joueurs.get(indexCurrentPlayer);
    }

    public void setPot(int mise){pot+=mise;}

    public boolean checkJoueur(){
        return indexCurrentPlayer >= nbJoueur;
    }

    public void checkScore(Joueur player){
        if(player.getScore()>=21){
            stop_joueurs.add(player);
            for(Joueur playerI : stop_joueurs){
                joueurs.remove(playerI);
            }
        }
        else{
            joueurs.set(indexCurrentPlayer,player);
        }

    }

    public ArrayList<Joueur> getJoueurs(){return joueurs;}

    public ArrayList<Joueur> getStopJoueurs() {return stop_joueurs; }

    public int getIndexCurrentPlayer() { return indexCurrentPlayer; }

    public int getPot(){return pot;}

    public void increaseIndex(){indexCurrentPlayer++;}

    public String getGagnant() {
        int max = 0;
        String gagnant = "";
        for (Joueur player : stop_joueurs) {
            if ((max < player.getScore()) && (player.getScore() <= 21)) {
                max = player.getScore();
            }
        }
        for (Joueur player : stop_joueurs) {
            if (max == player.getScore()) {
                player.bourse += this.getPot();
                gagnant = player.getName();
                break;
            }
            else{
                gagnant = "NONE";
            }
        }
        return gagnant;
    }

    public void setNbJoueur(int nbJoueur) { this.nbJoueur = nbJoueur; }

    public void init(ArrayList<String> names,ArrayList<Integer> bourses) {
        this.setNbJoueur(names.size());
        for(int i=0;i<nbJoueur;i++){
            Joueur player = new Joueur();
            player.setName(names.get(i));
            player.setBourse(bourses.get(i));
            joueurs.add(player);
        }
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    public class Joueur {
        private String name;
        public Integer bourse;
        public ArrayList<LogicGame.Pioche.Carte> Main;
        public int mise;
        private int score;

        //constructeur
        public Joueur() {
            this.Main = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                this.getMain().add(piocheMain.getCardinDeck());
            }
        }

        public void setName(String nom) {
            this.name = nom;
        }


        public void setBourse(Integer bourse) {
            this.bourse = bourse;
        }


        public void setMise(int mise) {
            this.mise = mise;
            this.bourse = this.bourse - this.mise;
        }


        public List<LogicGame.Pioche.Carte> getMain() {
            return this.Main;
        }


        public int getScore() {
            score = 0;
            for (LogicGame.Pioche.Carte card : this.getMain()) {
                score += card.getRate();
            }
            return score;
        }

        public String getName() {
            return name;
        }

        public Integer getBourse() {
            return bourse;
        }

        public int getMise() {
            return mise;
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////
    public class Pioche{


        public ArrayList<Carte> pioche ;

        //constructeur paquet
        public Pioche(){
            this.pioche= new ArrayList<>();
            for (int i = 0; i<4; i++){
                for (int j = 0;j<13; j++){
                    Carte card = new Carte();
                    card.setValues(j);
                    card.setFamily(i);
                    card.setRate();
                    this.pioche.add(card);
                }
            }
            this.shuffle();
        }
        //méthode de pioche
        public Carte getCardinDeck(){
            final Carte cardget = this.pioche.get(0);
            this.pioche.remove(cardget);
            return cardget;
        }

        //mélange
        private void shuffle(){
            Collections.shuffle(pioche);
        }

        //objet carte
        public class Carte{

            private int values;
            private int Family;
            private int rate;
            private int[] card = {Family,values};

            //constructeur
            public Carte(){
            }
            //Accesseurs
            public void setValues(int values){this.values = values; }
            public void setFamily(int family){this.Family = family; }
            public int getValues(){return values; }
            public int getFamily(){return Family; }
            public int[] getCard(){return card; }
            public int getRate(){return rate; }
            public void setRatePopup(int rate){this.rate = rate;}
            public void setRate(){
                if (values>=1 && values<=9){
                    rate = values+1;
                }
                else if (values>=10){
                    rate = 10;
                }
                else{
                    rate = -1;
                }
            }
        }
    }

}
