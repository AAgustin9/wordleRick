package com.example.wordlerick.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wordlerick.R
import com.example.wordlerick.ui.screens.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    private val _userProfile = MutableStateFlow(createMockUserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private fun createMockUserProfile(): UserProfile {
        return UserProfile(
            name = "John Foo",
            email = "johnfoo@gmail.com",
            profilePicture = R.drawable.morty,
            bio = "Passionate Android Developer with 5+ years of experience in building beautiful and functional mobile applications. Currently working on exciting projects at TechCorp.",
            location = "San Francisco, CA"
        )
    }
} 