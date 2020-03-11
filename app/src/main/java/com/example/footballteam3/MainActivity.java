package com.example.footballteam3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballteam3.data.GamerAppDataBase;
import com.example.footballteam3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private GamerAppDataBase gamerAppDataBase;
    private ArrayList<Gamer> gamerArrayList = new ArrayList<>();
    private GamerAdapter gamerAdapter;
    private ActivityMainBinding activityMainBinding;
    private ClickFloatingActionButton clickFloatingActionButton;

    private TextView gamerOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickFloatingActionButton = new ClickFloatingActionButton(this);
        activityMainBinding.setButtonHandler(clickFloatingActionButton);

        RecyclerView recyclerView = activityMainBinding.layoutContentMain.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        gamerAdapter = new GamerAdapter(gamerArrayList, MainActivity.this);
        recyclerView.setAdapter(gamerAdapter);

        gamerAppDataBase = Room.databaseBuilder(getApplicationContext(), GamerAppDataBase.class,
                "GamerDB").build();

        loadContacts();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void addAndEditGamer(final  boolean isUpdate, final Gamer gamer, final int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_gamer, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);

        TextView newGamerTitle = view.findViewById(R.id.newGamerTitle);
        final EditText nameGamerEditText = view.findViewById(R.id.nameGamerEditText);
        final EditText skillsGamerEditText = view.findViewById(R.id.skillsGamerEditText);
        gamerOk = view.findViewById(R.id.okGamerTextView);


        newGamerTitle.setText(!isUpdate ? "Add Gamer" : "Edit Gamer");

        if (isUpdate && gamer != null) {
            nameGamerEditText.setText(gamer.getGamerName());
            skillsGamerEditText.setText(gamer.getGamerSkills());
        }

        builder.setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(nameGamerEditText.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(skillsGamerEditText.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Enter Skills", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if (isUpdate && gamer != null) {
                                updateGamer(nameGamerEditText.getText().toString(),
                                        skillsGamerEditText.getText().toString(),
                                        position);
                            } else {
                                createGamer(nameGamerEditText.getText().toString(),
                                        skillsGamerEditText.getText().toString(),
                                        "No");
                            }
                        }
                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Работа с БД, добовление, удаление, редактирование, вывод всех игроков

    private void loadContacts() {

        new GetAllGamerAsyncTask().execute();

    }

    ////////////
    private void createGamer(String gamerName, String gamerSkills, String  gamerOk) {

        new CreateGamerAsyncTask().execute(new Gamer(0, gamerName, gamerSkills, gamerOk));
    }

    ///////////
    private void updateGamer(String gamerName, String gamerSkills, int position) {
        Gamer gamer = gamerArrayList.get(position);

        gamer.setGamerName(gamerName);
        gamer.setGamerSkills(gamerSkills);

        new UpdateGamerAsyncTask().execute(gamer);
        gamerArrayList.set(position, gamer);
    }

    ///////////
    private void deleteGamer() {

        new DeleteGamerAsyncTask().execute();
    }

    ///////////
    //Реакция на смену Ok на No и обратно
    @SuppressLint("ResourceAsColor")
    public void gamerOkAndNo(Gamer gamer, int position) {

        //ArrayList<String> nameGamer = new ArrayList<>();

        if (gamer.getGamerOk().equals("No")) {
            gamer.setGamerOk("Ok");
            gamerArrayList.set(position, gamer);
            new UpdateGamerAsyncTask().execute(gamer);
        } else {
            gamer.setGamerOk("No");
            gamerArrayList.set(position, gamer);
            new UpdateGamerAsyncTask().execute(gamer);
        }
    }

    //////////////////////////////////////////////////////

        public interface OkGamerClickHandler {
        void onNewClick(View view);
        }


    private class GetAllGamerAsyncTask extends AsyncTask<Void, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            gamerArrayList = (ArrayList<Gamer>) gamerAppDataBase.getGamerDAO().getAllGamer();

            //сортировка игроков по их скилам от большего к меньшему
            gamerArrayList.sort(new Comparator<Gamer>() {
                @Override
                public int compare(Gamer o1, Gamer o2) {
                    if (o1.getGamerSkills().equals(o2.getGamerSkills())) {
                        return 0;
                    } else if (Integer.parseInt(o1.getGamerSkills()) < Integer.parseInt(o2.getGamerSkills()) ) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            gamerAdapter.setGamerArrayList(gamerArrayList);
        }
    }

    private class CreateGamerAsyncTask extends AsyncTask<Gamer, Void, Void> {

        @Override
        protected Void doInBackground(Gamer... gamers) {
            gamerAppDataBase.getGamerDAO().addGamer(gamers[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadContacts();
        }
    }



    private class UpdateGamerAsyncTask extends AsyncTask<Gamer, Void, Void> {

        @Override
        protected Void doInBackground(Gamer... gamers) {

            gamerAppDataBase.getGamerDAO().updateGamer(gamers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadContacts();
        }
    }

    private class DeleteGamerAsyncTask extends AsyncTask<Gamer, Void, Void> {

        @Override
        protected Void doInBackground(Gamer... gamers) {

            gamerAppDataBase.getGamerDAO().deleteGamer(gamers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadContacts();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class ClickFloatingActionButton {
        Context context;

        public ClickFloatingActionButton(Context context) {
            this.context = context;
        }

        public void onFloatingActionButton(View view) {
            addAndEditGamer(false, null, -1);
        }
    }

}
