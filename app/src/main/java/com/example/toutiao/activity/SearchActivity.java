package com.example.toutiao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.toutiao.R;
import com.example.toutiao.ui.searchBar.SearchView;

public class SearchActivity extends AppCompatActivity {

    private Button mBackButton;
    private SearchView mNewsSearchView;
    private NavController mNavController;
    private Activity mActivity;

    /**
     * a method hide the keyboard with InputMethodManager
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * set back button event
     */
    private void setBackButtonOnClick() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavController.getCurrentDestination().getId() == R.id.searchResultFragment) {
                    mNavController.navigate(R.id.action_searchResultFragment_to_searchHotEventFragment);
                } else {
                    Leave();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // setting status bar's color
        Window mWindow = getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(getResources().getColor(R.color.tabbed_bg));

        mBackButton = findViewById(R.id.button_back);
        setBackButtonOnClick();

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_search);

        mActivity = this;

        mNewsSearchView = findViewById(R.id.search_view_news);

        // set EditView search keyBoard event
        mNewsSearchView.mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_GO
                ) {
                    // hide keyboard
                    hideKeyboard(mActivity);
                    // clear focus
                    v.clearFocus();
                    // arguments
                    Bundle bundle = new Bundle();
                    bundle.putString("keyWord", getKeywordFromSearchBar());
                    if (mNavController.getCurrentDestination().getId() == R.id.searchResultFragment) {
                        // move to self
                        mNavController.navigate(R.id.action_searchResultFragment_self, bundle);
                    } else {
                        // move to search result fragment
                        mNavController.navigate(R.id.action_searchHotEventFragment_to_searchResultFragment, bundle);
                    }
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * get the keyword from search bar
     * @return
     */
    public String getKeywordFromSearchBar() {
        return mNewsSearchView.mSearchEditText.getText().toString();
    }

    /**
     * back to MainActivity
     */
    public void Leave() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (mNavController.getCurrentDestination().getId() == R.id.searchResultFragment) {
            mNavController.navigate(R.id.action_searchResultFragment_to_searchHotEventFragment);
        } else if (mNavController.getCurrentDestination().getId() == R.id.searchHotEventFragment){
            Leave();
        }
    }
}