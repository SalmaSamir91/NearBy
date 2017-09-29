package com.example.cognitev.nearby.base;

import java.util.HashMap;

/**
 * Created by Salma on 9/21/2017.
 */

public interface BaseContract {

    interface BaseView {

        void showLoading();

        void showContent();

        void showError(String errorText);

        }

    interface BaseViewModel{

        void cancelAllRequests();

    }

}
