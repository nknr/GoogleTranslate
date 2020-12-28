package com.itdose.googletranslate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.google.cloud.translate.Language;
import com.google.gson.GsonBuilder;
import com.itdose.googletranslate.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String targetLanguage = "en";
    GoogleTranslate googleTranslate;
    SpinnerAdapter languageArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setHandler(new Handler());

        googleTranslate = new GoogleTranslate();
        getSupportedLanguages();
    }

    private void setupSpinner(List<Language> languageList){
        List<Object> objectList = new ArrayList<>(languageList);
        languageArrayAdapter = new SpinnerAdapter(this, android.R.layout.simple_list_item_1, objectList);
        binding.supportedLanguage.setAdapter(languageArrayAdapter);
    }

    private void getSupportedLanguages() {
        googleTranslate.getSupportedLanguages("en")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(languageObserver);

    }


    private final Observer<String> wordObserver = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull String s) {
            binding.convertedWord.setText(s);
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    private final Observer<List<Language>> languageObserver = new Observer<List<Language>>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull List<Language> languages) {
            setupSpinner(languages);
//            languageArrayAdapter.notify();
            System.out.println(new GsonBuilder().create().toJson(languages));
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    public class Handler {

        public void onLanguageItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position) instanceof Language) {
                Language language = (Language) parent.getItemAtPosition(position);
                targetLanguage = language.getCode();
                binding.convertedWord.setTranslationLanguage(targetLanguage);
            }
        }

        public void onTranslate(View view) {
            googleTranslate.translateWord(binding.word.getText().toString(), targetLanguage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(wordObserver);

        }
    }
}