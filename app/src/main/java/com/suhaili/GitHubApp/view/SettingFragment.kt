package com.suhaili.GitHubApp.view


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.suhaili.GitHubApp.R
import com.suhaili.GitHubApp.broadcastreceiver.RepeatingAlarm


class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    companion object{
        const val LANGUANGE = "languange"
        const val ABOUT = "about"
        const val REMINDER = "reminder"
    }
    private lateinit var Lang : Preference
    private lateinit var About : Preference
    private lateinit var Reminder : SwitchPreferenceCompat
    private lateinit var AlarmService : RepeatingAlarm

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferencessettings)
        init()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
    private fun init(){
        Lang = findPreference<Preference>(LANGUANGE) as Preference
        About = findPreference<Preference>(ABOUT) as Preference
        Reminder= findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
        AlarmService = RepeatingAlarm()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return when(preference?.key){
            LANGUANGE ->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }
            ABOUT -> {
                startActivity(Intent(activity, AboutActivity::class.java))
                return true
            }
            else ->  super.onPreferenceTreeClick(preferenceScreen)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
      if(key == REMINDER){
          if(Reminder.isChecked){
              AlarmService.repeatingAlarm(requireActivity())
          }else{
              AlarmService.cancelAlarm(requireActivity())
          }
      }
    }







}