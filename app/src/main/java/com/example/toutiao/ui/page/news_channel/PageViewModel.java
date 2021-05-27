package com.example.toutiao.ui.page.news_channel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
//    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
//        @Override
//        public String apply(Integer input) {
//            return "Hello world from section: " + input;
//        }
//    });

    private MutableLiveData<String> mCategory = new MutableLiveData<>();

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }

    public void setCategory(String category) {
        mCategory.setValue(category);
    }

    public MutableLiveData<String> getCategory() {
        return mCategory;
    }

    public MutableLiveData<Integer> getmIndex() {
        return mIndex;
    }
}
