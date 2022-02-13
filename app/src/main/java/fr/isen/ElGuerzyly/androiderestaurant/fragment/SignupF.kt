package fr.isen.ElGuerzyly.androiderestaurant.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.ElGuerzyly.androiderestaurant.LoginActivity
import fr.isen.ElGuerzyly.androiderestaurant.R
import fr.isen.ElGuerzyly.androiderestaurant.databinding.FragmentSignUpBinding
import fr.isen.ElGuerzyly.androiderestaurant.model.Signup
import org.json.JSONObject

class SignupF : Fragment() {
    private lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupToLogin.setOnClickListener {
            (activity as? LoginActivity)?.signupToLogin()
        }

        binding.signupButton.setOnClickListener {
            val signUpData = Signup(
                binding.signupFirstnameEditText.text.toString(),
                binding.signupLastnameEditText.text.toString(),
                binding.signupAddressEditText.text.toString(),
                binding.signupEmailEditText.text.toString(),
                binding.signupPasswordEditText.text.toString()
            )

            if (signUpData.firstname != "" && signUpData.lastname != "" && signUpData.address != "") {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(signUpData.email).matches()) {
                    if (signUpData.password.length > 8) {
                        signUp(signUpData)
                    } else {
                        Toast.makeText(context, getString(R.string.signupErrorPassword), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, getString(R.string.signupErrorEmail), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.signupErrorOther), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(signUpData : Signup) {
        val url = "http://test.api.catering.bluecodegames.com/user/register"

        val params = HashMap<String, Any>()
        params["id_shop"] = 1
        params["firstname"] = signUpData.firstname
        params["lastname"] = signUpData.lastname
        params["address"] = signUpData.address
        params["email"] = signUpData.email
        params["password"] = signUpData.password
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            {
                Toast.makeText(context, getString(R.string.successSignup), Toast.LENGTH_SHORT).show()
                (activity as? LoginActivity)?.signupToLogin()
            }, {
                Log.e("API", it.toString())
                Toast.makeText(context, getString(R.string.APIfailure), Toast.LENGTH_SHORT).show()
            }
        )

        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)

        Volley.newRequestQueue(context).add(request)
    }
}