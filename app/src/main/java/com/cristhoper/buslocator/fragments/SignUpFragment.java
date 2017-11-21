package com.cristhoper.buslocator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.services.ApiService;
import com.cristhoper.buslocator.services.ApiServiceGenerator;
import com.cristhoper.buslocator.services.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private static final String TAG = SignUpFragment.class.getSimpleName();

    Button btnRegistarCuenta;
    EditText etUsuario, etEmail, etContraseña, etNombre;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_sign_up, container, false);

        getActivity().setTitle("Regístrate");

        etUsuario = vista.findViewById(R.id.et_signUp_user);
        etEmail = vista.findViewById(R.id.et_signUp_email);
        etContraseña = vista.findViewById(R.id.et_signUp_pass);
        etNombre = vista.findViewById(R.id.et_signUp_fullname);

        btnRegistarCuenta = vista.findViewById(R.id.btnSignUp);

        //Cuando se pulsa el botón Registrar ---------------------
        btnRegistarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCuenta();
            }
        });
        //----------------------------------------------------------

        return vista;
    }

    public void registrarCuenta(){
        String usuario = etUsuario.getText().toString();
        String email = etEmail.getText().toString();
        String contraseña = etContraseña.getText().toString();
        String nombre = etNombre.getText().toString();

        if (usuario.isEmpty() || email.isEmpty() || contraseña.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(getContext(), "Completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = null;

        // Si no se incluye imagen hacemos un envío POST simple
        call = service.registrarUsuario(usuario, contraseña, email, nombre);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);

                        Toast.makeText(getActivity(), "Cuenta registrada exitosamente", Toast.LENGTH_LONG).show();

                        Fragment signInFragment = new SignInFragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.popBackStackImmediate();
                        //getActivity().finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
