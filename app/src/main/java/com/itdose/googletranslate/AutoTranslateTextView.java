package com.itdose.googletranslate;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AutoTranslateTextView extends AppCompatTextView {

    private String defaultTranslationLanguage = "en";
    private GoogleTranslate googleTranslate;

    public AutoTranslateTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public AutoTranslateTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoTranslateTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        googleTranslate = new GoogleTranslate();
    }

    public void setTranslationLanguage(String translationLanguage) {
        if (!getDefaultTranslationLanguage().equals(translationLanguage)) {
            this.defaultTranslationLanguage = translationLanguage;

            /**
             *  text translation only happen if text not empty and internet available
             */
            if (!getText().toString().isEmpty())
                onTranslate();
        }
    }



    public String getDefaultTranslationLanguage() {
        return defaultTranslationLanguage;
    }

    public void onTranslate() {
        googleTranslate.translateWord(getText().toString(), defaultTranslationLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wordObserver);

    }

    private final Observer<String> wordObserver = new Observer<String>() {
        @Override
        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

        }

        @Override
        public void onNext(@io.reactivex.annotations.NonNull String s) {
            setText(s);
        }

        @Override
        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

}
