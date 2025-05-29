package com.example.wordlerick.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            // Profile Picture
            Card(
                modifier = Modifier.size(dimensionResource(id = R.dimen.profile_picture_size)),
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = userProfile.profilePicture),
                    contentDescription = stringResource(id = R.string.profile_picture),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_16)))

            // Name
            Text(
                text = "WORK IN PROGRESS-RICK", //userProfile.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Email
            Text(
                text = userProfile.email,
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