package com.example.travel_journal.ui.share;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("If you like my application, I would be honored if you deemed it relevant enough to let your followers know about it.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
