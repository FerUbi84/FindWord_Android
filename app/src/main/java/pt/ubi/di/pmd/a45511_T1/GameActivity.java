package pt.ubi.di.pmd.a45511_T1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {
    LinearLayout linearWords;
    LinearLayout part_1;
    LinearLayout part_2;
    LinearLayout linear_guess_a;
    LinearLayout linear_guess_b;
    TextView checkSentence;
    TextView tryGuess;
    String theQuote = "";
    char[] abc = { 'a' ,'b' ,'c' ,'d' ,'e' ,'f' ,'g' ,'h' ,'i' ,'j' ,'k' ,'l' ,'m' ,'n' ,'o' ,'p' ,'q' ,'r' ,'s' ,'t' ,'u' ,'v' ,'w' ,'x' ,'y' ,'z'};
    String[][] movieQutoes ={
            {"damn","Frankly, my dear, I don't give a damn","Gone with the Wind"},
            {"offer","I'm gonna make him an offer he can't refuse.","The Godfather"},
            {"toto","Toto, I've a feeling we're not in Kansas anymore.","The Wizard of Oz"},
            {"force","May the Force be with you.","Star Wars"},
            {"seatbelts","Fasten your seatbelts. It's going to be a bumpy night.","All About Eve"},
            {"talking","You talking to me?","Taxi Driver"},
            {"napalm","I love the smell of napalm in the morning.","Apocalypse Now"},
            {"phone","E.T. phone home.","E.T.: The Extra-Terrestrial"},
            {"chianti","A census taker once tried to test me. I ate his liver with some fava beans and a nice Chianti.","The Silence of the Lambs"},
            {"truth","You can't handle the truth!","Gone with the Wind"},
            {"back","I'll be back.","The Terminator"},
            {"chocolates","My mama always said life was like a box of chocolates. You never know what you're gonna get.","Forrest Gump"},
            {"people","I see dead people.","The Sixth Sense"},
            {"alive","It's alive! It's alive!","Frankenstein"},
            {"lucky","You've got to ask yourself one question: 'Do I feel lucky?' Well, do ya, punk?","Dirty Harry"},
            {"mother","A boy's best friend is his mother.","Psycho"},
            {"enemies","Keep your friends close, but your enemies closer.","The Godfather Part II"},
            {"stinking","Take your stinking paws off me, you damned dirty ape.","Planet of the Apes"},
            {"hasta","Hasta la vista, baby.","Terminator 2: Judgment Day"},
            {"children","Listen to them. Children of the night. What music they make.","Dracula"},
            {"beauty","Oh, no, it wasn't the airplanes. It was Beauty killed the Beast.","King Kong"},
            {"martini","A martini. Shaken, not stirred.","Goldfinger"},
            {"stranger","I believe whatever doesn’t kill you, simply makes you…stranger.","The Dark Knight"},
            {"violent","Ideals are peaceful. History is violent.","Fury"},
            {"eternity","What we do in life echoes in eternity.","Gladiator"},
            {"quarter","I live my life a quarter mile at a time.","The Fast and the Furious"},
            {"ahead","Go ahead: Make my day.","Sudden Impact"},
            {"johnny","Here’s Johnny!","The Shining"},
            {"bigger","You’re gonna need a bigger boat.","Jaws"},
            {"humanity","Humanity’s last hope isn’t human.","Chappie"},
            {"welcome","Just because you’re invited, doesn’t mean you’re welcome.","Get Out"}
    };
    String[] theGuess={"Neo", "Corleone", "Jack Torrance", "Pennywise", "Norman Bates", "Luke", "Apollo", "Rohan"};
    String codedSentence = "";
    Vector<String> shuffledLetters = new Vector<>();
    Vector<String> guessQuotes = new Vector<>();
    Vector<EditText> editTextList = new Vector<>();
    ArrayList<String[]> movieQuotesList = new ArrayList<String[]>();
    QuotesDatabase database;
    int marginLeft = 12;
    Dialog dialog;
    AlertDialog.Builder nextQuote;
    AlertDialog.Builder wrongQuote;
    AlertDialog.Builder guessScene;
    AlertDialog next;
    String[] choosedQuote= new String[]{""};
    String movieSentece, movieSentececoded, movieTitle, guessQuote = "";
    Button btn_next, btn_wrong,btn_guess_quote,btn_not_now;
    int choosed_quote, guessQuoteInt;
    int discoverIndex = -1;
    String guessQuoteLower = "";
    ArrayList<String> secretIndex = new ArrayList<String>();
    ArrayList<String> part_1_text = new ArrayList<String>();
    ArrayList<String> part_1_view = new ArrayList<String>();
    ArrayList<String> part_2_text = new ArrayList<String>();
    ArrayList<String> part_2_view = new ArrayList<String>();
    ArrayList<TextView> list_t = new ArrayList<TextView>();
    Vector<EditText> guessList = new Vector<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tryGuess = findViewById(R.id.tryGuess);
        linearWords = findViewById(R.id.linearWords);
        checkSentence = findViewById(R.id.checkSentence);
        part_1 = findViewById(R.id.part_1);
        part_2 = findViewById(R.id.part_2);
        database = new QuotesDatabase(GameActivity.this);

/*
        for (String guess: theGuess) {
            database.addGuess(guess);
        }
        for (String [] qoutes: movieQutoes) {
            database.addQuotes(qoutes[0],qoutes[1],qoutes[2]);
        }
        */
        loadInfo();
        loadGuess();

/*
        for (String[] q:movieQuotesList) {
            System.out.println(Arrays.toString(q));
        }
*/

        choosed_quote = (int) (Math.random() * movieQuotesList.size());
        choosedQuote = movieQuotesList.get(choosed_quote);
        System.out.println(Arrays.toString(new String[]{choosedQuote[2]}));
        guessQuoteInt = (int) (Math.random() * guessQuotes.size());
        guessQuote = guessQuotes.get(guessQuoteInt);
        theQuote = choosedQuote[0];
        movieSentece = choosedQuote[1];
        movieTitle = choosedQuote[2];
        movieSentececoded = movieSentece.replace(theQuote,codedSentence);
        movieQuotesList.remove(choosed_quote);

        for(char l : abc){
            shuffledLetters.add(String.valueOf(l));
        }

        Collections.shuffle(shuffledLetters);
        System.out.println(shuffledLetters);
        guessQuoteLower = guessQuote.toLowerCase(Locale.ROOT);

        for(char t : guessQuoteLower.toCharArray()){
            int pos = shuffledLetters.indexOf(String.valueOf(t));
            secretIndex.add(String.valueOf(pos));
            if (pos == -1){
                codedSentence+= "\n\n";
            }
            else{
                codedSentence+= String.valueOf(pos) + " ";
            }
        }

        createText(secretIndex);

        if (part_1_text.size() == 0) {
            createTextView(part_2_text, part_1);
        } else if(part_1_text.size() != 0 && part_2_text.size() != 0){
            createTextView(part_1_text,part_1);
            createTextView(part_2_text,part_2);
        }

        quoteEditText();
    }
    public void discoverMessage(View view){

        String guessMessage = "";
        dialog = new Dialog(this);
        for (EditText text: editTextList) {
            if (TextUtils.isEmpty(text.getText().toString())){
                dialog.setContentView(R.layout.pop_miss);
                dialog.show();
                break;
            }else {
                guessMessage+=text.getText();
            }
        }
        if (guessMessage.length() == theQuote.length()){
            if (guessMessage.equals(theQuote)){
                discoverGuess(list_t);
                nextWord();
            }else{
                wrong_quote();
            }
        }
    }

    public ArrayList loadInfo(){

        Cursor cursor = database.selectAll();
        if (cursor.moveToFirst()){
            do{
                String[] info = new String[] {cursor.getString(1),cursor.getString(2),cursor.getString(3)};
                movieQuotesList.add(info);
            }while (cursor.moveToNext());
        }
        return  movieQuotesList;
    }

    public Vector<String> loadGuess(){

        Cursor cursor = database.selectAllGuess();
        if (cursor.moveToFirst()){
            do{
                guessQuotes.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return  guessQuotes;
    }
    public void movieHint(View view){

    AlertDialog quoteHint = new AlertDialog.Builder(GameActivity.this)
            .setCancelable(false)
            .setTitle("Quote Hint")
            .setMessage(movieSentece + "\n\n" + movieTitle)
            .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).create();
            quoteHint.show();
    }

    public void nextWord(){

        nextQuote = new AlertDialog.Builder(this);
        final View nextWord = getLayoutInflater().inflate(R.layout.next_word, null);
        nextQuote.setView(nextWord);
        next = nextQuote.create();
        next.show();
        btn_next = (Button) nextWord.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(view -> {
            discoverIndex++;
            int move_quote = (int) (Math.random() * movieQuotesList.size());
            choosedQuote = movieQuotesList.get(move_quote);
            theQuote = choosedQuote[0];
            System.out.println(theQuote);
            movieSentece = choosedQuote[1];
            movieTitle = choosedQuote[2];
            editTextList.removeAll(editTextList);
            linearWords.removeAllViews();
            quoteEditText();
            next.dismiss();
        });
    }

    public void  wrong_quote(){

        wrongQuote = new AlertDialog.Builder(this);
        final View closeQuote = getLayoutInflater().inflate(R.layout.wrong_quote,null);
        wrongQuote.setView(closeQuote);
        next = wrongQuote.create();
        next.show();
        btn_wrong = (Button) closeQuote.findViewById(R.id.bnt_close);
        btn_wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.dismiss();
            }
        });
    }

    public void createText(ArrayList<String> message){

        int spaceIndex = -1;
        spaceIndex = message.indexOf("-1");
        for (int i = 0; i < message.size(); i++) {
            if (i < spaceIndex){
                part_1_text.add(message.get(i));
            }else if(i > spaceIndex){
                part_2_text.add(message.get(i));
            }
        }
    }

    public void createTextView(ArrayList<String> message, LinearLayout l_out){

        for (int i = 0; i < message.size(); i++) {
            TextView letter = new TextView(this);
            letter.setText(message.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginStart(marginLeft);
            letter.setLayoutParams(params);
            letter.setId(i);
            letter.setTextSize(24);
            letter.setGravity(Gravity.CENTER_HORIZONTAL);
            l_out.addView(letter);
            list_t.add(letter);
        }
    }

    public void discoverGuess(ArrayList<TextView> text_v){
        ArrayList<String> quoteList = new ArrayList<>();
            for (char q:theQuote.toCharArray()) {
                quoteList.add(String.valueOf(q));
            }
            System.out.println(guessQuoteLower);
            int random_index = (int) (Math.random() * (guessQuoteLower.length()));
            String random_letter = String.valueOf(guessQuoteLower.charAt(random_index));
            int right = 0;
            System.out.println(quoteList);
            for (String q: quoteList) {
                String q_pos = String.valueOf(shuffledLetters.indexOf(q));
                for (TextView text:text_v) {
                    String text_pos = (String) text.getText();
                    if (q_pos.equals(text_pos)){
                        text.setText(q);
                        right+=1;
                    }
                }
            }
            if (right == 0){
              String q_pos = String.valueOf(shuffledLetters.indexOf(random_letter));
                for (TextView text:text_v) {
                    String text_pos = (String) text.getText();
                    if (q_pos.equals(text_pos)){
                        text.setText(random_letter);
                    }
                }
            }
        }

    public void divideViews(){

        int limit = part_2_text.size();
        for (int i = 0; i < list_t.size(); i++) {
            if (i <= limit){
                part_1_view.add(String.valueOf(list_t.get(i).getText()));
            }else{
                part_2_view.add(String.valueOf(list_t.get(i).getText()));
            }
        }
    }

    public void createEditGuess(ArrayList<String> message, LinearLayout l_out){

        for (int i = 0; i < message.size(); i++) {
            EditText letter = new EditText(GameActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMarginStart(marginLeft);
            params.width = 90;
            letter.setLayoutParams(params);
            letter.setId(i);
            letter.setText(message.get(i));
            letter.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(1)});
            letter.setGravity(Gravity.CENTER_HORIZONTAL);
            l_out.addView(letter);
            guessList.add(letter);
        }
    }

    @SuppressLint("MissingInflatedId")
    public void guessScene(View view){
      divideViews();

      guessScene = new AlertDialog.Builder(this);
      final View guessLay = getLayoutInflater().inflate(R.layout.guess,null);
      guessScene.setView(guessLay);
      next = guessScene.create();
      linear_guess_a = guessLay.findViewById(R.id.linear_guess_1);
      linear_guess_b = guessLay.findViewById(R.id.linear_guess_2);
      btn_guess_quote = guessLay.findViewById(R.id.btn_guess_quote);
      btn_not_now = guessLay.findViewById(R.id.btn_not_now);
      next.show();
      if (part_2_view.size() == 0) {
          createEditGuess(part_1_view,linear_guess_a);
      } else if(part_1_view.size() != 0 && part_2_view.size() != 0){
          createEditGuess(part_1_view,linear_guess_a);
          createEditGuess(part_2_view,linear_guess_b);
      }
      part_1_view.clear();
      part_2_view.clear();
      btn_guess_quote.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              checkGuess();
          }
      });
        btn_not_now.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              next.dismiss();
          }
        });
    }

    public void quoteEditText(){

        for (int i = 0; i < theQuote.length(); i++) {
            EditText letter = new EditText(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMarginStart(marginLeft);
            params.width = 100;
            letter.setLayoutParams(params);
            letter.setId(i);
            letter.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(1)});
            letter.setGravity(Gravity.CENTER_HORIZONTAL);
            linearWords.addView(letter);
            editTextList.add(letter);
        }
    }

    public void checkGuess(){

        String guessMessage = "";
        dialog = new Dialog(this);
        for (EditText text: guessList) {
            if (TextUtils.isEmpty(text.getText().toString())){
                dialog.setContentView(R.layout.pop_miss);
                dialog.show();
                break;
            }else {
                guessMessage+=text.getText();
            }
        }
        if (guessMessage.length() == guessQuoteLower.length()){
            if (guessMessage.equals(guessQuoteLower)){
                nextSecret();
            }else{
                wrong_quote();
            }
        }
    }

    public void nextSecret(){

        nextQuote = new AlertDialog.Builder(this);
        final View nextWord = getLayoutInflater().inflate(R.layout.next_word, null);
        nextQuote.setView(nextWord);
        next = nextQuote.create();
        next.show();
        btn_next = (Button) nextWord.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(view -> {
            int move_quote = (int) (Math.random() * guessQuotes.size());
            guessQuote = guessQuotes.get(move_quote);
            list_t.removeAll(list_t);
            part_1.removeAllViews();
            part_2.removeAllViews();
            loadGuess();
            next.dismiss();
        });
    }
}