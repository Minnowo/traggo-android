package com.github.traggo.fragment

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.github.traggo.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?,
    ) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val traggoServerPort = findPreference<EditTextPreference>(getString(R.string.key_setting_traggo_server_port_number))
        val traggoServerPasswordStrength =
            findPreference<EditTextPreference>(getString(R.string.key_setting_traggo_server_password_strength))

        traggoServerPort?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }

        traggoServerPasswordStrength?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }
}
