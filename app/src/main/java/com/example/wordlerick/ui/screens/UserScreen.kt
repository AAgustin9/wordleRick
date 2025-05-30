package com.example.wordlerick.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlerick.R
import com.example.wordlerick.ui.viewmodels.UserViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.example.wordlerick.security.BiometricAuthManager
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import coil.compose.AsyncImage

@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    val userProfile by viewModel.userProfile.collectAsState()

    // create our biometric auth manager
    val context = LocalContext.current
    val activity = remember { context as FragmentActivity }
    val biometricAuthManager = remember { BiometricAuthManager() }

    // trigger biometric auth on enter
    var isAuthenticated by remember { mutableStateOf(false) }
    var authMessage by remember { mutableStateOf("Authenticating…") }
    LaunchedEffect(Unit) {
        biometricAuthManager.authenticate(
            context = activity,
            onError = { authMessage = "Authentication error" },
            onFail = { authMessage = "Authentication failed" },
            onSuccess = { isAuthenticated = true }
        )
    }

    // --- Google Sign-In setup ---
    var googleAccount by remember { mutableStateOf<GoogleSignInAccount?>(GoogleSignIn.getLastSignedInAccount(context)) }
    val gso by remember {
        mutableStateOf(
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }
    val googleSignInClient by remember { mutableStateOf(GoogleSignIn.getClient(activity, gso)) }
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Exchange ID token for Firebase credential
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity) { authTask ->
                    if (authTask.isSuccessful) {
                        googleAccount = account
                        Toast.makeText(context, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Firebase auth failed: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign in failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }
    // --- end Google Sign-In setup ---

    if (!isAuthenticated) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.spacing_16)),
            contentAlignment = Alignment.Center
        ) {
            Text(authMessage, style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.spacing_16)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture (use Google photo if available)
            Card(
                modifier = Modifier.size(dimensionResource(id = R.dimen.profile_picture_size)),
                shape = CircleShape
            ) {
                // Safely unwrap the Uri for smart-casting
                val photoUri = googleAccount?.photoUrl
                if (photoUri != null) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = userProfile.profilePicture),
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_16)))

            // Name
            Text(
                text = googleAccount?.displayName ?: userProfile.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Email
            Text(
                text = googleAccount?.email ?: userProfile.email,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_24)))

            // Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = userProfile.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_24)))

            // Bio
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.card_padding))
                ) {
                    Text(
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8)))
                    Text(
                        text = userProfile.bio,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_24)))
            if (googleAccount == null) {
                Button(onClick = { googleLauncher.launch(googleSignInClient.signInIntent) }) {
                    Text("Sign in with Google")
                }
            } else {
                Text(
                    text = "Welcome, ${googleAccount?.displayName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_16)))
                Button(onClick = {
                    // Sign out of Google and Firebase
                    googleSignInClient.signOut()
                    FirebaseAuth.getInstance().signOut()
                    googleAccount = null
                    Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Log out")
                }
            }
        }
    }
}

data class UserProfile(
    val name: String,
    val email: String,
    val profilePicture: Int,
    val bio: String,
    val location: String = "Unknown",
)

@Preview(showBackground = true)
@Composable
fun UserProfilePagePreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            UserScreen()
        }
    }
}