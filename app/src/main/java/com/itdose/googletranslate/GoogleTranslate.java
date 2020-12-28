package com.itdose.googletranslate;

import androidx.annotation.NonNull;

import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.util.List;
import io.reactivex.Observable;


public class GoogleTranslate {

    Translate translate;

    public GoogleTranslate() {
        TranslateOptions options = TranslateOptions.newBuilder()
                .setApiKey("AIzaSyD32eenLpC2KjdQawheFyJC5dZpRInzKRg")
                .build();
        translate = options.getService();
    }


    public Observable<List<Language>> getSupportedLanguages(@NonNull String targetLanguage){
        return Observable.create(emitter -> {
            try {
                emitter.onNext(translate.listSupportedLanguages(Translate.LanguageListOption.targetLanguage(targetLanguage)));
            }catch (Exception e){
                System.out.println(" lang error "+e);
                emitter.onError(e);
            }
        });
    }

    public Observable<String> translateWord(@NonNull String word,@NonNull String targetLanguage){
        return Observable.create(emitter -> {
            try {
                Translation translation = translate.translate(word, Translate.TranslateOption.targetLanguage(targetLanguage));
                emitter.onNext(translation.getTranslatedText());
            }catch (Exception e){
                System.out.println("word error "+e);
                emitter.onError(e);
            }
        });
    }

}


