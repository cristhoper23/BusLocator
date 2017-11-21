package com.cristhoper.buslocator.fragments;


import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.activities.MainActivity;
import com.cristhoper.buslocator.models.Usuario;
import com.cristhoper.buslocator.services.ApiService;
import com.cristhoper.buslocator.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getSimpleName();

    EditText user, pass;
    Button btnSignIn;
    TextView txtSignUp;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_sign_in, container, false);

        getActivity().setTitle("Iniciar sesión");

        user = vista.findViewById(R.id.etUser);
        pass = vista.findViewById(R.id.etPass);

        btnSignIn = vista.findViewById(R.id.btnSignIn);
        txtSignUp = vista.findViewById(R.id.txtSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String usuario = user.getText().toString();
                String contraseña = pass.getText().toString();

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(getContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                Call<Usuario> call = null;

                // Si no se incluye imagen hacemos un envío POST simple
                call = service.iniciarSesion(usuario, contraseña);

                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        try {

                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if (response.isSuccessful()) {

                                Usuario usuario = response.body();
                                Log.d(TAG, "usuario: " + usuario);

                                int usuario_id = usuario.getId();

                                Intent dashAct = new Intent(getActivity(), MainActivity.class);
                                //dashAct.putExtra("usuario_id", usuario_id);

                                //Si la respuesta es satisfactoria pasamos al dashboard activity
                                startActivity(dashAct);

                                getActivity().finish();
                            } else {
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }

                        } catch (Throwable t) {
                            try {
                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }catch (Throwable x){}
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                    }
                });
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment signUpFragment = new SignUpFragment();
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .replace(R.id.register_activity, signUpFragment)
                                                    .addToBackStack("signUpActivity")
                                                    .commit();
            }
        });
        return vista;
    }
}
