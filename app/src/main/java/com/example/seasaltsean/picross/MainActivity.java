package com.example.seasaltsean.picross;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static final int maxN = 5;
    private ImageView[][] iVCell = new ImageView[maxN][maxN];
    private int[][] currentBoard = new int[maxN][maxN];
    private int[][] boardKey;
    private String[] boards = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fileString;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView myAwesomeTextView = (TextView)findViewById(R.id.textView3);
        //myAwesomeTextView.setText(fileString);
        loadBoards();
        DesignBoard();
    }

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    private void DesignBoard() {
        int sizeOfCells = Math.round(ScreenWidth()/maxN);
        TableLayout.LayoutParams lpRow = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        TableRow.LayoutParams lpCell = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f);
        final TableLayout board = (TableLayout) findViewById(R.id.board);
        final TableLayout leftNums = (TableLayout) findViewById(R.id.leftNums);

        boardKey = stringTo2dArray(boards[0]);
        Log.d("nut", Arrays.deepToString(boardKey));
        for(int i = 0; i < maxN; i++){
            TableRow tableRow = new TableRow(this);
            TableRow textRow = new TableRow(this);
            TextView textValue = new TextView(this);
            textValue.setText("1 2 3 4");
            textValue.setTextSize(15);
            textValue.setGravity(Gravity.CENTER);
            for(int j = 0; j < maxN; j++){
                iVCell[i][j] = new ImageView(this);
                iVCell[i][j].setBackgroundResource(R.drawable.my_button_bg);
                board.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        for (int i = 0; i < board.getChildCount(); i++) {
                            TableRow row = (TableRow) board.getChildAt(i);
                            for (int j = 0; j < row.getChildCount(); j++) {
                                View view = row.getChildAt(j);
                                if (isViewContains(view, (int) event.getRawX(), (int) event.getRawY())) {
                                    view.setBackgroundResource(R.drawable.button_x);
                                    currentBoard[i][j] = 1;
                                    Log.d("nut", Arrays.deepToString(currentBoard));
                                    TextView newtext = findViewById(R.id.textView3);
                                    newtext.setText(i + " " + j);
                                    if(Arrays.deepEquals(currentBoard, boardKey)){
                                        TextView newtext2 = findViewById(R.id.textView4);
                                        newtext2.setText("true");
                                    }

                                }
                            }
                        }
                        return true;
                    }
                });
                tableRow.addView(iVCell[i][j], lpCell);
            }
            board.addView(tableRow, lpRow);
            textRow.addView(textValue, lpCell);
            leftNums.addView(textRow, lpRow);
        }
    }

    private boolean isViewContains(View view, int rx, int ry) {
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        return rx >= x && rx <= x + w && ry >= y && ry <= y + h;
    }

    private void buttonClicked(int row, int col) {
        //iVCell[row][col].setImageDrawable(getResources().getDrawable(R.drawable.xpng));
        iVCell[row][col].setBackgroundResource(R.drawable.button_x);
    }

    private float ScreenWidth() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    private void loadBoards(){
        BufferedReader reader;
        try{
            final InputStream file = getAssets().open("Boards.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            int i = 0;
            String line = reader.readLine();
            while(line != null){
                boards[i] = line;
                i++;
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    private int[][] stringTo2dArray(String inputStr){
        String [] array= inputStr.split(" ");
        int [][] darray = new int[array.length][array[0].length()];
        for(int i = 0; i < array.length; i++){
            String str = array[i];
            for(int j = 0; j < str.length(); j ++){
                darray[i][j] = Character.getNumericValue(str.charAt(j));
            }
        }
        return darray;
    }


    /*
    private void writeToFile() {
        String filename = "myfile";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("myfile");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    */
}