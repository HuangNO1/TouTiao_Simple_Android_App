package com.example.toutiao.ui.page.newsChannel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A [ViewModel] for NewsChannelFragment
 */

public class PageViewModel extends ViewModel {
    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
//    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
//        @Override
//        public String apply(Integer input) {
//            return "Hello world from section: " + input;
//        }
//    });

    private final MutableLiveData<String> mCategory = new MutableLiveData<>();

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

    public MutableLiveData<Integer> getIndex() {
        return mIndex;
    }
}
