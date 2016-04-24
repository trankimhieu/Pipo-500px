package px500.pipoask.com.module.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import px500.pipoask.com.R;
import px500.pipoask.com.module.base.BaseActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements ILoginView {


    private static final String TAG = "LoginActivity";
    // UI Binding
    @Bind(R.id.email)
    AutoCompleteTextView mEmailView;
    @Bind(R.id.password)
    TextView mPasswordView;
    @Bind(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    @Inject
    LoginPresenter mLoginPresenter;
    // UI references.
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.attachView(this);
        mLoginPresenter.checkToken();
        // Set up the login form.
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                login();
                return true;
            }
            return false;
        });

        mEmailSignInButton.setOnClickListener(view -> login());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.login));

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }


    @Override
    public void showLoadingData() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingData() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showError(String e) {
        runOnUiThread(() -> {
            // TODO: Build dialog manager
            new MaterialDialog.Builder(LoginActivity.this)
                    .title(getString(R.string.error_login_title))
                    .content(getString(R.string.error_login_content))
                    .positiveText(getString(R.string.dialog_button_ok)).onPositive((dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public void login() {
        showLoadingData();
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            email = mEmailView.getText().toString();
            password = mPasswordView.getText().toString();
            mLoginPresenter.login(email, password);
        }
    }
}

