package com.example.orlik.ui.register;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orlik.R;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = "RegisterViewModelTag";
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository = new RegisterRepository();


    public MutableLiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public MutableLiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password, String name, String surname)
    {
        try {
            registerRepository.register(username,password, name, surname, registerResult);
        }catch (Exception e)
        {
            Log.v(TAG, e.getMessage());
        }

    }

    //TODO kiedy nie ma znalezionych uzytkowników wyswietlic komunikat o braku wynikow =modul wyszukiwania uztkownikow

    public void registerDataChanged(String username, String password, String secPassword, String name, String surname) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null, null, null));
        } else if (!isSecPasswordValid(secPassword, password)) {
            Log.v(TAG, "Sec Password invalid "+password+" "+ secPassword);
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_sec_password, null, null));
        } else if (!isNameValid(name)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.invalid_name, null));
        } else if (!isSurnameValid(surname)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.invalid_surname));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }if(username.length()<3){
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 5;
    }

    private boolean isNameValid(String name)
    {
        if(name==null){
            return false;
        }
        if(name.trim().length() < 3){
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            char bufor = name.charAt(i);
            if(!Character.isLetter(bufor))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isSurnameValid(String surname)
    {
        if(surname==null){
            return false;
        }
        if(surname.trim().length() < 3){
            return false;
        }
        for (int i = 0; i < surname.length(); i++) {
            char bufor = surname.charAt(i);
            if(!Character.isLetter(bufor))
            {
                return false;
            }
        }
        return true;
    }
    private boolean isSecPasswordValid(String secPassword, String password)
    {
        return secPassword.equals(password);
    }
}
