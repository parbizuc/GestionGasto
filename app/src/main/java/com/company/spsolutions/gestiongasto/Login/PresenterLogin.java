package com.company.spsolutions.gestiongasto.Login;
/**
 * Created by coralRodriguez on 27/03/19. cambion
 */

public interface PresenterLogin {
    void displayEmailError(String error);
    void displayPasswordError(String error);
    void displaySigninError(String error);
    void displayLoader(boolean loader);
    void successLogin();
}
