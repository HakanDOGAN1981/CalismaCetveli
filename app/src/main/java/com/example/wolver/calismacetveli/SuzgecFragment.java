package com.example.wolver.calismacetveli;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class SuzgecFragment extends android.support.v4.app.DialogFragment {

    private Spinner mSpinnerTur, mSpinnerYil, mSpinnerAy;
    private ImageButton mImgKapat;
    private Button mBtnSuz;
    private CheckBox mcheckBox;
    public static String shrTur;
    public static String shrAy;
    public static String shrYil;


    int ay, yil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suzgec, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImgKapat = (ImageButton) view.findViewById(R.id.imageButton);
        mImgKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mBtnSuz = (Button) view.findViewById(R.id.btnSuz);
        mSpinnerTur = (Spinner) view.findViewById(R.id.spnTur);
        mSpinnerYil = (Spinner) view.findViewById(R.id.spnYil);
        mSpinnerAy = (Spinner) view.findViewById(R.id.spnAy);
        mcheckBox = (CheckBox) view.findViewById(R.id.checkBox);


        mcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mcheckBox.isChecked()) {
                    guncelAySetSelection();
                    guncelYilSetSelection();
                } else {
                    mSpinnerAy.setSelection(0);
                    mSpinnerYil.setSelection(0);
                }
            }
        });

        guncelAySetSelection();
        guncelYilSetSelection();

        final int ayPosition = mSpinnerAy.getSelectedItemPosition();
        final int yilPosition = mSpinnerYil.getSelectedItemPosition();

        mSpinnerAy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSpinnerAy.getSelectedItemPosition() == ayPosition && mSpinnerYil.getSelectedItemPosition() == yilPosition) {
                    mcheckBox.setChecked(true);
                } else {
                    mcheckBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerYil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSpinnerYil.getSelectedItemPosition() == yilPosition && mSpinnerAy.getSelectedItemPosition() == ayPosition) {
                    mcheckBox.setChecked(true);
                } else {
                    mcheckBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapterAy = ArrayAdapter.createFromResource(getContext(), R.array.spinner_ay, android.R.layout
                .simple_dropdown_item_1line);
        ArrayAdapter<CharSequence> adapterYil = ArrayAdapter.createFromResource(getContext(), R.array.spinner_yil, android.R
                .layout.simple_dropdown_item_1line);
        ArrayAdapter<CharSequence> adapterTur = ArrayAdapter.createFromResource(getContext(), R.array.spinner_tur, android.R
                .layout.simple_dropdown_item_1line);
        mSpinnerAy.setAdapter(adapterAy);
        mSpinnerYil.setAdapter(adapterYil);
        mSpinnerTur.setAdapter(adapterTur);


        mBtnSuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSpinnerAy.getSelectedItemPosition() == 0 && mSpinnerYil.getSelectedItemPosition() == 0) {
                    suz();
                } else if (mSpinnerYil.getSelectedItemPosition() != 0 && mSpinnerAy.getSelectedItemPosition() != 0) {
                    suz();
                } else
                    Toast.makeText(getContext(), "Ay ve Yıl Değerleri \nDolu veya Boş " +
                            "Olmalıdır!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void suz() {
        Intent Main = new Intent(getContext(), MainActivity.class);
        Main.putExtra("tur", mSpinnerTur.getSelectedItem().toString());
        Main.putExtra("ay", mSpinnerAy.getSelectedItem().toString());
        Main.putExtra("yil", mSpinnerYil.getSelectedItem().toString());
        sharedPrefencesOlustur();
        startActivity(Main);
    }

    public int guncelYilSetSelection() {

        Calendar calendar = Calendar.getInstance();

        ay = calendar.get(Calendar.MONTH) + 1;
        yil = calendar.get(Calendar.YEAR);

        switch (yil) {
            case 2017:
                mSpinnerYil.setSelection(1);
                break;
            case 2018:
                mSpinnerYil.setSelection(2);
                break;
            case 2019:
                mSpinnerYil.setSelection(3);
                break;
            case 2020:
                mSpinnerYil.setSelection(4);
                break;
            default:
                mSpinnerYil.setSelection(0);
        }

        return yil;
    }

    public int guncelAySetSelection() {

        Calendar calendar = Calendar.getInstance();

        ay = calendar.get(Calendar.MONTH) + 1;
        yil = calendar.get(Calendar.YEAR);

        switch (ay) {
            case 1:
                mSpinnerAy.setSelection(1);
                break;
            case 2:
                mSpinnerAy.setSelection(2);
                break;
            case 3:
                mSpinnerAy.setSelection(3);
                break;
            case 4:
                mSpinnerAy.setSelection(4);
                break;
            case 5:
                mSpinnerAy.setSelection(5);
                break;
            case 6:
                mSpinnerAy.setSelection(6);
                break;
            case 7:
                mSpinnerAy.setSelection(7);
                break;
            case 8:
                mSpinnerAy.setSelection(8);
                break;
            case 9:
                mSpinnerAy.setSelection(9);
                break;
            case 10:
                mSpinnerAy.setSelection(10);
                break;
            case 11:
                mSpinnerAy.setSelection(11);
                break;
            case 12:
                mSpinnerAy.setSelection(12);
                break;
            default:
                mSpinnerYil.setSelection(0);
        }

        return ay;
    }

    public void sharedPrefencesOlustur() {

        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        String spnTur = mSpinnerTur.getSelectedItem().toString();
        String spnAy = mSpinnerAy.getSelectedItem().toString();
        String spnYil = mSpinnerYil.getSelectedItem().toString();



        //1. ihtimal Tür boş , ay ve yıla dolu
        if (spnTur.equalsIgnoreCase("Giriş Türünü Seçiniz") && !spnAy.equalsIgnoreCase("Seçiniz")
                && !spnYil.equalsIgnoreCase("Seçiniz")) {
            editor.putInt("secilen", 1);
            editor.putString("shrTur",spnTur);
            editor.putString("shrAy",spnAy);
            editor.putString("shrYil",spnYil);
        }

        //2. ihtimal Tür dolu, ay ve yıl boş
        if (!spnTur.equalsIgnoreCase("Giriş Türünü Seçiniz") && spnAy.equalsIgnoreCase("Seçiniz")
                && spnYil.equalsIgnoreCase("Seçiniz")) {
            editor.putInt("secilen", 2);
            editor.putString("shrTur",spnTur);
            editor.putString("shrAy",spnAy);
            editor.putString("shrYil",spnYil);
        }

        //3. ihtimal üçüde dolu
        if (!spnTur.equalsIgnoreCase("Giriş Türünü Seçiniz") && !spnAy.equalsIgnoreCase("Seçiniz")
                && !spnYil.equalsIgnoreCase("Seçiniz")) {
            editor.putInt("secilen", 3);
            editor.putString("shrTur",spnTur);
            editor.putString("shrAy",spnAy);
            editor.putString("shrYil",spnYil);
        }

        //4. ihtimal üçüde boş
        if (spnTur.equalsIgnoreCase("Giriş Türünü Seçiniz") && spnAy.equalsIgnoreCase("Seçiniz")
                && spnYil.equalsIgnoreCase("Seçiniz")) {
            editor.putInt("secilen", 4);
            editor.putString("shrTur",spnTur);
            editor.putString("shrAy",spnAy);
            editor.putString("shrYil",spnYil);
        }

        editor.apply();


    }
}




